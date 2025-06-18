import { GameSuggestion } from "../types";

const MIN_QUERY_LENGTH = 3;

export default class SuggestionService {
  static async getSuggestions(
    query: string,
    top: number
  ): Promise<GameSuggestion[]> {
    if (!query || query.length < MIN_QUERY_LENGTH) {
      return [];
    }

    const response = await fetch(
      `/api/games/search/${encodeURIComponent(query)}?top=${top}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    if (response.status !== 200) {
      throw new Error(
        `Failed to get game suggestions for query '${query}', top ${top}, ${await response.text()}, ${
          response.headers
        }`
      );
    }

    const suggestions = await response.json();
    return suggestions;
  }
}
