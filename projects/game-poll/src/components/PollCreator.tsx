import { useState } from "react";

interface PollCreatorProps {
  onCreatePoll: (question: string) => void;
}

export default function PollCreator({ onCreatePoll }: PollCreatorProps) {
  const [question, setQuestion] = useState("");

  const create = (): void => {
    if (!question) {
      return;
    }

    onCreatePoll(question);
    setQuestion("");
  };
  const onEnterKey = (key: string, callback: () => void): void => {
    if (key !== "Enter") {
      return;
    }

    callback();
  };

  return (
    <div className="subsection">
      <label>Question</label>
      <input
        type="text"
        placeholder="What are you playing right now?"
        value={question}
        onChange={(event) => setQuestion(event.target.value)}
        onKeyDown={(event) => onEnterKey(event.key, () => create())}
      />
      <button onClick={create}>Create</button>
    </div>
  );
}
