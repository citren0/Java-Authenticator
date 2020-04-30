# Java-Authenticator
authentication demo using hashing on java

Uses sha256 hashes to do basic authentication. Will print access granted or access denied based on if the password was correct.

The CSV file follows the following format:

beginningline,beginningline
user,password
user2,password2
etc...,etc...
endline,endline

the beginningline and endline parts are mostly placeholders and prevent the csv reader from going off the file and returning null data.

The program can be used as follows:

java Authenticator.java --help
java Authenticator.java -m user password
java Authenticator.java
