"use client";

import { useState } from "react";
import { Poll } from "@/app/types";

interface VotingFormProps {
  poll: Poll;
  onVotePoll: (pollId: number, answer: string) => void;
}

export default function VotingForm({ poll, onVotePoll }: VotingFormProps) {
  const [answer, setAnswer] = useState("");

  const vote = () => {
    if (!answer) {
      return;
    }

    setAnswer("");
    onVotePoll(poll.id, answer);
  };
  const onEnterKey = (key: string, callback: () => void) => {
    if (key !== "Enter") {
      return;
    }

    callback();
  };

  return (
    <div className="subsection">
      <label>{poll.question}</label>
      <input
        type="text"
        placeholder="Start typing a game..."
        value={answer}
        onChange={(event) => setAnswer(event.target.value)}
        onKeyDown={(event) => onEnterKey(event.key, () => vote())}
      />
      <button onClick={vote}>Vote</button>
    </div>
  );
}
