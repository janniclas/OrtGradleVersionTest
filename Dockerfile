FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
RUN apt-get update && apt-get -y upgrade && apt-get -y install git && apt-get clean
COPY . OrtGradleVersionTest
RUN git clone https://github.com/janniclas/OldGradleVersion.git
RUN git clone https://github.com/janniclas/NewGradleVersion.git

WORKDIR OrtGradleVersionTest
RUN ./gradlew build

ENTRYPOINT ["/app/OrtGradleVersionTest/gradlew", "run"]
CMD ["--help"]