import { Request, Response, NextFunction } from 'express';
import { z } from 'zod';
import prisma from '../config/prisma';
import { AppError } from '../lib/AppError';
import { sortChatMessages } from '../lib/chatHelpers';

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
    throw new AppError('Chat already exists', 'chat-already-exists', 400);
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

  res.json(sortChatMessages(chat));
};

export const getChats = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const querySchema = z.object({
    hostId: z.number().optional(),
    visitorId: z.number().optional(),
  });

  const { hostId, visitorId: ownerId } = querySchema.parse(req.query);

  const chats = await prisma.chat.findMany({
    where: {
      ...(hostId && { hostId }),
      ...(ownerId && { visitorId: ownerId }),
    },
    include: { messages: true },
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
    },
  });

  if (!chat) {
    throw new AppError('Chat not found', 'chat-not-found', 404);
  }

  res.json(sortChatMessages(chat));
};

export const createMessage = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const bodySchema = z.object({
    content: z.string(),
    userId: z.number(),
  });

  const chatId = z.coerce.number().parse(req.params.id);
  const { content, userId } = bodySchema.parse(req.body);

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

  const message = await prisma.chatMessage.create({
    data: {
      content,
      chatId,
      isHostMessage,
    },
  });

  res.json(message);
};

export const confirmMeeting = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const chatId = z.coerce.number().parse(req.params.id);

  const bodySchema = z.object({
    userId: z.number(),
  });

  const { userId } = bodySchema.parse(req.body);

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
  const updatedChat = await prisma.chat.update({
    where: {
      id: chatId,
    },
    data: {
      ...(isHost && { hasHostConfirmed: true }),
      ...(!isHost && { hasVisitorConfirmed: true }),
    },
  });

  res.json(updatedChat);
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

  res.json(comment);
};
