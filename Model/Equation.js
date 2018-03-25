import algebra from "algebra.js";
import Symbol from "./Symbol";
import Operators from "./Operators";

const State = {
    "MissingOperators": 0,
    "Equal": 1,
    "Unequal": 2
};

function checkEquation(numbers, solution, allowedOperators, str, i) {
    if (i < numbers.length) {
        return allowedOperators.find(op => checkEquation(numbers, solution, allowedOperators, `${str}${Operators[op].eqChar}${numbers[i]}`, i + 1));
    } else if (i == numbers.length) {
        if (allowedOperators.indexOf(/* - */ 2) >= 0) {
            return checkEquation(numbers, solution, allowedOperators, `${str}=-${solution}+C`, i + 1)
                || checkEquation(numbers, solution, allowedOperators, `${str}=${solution}+C`, i + 1);
        } else {
            return checkEquation(numbers, solution, allowedOperators, `${str}=${solution}+C`, i + 1);
        }
    } else {
        try {
            const eq = algebra.parse(str);
            const C = eq.solveFor("C");
            console.log(`${str}: ${C}`);
            return C == 0 || C.numer == 0;
        } catch (e) {
            return false;
        }
    }
}

export default class Equation {
    static State = State;

    static generate(numberCount, operators, callback) {
        console.log("Generating equation...");
        const iid = setInterval(() => {
            let numbers = [];
            for (let i = 0; i < numberCount; ++i) {
                numbers.push(Math.floor(10 * Math.random()));
            }
            let solution = Math.floor(10 * Math.random());
            if (checkEquation(numbers, solution, operators, "", 0)) {
                console.log("Equation generated.");
                clearInterval(iid);
                callback(new Equation(numbers, solution));
            }
        });
    }

    constructor(numbers, solution) {
        let offset = 1 / (4 + 2 * numbers.length);
        const change = 2 * offset;
        this.symbols = [];
        numbers.forEach(number => {
            this.symbols.push(new Symbol(Symbol.Type.Constant, number, offset, 0.5));
            offset += change;
        });
        this.symbols.push(new Symbol(Symbol.Type.Operator, /* = */ 0, offset, 0.5, false));
        this.symbols.push(new Symbol(Symbol.Type.Constant, solution, offset + change, 0.5));
        this.state = State.MissingOperators;
    }

    update() {
        let str = "";
        this.symbols.sort((a, b) => a.x - b.x).forEach(symbol => str += symbol.toString());
        str += "+C";
        try {
            const eq = algebra.parse(str);
            const C = eq.solveFor("C");
            this.state = C == 0 ? State.Equal : State.Unequal;
        } catch (e) {
            this.state = State.MissingOperators;
        }
    }
}
