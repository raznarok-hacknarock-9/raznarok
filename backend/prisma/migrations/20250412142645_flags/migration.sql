-- AlterTable
ALTER TABLE "Chat" ADD COLUMN     "hasHostCommented" BOOLEAN NOT NULL DEFAULT false,
ADD COLUMN     "hasHostConfirmed" BOOLEAN NOT NULL DEFAULT false,
ADD COLUMN     "hasVisitorCommented" BOOLEAN NOT NULL DEFAULT false,
ADD COLUMN     "hasVisitorConfirmed" BOOLEAN NOT NULL DEFAULT false;
