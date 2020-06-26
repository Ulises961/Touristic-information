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

## Performance

Measured on Linux 5.4.38-1 | Arch Linux 9.3 with built-in `time` utility. 2500 in the requests.txt

```
[INFO] Scanning for projects...
[INFO] 
[INFO] ----------------< com.OpenDataHub:PP201920_project_C4 >-----------------
[INFO] Building PP201920_project_C4 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- exec-maven-plugin:1.6.0:exec (default-cli) @ PP201920_project_C4 ---
INFO  | 2020-06-26 12:45:28 | [main] main.Main (Main.java:34) - Setting parameters for making requests to the Api
INFO  | 2020-06-26 12:45:28 | [main] main.Main (Main.java:42) - Start processing the responses retrieved and saving data
INFO  | 2020-06-26 12:46:06 | [main] analysis.ComputeAnalysis (ComputeAnalysis.java:30) - Start computing analysis
INFO  | 2020-06-26 12:46:06 | [main] analysis.ComputeAnalysis (ComputeAnalysis.java:78) - Computed analysis
INFO  | 2020-06-26 12:46:06 | [main] analysis.ComputeAnalysis (ComputeAnalysis.java:104) - Save analysis file
INFO  | 2020-06-26 12:46:06 | [main] requests.ActivityDescriptionsManager (ActivityDescriptionsManager.java:128) - Analysis computed without any problem
INFO  | 2020-06-26 12:46:06 | [main] requests.ActivityDescriptionsManager (ActivityDescriptionsManager.java:137) - Activity requested from the user: 2500
Activity retrieved: 2492
ERROR | 2020-06-26 12:46:06 | [main] requests.ActivityDescriptionsManager (ActivityDescriptionsManager.java:139) - Difference of activity number due to repetition in API responses. (duplicate number: [F27989996FB9D0731878D6D706ABA79C, EECF7DAB46530B6B4623BB710D69AB73, E79CAE7C416410F6FAA22819D8FE8DDA, 8F01570A77E0A780505169AE3559D0B0, B11B1AADF6EE3270D5411FE62E6E0B47, 0BB564F39B35422A9A9971CAEC848B5D, ACD7B60456144B52838231C964460230, D074ACF48D405532C451926C89542EEE])
INFO  | 2020-06-26 12:46:06 | [main] main.Main (Main.java:46) - Execution terminated, bye bye!
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  39.116 s
[INFO] Finished at: 2020-06-26T12:46:07+02:00
[INFO] ------------------------------------------------------------------------

real    0m39,729s
user    0m47,678s
sys     0m1,035s
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

* Json [FasterXML/jackson](https://github.com/FasterXML/jackson)
* JsonSchema validation [networknt/json-schema-validator](https://github.com/networknt/json-schema-validator)
* JsonSchema jsonschema-generator [victools/jsonschema-generator](https://github.com/victools/jsonschema-generator)
* Logging [Log4j](https://logging.apache.org/log4j/2.x/)
* HttpClient [HttpAsyncClient](https://hc.apache.org/httpcomponents-asyncclient-dev/index.html)
* Testing [JUnit](https://junit.org/junit4/)

# About the Team

## Ulises

#### Challenges faced

\*Setting the instance of the Retriever class so that it requests a simoultaneously different subsets of activities.
\*Accessing a list of future elements and retrieving those that have already completed the task.

#### Tools used during the project

Athough not used in the final version of the project, during its development several tools were used and studied while coming up with solution to the different tasks posed. Below there is a list of the tools used along the way
\*Http libraries
\*Multithreading patterns
\*Lambda expresions
\*Streams

## Riccardo

#### Challenges faced

The challenges I faced was:

* how to properly use Jackson library for deserialize objects
* how to correctly use streams
* avoid "dirty code"

At the very beginning of this project, I got some problems deserializing the responses into java objects and I was making a lot of confusion with Jackson tags.
It tooks me some times to deeply undesrtand their behaviour and how to use them, i.e., for relete a getter/setter to a specific field in the json file.

Then I got some troubles making the analysis over the data. In particular it tooks some time for me to figure out how to extract data without iterate over and over the same list of ActivityDescriptions.

#### Tools used during the project

In the project I used Jackson library to serialize and deserialize objects and streams for manipulate data efficiently. Then I implemented my own exception NoLanguagesAvailable for controlling flow of the program under special conditions.

I contributed to create the multithreaded infrastructure of the project and I found very convenient exploit lambda expressions, functions, comparators... for collecting together few lines of code.

## Gabriel

### Challenges faced

I was mainly coordinating and structuring the project as I have the most experience programming/managing due to reading lots of code on bigger projects and my job in the past.
My biggest challenge was proper Maven setup as well as JsonSchema.
I took over the IO of the filesystem and my goal was to write reusable and abstracted code for ease of usage and expansion.

### Small code description

I created an Interface [FileWritable](https://gitlab.inf.unibz.it/Riccardo.Rigoni/pp_201920_project_c4/-/blob/master/src/main/java/com/OpenDataHub/fileio/FileWritable.java) which any class can implement if it's meant to be written in json format to the filesystem. The [JsonFile](https://gitlab.inf.unibz.it/Riccardo.Rigoni/pp_201920_project_c4/-/blob/master/src/main/java/com/OpenDataHub/fileio/JsonFile.java) class accepts this interface in it's constructor and handles the filewriting on calling the method `Save` on the JsonFile object.
The `JsonFile` class also contains a generic static method for deserializing any .json file into its corresponding object which has to implement the `FileWritable` interface.

### Conclusion

Creating this project was mostly a nice experience allthough I did not write a lot of code. Programming in a team really showed the strength of git in particular.
The Maven build system is great with a proper IDE. Such a build system really shines when it is set up. Managing dependencies is a breeze and their is one for everything you can imagine.
All in all it thought me a lot.