# txt-to-pdf

Simple Java program to convert all txt, plain format files into pdf.
It uses __iText 5__, so all app is under _GNU Affero General Public License (AGPLv3)_.

## Before anything...

Check if you have installed these features in your machine:

* **JDK 11**,
* **Maven 3.6.x**,

## Build

Use Maven to build this application.

```mvn clean package```

or 

```mvn clean install -U```

## Run it!

We can use the jar standalone archive or simply executing via Maven after building it.

There are X mandatory arguments:

1) file path, e.g. ```/home/user/directory/file.txt``` (absolute path) or ```file.txt```(relative path to the jar execution)
2) if page are numbered, e.g. ```true``` or ```false```
3) font size, e.g. 10, 12 or 14 (min 8, max 20)
4) font type, e.g. ```HELVETICA``` (0) or ```COURIER``` (1) or ```TIMES```(2)

Example:

```mvn exec:java -Dexec.args="/home/user/directory/file.txt true 10 0"```

This will create a pdf document of the given path, numbered, with HELVETICA 10. The file will be named ```output.pdf```.

