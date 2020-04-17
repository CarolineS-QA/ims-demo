
# Inventory Management System (IMS)

My first Individual Project!

It involves Java source code, JDBC and a SQL database hosted on Google Cloud Platform. To manage the project I have used Git source control, Eclipse as my IDE to run JUnit/Mockito tests, Maven to build and Jenkins as part of my CI Pipeline to send to Sonarqube (hosted on a Google Cloud VM) and to an artifact repository (Nexus).

MVP: A built application runnable via the command line interface.


Trello board for project: [initial Board](https://trello.com/b/OemgxWxd/qa-project-ims-kanban)

Presentation about the project: [on google slides.](https://docs.google.com/presentation/d/1YDEU1-LP33aZws2V7fW8h5KjA1Orr4djnfgzTaIa310/edit?usp=sharing)

**Test Coverage**
For src/main/java: 38.9%	Overall: 59.1%

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

1. Clone the repo to your machine (fork it first if you want to make changes for yourself).
2. Open git bash (git should already be initalised if you clone it).
3. It's recommended that you start making changes on a new branch ``` git checkout -b NAME-OF-YOUR-BRANCH ```
4. Open project as a maven project in an IDE of your choice
5. You can start developing!
6. To run tests you can 'Run as... JUnit tests' or 'Coverage as... JUnit tests'
7. Or go into the src/test/java via your IDE, pick a test file and run JUnit tests per file

### Prerequisites

What things you need to install the software and how to install them.

**Links for Prerequisites**

Java latest version [here](https://www.oracle.com/java/technologies/javase-downloads.html#JDK14),
Maven [here](https://maven.apache.org/),
mySQL [here](https://dev.mysql.com/downloads/installer/),
Git & Git Bash [here](https://git-scm.com/downloads),
Eclipse [here](https://www.eclipse.org/downloads/),
Jenkins [here](https://jenkins.io/download/)

**To run**

```
Java SE 8 (or later) to run the jar file.
Maven to create the jar-file. 
One of the following: mySQL on your local machine, the IP and login details for my GCP instance or your own GCP instance.
You can use the command line to run the program but git & git bash are nice to have.
```
**To develop**
```
The IDE that I used for this project was Eclipse.
As part of the CI pipeline for this project I used Jenkins.
```

<details>
<summary>Installing Demo</summary>

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
<summary>Running the tests</summary>

### Unit Tests 
JUnit is used for unit tests. A unit test will test individual methods within a class for functionality.

```
Give an example of why and how to run them
```

### Integration Tests 
Mockito is used for intergration testing. It tests how different classes interact with each other. By 'mocking' the functions that a method/class relies on we can see how the code we are testing works by assuming the parts it relies on work too.

```
Give an example of why and how to run them
```

### Coding style tests (static analysis)
Sonarqube is used for static analysis. I used it to see how well my code conformed to an industry standard, the amount of coverage for my tests, and also highlighting bugs and security warnings.

```
Give an example of why and how to run them
```
</details>


## Deployment

* Clone the repo to your machine.
* Open the cmd line / git bash inside the repo file directory
* Run the following commands:

``` mvn clean package ```

``` java -jar target/CarolineStrasenburgh-SoftwareMarch16-0.0.1-SNAPSHOT-jar-with-dependencies.jar ```

You can double check the file name (you want the jar-with-dependencies) with ``` ls target/ ```

Note: You will need a GCP instance or mySQL on your machine set up to connect to. When you execute the jar the program will run and a scanner will take in your input, then follow the instructions to use the app.


## Built With

[Maven](https://maven.apache.org/) - Dependency Management

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
for initial intro in Java wizardry and JDBC.

* Chris Perrins [christophperrins] (https://github.com/christophperrins/ims-demo)
for providing initial code base and structure to work from.

* Nick Johnson [nickrstewarttds] (https://github.com/nickrstewarttds)
for his knowledge on the Project/Java fundementals.
