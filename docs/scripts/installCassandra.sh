#!/usr/bin/env bash
###Author: Rubinder Singh

#Please follow enter password and Y/n instructions on terminal after executing this script;

#Go to home dir
cd

#Install Java 8
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
echo "JAVA_HOME=\"/usr/lib/jvm/java-8-oracle\"" | tee -a ~/.bashrc
source ~/.bashrc

#Install old version of OpenSSL lib for CQL to Work using Python 2.7.10
sudo apt-get install libssl1.0.0=1.0.2g-1ubuntu4
sudo apt-get install libssl-dev

#Install Python 2.7.10 as CQL is compatible to only this version
wget https://www.python.org/ftp/python/2.7.10/Python-2.7.10.tar.xz
tar -xf Python-2.7.10.tar.xz
cd  Python-2.7.10
./configure
sudo make clean
sudo make
sudo make install

#Go to home dir
cd

#Install Cassandra 3.7
wget http://archive.apache.org/dist/cassandra/3.7/apache-cassandra-3.7-bin.tar.gz
tar -xf apache-cassandra-3.7-bin.tar.gz
ln -s apache-cassandra-3.7 cassandra
echo CASSANDRA_HOME=$HOME/cassandra | tee -a ~/.bashrc
source ~/.bashrc

