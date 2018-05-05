# auto-complete-system [![Build Status](https://travis-ci.org/aarora91/auto-complete-system.svg?branch=master)](https://travis-ci.org/aarora91/auto-complete-system)
A simple query auto completion system which suggests possible query completions by training on a log file.

# Setup

1. Clone the project

```sh
git clone git@github.com:aarora91/auto-complete-system.git
```

2. Add samples to the log file
```sh
cd auto-complete-system-master
```

Add log samples to `src/main/resources/logs.txt` following the format specified in the default.
The suggestions will depend on the richness of the log samples, the more the better.

3. Install dependencies

```sh
mvn install
```

4. Train and suggest

You can either execute with the default argument(partial query) present in `pom.xml` by:

```sh
mvn exec:java
```

or run with custom arguments:

```sh
mvn exec:java -Dexec.mainClass="aarora.AutoCompleteSystem.QuerySuggestionSystem" -Dexec.args="christmas"
```

*Note:A training file dump is saved after the first run. If you change the log file, then delete the dump before running mvn exec.*
