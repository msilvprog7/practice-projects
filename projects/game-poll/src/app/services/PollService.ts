import { Poll, StoredPoll, StoredPollAnswer } from "@/app/types";

export default class PollService {
  // GET /api/polls
  static async getPolls(): Promise<Poll[]> {
    const response = await fetch("/api/polls", {
      cache: "no-store",
      headers: {
        "Cache-Control": "no-cache",
      },
    });

    if (response.status !== 200) {
      throw new Error(
        `Failed to get polls, Status: ${
          response.status
        }, Body: ${await response.text()}, Headers: ${response.headers}`
      );
    }

    const polls = (await response.json()).polls as StoredPoll[];
    return polls.map(this.toApplicationModel);
  }

  // POST /api/polls
  static async createPoll(question: string): Promise<Poll> {
    const data: StoredPoll = { id: 0, question, answers: [] };
    const body = JSON.stringify(data);
    const response = await fetch("/api/polls", { method: "post", body });

    if (response.status !== 200) {
      throw new Error(
        `Failed to create poll, Status: ${
          response.status
        }, Body: ${await response.text()}, Headers: ${response.headers}`
      );
    }

    const poll = (await response.json()) as StoredPoll;
    return this.toApplicationModel(poll);
  }

  // POST /api/polls/{pollId}
  static async vote(pollId: number, answer: string): Promise<Poll> {
    const data: StoredPollAnswer = { text: answer, votes: 0 };
    const body = JSON.stringify(data);
    const response = await fetch(`/api/polls/${pollId}`, {
      method: "post",
      body,
    });

    if (response.status !== 200) {
      throw new Error(
        `Failed to vote on poll, Status: ${
          response.status
        }, Body: ${await response.text()}, Headers: ${response.headers}`
      );
    }

    const poll = (await response.json()) as StoredPoll;
    return this.toApplicationModel(poll);
  }

  private static toApplicationModel(storedPoll: StoredPoll): Poll {
    return {
      id: storedPoll.id,
      question: storedPoll.question,
      answers:
        storedPoll.answers?.map((a) => {
          return { text: a.text, votes: a.votes };
        }) || [],
    };
  }

  private static toStorageModel(poll: Poll): StoredPoll {
    return {
      id: poll.id,
      question: poll.question,
      answers:
        poll.answers?.map((a) => {
          return { text: a.text, votes: a.votes };
        }) || [],
    };
  }

  private static copy(poll: Poll): Poll {
    return {
      id: poll.id,
      question: poll.question,
      answers:
        poll.answers?.map((a) => {
          return { text: a.text, votes: a.votes };
        }) || [],
    };
  }
}

/**
 * Earlier, I found it useful to use LocalStorage to manage state.
 * I'm keeping this for my own reference if I later want to reference it.
 */
class LocalStoragePollService {
  static async getPolls(): Promise<Poll[]> {
    const storedPolls = localStorage.getItem("polls");
    if (!storedPolls) {
      return [];
    }

    const parsedPolls: StoredPoll[] = JSON.parse(storedPolls);
    return parsedPolls.map(this.toApplicationModel);
  }

  static async createPoll(question: string): Promise<Poll> {
    const polls = await this.getPolls();

    const newPoll = {
      id: polls.length,
      question,
      answers: [],
    };

    localStorage.setItem(
      "polls",
      JSON.stringify([...polls, newPoll].map(this.toStorageModel))
    );
    return newPoll;
  }

  static async vote(pollId: number, answer: string): Promise<Poll> {
    const polls = await this.getPolls();
    const pollIndex = polls.findIndex((p) => p.id === pollId);

    if (pollIndex === -1) {
      throw new Error(`Poll '${pollId}' not found`);
    }

    const poll = this.copy(polls[pollIndex]);
    const answerIndex = poll.answers.findIndex((a) => a.text === answer);

    if (answerIndex >= 0) {
      poll.answers[answerIndex].votes++;
    } else {
      poll.answers.push({ text: answer, votes: 1 });
    }

    polls[pollIndex] = poll;

    localStorage.setItem(
      "polls",
      JSON.stringify(polls.map(this.toStorageModel))
    );

    return poll;
  }

  private static toApplicationModel(storedPoll: StoredPoll): Poll {
    return {
      id: storedPoll.id,
      question: storedPoll.question,
      answers:
        storedPoll.answers?.map((a) => {
          return { text: a.text, votes: a.votes };
        }) || [],
    };
  }

  private static toStorageModel(poll: Poll): StoredPoll {
    return {
      id: poll.id,
      question: poll.question,
      answers:
        poll.answers?.map((a) => {
          return { text: a.text, votes: a.votes };
        }) || [],
    };
  }

  private static copy(poll: Poll): Poll {
    return {
      id: poll.id,
      question: poll.question,
      answers:
        poll.answers?.map((a) => {
          return { text: a.text, votes: a.votes };
        }) || [],
    };
  }
}
