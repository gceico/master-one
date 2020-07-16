def genArray(rows, cols):
    newArray = []
    for i in range(rows):
        newLine = []
        for j in range(cols):
            newLine.append(None)
        newArray.append(newLine)

    return newArray


def parseMatrix(lines):
    parsedLines = []
    rows = 0
    cols = 0
    for i in range(len(lines)):
        value = lines[i]
        if(i == 0):
            rows = int(value)
        if(i == 1):
            cols = int(value)
        if (i > 1):
            v = value.split(' ')
            parsedLines.append([int(v[0]), int(v[1]), v[2]])
    return [parsedLines, rows, cols]


def isGreater(matrix, cols, source, dest):
    for j in range(cols):
        compare = matrix[source][j] <= matrix[dest][j]
        if (compare == True):
            return True
    return False


def swap(matrix, source, dest):
    aux = matrix[source]
    matrix[source] = matrix[dest]
    matrix[dest] = aux
    return matrix


def sort(matrix, rows, cols):
    for i in range(rows - 1):
        for j in range(rows - i - 1):
            if (isGreater(matrix, cols, j, j + 1)):
                matrix = swap(matrix, j, j + 1)
                break
    return matrix


def main():
    name = input("File name: ")
    file = open(name, "r+")
    contents = file.read()
    lines = contents.split('\n')
    [parsedLines, rows, cols] = parseMatrix(lines)
    matrix = genArray(rows, cols)

    for k in range(len(parsedLines)):
        [i, j, v] = parsedLines[k]
        matrix[i][j] = v

    matrix = sort(matrix, rows, cols)
    print(matrix)
main()
