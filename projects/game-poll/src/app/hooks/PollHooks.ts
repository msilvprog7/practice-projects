import { useCallback, useEffect, useState } from "react";
import { Poll } from "../types";
import PollService from "../services/PollService";

export function usePolls() {
  const [polls, setPolls] = useState<Poll[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const fetchPolls = useCallback(async () => {
    try {
      setLoading(true);

      const fetchedPolls = await PollService.getPolls();
      setPolls(fetchedPolls);
      setError(null);
    } catch (err) {
      setError(`Failed to fetch polls, Error: ${err}`);
      console.error(err);
    } finally {
      setLoading(false);
    }
  }, []);
  const createPoll = useCallback(
    async (question: string) => {
      try {
        setLoading(true);
        await PollService.createPoll(question);
        await fetchPolls();
        setError(null);
      } catch (err) {
        setError(`Failed to create poll, Error: ${err}`);
        console.error(err);
      } finally {
        setLoading(false);
      }
    },
    [fetchPolls]
  );
  const votePoll = useCallback(
    async (pollId: number, answer: string) => {
      try {
        setLoading(true);
        await PollService.vote(pollId, answer);
        await fetchPolls();
        setError(null);
      } catch (err) {
        setError(`Failed to vote on poll, Error: ${err}`);
        console.error(err);
      } finally {
        setLoading(false);
      }
    },
    [fetchPolls]
  );

  // Load polls initially
  useEffect(() => {
    fetchPolls();
  }, [fetchPolls]);

  return {
    polls,
    loading,
    error,
    createPoll,
    votePoll,
    fetchPolls,
  };
}
