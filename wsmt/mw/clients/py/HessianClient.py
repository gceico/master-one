import sys
from pyhessian.client import HessianProxy

url = "http://localhost:8080/"
# url = "http://localhost:8080/HessianServer.php"

if len(sys.argv) > 1:
    url = sys.argv[1]

proxy = HessianProxy(url)


def showMenu():
    print("Select option:")
    print("---BOOKS---------------------------------")
    print("0. all books")
    print("1. search book by [column, value]")
    print("2. insert book [title, author_id]")
    print("3. delete book [id]")
    print("4. patch book [id, column, value]")
    print("---AUTHORS-------------------------------")
    print("5. all authors")
    print("6. search author by [column, value]")
    print("7. insert author [name]")
    print("8. delete author [id]")
    print("9. patch author [id, column, value]")


def main():
    while (True):
        showMenu()
        choice = 0
        params = []
        tableName = "_books"

        booksChoices = [0, 1, 2, 3, 4]
        choicesMap = ["all", "search", "create", "delete", "update"]

        choice = int(input())

        if choice in booksChoices:
            tableName = "_books"
        else:
            tableName = "_authors"

        if (choice != 0 and choice != 5):
            params = input().split(", ")
        try:
            if (choicesMap[choice % 5] == "all"):
                print(proxy.list(tableName))

            if (choicesMap[choice % 5] == "search"):
                print(proxy.search(tableName, params[0], params[1]))

            if (choicesMap[choice % 5] == "create"):
                print(proxy.create(tableName, params))

            if (choicesMap[choice % 5] == "delete"):
                print(proxy.delete(tableName, int(params[0])))

            if (choicesMap[choice % 5] == "update"):
                print(proxy.update(
                    tableName, int(params[0]), params[1], params[2]))
        except Exception as e:
            pass


if __name__ == "__main__":
    main()
