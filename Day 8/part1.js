const { readFile } = require("../utils/reader");

const IMG_WIDTH = 25;
const IMG_HEIGHT = 6;
const IMG_SIZE = IMG_WIDTH * IMG_HEIGHT;

const data = readFile("input.txt")[0].split("").map((num) => parseInt(num, 10));
const num_layers = Math.round(data.length / IMG_SIZE);
const counts = [];

for (let i = 0; i < num_layers; ++i) {
    counts.push(Array(3).fill(0));

    for (let j = 0; j < IMG_SIZE; ++j) {
        const index = (i * IMG_SIZE) + j;
        counts[i][data[index]]++;
    }
}

const least_zeros_layer = counts.reduce((prev, curr) => prev[0] < curr[0] ? prev : curr);
console.log(least_zeros_layer[1] * least_zeros_layer[2]);
