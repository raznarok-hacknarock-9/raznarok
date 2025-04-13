import { Request, Response, NextFunction } from 'express';
import { z } from 'zod';
import prisma from '../config/prisma';
import { AppError } from '../lib/AppError';
import { UserWithRelations } from '../models/prisma';
import { calculateUserAverageRatings } from '../lib/userHelpers';

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
        chatsAsHost: {
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
        },
        chatsAsVisitor: {
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
        },
        commentsAsVisitor: {
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
        },
        commentsAsHost: {
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
        },
        tags: true,
      },
    });

    res.json(users.map(calculateUserAverageRatings));
  } catch (error) {
    next(error);
  }
};

export const getUserById = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const id = z.coerce.number().parse(req.params.id);
  const user = await prisma.user.findUnique({
    where: {
      id,
    },
    include: {
      availabilities: true,
      chatsAsHost: {
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
      },
      chatsAsVisitor: {
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
      },
      commentsAsVisitor: {
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
      },
      commentsAsHost: {
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
      },
      tags: true,
    },
  });
  if (!user) {
    throw new AppError('User not found', 404);
  }

  const userWithRatings = calculateUserAverageRatings(user);

  res.json(userWithRatings);
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
      include: {
        availabilities: true,
        chatsAsHost: {
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
        },
        chatsAsVisitor: {
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
        },
        commentsAsVisitor: {
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
        },
        commentsAsHost: {
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
        },
        tags: true,
      },
    });
    if (!user) {
      throw new AppError('User not found', 404);
    }

    const userWithRatings = calculateUserAverageRatings(user);

    res.json(userWithRatings);
  } catch (error) {
    next(error);
  }
};

export const addPoints = async (
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  const id = z.coerce.number().parse(req.params.id);
  const pointsToAdd = z.coerce.number().parse(req.body.points);
  const user = await prisma.user.update({
    where: { id },
    data: {
      points: {
        increment: pointsToAdd,
      },
    },
  });

  res.json(user);
};