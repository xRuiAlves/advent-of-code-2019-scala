const { readFile } = require("../utils/reader");

const getFuel = (mass) => Math.floor(mass / 3) - 2;

const data = readFile("input.txt");

const ans = data.reduce((prev, curr) => {
    let sum = 0, current_mass = curr;

    while (current_mass > 0) {
        current_mass = getFuel(current_mass);
        sum += Math.max(current_mass, 0);
    }
    return prev + sum;
}, 0);

console.log(ans);
