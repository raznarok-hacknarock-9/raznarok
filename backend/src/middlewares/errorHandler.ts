import { Request, Response, NextFunction } from 'express';
import { AppError } from '../lib/AppError';

export const errorHandler = (
  error: unknown,
  req: Request,
  res: Response,
  next: NextFunction,
) => {
  console.error(error);
  if (error instanceof AppError) {
    res.status(error.status ?? 500).json({
      message: error.message,
      code: error.code ?? null,
    });
    return;
  }

  res.status(500).json({
    message: 'Unknown error.',
    code: null,
  });
};
