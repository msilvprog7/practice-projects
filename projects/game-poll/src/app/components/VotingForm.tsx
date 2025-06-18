"use client";

import { useState, useEffect, useRef } from "react";
import { GameSuggestion, Poll } from "@/app/types";
import SuggestionService from "../services/SuggestionService";

interface VotingFormProps {
  poll: Poll;
  onVotePoll: (pollId: number, answer: string) => void;
}

export default function VotingForm({ poll, onVotePoll }: VotingFormProps) {
  const [answer, setAnswer] = useState("");
  const [suggestions, setSuggestions] = useState<GameSuggestion[]>([]);
  const [showSuggestions, setShowSuggestions] = useState(false);
  const [selectedIndex, setSelectedIndex] = useState(-1);
  const containerRef = useRef<HTMLDivElement>(null);
  const inputRef = useRef<HTMLInputElement>(null);

  const vote = () => {
    if (!answer.trim()) {
      return;
    }

    setAnswer("");
    setSuggestions([]);
    setShowSuggestions(false);
    onVotePoll(poll.id, answer);
  };

  const updateAnswer = async (text: string) => {
    setAnswer(text);

    if (text.trim().length === 0) {
      setSuggestions([]);
      setShowSuggestions(false);
      return;
    }

    let next = await SuggestionService.getSuggestions(text, 5);
    next = next.filter((s) => s.name.toLowerCase() !== text.toLowerCase());
    setSuggestions(next);
    setShowSuggestions(next.length > 0);
    setSelectedIndex(-1);
  };

  const selectSuggestion = (suggestion: string) => {
    setAnswer(suggestion);
    setSuggestions([]);
    setShowSuggestions(false);
    inputRef.current?.focus(); // Keep focus on input
  };

  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (!showSuggestions || suggestions.length === 0) {
      if (event.key === "Enter") {
        vote();
      }
      return;
    }

    switch (event.key) {
      case "ArrowDown":
        event.preventDefault();
        setSelectedIndex((prev) =>
          prev < suggestions.length - 1 ? prev + 1 : 0
        );
        break;

      case "ArrowUp":
        event.preventDefault();
        setSelectedIndex((prev) =>
          prev > 0 ? prev - 1 : suggestions.length - 1
        );
        break;

      case "Enter":
        event.preventDefault();
        if (selectedIndex >= 0) {
          selectSuggestion(suggestions[selectedIndex].name);
        } else {
          vote();
        }
        break;

      case "Escape":
      case "Tab":
        setShowSuggestions(false);
        setSelectedIndex(-1);
        break;
    }
  };

  // Hide suggestions when clicking outside
  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        containerRef.current &&
        !containerRef.current.contains(event.target as Node)
      ) {
        setShowSuggestions(false);
        setSelectedIndex(-1);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  return (
    <div className="subsection">
      <label>{poll.question}</label>
      <div className="input-container" ref={containerRef}>
        <input
          ref={inputRef}
          type="text"
          placeholder="Start typing a game..."
          value={answer}
          onChange={(event) => updateAnswer(event.target.value)}
          onKeyDown={handleKeyDown}
          onFocus={() => {
            if (suggestions.length > 0) {
              setShowSuggestions(true);
            }
          }}
        />
        {showSuggestions && suggestions.length > 0 && (
          <ul className="suggestions-dropdown">
            {suggestions.map((s, i) => (
              <li
                key={i}
                className={selectedIndex === i ? "selected" : ""}
                onClick={() => selectSuggestion(s.name)}
                onMouseEnter={() => setSelectedIndex(i)}
              >
                {s.name}
              </li>
            ))}
          </ul>
        )}
      </div>
      <button onClick={vote}>Vote</button>
    </div>
  );
}
