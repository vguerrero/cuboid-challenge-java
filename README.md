# cuboid-challenge-java

<img src="https://raw.githubusercontent.com/fullstacklabs/toy-blocks/master/FSL-logo-portrait.png" alt="FullStack Labs" align="center" />

<br />

# Cuboids

## Challenge

This API manages bags and cuboids. A cuboid is a three-dimensional rectangular box. Each face of a cuboid is a rectangle
and adjacent faces meet at right angles. A cube is a cuboid with equal dimensions. A cuboid has a volume that is
straightforward to calculate.

A bag is a malleable container with adjustable dimensions, but a fixed volume. The bag can expand to hold any shape or
combination of shapes, but the volume of the bag is limited and cannot expand. In our model a bag has many cuboids.

This app has an almost complete test suite.

The tests to update and delete a cuboid are incomplete, your task is to improve them.

The other tests are valid and you must not modify them. In other words, you need to add the missing functionalities so
that these tests pass.

You should also take note of PMD. The PMD is currently passing and must pass on completion of the
challenge, without any modifications to the config.

**Note**: 
1. The only tests to be modified are tests to update and delete a cuboid. All other tests must remain unchanged. 
2. The controller tests are using real service and DB (H2 database). Some of them are broken due to inconsistency with DB. 
3. You should fix these problems related to database, according to the controllers tests, using flyway.

### Steps

To participate in this challenge take the following steps:

1. Clone this repository.
1. Create a private repository of the same name in your personal GitHub account. (Do not fork)
1. Add a second remote to your local copy of this repository and push the master branch.
1. Checkout a feature branch where you will make your changes.
1. Setup the app and get it running. Verify that the linter passes and the test suite fails.
1. Implement tests to update and delete a cuboid.
1. Add missing functionalities so the other tests pass. Do NOT modify these tests.
1. Commit as appropriate as you complete the challenge. (More than one commit is expected)
1. Push your committed changes to your repository on your feature branch.
1. Create a pull request to the master branch on your repository.
1. Invite @bencarle and @mfpiccolo to your private repository.
1. Send the link to your pull request to signify the completion of the challenge.

## Technology

This app uses the following key technologies:

- [Java 11](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3.8.3](https://maven.apache.org/download.cgi)

## Setup
Create JAVA_HOME and add the 'bin' directory of maven to the PATH in the environment variables 
Use `mvn -Dmaven.test.skip=true clean install` to install the dependencies:

```shell script
mvn -Dmaven.test.skip=true clean install
```
## Usage

Run the app (Or you can run in your IDE). 

```shell script
mvn spring-boot:run
```

Run the PMD.

```shell script
mvn pmd:pmd
```

Run the tests.

```shell script
mvn test
```
