# Hubrick Backend's Code Challenge #

This respository contains a solution for the Hubrick Backend's Code Challenge.

### Usage: ###

```
#!bash

java -jar CsvReport.jar <SOURCE_DIR>
```

### Package description ###

* com.hubrick.csv: Reading/writing CSV files
* com.hubrick.exception: Exceptions
* com.hubrick.model: Domain of the challenge (Employee, Department and Sex)
* com.hubrick.report: Generation of the desired reports
* com.hubrick.util: Config holder and a small Statistics module

### TODO ###
Due to time constraints, the following features/improvementes have not been implemented:

* Improve robustness of CsvReader and CsvWriter so that it strictly follows RFC 4180 (preferably use an external library for it)
* Write more Unit tests for each of the components of the system
* Improve error handling / auto-correct mistakes if possible
* Implement a more generic way to generate reports based on given formulas
* Improve args parser so that it accepts a target directory
