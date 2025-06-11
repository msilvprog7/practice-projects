import { useState } from "react";
import "./Quiz.css";
import moment from "moment";

export default function Quiz() {
  const [question, setQuestion] = useState(-1);
  const [answers, setAnswers] = useState<number[]>([]);
  const [startTime, setStartTime] = useState<Date>(new Date());
  const [endTime, setEndTime] = useState<Date>(new Date());

  const quiz = {
    name: "Math quiz",
    questions: [
      {
        text: "What is 3 + 4?",
        choices: [{ text: "5" }, { text: "6" }, { text: "7" }, { text: "8" }],
        correct: 2,
      },
      {
        text: "What is 11 - 2?",
        choices: [{ text: "8" }, { text: "9" }, { text: "10" }, { text: "11" }],
        correct: 1,
      },
      {
        text: "What is 5 × 6?",
        choices: [
          { text: "25" },
          { text: "30" },
          { text: "35" },
          { text: "40" },
        ],
        correct: 1,
      },
      {
        text: "What is 20 ÷ 4?",
        choices: [{ text: "4" }, { text: "5" }, { text: "6" }, { text: "7" }],
        correct: 1,
      },
      {
        text: "What is 15 + 7?",
        choices: [
          { text: "20" },
          { text: "21" },
          { text: "22" },
          { text: "23" },
        ],
        correct: 2,
      },
    ],
  };

  const start = () => {
    setAnswers([]);
    setQuestion(0);
    setStartTime(new Date());
  };

  const choose = (index: number) => {
    setAnswers((a) => {
      return [...a, index];
    });
    setQuestion((q) => {
      return q + 1;
    });
    setEndTime(new Date());
  };

  const score = answers.reduce(
    (acc, a, index) => (a === quiz.questions[index].correct ? acc + 1 : acc),
    0
  );
  const passed = score / quiz.questions.length >= 0.7;
  const time = moment
    .utc(moment(endTime).diff(moment(startTime), "milliseconds"))
    .format("HH:mm:ss");

  return (
    <div className="quiz-container">
      {question == -1 ? (
        <button onClick={() => start()}>Start</button>
      ) : question >= 0 && question < quiz.questions.length ? (
        <div>
          <h3>
            Question {question + 1}. {quiz.questions[question].text}
          </h3>
          <ol className="choices">
            {quiz.questions[question].choices.map((choice, index) => (
              <li key={index}>
                <button onClick={() => choose(index)}>{choice.text}</button>
              </li>
            ))}
          </ol>
        </div>
      ) : (
        <div className="result-screen">
          <h3 className="score">
            Score: {score} / {quiz.questions.length}
          </h3>
          <h3 className={passed ? "passed" : "failed"}>
            {passed ? "Passed. Great work!" : "Failed. Please try again."}
          </h3>
          <h3>Time: {time}</h3>
          <button onClick={() => start()}>Try again?</button>
        </div>
      )}
    </div>
  );
}
