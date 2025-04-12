import { UserWithRelations } from "../models/prisma";

export function calculateUserAverageRatings(user: UserWithRelations ) {
  const averageRatingAsHost = user.commentsAsHost.length !== 0 ? user.commentsAsHost.reduce((sum, comment) => sum + (comment.rating), 0) / user.commentsAsHost.length : 0;
  const averageRatingAsVisitor = user.commentsAsVisitor.length !== 0 ? user.commentsAsVisitor.reduce((sum, comment) => sum + (comment.rating), 0) / user.commentsAsVisitor.length : 0;
  return {
    ...user,
    averageRatingAsHost: averageRatingAsHost,
    averageRatingAsVisitor: averageRatingAsVisitor,
  };
}