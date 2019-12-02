const { readFile } = require("../utils/reader");

const getFuel = (mass) => Math.floor(mass / 3) - 2;

const data = readFile("input.txt");
const ans = data.map(getFuel).reduce((prev, curr) => prev + curr);
console.log(ans);
