# 2-semester-eksamensprojekt
<b>Kørselsvejledning</b>
<ol>
<li>Peg Spring til din lokale MySQL server ved at navigere til resources og tilføje username og password til <code>application.properties</code>. </li>
  <li>Opsæt databasen ved at navigere til resources/sql og køre <code>DDL-create-schema.sql</code> og indsæt testdata ved at køre <code>DML-populate-schema.sql</code> i den rækkefølge. Brug eventuelt MySQL Workbench.</li>
<li>Vi anbefaler at åbne projektet i IntelliJ IDEA.</li>
<li>Ved opstart af projektet er det vigtigt at sørge for at Maven har importeret de nødvendige dependencies som kan findes i <code>pom.xml</code> filen. I IntelliJ sker dette ved at en popup nederst i højre hjørne spørger om du vil auto-importe de fundne dependencies.</li>
<li>Sørg eventuelt for at port 8080 ikke er optaget da Spring Boot benytter en Tomcat server som kræver denne port for at kunne køre.</li>
<li>Du burde nu kunne tilgå applikationen via din browser på addressen "localhost:8080"</li>
</ol>

Bemærk at fejlhåndtering ikke er implementeret og at det at oprette en booking tager udgangspunkt i et main success scenario hvor alle input indtastes efter hensigten. Eksempelvis vil et for langt telefonnummer under oprettelse af ny kunde resulterer i en exception da int datatypens max værdi overskrides.

<k>Er du i tvivl om hvad du skal logge ind med kan du kigge i <code>DML-populate-schema.sql</code> filen eller logge ind med username "Ejer1" og password "ejer1".</k>
