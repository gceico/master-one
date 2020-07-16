# Probleme Miniaplicatie Middleware (Hessian)

**Autor: Ceicoschi V. Gabriel, grupa 244-1**

# Enunt:

Biblioteca: Evidenta carti, autori:

-   Introduceri / stergeri / modificari carti si autori
-   Lista cartilor cu filtre dupa parti din titlu
-   Lista autorilor cu filtre dupa parti din nume
-   Hessian - server Java, PHP si clienti Java, PHP, Python.

# Implementare:

Problem propusa propune de fapt realizarea a 2 servere (javam, php) de tip middleware care folosesc tehnologia Hessian (RPC) si 3 clienti (php, py, java) care se conecteaza la servere si ruleaza comenzi de tip CRUD.

## Baza de date

Este necesara o instanta MySQL care sa ruleze la portul default si care contine o baza de date `library` cu tabelele `_books` si `_authors`. Toate aceste configuratii trebuie specificate in fisierul `MySql.java`.

<table>
- - - - - - - - - - - -      - - - - - - - - - - - -
| AUTHOR              |      | BOOK                |
- - - - - - - - - - - - 1..n - - - - - - - - - - - -
| id: int(3) PK       | -- < | id: int(3) PK       |
| name: varchar(30)   |      | title: varchar(30)  |
- - - - - - - - - - - -      | author_id: int(3) FK|
                             - - - - - - - - - - - -
</table>

## Server Java

Pentru serverul Java am folost exemplul din curs (ExecHessServ.java care extinde HessianServlet si implementeaza o interfata cu metodele necesare), dar la care am adaugat 4 metode noi pentru operatiile CRUD:

-   **list()** cu parametrii **tableName** si cu parametrii **tableName, searchColumn, searchValue**
-   **create()** cu parametrii **tableName, args** (args permite numar variabil de elemente)
-   **update()** cu parametrii **tableName, id, column, value**
-   **delete()** cu parametrii **tableName, id, column, value**

Pentru fiecare metoda am realizat cate o metoda in clasa MySQL.java. Aceasta clasa se conecteaza la instanta locala de **MySQL** si realizeaza realizeaza comenzi. Pentru a realizarea conexiunii se foloseste **jdbc** cu parametrii:
`connect = DriverManager.getConnection("jdbc:mysql://localhost/library?serverTimezone=UTC", "root", "pass");`

Dupa ce conexiunea s-a realizat cu succes se pot procesa comenzile sql, de exemplu:
`ResultSet result = s.executeQuery("select * from library." + tableName + " where " + column + " like '%" + search + "%'");`

La primirea rezultatului din BD, acesta este converit in format String si este trimis catre client.

## Server PHP

Asemanator cu serverul Java, s-a folosit exemplul de la curs la care s-a adaugat metodele care realizeaza operatiile CRUD. Se include libraria Hessian in fisier si se intanteza o noua conexiune la MySQL si un HessianService care se ruleaza:
`$conn = new mysqli("127.0.0.1", "root", "pass", "library");`
`$service = new HessianService(new HessianServer());`
`$service->handle();`

De data aceasta nu am mai folosit un fisier ajutator care sa realizeze comenzile SQL, acestea sunt scrise direct in fucntii:
`$sql = "update library." . $tableName . " set " . $column . " = " . '"'. $value . '"' . " where Id = " . $id;`

Arhitectura bazei de date:
`Authors (id, nume), Books (id, author, title)`

## Clienti

La nivel de client am modificat clasa ExecHessClie.java la care am adaugat o bucla de meniu care citeste parametrii de la tastarua si ii trimite catre server.

Aici am realizat un proxy dupa cum urmeaza:
`HessianProxyFactory hpf = new HessianProxyFactory()`
`hpf.setHessian2Request(true);`
`HessianInterface proxy = (HessianInterface) hpf.create(HessianInterface.class, urlServ);`

Si pentru fiecare optiune din meniu, aplez functiile disponibile in proxy dupa caz.

De asmenea, clientii **Python** si **PHP** sunt realizati intr-o maniera asemanatoare folosind pachetele Hessian specifice, acestia implementeaza aceeasi interfata la nivel de consola.

# Rulare

1. Java: Pentru a rula serverul este necesar pachetul `com.caucho:hessian:+`. Dupa configurarea mediulul si realizarea arhivei `.jar` prin comanda `gradle clean build` si rularea acesteia cu `java -jar build/libs/mw2-1.0.jar` aplicatia este diponibila la `http://localhost/`. De asemena, este necesara o instanta MySQL care sa ruleze la portul default si care contine o baza de date `library` cu tabelele `books` si `authors`. Toate aceste configuratii trebuie specificate in fisierul HessianServer.java precum si in HessianClient.java. Clientul se ruleaza folosind orice IDE sau comanda `java -cp hessian.jar HessianClient.java` (Java 11) sau `java -cp build/libs/mw2-1.0.jar src/main/java/client/Client.java`.

2. PHP: Asemanator cu serverul Java, serverul in PHP are nevoie de o conexiune la baza de date MySQL configurata asemanator si de pachetul Hessian disponibil pe siteul oficial [http://hessian.caucho.com/](http://hessian.caucho.com/). Pentru a rula un server php, se poate apela `php -S localhost:8080` in folderul in care se afla HessianServer.php (sau folosind XAMPP si adaugand fisierul mentionat in httdocs). Pentru client e sificient sa se ruleze `php HessianClient.php` sau alta metoda cunoscuta. E nevoie ca la nivel de clienti sa se schimbe URL-ul la care se fac requesturiele.

3. Ptyhon: Clientul de Python se ruleaza cu `python3 HessianClient.py` dar trebuie instalat pachetul hessian `pip3 install python-hessian`

Clients PHP URL http://localhost:8080/HessianServer.php
Clients Java URL http://localhost:8080/

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
