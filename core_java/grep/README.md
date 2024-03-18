# Introduction

This project is designed to collect and report lines that match a specified regex recursively in a
provided directory. The application leverages a combination of Java 8, Docker, streams/lambdas,
regex, and SLF4j. Docker is employed for containerization, ensuring portability and scalability of
the application across various environments. Java 8 is utilized to loop through our directories and 
read through our files to find matching lines. Streams are used to allow for better heap memory 
management, allowing our application to process larger files. Lastly, we utilize SLF4j to keep track
of logging issues, errors, and debugging purposes.

# Quick Start
Assuming you have docker desktop installed and running simply run the following commands after
cloning the project.

```shell
cd core_java/grep
# Docker image requires jar to be built beforehand
mvn clean compile package

docker pull galegoer/grep

docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log \
galegoer/grep "regex_to_test" "directory_to_search" "output_file"

```
The docker image is located at https://hub.docker.com/r/galegoer/grep

If you are not running docker you can simply open and run the project through a java IDE and change
the run/debug configurations similar to the below command. Otherwise, you can simply run the 
following commands. (Assumed maven and java is installed) The pom.xml has been set up to build an
uber/fat jar to include our logging dependencies.


```shell
cd core_java/grep
mvn clean compile package

# jar will be created in target directory
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp .*Romeo.*Juliet.* ./data \
./out/grep.txt
# run the jar
java -jar .\target\grep-1.0-SNAPSHOT.jar "regex_to_test" "directory_to_search" "output_file"
```

# Implementation

## Pseudocode
Note: matchedLines have been converted to a stream to account for memory issues
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

## Performance Issue
Due to the original solution of storing every matching line in an ArrayList, improvements are to be
made in case of low heap memory and a high output of matching lines. One of the solutions to be 
implemented was the conversion of ArrayLists to Streams. Java Streams do not store the data and
compute on demand, therefore no matter the number of matching lines memory issues won't arise.

# Test
Testing this application required testing both the original demo program and the created docker 
image. First some test text files were created with matching regex lines and various non-matching 
lines, they were then placed in various subdirectories to ensure they are recursively checked and 
the provided demo-app along with grep was tested to confirm the matching lines in these 
directories.

# Deployment
First we create the following dockerfile.
```
# Dockerfile
FROM openjdk:8-alpine
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
ENTRYPOINT ["java","-jar","/usr/local/app/grep/lib/grep.jar"]
```
Run the following commands in the terminal.
```shell
#Package your java app
mvn clean package
#build a new docker image locally
docker build -t ${docker_user}/grep .
#verify your image
docker image ls | grep "grep"
#run docker container (you must undertand all options)
docker run --rm \
-v `pwd`/data:/data -v `pwd`/log:/log \
${docker_user}/grep .*Romeo.*Juliet.* /data /log/grep.out

#push your image to Docker Hub
docker push ${docker_user}/grep
```


# Improvement
- I would like to add some more information provided in each line produced in the
output file therefore users can gain more information about each line that was matched
using the provided regex
- I would like to provide the ability to only access certain files, specify the file
types to search within the specified directory or a specific regex of files
- I would like to add the ability to search multiple regexes recursively and set a limit on the
recursive level of the directories searched