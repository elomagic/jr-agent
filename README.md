[![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/apache/maven.svg?label=License)][license]
[![build workflow](https://github.com/elomagic/elo-agent-java/actions/workflows/maven.yml/badge.svg)](https://github.com/elomagic/elo-agent-java/actions)
[![GitHub issues](https://img.shields.io/github/issues-raw/elomagic/elo-agent-java)](https://github.com/elomagic/elo-agent-java/issues)
[![Latest](https://img.shields.io/github/release/elomagic/elo-agent-java.svg)](https://github.com/elomagic/elo-agent-java/releases)
[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://github.com/elomagic/elo-agent-java/graphs/commit-activity)
[![Buymeacoffee](https://badgen.net/badge/icon/buymeacoffee?icon=buymeacoffee&label)](https://www.buymeacoffee.com/elomagic)

![](/doc/header.png "Logo")

Java agent that writes the names of the JARs used by the JVM to a file.

---

## How to use

In the JVM arguments fields, add the ``-javaagent`` argument

```shell
java -javaagent:/full/path/to/elo-agent-java-[VERSION]-jar-with-dependencies.jar -jar yourApp.jar
```

or 

```shell
java -javaagent:"C:/full/path/to/elo-agent-java-[VERSION]-jar-with-dependencies.jar" -jar yourApp.jar
```

The agent writes all loaded JAR names to the file ```elo-agent-file.csv```.

This file can be evaluated using the [elo-Agent Tool](https://github.com/elomagic/elo-agent).

## Agent schema file

The agent schema file is a comma separated file. Each line is similar to an event in the JVM.

### Columns

1. Current time in milliseconds.
2. Path as given from the JVM with normalized pfad seperator. 

## Contributing

Pull requests and stars are always welcome. For bugs and feature requests, [please create an issue](../../issues/new).

### Versioning

Versioning follows the semantic of [Semantic Versioning 2.0.0](https://semver.org/)

## License

The elo-agent-java tool is distributed under [Apache License, Version 2.0][license]

## Donations

Donations will ensure the following:

* 🔨 Long term maintenance of the project
* 🛣 Progress on the roadmap
* 🐛 Quick responses to bug reports and help requests

[license]: https://www.apache.org/licenses/LICENSE-2.0



