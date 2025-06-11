import { useState } from "react";
import "./Calculator.css";

enum Operation {
  None = "",
  Equals = "=",
  Add = "+",
  Subtract = "-",
  Multiply = "x",
  Divide = "/",
}

function Calculator() {
  const maxDisplayLength = 8;
  const maxDisplayLengthError = "ERR: MAX EXCEEDED";
  const maxDecimalPlaces = 2;
  const defaultValue = "0";
  const defaultOperation = Operation.None;
  const [display, setDisplay] = useState(defaultValue);
  const [operation, setOperation] = useState(defaultOperation);
  const [current, setCurrent] = useState(defaultValue);

  const type = (c: string) => {
    setDisplay((d) => {
      // Don't allow typing when:
      // 1. limit length
      // 2. handle .
      // 3. handle =
      if (
        d.length >= maxDisplayLength ||
        (c === "." && d.indexOf(".") !== -1) ||
        operation === Operation.Equals
      ) {
        return d;
      }

      const prev = d !== defaultValue ? d : "";
      return `${prev}${c}`;
    });
  };
  const backspace = () => {
    setDisplay((d) => {
      if (d === maxDisplayLengthError) {
        return d;
      }

      if (d === defaultValue || d.length === 1) {
        return defaultValue;
      }

      return d.slice(0, d.length - 1);
    });
  };
  const evaluate = (a: number, b: number, op: Operation) => {
    switch (op) {
      case Operation.None:
      case Operation.Equals:
        return b;
      case Operation.Add:
        return a + b;
      case Operation.Subtract:
        return a - b;
      case Operation.Multiply:
        return a * b;
      case Operation.Divide:
        return a / b;
    }
  };
  const operate = (op: Operation) => {
    const value = parseFloat(display);

    if (Number.isNaN(value)) {
      return;
    }

    const evaluated = evaluate(parseFloat(current), value, operation);
    const factor = Math.pow(10, maxDecimalPlaces);
    const rounded = Math.round(evaluated * factor) / factor;
    const result = `${rounded}`;
    setCurrent(result);
    setOperation(op);

    let next = op === Operation.Equals ? result : defaultValue;
    next = next.length <= maxDisplayLength ? next : maxDisplayLengthError;
    setDisplay(next);
  };
  const clear = () => {
    setCurrent(defaultValue);
    setOperation(defaultOperation);
    setDisplay(defaultValue);
  };

  return (
    <div className="calculator">
      <div className="row">
        <span>{display}</span>
      </div>
      <div className="row">
        <button></button>
        <button></button>
        <button onClick={() => clear()}>C</button>
        <button onClick={() => backspace()}>←</button>
      </div>
      <div className="row">
        <button onClick={() => type("7")}>7</button>
        <button onClick={() => type("8")}>8</button>
        <button onClick={() => type("9")}>9</button>
        <button onClick={() => operate(Operation.Divide)}>/</button>
      </div>
      <div className="row">
        <button onClick={() => type("4")}>4</button>
        <button onClick={() => type("5")}>5</button>
        <button onClick={() => type("6")}>6</button>
        <button onClick={() => operate(Operation.Multiply)}>x</button>
      </div>
      <div className="row">
        <button onClick={() => type("1")}>1</button>
        <button onClick={() => type("2")}>2</button>
        <button onClick={() => type("3")}>3</button>
        <button onClick={() => operate(Operation.Subtract)}>-</button>
      </div>
      <div className="row">
        <button onClick={() => type("0")}>0</button>
        <button onClick={() => type(".")}>.</button>
        <button onClick={() => operate(Operation.Equals)}>=</button>
        <button onClick={() => operate(Operation.Add)}>+</button>
      </div>
    </div>
  );
}

export default Calculator;
