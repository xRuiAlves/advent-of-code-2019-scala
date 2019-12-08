const { readFile } = require("../utils/reader");

const IMG_WIDTH = 25;
const IMG_HEIGHT = 6;
const IMG_SIZE = IMG_WIDTH * IMG_HEIGHT;

const img = Array(IMG_HEIGHT).fill().map(() => Array(IMG_WIDTH).fill("_"));

const data = readFile("input.txt")[0].split("").map((num) => parseInt(num, 10));
const num_layers = Math.round(data.length / IMG_SIZE);

for (let i = num_layers - 1; i >= 0; --i) {
    for (let j = 0; j < IMG_SIZE; ++j) {
        const pixel = data[(i * IMG_SIZE) + j];

        const target_x = j % IMG_WIDTH;
        const target_y = Math.floor(j / IMG_WIDTH);

        if (pixel === 0) {
            img[target_y][target_x] = " ";
        } else if (pixel === 1) {
            img[target_y][target_x] = "█";
        }
    }
}

console.log(img.map((row) => row.join("")).join("\n"));
