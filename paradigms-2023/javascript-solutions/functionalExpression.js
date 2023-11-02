"use strict"

//Fixed
const operation = (f) => (...args) => (x, y, z) => f(...args.map(a => a(x, y, z)))

const add = operation((a, b) => a + b);
const subtract = operation((a, b) => a - b);
const multiply = operation((a, b) => a*b); 
const divide = operation((a, b) => a/b);
//Fixed
const negate = (operation(a => -a));
const cnst = (a) => () => a;


const pi = cnst(Math.PI);
const e = cnst(Math.E);


const one = cnst(1);
const two = cnst(2);
const sinh = operation(Math.sinh);
const cosh = operation(a => Math.cosh(a));


const floor = operation(Math.floor);
const ceil = operation(a => Math.ceil(parseFloat(a)));
const madd = (a, b, c) => (x, y, z) => (a(x, y, z) * b(x, y, z) + c(x, y, z));

const sin = (a) => (x, y, z) => Math.sin(a(x, y, z));
const cos = (a) => (x, y, z) => Math.cos(a(x, y, z));

const abs = operation(a => Math.abs(a));
const avg3 = (a, b, c) => (x, y, z) => ((a(x, y, z) + b(x, y, z) + c(x, y, z))/3);
const iff = (a, b, c) => (x, y, z) => a(x, y, z) >= 0 ? b(x, y, z) : c(x, y, z);
const med5 = (a, b, c, d, e) => (x, y, z) => (consol.log(Array.from(a(x, y, z), b(x, y, z), c(x, y, z), d(x, y, z), e(x, y, z)).sort((a, b) => a - b).toString()));
const variable = (a) => (x, y, z) => { 
    // console.log("test");
    switch(a) {
        case "x": return x;
        case "y": return y;
        case "z": return z;
        default: console.log("Wrong variable name!");
    }    
};
// const values = {"x": variable("x"), "y": variable("y"), "z": variable("z"), "e": e, "pi": pi};
// const nAryOperations = { "+": 2, "-": 2, "*": 2, "/": 2, "negate": 1, "sinh": 1, "cosh": 1};
// const createOp = {"+": add, "-": subtract, "*": multiply, "/": divide, "negate": negate, "sinh": sinh, "cosh": cosh}
const values = new Map([["x", variable("x")], ["y", variable("y")], ["z", variable("z")], ["e", e], ["pi", pi], ["one", one], ["two", two]])
const nAryOperations = new Map([["+", 2], ["-", 2], ["*", 2], ["/", 2], ["negate", 1], ["sinh", 1], ["cosh", 1], ["iff", 3], ["abs", 1], ["_", 1], ["*+", 3], ["^", 1], ["sin", 1], ["cos", 1]])
const createOp = new Map([["+", add], ["-", subtract], ["*", multiply], ["/", divide], ["negate", negate], ["sinh", sinh], ["cosh", cosh], ["iff", iff], ["abs", abs], ["*+", madd], ["_", floor], ["^", ceil], ["sin", sin], ["cos", cos]])
const parse = (expressions) => (x, y, z) => {
    // console.log(expressions);
    let stack = [];
    let expression = expressions.replace(/ +/g, ' ').trim().split(" ");
    for (const operand of expression) {
        if (values.has(operand)) { //opearnd in values
            stack.push(values.get(operand));
        } else if (nAryOperations.has(operand)) { // in nAryOpeartions
            stack.push(createOp.get(operand)(...stack.splice(-nAryOperations.get(operand)))); //nary[operand], createOp[operand]
        } else if (!isNaN(operand)) {
            stack.push(cnst(operand));
        }
    }
    return stack[0](x, y, z);
}

// console.log(abs());
// console.log(parse("x y - abs")(0, 1, 0));
