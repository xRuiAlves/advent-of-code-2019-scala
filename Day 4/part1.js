const { readFile } = require("../utils/reader");

const [from, to] = readFile("input.txt")[0].split("-").map((num) => parseInt(num, 10));

const getDigits = (num) => {
    let number = num;
    const digits = [];
    for (let i = 0; i < 6; ++i) {
        digits.unshift(number % 10);
        number = Math.floor(number / 10);
    }
    return digits;
};

const rule1 = (digits) => {
    for (let i = 0; i < 5; ++i) {
        if (digits[i] === digits[i + 1]) {
            return true;
        }
    }
    return false;
};

const rule2 = (digits) => {
    for (let i = 0; i < 5; ++i) {
        if (digits[i] > digits[i + 1]) {
            return false;
        }
    }
    return true;
};

const isPassword = (num) => {
    const digits = getDigits(num);
    return rule1(digits) && rule2(digits);
};

let count = 0;
for (let i = from; i <= to; ++i) {
    if (isPassword(i)) ++count;
}
console.log(count);
