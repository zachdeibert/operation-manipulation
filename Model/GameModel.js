import Equation from "./Equation";

export default class GameModel {
    constructor() {
        this.equations = [];
        this.numberCount = 3;
        this.allowedOperators = [
            1, 2, 3, 4
        ];
    }

    addEquation(callback) {
        Equation.generate(this.numberCount, this.allowedOperators, callback ? eq => {
            this.equations.push(eq);
            callback();
        } : this.equations.push.bind(this.equations));
    }
}
