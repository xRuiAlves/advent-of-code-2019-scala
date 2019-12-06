const { readFile } = require("../utils/reader");

const INITIAL = "COM";

const data = readFile("input.txt");
const pairs = {};

for (const entry of data) {
    const [from, to] = entry.split(")");
    if (!pairs[from]) {
        pairs[from] = [];
    }
    pairs[from].push(to);
}

const depths = {};
const to_visit = [INITIAL];
depths[INITIAL] = 0;

while (to_visit.length > 0) {
    const node = to_visit.shift();

    if (!pairs[node]) continue;

    for (const other of pairs[node]) {
        depths[other] = depths[node] + 1;
        to_visit.push(other);
    }
}

const num = Object.values(depths).reduce((prev, curr) => prev + curr);
console.log(num);
