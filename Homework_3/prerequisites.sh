#!/usr/bin/env bash


apt-get update
echo  "-- Git installation --" # Default version, outdated but works.
apt-get install -y git
echo  "-- Java installation --"
apt-get install -y openjdk-17-jdk # Java 17 Version, required.
echo  "-- Maven installation --"
wget https://dlcdn.apache.org/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.tar.gz -P /tmp 
tar xf /tmp/apache-maven-*.tar.gz -C /opt # Latest version at the moment, required.
cp /vagrant/maven.sh /etc/profile.d/
source /etc/profile.d/maven.sh 
echo  "-- Docker installation --" 
apt-get install -y docker.io # Default version, outdated but works.
echo  "-- Fetch and run postgres database in docker --"
docker run --name my-postgres -e POSTGRES_PASSWORD=secret -p 5433:5432 -d postgres
echo  "-- Git clone the application --"
mkdir /home/vagrant/application
git clone https://github.com/Eagleciam/praxis-gildedrose.git /home/vagrant/application
cd /home/vagrant/application
mvn install
echo  "-- Launching the app --"
java -jar /home/vagrant/application/target/gildedrose-0.0.1-SNAPSHOT.jar &
# Bonus 2 : Add app start as init service
cp /vagrant/startapp.sh /home/vagrant/
cp /vagrant/appboot.service /etc/systemd/system/
systemctl enable appboot.service
echo "Done"