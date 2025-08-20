import type { Calculator } from "./calculator.js";

export class ExpressionCalculator implements Calculator {
  /** Returns a calculation involving a, b, c, d, and e */
    calculate = (a: number, b: number, c: number, d: number, e: number): number => {
      const powerResult = this.pow(d, e);
      const multiplyResult = this.multiply(c, powerResult);
      const divideResult = this.divide(a, b);
      return this.add(divideResult, multiplyResult);
    };

    pow = (base: number, exponent: number): number => {
      return Math.pow(base, exponent);
    };

    add = (a: number, b: number): number => {
      return a + b;
    };

    divide = (a: number, b: number): number => {
      return a / b;
    };

    multiply = (a: number, b: number): number => {
      return a * b;
    };


}