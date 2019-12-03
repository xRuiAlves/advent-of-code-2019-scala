const { readFile } = require("../utils/reader");

const wires = readFile("input.txt").map((wire) => wire.split(","));
const visited = {};
let curr_x = 0;
let curr_y = 0;
let best_place = Infinity;

const visit = (x, y, player) => {
    if (!visited[x]) {
        visited[x] = {};
    }
    if (visited[x][y] && visited[x][y] !== player) {
        best_place = Math.min(best_place, Math.abs(x) + Math.abs(y));
    }
    visited[x][y] = player;
};

const apply_step = (step, player) => {
    const dir = step[0];
    const amount = parseInt(step.substr(1), 10);

    for (let i = 0; i < amount; ++i) {
        if (dir === "R") {
            curr_x++;
        } else if (dir === "L") {
            curr_x--;
        } else if (dir === "U") {
            curr_y++;
        } else if (dir === "D") {
            curr_y--;
        }
        visit(curr_x, curr_y, player);
    }
};

wires.forEach((wire, i) => {
    wire.forEach((step) => {
        apply_step(step, i + 1);
    });
    curr_x = 0;
    curr_y = 0;
});

console.log(best_place);
