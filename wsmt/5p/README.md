# Probleme de tip I/O local in 5 limbaje

**Autor: Ceicoschi V. Gabriel, grupa 244-1**

# Enunt:

Sa se ordoneze alfabetic liniile unei matrice de stringuri prin interschimbare (Bubble sort).
Doua linii se compara mai intai prima coloana, la egalitate dupa a doua s.a.m.d
Matricea se da intr-un fisier, specificand pentru fiecare celula:
(nmarul liniei, numarul coloanei, stringul)
Numele fisierului se da in linia de comanda.
Nu se fac limitari asupra dimensiunii matricei.

# Implementare:

Problem propusa propune de fapt rezolvarea a 3 probleme particulare:

## 1. Citirea din fisier

Fiecare din cele 5 limbaje pun la dispozitie o modalitate standard de a citi din fisier.
Dupa ce contitnutul a fost citit din fisier este trimis catre urmatorul pas
In unele limbaje pasii de citire si parsare se realizeza in aceeasi functie `parseMatrix()`

## 2. Parsarea datelor cititi si crearea unei matrici

De cele mai multe ori, parsarea datelor a presupus impartirea acestora in linii, iar fiecare liniie in cele 3 componente (i, j, value). Exceptie fac primele doua linii din fisier care reprezinta numarul de linii,
respectiv de coloane ale matricei. Functia `parseMatrix()` realizeaza acest lucru si populeaza matricea care va fi sortat cu fiecare valoare. Exista posibilitatea ca liniile din fisier sa nu fie in ordinea in care apar
in matrice, dar acest lucru este tratat prin setarea explicita a elemetului cu linia `i` si coloana `j`

## 3. Sortarea (Bubble) a matricei

Pentru sortarea matricei s-a folosit simplul algoritm **Bubble Sort** care presupune interschimbarea liniilor
lexicografic. Aici s-au defenit doua functii ajutatoare `isGreater()`, verifica daca o linie este lexicofrafic mai mare decat cealalta, si `swap()` care interschimba cele doua linii si returneaza matricea noua (sau face modificarile intr-o matrice globala dupa caz)

# Rulare

1. JS: `node app.jss`
2. Java: `javac app.java` urmat de `java App`
3. Python: `python3 app.py`
4. C#: `mkdir Program & cd Program & dotnet new console`, se copiaza continutul app.cs in Program.cs din ./App si `dotnet run`
5. PHP: `php app.php`
   Cand se cere numele fisierului de intrare, sa se specifice calea relativa catre fisier: `../demo.txt`

## Fisierul de intrare trebuie sa respecte structura:

[rowCount][colcount]
[row][col] [value]
...
[row][col] [value]

## Envirement

OS: **macOS Catalina v10.15.3**
Node **v12.14.0**
Java: **Java(TM) SE Runtime Environment (build 13.0.1+9)**
Python: **Python 3.6.4**
C#: **3.1.102**
PHP: **Zend Engine v3.3.11**

**DISCLAIMER**: Programul poate suferi probleme pe alte sisteme de operare sau versiuni de compiplatoare
