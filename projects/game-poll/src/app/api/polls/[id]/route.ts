import { NextResponse } from "next/server";
import { InMemoryPolls } from "@/app/api/polls/route";
import { StoredPoll } from "@/app/types";

// mock poll data for integration
// todo: retrieve polls from database
export async function GET(
  request: Request,
  { params }: { params: Promise<{ id: number }> }
) {
  const { id } = await params;
  const index = InMemoryPolls.findIndex((p: StoredPoll) => p.id === id);

  if (index === -1) {
    return new NextResponse(`Poll id '${id}' is not found`, {
      status: 404,
    });
  }

  const poll = InMemoryPolls[index];
  return NextResponse.json(poll);
}

// mock vote on poll for integration
// todo: update vote in poll database
export async function POST(
  request: Request,
  { params }: { params: Promise<{ id: number }> }
) {
  const { id } = await params;
  const index = InMemoryPolls.findIndex((p: StoredPoll) => p.id == id);

  if (index === -1) {
    return new NextResponse(`Poll id '${id}' is not found`, {
      status: 404,
    });
  }

  const poll = InMemoryPolls[index];
  const data = await request.json();
  const text = data.text;
  let answerIndex = poll.answers.findIndex((a) => a.text === text);

  if (answerIndex === -1) {
    poll.answers.push({ text, votes: 0 });
    answerIndex = poll.answers.length - 1;
  }

  poll.answers[answerIndex].votes++;
  console.log(JSON.stringify(poll));
  return NextResponse.json(poll);
}
