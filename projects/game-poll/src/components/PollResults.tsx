import { Poll } from "../types";

interface PollResultsProps {
  poll: Poll;
  back: () => void;
}

export default function PollResults({ poll, back }: PollResultsProps) {
  return (
    <div className="subsection">
      <label>Results</label>
      <ol>
        {Array.from(poll.answers)
          .sort((a, b) => b.votes - a.votes)
          .map((answer, index) => (
            <li key={index}>
              {answer.text} ({answer.votes} vote{answer.votes !== 1 ? "s" : ""})
            </li>
          ))}
      </ol>
      <button onClick={back}>Back</button>
    </div>
  );
}
