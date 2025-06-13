"use client";

import { useEffect, useState } from "react";
import PollCreator from "./components/PollCreator";
import PollList from "./components/PollList";
import PollResults from "./components/PollResults";
import VotingForm from "./components/VotingForm";
import { usePolls } from "./hooks/PollHooks";

export default function Home() {
  const { polls, loading, error, createPoll, votePoll, fetchPolls } =
    usePolls();
  const [pollId, setPollId] = useState<number | null>(null);

  useEffect(() => {
    fetchPolls();
  }, [fetchPolls]);

  if (loading && polls.length === 0) {
    return <div className="app">Loading polls...</div>;
  }

  if (error && polls.length === 0) {
    return <div className="app">Error: {error}</div>;
  }

  if (pollId != null) {
    const poll = polls[pollId];
    return (
      <div className="app">
        <div className="section">
          <VotingForm poll={poll} onVotePoll={votePoll} />
          <PollResults poll={poll} back={() => setPollId(null)} />
        </div>
      </div>
    );
  }

  return (
    <div className="app">
      <div className="section">
        <PollCreator onCreatePoll={createPoll} />
        <PollList polls={polls} onClickPoll={setPollId} />
      </div>
    </div>
  );
}
