#!/usr/bin/env bash

"Usefull links
https://www.digitalocean.com/community/tutorials/como-instalar-y-usar-docker-en-ubuntu-18-04-1-es
https://linuxize.com/post/how-to-install-apache-maven-on-ubuntu-18-04/
https://www.digitalocean.com/community/tutorials/como-instalar-java-con-apt-en-ubuntu-18-04-es
"

apt-get update
# Git installation
apt-get install -y git-all
# Java installation
apt-get install -y openjdk-17-jdk
# Maven installation
wget https://dlcdn.apache.org/maven/maven-3/3.8.5/binaries/apache-maven-3.8.5-bin.tar.gz -P /tmp
tar xf /tmp/apache-maven-*.tar.gz -C /opt
cp /vagrant/maven.sh /etc/profile.d/
source /etc/profile.d/maven.sh
# Docker installation
apt-get install -y apt-transport-https ca-certificates curl software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"
apt update
apt-cache policy docker-ce
apt-get install -y docker-ce
# Fetch and run postgres database in docker
docker run --name my-postgres -e POSTGRES_PASSWORD=secret -p 5433:5432 -d postgres
# Git clone the application
mkdir /home/vagrant/application
git clone https://github.com/Eagleciam/praxis-gildedrose.git /home/vagrant/application
cd /home/vagrant/application
mvn install
# Launch the app
java -jar /home/vagrant/application/target/gildedrose-0.0.1-SNAPSHOT.jar &
# Bonus 2 : Add app start as init service
cp /vagrant/appboot.service /etc/systemd/system/
cp /vagrant/startdocker.sh /etc/init.d/
cp /vagrant/startapp.sh /etc/init.d/
update-rc.d startdocker.sh defaults 98 02
update-rc.d startapp.sh defaults 98 02
systemctl enable appboot.service
echo "Done"