# Probleme Miniaplicatie Middleware (Hessian)

**Autor: Ceicoschi V. Gabriel, grupa 244-1**

# Enunt:

Biblioteca: Evidenta carti, autori:

-   Introduceri / stergeri / modificari carti si autori
-   Lista cartilor cu filtre dupa parti din titlu
-   Lista autorilor cu filtre dupa parti din nume
-   Tehnologii: Tehnologie: **JSON-RPC**, serviciu: **Java**, client: **C#**

# Implementare:

Problem propusa propune de fapt realizarea unui server java de tip serviciu web care folosesc tehnologia JSON-RPC si un client C# (+ un client java de test) care se conecteaza la server si ruleaza comenzi de tip CRUD.

## Server Java

Pentru serverul Java am implementat un container embeded de servleturi care expune un Servlet Default la ruta / si. Acest Servlet primeste requestul si verifica daca continutul este de tip JSON si respecta structura RPC.
Aici se instantiaza un dispatcher care implementeaza cate o metoda pentru fiecare operatie CRUD:

-   **list()** cu parametrii **tableName** si cu parametrii **tableName, searchColumn, searchValue**
-   **create()** cu parametrii **tableName, args** (args permite numar variabil de elemente)
-   **update()** cu parametrii **tableName, id, column, value**
-   **delete()** cu parametrii **tableName, id, column, value**

Pentru fiecare metoda am realizat cate o metoda in clasa `MySQL.java`. Aceasta clasa se conecteaza la instanta locala de **MySQL** si realizeaza comenzi. Pentru a realizarea conexiunii se foloseste **jdbc** cu parametrii:
`connect = DriverManager.getConnection("jdbc:mysql://localhost/library?serverTimezone=UTC", "root", "pass");`

Dupa ce conexiunea s-a realizat cu succes se pot procesa comenzile sql, de exemplu:
`ResultSet result = s.executeQuery("select * from library." + tableName + " where " + column + " like '%" + search + "%'");`

La primirea rezultatului din BD, acesta este converit in format Json si este trimis catre client.

S-a folosit o biblioteca numita `jsonrpc2` care faciliteaza parsarea jsonului si maparea metodelor. Tot acesta verifica daca jsonul este valid si daca metoda apelata este disponibila.

## Clienti

La nivel de client am creat o bucla de meniu care citeste parametrii de la tastarua si ii trimite catre server.
Aici am configurat doua modele `RequestModel` si `ResponseModel` de forma JSON-RPC pe care le folosesc pentru serializare, respectiv deserializare.
Tot aici am implementat o metoda care realizaeza o conexiune la server, trimite requestul si afiseaza raspunul

# Rulare

1. Java: Pentru a rula serverul este necesar pachetul `com.thetransactioncompany:jsonrpc2-client:+` si `com.thetransactioncompany:jsonrpc2-server:+`, dar si un container servlet (Eu am folosit Tomcat). Dupa configurarea mediulul si realizarea arhivei (cu `gradle clean build`) `.jar` si rularea acetaia cu `java -jar build/libs/ws-1.0.jar`. Aplicatia este diponibila la `http://localhost/`.

2. Clientul C#: `mkdir Program & cd Program & dotnet new console`, se copiaza continutul client.cs in Program.cs din ./Client si se ruleaza cu `dotnet run http://localhost/`

3. Pentru a rula clientul de test in java se poate apela `java -cp build/libs/ws-1.0.jar src/main/java/client/Client.java`

## Baza de date

Este necesara o instanta MySQL care sa ruleze la portul default si care contine o baza de date `library` cu tabelele `_books` si `_authors`. Toate aceste configuratii trebuie specificate in fisierul `MySql.java`.

<table>
- - - - - - - - - - - -      - - - - - - - - - - - -
| AUTHOR              |      | BOOK                |
- - - - - - - - - - - - 1..n - - - - - - - - - - - -
| id: int(3) PK       | ---< | id: int(3) PK       |
| name: varchar(30)   |      | title: varchar(30)  |
- - - - - - - - - - - -      | author_id: int(3) FK|
                             - - - - - - - - - - - -
</table>

## Meniul clientilor

-   `Select option:`
-   `0. all books]` pentru a afisa toate cartile
-   `1. search book [column, value]` pentru a face o cautare dupa titlu sau autor
-   `2. creat book [title, author_id]` pentru a insera o carte
-   `3. delete book [id]` pentru a sterge o carte
-   `4. patch book [id, column, value]` pentru a actualiza o carte
-   `5. all authors` pentru a afisa toti autorii
-   `6. search author [column, value]` pentru a face o cautare dupa numele autorului
-   `7. creat author [name]` pentru a insera un autor
-   `8. delete author [id]` pentru a sterge un autor
-   `9. patch author [id, column, value]` pentru a actualiza un autor

## Envirement

OS: **macOS Catalina v10.15.3**
Java: **Java(TM) SE Runtime Environment (build 13.0.1+9)**
Python: **Python 3.6.4**
PHP: **Zend Engine v3.3.11**

**DISCLAIMER**: Programele poat suferi probleme pe alte sisteme de operare sau versiuni de compiplatoare
