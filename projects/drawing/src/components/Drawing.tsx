import { useEffect, useRef, useState } from "react";
import { ReactP5Wrapper } from "@p5-wrapper/react";
import "./Drawing.css";

interface Point {
  x: number;
  y: number;
  stroke: number;
  color: string;
}

export default function Drawing() {
  const [color, setColor] = useState("#000000");
  const [stroke, setStroke] = useState(3);
  const [paths, setPaths] = useState<Point[][]>([]);

  const sketch = (p: any) => {
    p.setup = () => {
      p.createCanvas(800, 300, p.WEBGL);
      p.background(255);
    };

    p.draw = () => {
      p.noFill();

      paths.forEach((path) => {
        p.beginShape();
        path.forEach((point) => {
          p.stroke(point.color);
          p.strokeWeight(point.stroke);
          p.vertex(point.x - 400, point.y - 150);
        });
        p.endShape();
      });

      if (p.mouseIsPressed) {
        setPaths((paths) => {
          paths[paths.length - 1].push({
            x: p.mouseX,
            y: p.mouseY,
            color,
            stroke,
          });
          return paths;
        });
      }
    };

    p.mousePressed = () => {
      setPaths((paths) => {
        paths.push([]);
        return paths;
      });
    };
  };
  const clear = () => {
    setPaths([]);
  };

  return (
    <div className="drawing-container">
      <ReactP5Wrapper sketch={sketch} />
      <ul>
        <li>
          <label>Color</label>
          <input
            type="color"
            value={color}
            onChange={(event) => setColor(event.target.value)}
          />
        </li>
        <li>
          <label>Stroke</label>
          <input
            type="number"
            min="1"
            max="200"
            value={stroke}
            onChange={(event) => setStroke(Number.parseInt(event.target.value))}
          />
        </li>
        <li>
          <label>Clear</label>
          <button onClick={() => clear()}>Clear</button>
        </li>
      </ul>
    </div>
  );
}
