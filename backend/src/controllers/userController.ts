import { Request, Response, NextFunction } from 'express';
import { z } from 'zod';
import prisma from '../config/prisma';
import { AppError } from '../lib/AppError';

export const createUser = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  try {
    const { email } = req.body;
    // const newUser = await prisma.user.create({ data: { email } });
    // res.status(201).json(newUser);
  } catch (error) {
    next(error);
  }
};

export const getUsers = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  try {
    const querySchema = z.object({
      email: z.string().email().optional(),
      dateFrom: z.coerce.number().optional(),
      dateTo: z.coerce.number().optional(),
      location: z.string().optional(),
    });

    const { email, dateFrom, dateTo, location } = querySchema.parse(req.query);

    const users = await prisma.user.findMany({
      where: email ? { email } : undefined,
      include: {
        availabilities: true,
        chatsAsHost: true,
        chatsAsVisitor: true,
        commentsAsHost: true,
        commentsAsVisitor: true,
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
  const user = await prisma.user.findUnique({ where: { id } });
  if (!user) {
    throw new AppError('User not found', 404);
  }
  res.json(user);
};

export const updateUser = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  try {
    const id = parseInt(z.string().parse(req.params.id), 10);
    const { email } = req.body;
    const updateUser = await prisma.user.update({
      data: { email },
      where: { id },
    });
    res.json(updateUser);
  } catch (error) {
    next(error);
  }
};

export const deleteUser = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  try {
    const id = parseInt(z.string().parse(req.params.id), 10);
    const deletedUser = await prisma.user.delete({ where: { id } });
    res.json(deletedUser);
  } catch (error) {
    next(error);
  }
};
