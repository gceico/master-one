const fs = require('fs')
const path = require('path')
const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
})

readline.question(`File name: `, fileName => {
    const filePath = path.join(__dirname, fileName)
    fs.readFile(filePath, 'utf8', main)
    readline.close()
})

function genArray(rows, cols) {
    let newArray = []
    for (i = 0; i < rows; i++) {
        let newLine = []
        for (j = 0; j < cols; j++) {
            newLine.push(undefined)
        }
        newArray.push(newLine)
    }
    return newArray
}

function swap(matrix, source, dest) {
    let newMatrix = [...matrix]
    newMatrix[source] = [...matrix[dest]]
    newMatrix[dest] = [...matrix[source]]
    return newMatrix
}

function isGreater(matrix, cols, source, dest) {
    for (j = 0; j < cols; j++) {
        const compare = matrix[source][j].localeCompare(matrix[dest][j])
        if (compare === 1) {
            return true
        }
    }
    return false
}

function sort(matrix, rows, cols) {
    let newMatrix = [...matrix]
    for (i = 0; i < rows - 1; i++) {
        for (j = 0; j < rows - i - 1; j++) {
            if (isGreater(newMatrix, cols, j, j + 1)) {
                newMatrix = [...swap(newMatrix, j, j + 1)]
                break;
            }
        }
    }
    return newMatrix
}

function parseMatrix(lines) {
    const parsedLines = []
    let rows = 0
    let cols = 0
    for (i = 0; i < lines.length; i++) {
        const value = lines[i]
        if (i == 0) rows = parseInt(value)
        if (i == 1) cols = parseInt(value)
        if (i > 1) {
            const v = value.split(' ')
            parsedLines.push([parseInt(v[0], 10), parseInt(v[1], 10), v[2]])
        }
    }
    return [parsedLines, rows, cols]
}

function main(err, contents) {
    const lines = contents.split('\n')
    const [parsedLines, rows, cols] = parseMatrix(lines)
    let matrix = genArray(rows, cols)
    for (k = 0; k < parsedLines.length; k++) {
        const [i, j, v] = parsedLines[k]
        matrix[i][j] = v
    }

    const newMatrix = sort(matrix, rows, cols)

    console.log(newMatrix)
}
