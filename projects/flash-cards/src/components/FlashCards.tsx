import { useState } from "react";
import "./FlashCards.css";
import flashCards from "./FlashCards.json";

// fisher-yates shuffle
const shuffle = (arr: any[]): any[] => {
  const shuffled = [...arr];
  for (let i = shuffled.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
  }
  return shuffled;
};

export default function FlashCards() {
  const [cards, setCards] = useState(shuffle(flashCards.cards));
  const [index, setIndex] = useState(0);
  const [isShown, setIsShown] = useState(false);

  const move = (i: number) => {
    if (i < 0 || i >= cards.length) {
      return;
    }

    setIsShown(false);
    setIndex(i);
  };

  return (
    <div className="flash-cards">
      <div className="section">
        <div className="controls">
          <button
            className={index === 0 ? "hide disabled" : "hide"}
            onClick={() => move(index - 1)}
          >
            Back
          </button>
          <span>
            {index + 1} of {cards.length}
          </span>
          <button
            className={index === cards.length - 1 ? "disabled" : ""}
            onClick={() => move(index + 1)}
          >
            Next
          </button>
        </div>
      </div>
      <div className="section">
        <label>Question</label>
        <span>{cards[index].question}</span>
        <div className="controls">
          {!isShown ? (
            <button onClick={() => setIsShown(true)}>Show</button>
          ) : (
            <button className="hide" onClick={() => setIsShown(false)}>
              Hide
            </button>
          )}
        </div>
      </div>
      {isShown ? (
        <div className="section">
          <label>Answer</label>
          <span>{cards[index].answer}</span>
        </div>
      ) : (
        <div />
      )}
    </div>
  );
}
