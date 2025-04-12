/*
  Warnings:

  - You are about to drop the column `hasHostConfirmed` on the `Chat` table. All the data in the column will be lost.
  - You are about to drop the column `hasVisitorConfirmed` on the `Chat` table. All the data in the column will be lost.

*/
-- AlterTable
ALTER TABLE "Chat" DROP COLUMN "hasHostConfirmed",
DROP COLUMN "hasVisitorConfirmed",
ADD COLUMN     "cost" INTEGER NOT NULL DEFAULT 100,
ADD COLUMN     "isCostConfirmed" BOOLEAN NOT NULL DEFAULT false,
ADD COLUMN     "isVisitConfirmed" BOOLEAN NOT NULL DEFAULT false;

-- AlterTable
ALTER TABLE "ChatMessage" ADD COLUMN     "status" TEXT NOT NULL DEFAULT 'pending',
ADD COLUMN     "type" TEXT NOT NULL DEFAULT 'text';

-- AlterTable
ALTER TABLE "User" ADD COLUMN     "maxPriceRange" INTEGER NOT NULL DEFAULT 10000,
ADD COLUMN     "minPriceRange" INTEGER NOT NULL DEFAULT 100;
