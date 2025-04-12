import { Prisma } from '@shared/prisma';

type ChatWithMessages = Prisma.ChatGetPayload<{
  include: { messages: true };
}>;

export function sortChatMessages(chat: ChatWithMessages): ChatWithMessages {
  return {
    ...chat,
    messages: [...chat.messages].sort(
      (a, b) => a.timestamp.getTime() - b.timestamp.getTime(),
    ),
  };
}
