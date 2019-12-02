const { readFile } = require("../utils/reader");

const TARGET = 19690720;
const OUTPUT_POS = 0;

const exp = (noun, verb) => (100 * noun) + verb;

const data = readFile("input.txt")[0];

for (let i = 0; i <= 99; i++) {
    for (let j = 0; j <= 99; j++) {
        const execution = data.split(",").map((num) => parseInt(num, 10));
        let index = 0;

        execution[1] = i;
        execution[2] = j;

        while (index < execution.length) {
            if (execution[index] === 99) {
                break;
            }

            const param1_pos = execution[index + 1];
            const param2_pos = execution[index + 2];
            const output_pos = execution[index + 3];

            if (execution[index] === 1) {
                execution[output_pos] = execution[param1_pos] + execution[param2_pos];
            } else if (execution[index] === 2) {
                execution[output_pos] = execution[param1_pos] * execution[param2_pos];
            } else {
                console.log("ERROR");
                process.exit(1);
            }

            index += 4;
        }

        if (execution[OUTPUT_POS] === TARGET) {
            console.log(exp(i, j));
            process.exit(0);
        }
    }
}

console.log("ERROR");
process.exit(1);
