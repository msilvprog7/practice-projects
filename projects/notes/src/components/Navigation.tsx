import { useEffect, useState } from "react";
import { Controls, State, ViewType } from "./Notes";

interface NavigationProps {
  state: State;
  controls: Controls;
}

function Navigation(props: NavigationProps) {
  const controls = props.controls;
  const [name, setName] = useState(props.state.note?.name || "");

  // Add this effect to update text when note changes
  useEffect(() => {
    setName(props.state.note?.name || "");
  }, [props.state.note]);

  useEffect(() => {
    const timeoutId = setTimeout(() => {
      if (props.state.note && name) {
        controls.updateNote(props.state.document, {
          ...props.state.note,
          name,
        });
      }
    }, 1000);

    return () => clearTimeout(timeoutId);
  }, [name, props.state.document, props.state.note]);

  return (
    <div className="navigation row">
      <a onClick={() => controls.changeViewType(ViewType.List)}>notes</a>
      {props.state.viewType === ViewType.List ? (
        <button
          onClick={() =>
            controls.changeViewType(
              ViewType.Edit,
              controls.createNote(props.state.document)
            )
          }
        >
          + add note
        </button>
      ) : (
        <input
          type="text"
          placeholder="Name..."
          value={name}
          onChange={(e) => setName(e.target.value)}
        ></input>
      )}
    </div>
  );
}

export default Navigation;
