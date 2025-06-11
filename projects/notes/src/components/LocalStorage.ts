export interface Note {
  id: number;
  name?: string;
  created: Date;
  edited: Date;
  text?: string;
}

export interface Document {
  notes: Note[];
}

export default class LocalStorage {
  private static Key = "notes";

  public static getDocument(): Document {
    return LocalStorage.get<Document>(LocalStorage.Key) || { notes: [] };
  }

  public static saveDocument(document: Document) {
    LocalStorage.save(LocalStorage.Key, document);
  }

  private static get<T>(key: string): T | undefined {
    this.check();

    const item = window.localStorage.getItem(key);
    if (item === null) {
      return undefined;
    }

    try {
      return JSON.parse(item) as T;
    } catch (error) {
      console.error(
        `Failed to parse item from localStorage key "${key}":`,
        error
      );
      return undefined;
    }
  }

  private static save<T>(key: string, value: T) {
    this.check();

    try {
      const item = JSON.stringify(value);
      window.localStorage.setItem(key, item);
    } catch (error) {
      console.error(`Failed to stringify value for key "${key}":`, error);
      throw new Error(`Failed to save to localStorage: ${error}`);
    }
  }

  private static check() {
    if (!window.localStorage) {
      throw new Error("Browser does not support localStorage");
    }
  }
}
