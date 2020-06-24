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

## INFRASTRUCTURE

The infrastructure of the project is very simple and straightforward.
It is divided into:

1. Input
2. Parsing
3. Output

### Input
Divided into:

* load activity number
* get responses from the South Tyrol API


[request.txt](https://gitlab.inf.unibz.it/Riccardo.Rigoni/pp_201920_project_c4/-/blob/master/src/main/resources/requests.txt) is the input file, in which the user can change the number of activities that he would like the program to analyze.

The retrival of the data is not just a single call to the API: the program receives subsets of the requested activities and then collects everything together.
This choice is made in order to increase performance. 

### Parsing





