const getPermutations = (arr) => {
    const used = Array(arr.length).fill(false);
    const solution = [];
    permute(arr, used, [], solution);
    return solution;
};

const permute = (arr, used, current, solution) => {
    if (current.length === arr.length) {
        solution.push([...current]);
        return;
    }

    for (let i = 0; i < used.length; ++i) {
        if (used[i]) continue;

        used[i] = true;
        current.push(arr[i]);
        permute(arr, used, current, solution);
        current.pop();
        used[i] = false;
    }
};

module.exports = {
    getPermutations,
};
