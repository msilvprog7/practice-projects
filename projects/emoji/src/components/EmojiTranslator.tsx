import { useState } from "react";
import "./EmojiTranslator.css";
import Emojis from "./Emojis.json";

const sortedEntries = Emojis.entries.map((entry) => ({
  ...entry,
  words: entry.words
    .sort((a, b) => b.length - a.length)
    .map((word) => new RegExp(`\\b${word}\\b`, "gi")),
}));

export default function EmojiTranslator() {
  const [text, setText] = useState("");
  const [warning, setWarning] = useState("");
  const [translated, setTranslated] = useState(
    "Translated text will display here..."
  );

  const type = (text: string) => {
    setWarning("");
    setText(text);
  };
  const translate = () => {
    if (text.length === 0) {
      setWarning("Enter text, then click the translate button.");
      return;
    }

    let updated = text;
    for (const entry of sortedEntries) {
      for (const word of entry.words) {
        updated = updated.replace(word, entry.emoji);
      }
    }

    if (updated === translated) {
      setWarning("Change text, then click the translate button.");
      return;
    }

    setTranslated(updated);
  };

  return (
    <div className="emoji-translator-container">
      <div>
        <input
          type="text"
          placeholder="Text to translate..."
          value={text}
          onChange={(event) => type(event.target.value)}
        />
        <span
          className="warning"
          style={{ display: warning === "" ? "none" : "block" }}
        >
          {warning}
        </span>
        <button onClick={() => translate()}>Translate</button>
        <span className="output">{translated}</span>
      </div>
    </div>
  );
}
