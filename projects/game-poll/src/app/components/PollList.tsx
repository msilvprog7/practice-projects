"use client";

import { Poll } from "@/app/types";

interface PollListProps {
  polls: Poll[];
  onClickPoll: (id: number) => void;
}

export default function PollList({ polls, onClickPoll }: PollListProps) {
  return (
    <div className="subsection">
      <label>Polls</label>
      <ul>
        {polls.map((poll) => (
          <li key={poll.id}>
            <a onClick={() => onClickPoll(poll.id)}>{poll.question}</a>
          </li>
        ))}
      </ul>
    </div>
  );
}
