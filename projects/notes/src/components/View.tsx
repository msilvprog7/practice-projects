import { useEffect, useState } from "react";
import { Controls, State, ViewType } from "./Notes";

interface ViewProps {
  state: State;
  controls: Controls;
}

function View(props: ViewProps) {
  const controls = props.controls;
  const notes = props.state.document.notes;
  const [text, setText] = useState(props.state.note?.text || "");

  useEffect(() => {
    setText(props.state.note?.text || "");
  }, [props.state.note]);

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      if (props.state.note && text) {
        controls.updateNote(props.state.document, {
          ...props.state.note,
          text,
        });
      }
    }, 1000);

    return () => clearTimeout(timeoutId);
  }, [text, props.state.note, props.state.document]);

  return (
    <div>
      {props.state.viewType === ViewType.List ? (
        <ul>
          {notes.map((note) => (
            <li key={note.id}>
              <a onClick={() => controls.changeViewType(ViewType.Edit, note)}>
                {note.name || "Untitled"}
              </a>
            </li>
          ))}
        </ul>
      ) : (
        <div>
          <textarea
            placeholder="text..."
            value={text}
            onChange={(e) => setText(e.target.value)}
          ></textarea>
          <span></span>
        </div>
      )}
    </div>
  );
}

export default View;
