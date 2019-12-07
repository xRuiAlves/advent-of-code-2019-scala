const { readFile } = require("../utils/reader");
const { getPermutations } = require("../utils/combinatorics");

const jumps = {
    1: 4,
    2: 4,
    3: 2,
    4: 2,
    5: 3,
    6: 3,
    7: 4,
    8: 4,
    99: 0,
};

const NUM_APLIFIERS = 5;

const input_data = readFile("input.txt")[0].split(",").map((num) => parseInt(num, 10));
const amplifiers = getPermutations([0, 1, 2, 3, 4]);
let memory = 0;
let data = [...input_data];
let instruction_pointer = 0;
let from_phase_setting = true;
let amplifier_index = 0;
let run_number = 0;
let max_thruster_signal = 0;

const amplifierReset = () => {
    instruction_pointer = 0;
    data = [...input_data];
    from_phase_setting = true;
};

const phaseReset = () => {
    amplifierReset();

    max_thruster_signal = Math.max(max_thruster_signal, memory);

    memory = 0;
    amplifier_index = 0;
    run_number++;
    if (run_number >= amplifiers.length) {
        console.log(max_thruster_signal);
        process.exit(0);
    }
};

const parseInstruction = (raw_instruction) => {
    const numbers = raw_instruction.toString().padStart(5, 0).split("");
    const op_code = raw_instruction % 100;
    const mode_1 = parseInt(numbers[2], 10);
    const mode_2 = parseInt(numbers[1], 10);

    return {
        op_code,
        mode_1,
        mode_2,
    };
};

const sum = (instruction, a, b, c) => {
    const v1 = instruction.mode_1 ? a : data[a];
    const v2 = instruction.mode_2 ? b : data[b];

    data[c] = v1 + v2;
};

const mult = (instruction, a, b, c) => {
    const v1 = instruction.mode_1 ? a : data[a];
    const v2 = instruction.mode_2 ? b : data[b];

    data[c] = v1 * v2;
};

const jumpiftrue = (instruction, a, b) => {
    const v1 = instruction.mode_1 ? a : data[a];
    const v2 = instruction.mode_2 ? b : data[b];

    if (v1 !== 0) {
        instruction_pointer = v2 - jumps[5];
    }
};

const jumpiffalse = (instruction, a, b) => {
    const v1 = instruction.mode_1 ? a : data[a];
    const v2 = instruction.mode_2 ? b : data[b];

    if (v1 === 0) {
        instruction_pointer = v2 - jumps[6];
    }
};

const lessthan = (instruction, a, b, c) => {
    const v1 = instruction.mode_1 ? a : data[a];
    const v2 = instruction.mode_2 ? b : data[b];

    data[c] = (v1 < v2) ? 1 : 0;
};

const equals = (instruction, a, b, c) => {
    const v1 = instruction.mode_1 ? a : data[a];
    const v2 = instruction.mode_2 ? b : data[b];

    data[c] = (v1 === v2) ? 1 : 0;
};

const input = (_, a) => {
    data[a] = from_phase_setting ? amplifiers[run_number][amplifier_index] : memory;
    from_phase_setting = !from_phase_setting;
};

const output = (_, a) => {
    memory = data[a];
};

const doInstruction = (instruction, a, b, c) => {
    if (instruction.op_code === 1) {
        sum(instruction, a, b, c);
    } else if (instruction.op_code === 2) {
        mult(instruction, a, b, c);
    } else if (instruction.op_code === 3) {
        input(instruction, a);
    } else if (instruction.op_code === 4) {
        output(instruction, a);
    } else if (instruction.op_code === 5) {
        jumpiftrue(instruction, a, b, c);
    } else if (instruction.op_code === 6) {
        jumpiffalse(instruction, a, b, c);
    } else if (instruction.op_code === 7) {
        lessthan(instruction, a, b, c);
    } else if (instruction.op_code === 8) {
        equals(instruction, a, b, c);
    }   else if (instruction.op_code === 99) {
        amplifier_index++;
        if (amplifier_index >= NUM_APLIFIERS) {
            phaseReset();
        } else {
            amplifierReset();
        }
    }
};

const applyInstruction = () => {
    const instruction = parseInstruction(data[instruction_pointer]);

    doInstruction(instruction, data[instruction_pointer + 1], data[instruction_pointer + 2], data[instruction_pointer + 3]);

    return jumps[instruction.op_code];
};

while (instruction_pointer < data.length) {
    const jump = applyInstruction(instruction_pointer);
    instruction_pointer += jump;
}
