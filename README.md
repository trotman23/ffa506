# ffa506
506 project

This project users spring boot and maven to build and deploy. 

Current requirments are:
  1. java 1.8 (jdk not jre)
  2. eclipse or spring tool suite
  3. local tomcat 8 server set up in eclipse

To run and deploy simply use <b>mvn spring-boot:run</b> or use a maven build with a goal listed as <b>spring-boot:run</b>

Navagate to localhost:8080/ to view the first iteration landing page

Launching Application:

Via Eclipse:
1. import project into eclipse (file­>import­>git and add the above link to path)
2. Checkout Iteration2 branch
3. Right click project directory and select Run As..­>Maven Build… (notice the …)
4. In Goals add “spring­boot:run” and select apply
5. Open browser and go to localhost:8080/dashboard for main dashboard page

Run in terminal on MAC
1. install homebrew: http://brew.sh/
2. open command line and run 'brew install maven'
3. go to project root directory and run: mvn spring-boot:run


JUnit Testing
build tests with @Test reference
use values like assertNotNull() to check return values of functions
run specific tests in eclipse by going to run->run as-> JUnit Test
should show test report

Code Coverage Tool
1. Used EclEmma inside eclipese: http://eclemma.org
2. Downloaded using Install New software tool inside eclipse
3. Clicked coverage run on tool bar
4. Selected "Run all tests in the selected project, package or source folder
5. Added directory: src/test/java/com.ffa package
6. Select Coverage








