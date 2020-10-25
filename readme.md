###### Package delivery program

Run command "mvn clean compile assembly:single" to create executable jar file. Then, artifact is generated inside the target directory. Run program using command "java -jar bsc-1.0-SNAPSHOT-jar-with-dependencies.jar" in the directory containing the generated jar file. Program can take one argument specifying filename of file containing lines being considered as an initial load of package information.

Possible commands:

- add new package in following format [weight: positive number > 0, maximal 3 decimal places, . (dot) as decimal separator][space][postal code: fixed 5 digits]
- Write 'quit' to end program.

No action is performed in case of invalid input.