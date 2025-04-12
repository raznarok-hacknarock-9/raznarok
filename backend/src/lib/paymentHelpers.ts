import config from '../config/config';
import prisma from '../config/prisma';

export async function pay(payerId: number, receiverId: number, amount: number) {
  await prisma.user.update({
    where: { id: payerId },
    data: {
      points: {
        decrement: amount,
      },
    },
  });

  const earningsAfterCommision = amount * (1 - config.commissionRate);
  await prisma.user.update({
    where: { id: receiverId },
    data: {
      points: {
        increment: earningsAfterCommision,
      },
    },
  });
}
