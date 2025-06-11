import { useState } from "react";
import LocalStorage, { Document, Note } from "./LocalStorage";
import Navigation from "./Navigation";
import View from "./View";
import "./Notes.css";

export enum ViewType {
  List = "List",
  Edit = "Edit",
}

export interface State {
  document: Document;
  viewType: ViewType;
  note?: Note;
}

export interface Controls {
  changeViewType: (viewType: ViewType, note?: Note) => void;
  createNote: (d: Document) => Note;
  updateNote: (d: Document, note: Note) => void;
}

export default function Notes() {
  const [state, setState] = useState<State>({
    document: LocalStorage.getDocument(),
    viewType: ViewType.List,
  });
  const controls = {
    changeViewType: (viewType: ViewType, note?: Note) => {
      setState((s) => {
        return { ...s, viewType, note };
      });
    },
    createNote: (d: Document): Note => {
      const timestamp = new Date();
      const note = {
        id: d.notes.length,
        created: timestamp,
        edited: timestamp,
      };
      d.notes.push(note);
      LocalStorage.saveDocument(d);
      return note;
    },
    updateNote: (d: Document, note: Note) => {
      note.edited = new Date();

      const index = d.notes.findIndex((n) => n.id === note.id);
      d.notes[index] = note;
      LocalStorage.saveDocument(d);
    },
  };
  return (
    <div className="notes">
      <Navigation state={state} controls={controls} />
      <View state={state} controls={controls} />
    </div>
  );
}
