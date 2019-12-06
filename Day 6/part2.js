const { readFile } = require("../utils/reader");

const INITIAL = "COM";
const TARGET1 = "YOU";
const TARGET2 = "SAN";

const data = readFile("input.txt");
const pairs = {};

const getPath = (initial, target) => {
    const path = [];
    let node = initial;
    while (pairs[node] !== target) {
        path.push(pairs[node]);
        node = pairs[node];
    }
    return path;
};

const getCommonNode = (list1, list2) => {
    const set2 = new Set(list2);
    for (const elem of list1) {
        if (set2.has(elem)) {
            return elem;
        }
    }
    return null;
};

for (const entry of data) {
    const [from, to] = entry.split(")");
    pairs[to] = from;
}

const path1 = getPath(TARGET1, INITIAL);
const path2 = getPath(TARGET2, INITIAL);

const common_node = getCommonNode(path1, path2);
const d1 = path1.lastIndexOf(common_node);
const d2 = path2.lastIndexOf(common_node);
console.log(d1 + d2);
