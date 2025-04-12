import { Request, Response, NextFunction } from 'express';
import { z } from 'zod';
import prisma from '../config/prisma';
import { AppError } from '../lib/AppError';

export const getUsers = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  try {
    const querySchema = z.object({
      dateFrom: z.coerce.number().optional(),
      dateTo: z.coerce.number().optional(),
      location: z.string().optional(),
    });

    const { dateFrom, dateTo, location } = querySchema.parse(req.query);
    console.log(
      dateFrom,
      dateTo,
      location,
      new Date(dateFrom ?? 0),
      new Date(dateTo ?? 0),
    );

    if ((dateFrom && !dateTo) || (!dateFrom && dateTo)) {
      throw new AppError(
        'dateFrom and dateTo must both be set or both be omitted',
        400,
      );
    }

    const users = await prisma.user.findMany({
      where: {
        availabilities: {
          some: {
            ...(dateTo && { dateFrom: { lte: new Date(dateTo) } }),
            ...(dateFrom && { dateTo: { gte: new Date(dateFrom) } }),
            ...(location && {
              location: { contains: location, mode: 'insensitive' },
            }),
          },
        },
      },
      include: {
        availabilities: true,
        chatsAsHost: true,
        chatsAsVisitor: true,
        commentsAsHost: true,
        commentsAsVisitor: true,
        tags: true,
      },
    });

    res.json(users);
  } catch (error) {
    next(error);
  }
};

export const getUserById = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const id = parseInt(z.string().parse(req.params.id), 10);
  const user = await prisma.user.findUnique({
    where: { id },
    select: {
      availabilities: true,
      chatsAsHost: true,
      chatsAsVisitor: true,
      commentsAsHost: true,
      commentsAsVisitor: true,
      tags: true,
    },
  });
  if (!user) {
    throw new AppError('User not found', 404);
  }
  res.json(user);
};

export const loginUser = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  try {
    const authSchema = z.object({
      email: z.string().email(),
    });
    const { data, success } = authSchema.safeParse(req.body);
    if (!success) {
      throw new AppError('Invalid request', 400);
    }
    const user = await prisma.user.findUnique({
      where: { email: data.email },
      select: {
        availabilities: true,
        chatsAsHost: true,
        chatsAsVisitor: true,
        commentsAsHost: true,
        commentsAsVisitor: true,
        tags: true,
      },
    });
    if (!user) {
      throw new AppError('User not found', 404);
    }
    res.json(user);
  } catch (error) {
    next(error);
  }
};
