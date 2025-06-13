import { NextResponse } from "next/server";
import { StoredPoll } from "@/app/types";

export const InMemoryPolls: StoredPoll[] = [
  {
    id: 0,
    question: "What are you playing right now?",
    answers: [
      { text: "Elden Ring", votes: 2 },
      { text: "Mario Kart", votes: 1 },
    ],
  },
  {
    id: 1,
    question: "What's your all-time favorite game?",
    answers: [
      { text: "Mario", votes: 3 },
      { text: "Zelda", votes: 1 },
    ],
  },
];

// mock polls for integration
// todo: retrieve polls from database
export async function GET(request: Request) {
  return NextResponse.json({ polls: InMemoryPolls });
}

// mock adding poll to mocks for integration
// todo: create poll in database
export async function POST(request: Request) {
  const data = await request.json();
  const poll = {
    id: InMemoryPolls.length,
    question: data.question,
    answers: [],
  };
  InMemoryPolls.push(poll);
  return NextResponse.json(poll);
}
