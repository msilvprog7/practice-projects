import { NextResponse } from "next/server";
import { PrismaClient } from "../../../../generated/prisma";

const prisma = new PrismaClient();

// retrieve polls from database
export async function GET(request: Request) {
  const polls = await prisma.poll.findMany({
    include: {
      answers: true,
    },
  });
  return NextResponse.json({ polls });
}

// create poll in database
export async function POST(request: Request) {
  const data = await request.json();
  const poll = await prisma.poll.create({
    data: {
      question: data.question,
    },
  });
  return NextResponse.json(poll);
}
