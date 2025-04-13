import { Request, Response, NextFunction } from 'express';
import { z } from 'zod';
import prisma from '../config/prisma';
import { AppError } from '../lib/AppError';
import { sortChatMessages } from '../lib/chatHelpers';
import { pay } from '../lib/paymentHelpers';

export const createChat = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const bodySchema = z.object({
    hostId: z.number(),
    visitorId: z.number(),
  });

  const { hostId, visitorId } = bodySchema.parse(req.body);

  const existingChat = await prisma.chat.findFirst({
    where: {
      hostId,
      visitorId,
    },
  });
  if (existingChat) {
    console.log(
      'tried to create a new chat, but it already exists',
      existingChat,
    );
    res.json({ id: existingChat.id });
    return;
  }

  const chat = await prisma.chat.create({
    data: {
      hostId,
      visitorId,
    },
    include: {
      messages: true,
    },
  });
  console.log('created chat', chat);

  res.json({ id: chat.id });
};

export const getChats = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const querySchema = z.object({
    hostId: z.coerce.number().optional(),
    visitorId: z.coerce.number().optional(),
  });

  const { hostId, visitorId: ownerId } = querySchema.parse(req.query);

  const chats = await prisma.chat.findMany({
    where: {
      ...(hostId && { hostId }),
      ...(ownerId && { visitorId: ownerId }),
    },
    include: {
      messages: true,
      host: {
        select: {
          id: true,
          firstName: true,
          profilePictureFilename: true,
        },
      },
      visitor: {
        select: {
          id: true,
          firstName: true,
          profilePictureFilename: true,
        },
      },
    },
  });

  res.json(chats.map(sortChatMessages));
};

export const getChatById = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const id = z.coerce.number().parse(req.params.id);

  const chat = await prisma.chat.findUnique({
    where: { id },
    include: {
      messages: true,
      host: {
        select: {
          id: true,
          firstName: true,
          profilePictureFilename: true,
        },
      },
      visitor: {
        select: {
          id: true,
          firstName: true,
          profilePictureFilename: true,
        },
      },
    },
  });

  if (!chat) {
    throw new AppError('Chat not found', 'chat-not-found', 404);
  }

  // console.log('resturing chat', chat);

  res.json(sortChatMessages(chat));
};

export const createMessage = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const bodySchema = z.object({
    content: z.string().default(''),
    userId: z.number(),
    type: z.string().optional(),
  });

  const chatId = z.coerce.number().parse(req.params.id);
  const { content, userId, type } = bodySchema.parse(req.body);

  const chat = await prisma.chat.findFirst({
    where: {
      id: chatId,
    },
  });

  if (!chat) {
    throw new AppError('Chat not found', 'chat-not-found', 404);
  }

  if (chat.hostId !== userId && chat.visitorId !== userId) {
    throw new AppError('User is not part of the chat', 'invalid-user-id', 400);
  }

  const isHostMessage = chat.hostId === userId;

  if (type && type != 'text' && !isHostMessage) {
    throw new AppError(
      'Only host can send this type of message',
      'invalid-message-type',
      400,
    );
  }

  if (type && type != 'text') {
    await prisma.chatMessage.updateMany({
      where: {
        chatId,
        type,
      },
      data: {
        status: 'canceled',
      },
    });
  }

  const message = await prisma.chatMessage.create({
    data: {
      content,
      chatId,
      isHostMessage,
      type,
    },
  });
  console.log('created message', message);

  res.json(message);
};

export const updateMessageStatus = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const paramsSchema = z.object({
    messageId: z.coerce.number(),
  });
  const { messageId } = paramsSchema.parse(req.params);

  const bodySchema = z.object({
    status: z.string(),
  });

  const { status } = bodySchema.parse(req.body);

  const message = await prisma.chatMessage.findFirst({
    where: {
      id: messageId,
    },
  });
  if (!message) {
    throw new AppError('Message not found', 'message-not-found', 404);
  }

  const chat = await prisma.chat.findFirst({
    where: {
      id: message.chatId,
    },
  });
  if (!chat) {
    throw new AppError('Chat not found', 'chat-not-found', 404);
  }

  const updatedMessage = await prisma.chatMessage.update({
    where: {
      id: messageId,
    },
    data: {
      status,
    },
  });

  if (status === 'confirmed' && message.type === 'meeting') {
    await prisma.chat.update({
      where: {
        id: message.chatId,
      },
      data: {
        isVisitConfirmed: true,
      },
    });

    await pay(chat.visitorId, chat.hostId, chat.cost);
  }
  if (status === 'confirmed' && message.type === 'cost') {
    await prisma.chat.update({
      where: {
        id: message.chatId,
      },
      data: {
        cost: parseInt(message.content, 10),
        isCostConfirmed: true,
      },
    });
  }

  res.json(updatedMessage);
};

export const commentMeeting = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const chatId = z.coerce.number().parse(req.params.id);

  const bodySchema = z.object({
    content: z.string(),
    rating: z.number().int().min(1).max(5),
    userId: z.number(),
  });

  const { content, rating, userId } = bodySchema.parse(req.body);

  const chat = await prisma.chat.findFirst({
    where: {
      id: chatId,
    },
  });

  if (!chat) {
    throw new AppError('Chat not found', 'chat-not-found', 404);
  }

  if (chat.hostId !== userId && chat.visitorId !== userId) {
    throw new AppError('User is not part of the chat', 'invalid-user-id', 400);
  }

  const isHost = chat.hostId === userId;
  await prisma.chat.update({
    where: {
      id: chatId,
    },
    data: {
      ...(isHost && { hasHostCommented: true }),
      ...(!isHost && { hasVisitorCommented: true }),
    },
  });

  const commentedUserId = isHost ? chat.visitorId : chat.hostId;
  const comment = await prisma.comment.create({
    data: {
      content,
      rating,
      authorId: userId,
      userId: commentedUserId,
    },
  });

  const fullComment = await prisma.comment.findUnique({
    where: { id: comment.id },
    include: {
      author: {
        select: {
          id: true,
          firstName: true,
          profilePictureFilename: true,
        },
      },
      user: {
        select: {
          id: true,
          firstName: true,
          profilePictureFilename: true,
        },
      },
    },
  });

  res.json(fullComment);
};
