const { readFile } = require("../utils/reader");

const OUTPUT_POS = 0;

const data = readFile("input.txt")[0].split(",").map((num) => parseInt(num, 10));
data[1] = 12;
data[2] = 2;

let index = 0;
while (index < data.length) {
    if (data[index] === 99) {
        break;
    }

    const param1_pos = data[index + 1];
    const param2_pos = data[index + 2];
    const output_pos = data[index + 3];

    if (data[index] === 1) {
        data[output_pos] = data[param1_pos] + data[param2_pos];
    } else if (data[index] === 2) {
        data[output_pos] = data[param1_pos] * data[param2_pos];
    } else {
        console.log("ERROR");
        process.exit(1);
    }

    index += 4;
}

console.log(data[OUTPUT_POS]);
