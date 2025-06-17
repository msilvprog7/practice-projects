/*
  Warnings:

  - A unique constraint covering the columns `[pollId,text]` on the table `Answer` will be added. If there are existing duplicate values, this will fail.

*/
-- CreateIndex
CREATE UNIQUE INDEX "Answer_pollId_text_key" ON "Answer"("pollId", "text");
