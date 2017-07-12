# fxCalculator
Calculate foreign exchange rate for a given  pair of tradeable currencies

##Development Stack
- Java 8
- Spring 4
- Maven
- JUnit 4
- Mockito
- Jacoco

##Pre-requisites
- Install Java 8
- Install Maven

##Getting the code base
- Please use the following git command to clone the project from the remote repository
  - git clone https://github.com/RajeAshok/fxCalculator.git

  
##Running the project
 - Please navigate to the folder fxCalculator 
 
 - Please run the application using the below mvn command
   - mvn spring-boot:run -Drun.arguments="INPUT_STRING"
    - INPUT_STRING must be provided in the format <BASE_CURRENCY> <EXCHANGE_AMOUNT> IN <TERM_CURRENCY> 
    - eg. mvn spring-boot:run -Drun.arguments="JPY 100.0 in NOK"

   
 - Please run the tests using the command
   - mvn clean test
   
 - Please check the code coverage reports at   
   - /fxCalculator/target/site/jacoco-ut/index.html
   
##Developer Notes
   - The default log level for the application is INFO. To enable logging at DEBUG level, please
   update the log level configured in /src/main/java/resources/logback.xml

