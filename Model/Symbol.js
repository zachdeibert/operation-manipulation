import Operators from "./Operators";

const Type = {
    "Constant": 0,
    "Operator": 1
};

export default class Symbol {
    static Type = Type;

    constructor(type, val, x, y, canMove = undefined) {
        this.type = type;
        switch (this.type) {
            case Type.Constant:
                this.value = val;
                break;
            case Type.Operator:
                this.id = val;
                break;
            default:
                throw new TypeError("Invalid symbol type");
        }
        this.x = x;
        this.y = y;
        this.canMove = canMove === undefined ? type == Type.Operator : canMove;
    }

    toString() {
        switch (this.type) {
            case Type.Constant:
                return `${this.value}`;
            case Type.Operator:
                return Operators[this.id].eqChar;
            default:
                throw new TypeError("Invalid symbol type");
        }
    }
}
