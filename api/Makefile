open-h2:
	open http://localhost:8080/h2-console/login.do

deps:
	mvn dependency:copy-dependencies 

build:
	mvn -B package --file pom.xml

run:
	mvn spring-boot:run

test:
	mvn test jacoco:report