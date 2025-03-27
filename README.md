# Indexer

A command-line Java application built with Spring Boot that processes text files according to custom indexing rules.

## How to build

Make sure you have Java 17+ and Maven installed.

```
mvn clean install
```


This will generate a runnable JAR file in target/.


## How to Run 

```
java -jar target/csg-0.0.1-SNAPSHOT.jar file1.txt file2.html
```

### Example with provided files:

```
java -jar target/csg-0.0.1-SNAPSHOT.jar src/main/resources/file1.txt src/main/resources/file2.html 
```

Expected output:
```
File: src/main/resources/file1.txt
UpperCaseWord: 2
WordsLongerThanFive: 1

File: src/main/resources/file2.html
UpperCaseWord: 3
WordsLongerThanFive: 34

```