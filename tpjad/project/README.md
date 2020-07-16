_Ceicoschi V. Gabriel_
_George Erevanescu_
_Grupa 244, SDI_

# Magazin Online

## Introducere

Tema aleasă presupune implementarea unei soluții pentru un magazin online folosind Spring și Hibernate. Soluția iși propune sa atingă câteva obiective principale din strategia de bussiness a unui magazin online, astfel:
Crearea unui cont de utilizator, logarea în aplicatie
Vizualizarea, sortarea și filtrarea produselor după diferiți parametrii
Adaugarea de produse în cosul de cumpărături, specificarea cantitatii, stergerea produselor din cos, etc.
Finalizarea comnezii prin completarea unui formular, anularea comnezii și intoarcerea la ecranul principal
Ne propunem sa implementam aceasta soluție folosind Spring pentru a crea un API REST, Hibernate pentru ORM, iar MySQL ca modalitate principala de stocare a datelor. De asemenea, pentru partea de VIEW a modelului MVC, ne propunem sa folosim React.

API-ul va fi deployed într-un container Tomcat, iar componenta de React va fi servita drept continut static din același context sau dintr-un context extern.

# Arhitectura

## Clase și UML

Arhitectura aplicație cuprinde mai multe entitati care vor mapa tablele MySQL, iar cele mai importante sunt:

> public class Product {
> private String id;
> private String name;
> private String category;
> private String description;
> }

Clasa _Product_ este responsabila de table Products din baza de date. Aceasta clasa este subordonata de LineItem care reprezintă oferta pentru un produs anume, astfel de aici se controleaza prețul și cantitatea unui produs anume.

> public class Account {
> private String id;
> private address Address;
> private is_closed Boolean;
> }

Clasa _Account_ mapeaza elementele din tabela Accounts și este în relație de 1-1 cu clasa Customer. Astfel prin intermediul acestora se gestioneaza accesul unui utiliator la magazin, adresele de livrare și comenzile active de pe acel cont de utilizator.

> public class Order {
> private String id;
> private ordered Date;
> private shipped Date;
> private ship_to Address;
> private total Integer
> }

Clasa _Order_ reprezintă de fapt o comanda plasata. Astfel, prin aceasta clasa putem spune când,unde și cui trebuie livrat fiecare produs.

## Endponit-uri și Controllere

_/products_
Acest enpoint este responsabil de operatiile CRUD la nivel de Products. De asemenea aici se pot lista produsele după diferiți parametrii de sortare și filtrare. De acest enpoint este responsabil Products.Controller care determina în ce maniera să se interpreteze cererea cleintului.
_/login, /register_
Aceste enpoint-uri sunt responsabile de autentificarea utilizatorului în aplicație. Aici avem doua controllere care vor gestiona tabela Accounts în functiile de nevoile clientului.
_/order_
Acest enpoint va primi continutul formularului de comanda și va crea o inregistrare noua în tabela Orders cu detaliile comenzii.

_/cart_
Acest enpoint este responsabil de salvarea produselor și a cantitatii acestora în cosul de cumpărături ca care va fi ulterior folosit la finalizarea comnezii.

## Testare

Pentru componenta de frontend s-a realizat testare manuala, iar pe partea de API, testarea s-a realizat prin intermediul

## Interfata

Principalele view-uri ale aplicatiie sunt:
_Pagina Produse_ – aici se pot vizualiza produse, se pot sorta după data, preț, nume și se pot filtra după categorie.
_Pagina Login/Register_ – aici utilizatorului ii este permisa intrarea în aplicație
_Pagin Cart_ – aici se poate modifica cantitatea produselor selectate, se pot sterge produse din cart și se poate accesa următorul pas din plasarea comenzii
_Pagina Order_ – cuprinde formularul cu datele utilizatorului, adresa, costurile finale ale produselor, iar acesta este ultimul pas din plasarea comnezii
_Header_ – componenta flexibila care cuprinde meniul principal al aplicației, un butonul de login/logout în funcție de starea utilizatorului

Instalare si rulare
Pentru a rula proiectul este nevoie de:

> Nodejs
> Xamp
> Maven
> _Dupa lansarea MySQL din XAMPP, se executa comanda mvn clean spring-boot:run._ > _Din directorul www se executa npm install apoi npm start_

## Contributii

### Gabriel:

-   Configurarea mediului de lucru (pom.xml, librariile necesare, popularea bazei de date)
-   Redactarea documentatiei si a materialelor ajutatoare
-   Frontend
-   Integrare
-   Modulul Order
-   Testare

### George

-   Realizarea moduleleor Products si Lineitem (Cart)
-   Realizarea endpoint-urilor necesare integrarii
-   Testare
-   Documentatie

# Bibliografie

[https://hellokoding.com/](https://hellokoding.com/)[full-stack-crud-web-app-and-restful-apis-web-services-example-with-spring-boot-jpa-hibernate-mysql-vuejs-and-docker/](full-stack-crud-web-app-and-restful-apis-web-services-example-with-spring-boot-jpa-hibernate-mysql-vuejs-and-docker/)
[https://hibernate.org/orm/](https://hibernate.org/orm/)
[https://spring.io/guides](https://spring.io/guides)
[https://reactjs.org/docs/getting-started.html](https://reactjs.org/docs/getting-started.html)
[https://www.journaldev.com/3531/spring-mvc-hibernate-mysql-integration-crud-example-tutorial](https://www.journaldev.com/3531/spring-mvc-hibernate-mysql-integration-crud-example-tutorial)
[https://howtodoinjava.com/spring-boot2/spring-boot-crud-hibernate/](https://howtodoinjava.com/spring-boot2/spring-boot-crud-hibernate/)
[https://www.javaguides.net/2019/10/spring-boot-crud-operations-example-with-jpa-hibernate.html](https://www.javaguides.net/2019/10/spring-boot-crud-operations-example-with-jpa-hibernate.html)
