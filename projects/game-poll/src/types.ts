export interface Poll {
  id: number;
  question: string;
  answers: PollAnswer[];
}

export interface PollAnswer {
  text: string;
  votes: number;
}

export interface StoredPoll {
  id: number;
  question: string;
  answers: PollAnswer[];
}

export interface StoredPollAnswer {
  text: string;
  votes: number;
}
