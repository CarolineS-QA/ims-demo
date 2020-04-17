
# Inventory Management System (IMS)

My first Individual Project! It involves Java source code, JDBC and a SQL database hosted on Google Cloud Platform.

MVP: A built application runnable via the command line interface.

Trello board for project: [My initial Trello Board](https://trello.com/b/OemgxWxd/qa-project-ims-kanban)

Presentation about the project: [Here on google slides.](https://docs.google.com/presentation/d/1YDEU1-LP33aZws2V7fW8h5KjA1Orr4djnfgzTaIa310/edit?usp=sharing)

#### Test Coverage
for src/main/java: 38.9%
overall: 59.1%

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

* Clone the repo to your machine.
* Open the cmd line / git bash inside the repo file directory
* Run the following commands:

``` mvn clean package ```

Note: if the build fails here the program is not runnable!

``` java -jar target/CarolineStrasenburgh-SoftwareMarch16-0.0.1-SNAPSHOT-jar-with-dependencies.jar ```

You can double check the file name (you want the jar-with-dependencies) with ``` ls target/ ```

### Prerequisites

What things you need to install the software and how to install them
**To run**

```
Java SE 8 (or later) to run the jar file. Latest version [here](https://www.oracle.com/java/technologies/javase-downloads.html#JDK14)

Maven to create the jar-file. Found [here](https://maven.apache.org/)

You can use the command line to run the program but git & git bash are nice to have: Link [here](https://git-scm.com/downloads)
```
**To develop** (not required)
```
The IDE that I used for this project was Eclipse which you can find [here](https://www.eclipse.org/downloads/)

As part of the CI pipeline for this project I used Jenkins which you can find [here](https://jenkins.io/download/)
```
<details>
<summary>### Installing Demo</summary>

A step by step series of examples that tell you how to get a development env running

Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo
</details>

<details>
<summary>## Running the tests</summary>

Explain how to run the automated tests for this system. Break down into which tests and what they do

### Unit Tests 
JUnit
Explain what these tests test, why and how to run them

```
Give an example
```

### Integration Tests 
Mockito
Explain what these tests test, why and how to run them

```
Give an example
```

### And coding style tests
Sonarqube
Explain what these tests test and why

```
Give an example
```
</details>

<details>
<summary>## Deployment</summary>

Add additional notes about how to deploy this on a live system.
</details>

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Caroline Strasenburgh**
* **Chris Perrins** - *initial work*

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

* Jordan Harrison [JHarry444] (https://github.com/JHarry444/MarchJDBC)
For initial into Java wizardry and JDBC.

* Chris Perrins [christophperrins] (https://github.com/christophperrins/ims-demo)
For providing initial code base structure to work from.

* Nick Johnson [nickrstewarttds] (https://github.com/nickrstewarttds)
For knowledge on the Java/Project fundementals.
