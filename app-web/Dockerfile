FROM adoptopenjdk/openjdk11:alpine-jre
LABEL authors="ilk07"
EXPOSE 5500
ADD moneyTransferApp-0.0.1-SNAPSHOT.jar moneytransferapp.jar
ENTRYPOINT ["java", "-jar", "moneytransferapp.jar"]