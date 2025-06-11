import { Poll, StoredPoll } from "../types";

export default class PollService {
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
