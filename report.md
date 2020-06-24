# PP\_201920\_project\_C4

## INTRODUCTION

This project exploits the [South Tyrolian](https://opendatahub.bz.it/) Api and ease the user to retrieve crucial information regarding different kinds of activities in South Tyrol.

## HOW TO EXECUTE

```
mvn clean package exec:exec
```

## HOW TO TEST

```
mvn test
```

## INFRASTUCTURE

The infrastructure of the project is very simple and straightforwarad.
It is divided into:

1. Input
2. Parsing
3. Output

### Input
Divided into:

* load activity number
* get responses from the South Tyrol API


[request.txt](https://gitlab.inf.unibz.it/Riccardo.Rigoni/pp_201920_project_c4/-/blob/master/src/main/resources/requests.txt) is the input file, in which the user could change the number of activities he would like the program to analyze.

### Parsing



