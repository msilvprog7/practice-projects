import { NextResponse } from "next/server";
import { PrismaClient } from "../../../../../generated/prisma";

const prisma = new PrismaClient();

// retrieve poll from database
export async function GET(
  request: Request,
  { params }: { params: Promise<{ id: string }> }
) {
  try {
    const { id } = await params;
    const pollId = parseInt(id);
    if (isNaN(pollId)) {
      return new NextResponse(`Poll id '${id}' is invalid`, { status: 404 });
    }

    const poll = await prisma.poll.findUnique({
      where: { id: pollId },
      include: { answers: true }, // Include related answers
    });

    if (!poll) {
      return new NextResponse(`Poll id '${id}' is not found`, {
        status: 404,
      });
    }

    return NextResponse.json(poll);
  } catch (error) {
    console.error("Failed to get poll for id:", error);
    return new NextResponse("Failed to get poll for id", { status: 500 });
  }
}

// update vote in poll database
export async function POST(
  request: Request,
  { params }: { params: Promise<{ id: string }> }
) {
  try {
    const { id } = await params;
    const data = await request.json();
    const text = data.text;

    const pollId = parseInt(id);
    if (isNaN(pollId)) {
      return new NextResponse(`Poll id '${id}' is invalid`, { status: 404 });
    }

    // Check if poll exists
    const poll = await prisma.poll.findUnique({ where: { id: pollId } });
    if (!poll) {
      return new NextResponse(`Poll id '${id}' is not found`, { status: 404 });
    }

    // Upsert answer - create if doesn't exist, update if it does
    await prisma.answer.upsert({
      where: {
        // Note: This requires a unique constraint on pollId + text
        pollId_text: { pollId, text },
      },
      update: {
        votes: { increment: 1 },
      },
      create: {
        text: text,
        votes: 1,
        pollId,
      },
    });

    // Return updated poll
    const updatedPoll = await prisma.poll.findUnique({
      where: { id: pollId },
      include: { answers: true },
    });

    return NextResponse.json(updatedPoll);
  } catch (error) {
    console.error("Failed to vote on poll for id:", error);
    return new NextResponse("Failed to vote on poll for id", { status: 500 });
  }
}
