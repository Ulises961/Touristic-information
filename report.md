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
3. Analysis
4. Output

### Input

Divided into:

* load activity number
* get responses from the South Tyrol API

[request.txt](https://gitlab.inf.unibz.it/Riccardo.Rigoni/pp_201920_project_c4/-/blob/master/src/main/resources/requests.txt) is the input file, in which the user can change the number of activities that he would like the program to analyze.

The retrieval of the data is not just a single call to the API: the program receives subsets of the requested activities and then collects everything together.
This choice is made in order to increase performance exploiting threads and concurrency.

### Parsing

It is divided into:

* generate [Activity](https://gitlab.inf.unibz.it/Riccardo.Rigoni/pp_201920_project_c4/-/blob/master/src/main/java/com/OpenDataHub/parser/support_classes/Activity.java) class
* generate [ActivityDescription](https://gitlab.inf.unibz.it/Riccardo.Rigoni/pp_201920_project_c4/-/blob/master/src/main/java/com/OpenDataHub/parser/support_classes/ActivityDescription.java) class

Activity class deserializes the objects received as input and ActivityDescription class is used for storing in a cleaner way the most relevant data of every activity.

Deserialization of responses exploits Jackson library that map values extracted from the String into different fields of the Activity class through the ObjectMapper.
Very useful are Jackson tags as:

```
@JsonProperty
@JsonSetter
@JsonIgnoreProperties
```

that let the program automatically understand which values to take into account during serialization/deserialization.

Reflecting as much as possible the structure of the data coming in input with the classes generated, while deserializing, is very helpful.

### Analysis

Looks at:

* id of activities that provides any kind of gps track
* occurrences of types of activities
* region that has most/less activities

The analysis over the dataset is based on streams for iterate easier through the elements. Then, exploiting basic functionalities like

```
.stream().filter()
.stream().map()
.stream().collect()
```

results very easy to extract crucial values as the max or min occurrence for a particular String element.

### Output

The [ActivityDescription](https://gitlab.inf.unibz.it/Riccardo.Rigoni/pp_201920_project_c4/-/blob/master/src/main/java/com/OpenDataHub/parser/support_classes/ActivityDescription.java) objects are serialized into .json files and the same for data resulting from analysis process.
Each activity file is named *<Activity\_Id>.json* and alysis file *analysis.json*
Files are available after execution in the *src/main/results* folder.

### Dependencies used

* Json [Jackson](https://github.com/FasterXML/jackson)
* JsonSchema validation [json-schema-validator](https://github.com/networknt/json-schema-validator)
* JsonSchema jsonschema-generator [GitHub](https://github.com/victools/jsonschema-generator)
* Logging [Log4j](https://logging.apache.org/log4j/2.x/)
* HttpClient [HttpAsyncClient](https://hc.apache.org/httpcomponents-asyncclient-dev/index.html)
* Testing [JUnit](https://junit.org/junit4/)

## About the Team

### Ulises

#### Challenges faced

\*Setting the instance of the Retriever class so that it requests a simoultaneously different subsets of activities.
\*Accessing a list of future elements and retrieving those that have already completed the task.

#### Tools used during the project

Athough not used in the final version of the project, during its development several tools were used and studied while coming up with solution to the different tasks posed. Below there is a list of the tools used along the way
\*Http libraries
\*Multithreading patterns
\*Lambda expresions
\*Streams

### Riccardo

#### Challenges faced

The challenges I faced was:

* how to properly use Jackson library for deserialize objects
* how to correctly use streams
* avoid "dirty code"

At the very beginning of this project, I got some problems deserializing the responses into java objects and I was making a lot of confusion with Jackson tags.
It tooks me some times to deeply undesrtand their behaviour and how to use them, i.e., for relete a getter/setter to a specific field in the json file.

Then I got some trobles making the analysis over the data. In particular it tooks some time for me to figure out how to extract data without iterate over and over the same list of ActivityDescriptions.

#### Tools used during the project

In the project I used Jackson library to serialize and deserialize objects and streams for manipulate data efficiently. Then I implemented my own exception NoLanguagesAvailable for controlling flow of the program under special conditions.

I contributed to create the multithreaded infrastructure of the project and I found very convenient exploit lambda expressions, functions, comparators... for collecting together few lines of code.
