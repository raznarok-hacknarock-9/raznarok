import { Prisma } from '../../../shared/prisma';

export type UserWithRelations = Prisma.UserGetPayload<{
  include: {
    availabilities: true,
    chatsAsHost: true,
    chatsAsVisitor: true,
    commentsAsHost: true,
    commentsAsVisitor: true,
    tags: true,
  };
}>;