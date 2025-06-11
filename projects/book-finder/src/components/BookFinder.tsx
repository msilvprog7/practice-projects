import fetch from "axios";
import { useState } from "react";
import "./BookFinder.css";

export default function BookFinder() {
  const [query, setQuery] = useState("");
  const [items, setItems] = useState<any[]>([]);

  const search = async () => {
    const response = await fetch(
      `https://www.googleapis.com/books/v1/volumes?q=${query}&key=${process.env.REACT_APP_GOOGLE_BOOKS_API_KEY}`
    );

    if (response.status !== 200) {
      throw new Error(
        `Failed to get books from Google Books API, Status: ${response.status}, Content: ${response.data}, Headers: ${response.headers}`
      );
    }

    setItems(response.data.items ?? []);
  };

  return (
    <div className="book-finder-container">
      <input
        type="text"
        placeholder="Search..."
        value={query}
        onChange={(event) => setQuery(event.target.value)}
        onKeyUp={(event) => {
          if (event.key === "Enter") {
            search();
          }
        }}
      ></input>
      <button onClick={search}>Search</button>
      <ol>
        {items.map((item) => (
          <li>
            <div className="book-info">
              <div className="book-details">
                <h3 className="book-title">{item.volumeInfo.title}</h3>
                {item.volumeInfo.authors && (
                  <p className="book-authors">
                    {item.volumeInfo.authors.join(", ")}
                  </p>
                )}
                <p className="book-published">
                  {item.volumeInfo.publishedDate}
                </p>
                <br />
                {item.volumeInfo.previewLink && (
                  <a
                    href={item.volumeInfo.previewLink}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="preview-button"
                  >
                    Preview
                  </a>
                )}
              </div>
              {item.volumeInfo.imageLinks?.smallThumbnail && (
                <img
                  src={item.volumeInfo.imageLinks.smallThumbnail}
                  alt={item.volumeInfo.title}
                />
              )}
            </div>
          </li>
        ))}
      </ol>
    </div>
  );
}
