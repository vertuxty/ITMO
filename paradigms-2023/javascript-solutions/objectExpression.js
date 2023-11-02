"use strict"
function Operations(f, f2, ind, operationType, ...args) {
    this.f = f;
    this.f2 = f2;
    this.ind = ind;
    this.arg = args;
    this.operationType = operationType;
}
Operations.prototype.toString = function() {
    return (this.arg).map((a) => a.toString()).reduce((prev, curr) => prev + " " + curr) + " " + this.operationType;
}
Operations.prototype.evaluate = function(x, y, z) {
    // console.log(this.operationType + " " + tmp.length);
    return this.ind == 0 ? (this.f).apply(this, (this.arg).map(a=> a.evaluate(x, y, z))) : (this.f2)(((this.arg).map(a=> (this.f)(a.evaluate(x, y, z)))).reduce((prev, curr) => prev + curr, 0));
}
Operations.prototype.prefix = function() {
    return this.arg.length > 0 ? "(" + this.operationType + " " + (this.arg).map((a) => a.prefix()).reduce((prev, curr) => prev + " " + curr) + ")" : "(" +  this.operationType + " )";
}

function Negate(val1) {
    return new Operations((a => -a), a => a, 0,  "negate", val1);
}

function Add(...args) {
    return new Operations((a, b) => a + b, a => a, 0, "+", ...args);
}

function Subtract(...args) {
    return new Operations((a, b) => a - b, a => a, 0,"-", ...args);
}

function Multiply(...args) {
    return new Operations((a, b) => a * b, a => a, 0, "*", ...args);
}

function Divide(...args) {
    return new Operations((a, b) => a / b, a => a, 0, "/", ...args);
} 

function Exp(val) {
    return new Operations(Math.exp, a => a, 0, "exp", val);
}
// Exp = createOperation("exp", func, diff)...

function Ln(val) {
    return new Operations(Math.log, a => a, 0, "ln", val);
}

function ArcTan(val) {
    return new Operations(Math.atan, a => a, 0, "atan", val);
}

function ArcTan2(...args) {
    return new Operations(Math.atan2, a => a, 0, "atan2", ...args);
}

function Sinh(val1) {
    return new Operations(Math.sinh, a => a, 0, "sinh", val1);
}

function Cosh(val1) {
    return new Operations(Math.cosh, a => a, 0, "cosh", val1);
}

function Log(...args) {
    return new Operations((a, b) => Math.log(Math.abs(b))/Math.log(Math.abs(a)), "log", ...args);
}

function Pow(...args) {
    return new Operations((a, b) => Math.pow(a, b), "myBase.pow", ...args);
}

function Mean(...args) {
    return new Operations((a, b) => a/(args.length) + b/(args.length), "mean", ...args);
}

function Meansq(...args) {
    return new Operations((a) => Math.pow(a, 2)/args.length, a => a ,1, "meansq", ...args);
}

function Softmax(...args) {
    return new Operations("softmax", ...args);
}

function Sum(...args) {
    return new Operations((a) => a, a => a, 1, "sum", ...args);
}

function Avg(...args) {
    return new Operations((a) => a, a=> a/args.length, 1, "avg", ...args);
}

function Sumexp(...args) {
    return new Operations(Math.exp, a => a,  1, "sumexp", ...args);
}

function RMS(...args) {
    return new Operations((a) => Math.pow(a, 2)/args.length, Math.sqrt, 1, "rms", ...args);
}

function LSE(...args) {
    return new Operations((a) => Math.exp(a), Math.log, 1, "lse", ...args);
}

function ConstVar(val) {
    this.val = val;
}
ConstVar.prototype.toString = function() {
    return this.val + "";
}
ConstVar.prototype.prefix = function() {
    return this.val + "";
}
ConstVar.prototype.evaluate = function(x, y, z) {
    if (isNaN(this.val)) {
        switch(this.val) {
            case "x": return x;
            case "y": return y;
            case "z": return z;
            default: console.log("Wrong variable name!");
        }
    }
    return parseFloat(this.val);
}

function Const(val) {
    return new ConstVar(parseFloat(val));
}


function Variable(val) {
    return new ConstVar(val);
}

const typesOfVariable = new Map([["x", "x"], ["y", "y"], ["z", "z"]]);
let nAryOperations = {"+": 2, "-": 2, "/": 2, "*": 2, "log": 2, "myBase.pow": 2, "min3": 3, "max5": 5, "negate": 1, "sinh": 1, "cosh": 1, "exp": 1, "ln": 1, "atan": 1, "atan2": 2};
let diffAryOperations = {"mean": "mean", "var": "var", "sumexp": "sumexp", "softmax": "softmax", "lse": "lse", "meansq": "meansq", "rms" : "rms", "sum": "sum", "avg" : "avg"};
let createOp = {"+": Add, "-": Subtract, "*": Multiply, 
                "log": Log, "myBase.pow": Pow,
                 "negate": Negate, "sinh": Sinh, 
                "cosh": Cosh, "/": Divide, "mean": Mean, 
                "lse": LSE, "sumexp": Sumexp, "rms": RMS, "meansq": Meansq, "sum" : Sum, "avg": Avg,
                "exp": Exp, "ln": Ln, "atan": ArcTan, "atan2": ArcTan2};


function parse(expression) {
    const stack = [];
    expression = expression.trim().split(/\s+/);
    for (const operand of expression) {
        if (typesOfVariable.has(operand)) {
            stack.push(new Variable(operand));
        } else if (!isNaN(operand)) {
            stack.push(new Const(operand));
        } else if (operand in nAryOperations) {
            stack.push(new createOp[operand](...stack.splice(-nAryOperations[operand])));
        }
    }
    return stack[0];
}

console.log(new Add(new Variable("x"), new Const(2)).evaluate(2, 0, 0))






//======================================================  HOMEWORK 8 =================================================



function ParseError(message) {
    this.message = message;
} 
ParseError.prototype = Object.create(Error.prototype);
ParseError.prototype.message = function() {
    return this.message;
}

function EmptyEquationError(message) {
    return new ParseError(message);
}

function BracketsError(message) {
    return new ParseError(message);
}

function WrongArgumentError(message) {
    return new ParseError(message);
}

function WrongOpeationError(message) {
    return new ParseError(message);
}

function MissOperationError(message) {
    return new ParseError(message);
}

function BaseParser(expression) {
    this.expression = expression;
    this.pointer = 0;
    this.END = "END";
    this.ch = expression[0];
    this.getCh = () => this.ch;
    this.hasNext = () => this.pointer < this.expression.length;
    this.next = () => this.expression[++this.pointer];
    this.test = (expected) => this.expression[this.pointer] === expected;
    this.eof = () => this.END === this.ch; 
    this.take = () => this.ch = this.hasNext() ? this.next() : this.END;
    this.skipWs = function() {
        while (/\s/.test(this.ch)) {
            this.take();
        }
    }
    this.btwZeroAndNine = () => this.ch >= '0' && this.ch <= '9';
}

// console.log(parsePrefix("-a"));

function parsePrefix(expression) {
    const parser = new BaseParser(expression.trim());
    // console.log(expression);
    let open = 0;
    let close = 0;

    function parseExpression() {
        while (!parser.eof()) {
            parser.skipWs();
            if (parser.test('(')) {
                open++;
                parser.take();
                parser.skipWs();
                let opType = "";
                if (parser.getCh() === ")") {
                    throw new BracketsError("Empty equation in brakets!");
                }
                while (parser.getCh() !== "(" && parser.getCh() !== " " && parser.getCh() !== parser.END && parser.getCh() !== undefined) {
                    opType += parser.getCh();
                    parser.take();
                }
                let parsed = parseEquation(opType);
                if (!parser.test(')')) {
                    throw new BracketsError("Not enought brackets!: (" + parsed);
                }
                close++;
                parser.take();
                parser.skipWs();
                if (!parser.eof() && parser.getCh() !== undefined && open === close) {
                    throw new WrongArgumentError("Expected EOF, found: " + parser.getCh());
                }
                return parsed;
            } else {
                return parseConstVar();
            } 
        }
    }
    
    function parseEquation(opType) {
        parser.skipWs();
        let stack = [];
        if (opType in nAryOperations) {
            while (stack.length !== nAryOperations[opType]) {
                stack.push(parseExpression());
            }
            return new createOp[opType](...stack.slice(-nAryOperations[opType]))
        } else if (opType in diffAryOperations) { 
            while (!parser.test(')')) {
                if (!parser.test(' ')) {
                    stack.push(parseExpression());
                }
            }
            return new createOp[opType](...stack.slice(-stack.length));
        } else {
            throw new WrongOpeationError("Operation is not supported: " + opType);
        }
    }

    function parseConstVar() {
        // console.log(parser.getCh());
        parser.skipWs();
        let flag = false;
        if (parser.test('-')) {
            parser.take();
            if (!isNaN(parser.getCh())) {
                if ((open <= close && parser.getCh() === " ")) {
                    throw new BracketsError("- " + "number");
                }
                flag = true;
                return parseConst(flag);
            } else if (parser.getCh() !== "(") {
                throw new WrongArgumentError("Wrong number format!");
            }
            return parseVar();
        } else {
            if (!isNaN(parser.getCh())) {
                return parseConst(flag);
            }
            return parseVar();
        }
    }
    return parseExpression();
    
    function parseConst(flag) {
        let num = "";
        parser.skipWs();
        while (parser.btwZeroAndNine()) {
            num += parser.getCh();
            parser.take();
        }
        num = flag ? "-" + num : num;
        parser.skipWs();
        return new Const(num);
    }

    function parseVar() {
        let variab = parser.getCh();
        parser.take();
        while (parser.getCh() !== ")" && parser.getCh() !== " " && !parser.eof() && parser.getCh() !== undefined) {
            if (parser.getCh() === "(" && open > 0) {
                break;
            }
            variab += parser.getCh();
            parser.take()
        }
        if (!typesOfVariable.has(variab)) {
            throw new WrongArgumentError("Wrong variable: " + variab)
        }
        parser.skipWs();
        return new Variable(variab); 
    }
}
