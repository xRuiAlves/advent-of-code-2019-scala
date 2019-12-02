const fs = require("fs");

const readFile = (file_name) => (
    fs.readFileSync(file_name).toString().split("\n")
);

module.exports = {
    readFile,
};
