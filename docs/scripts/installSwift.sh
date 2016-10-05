#!/usr/bin/env bash
###Author: Rubinder Singh

# This file is Ubuntu specific
#Please follow enter password and Y/n instructions on terminal after executing this script;
#Please make sure GIT, PYTHON and PIP is already installed before executing thsi file.

#I have followed instructions to install Swift from Openstack docs on following link:
#http://docs.openstack.org/developer/swift/development_saio.html
#This is a development setup on single node running multiple swift server i.e:
# 4 Account Nodes
# 4 Container Nodes
# 4 Object Nodes
# 1 Proxy for load balancing


#Installing dependencies
sudo apt-get update
sudo apt-get install curl gcc memcached rsync sqlite3 xfsprogs \
                     git-core libffi-dev python-setuptools \
                     liberasurecode-dev libssl-dev
sudo apt-get install python-coverage python-dev python-nose \
                     python-xattr python-eventlet \
                     python-greenlet python-pastedeploy \
                     python-netifaces python-pip python-dnspython \
                     python-mock


#I have used loopback device as data will be stored on same machine
sudo mkdir /srv
sudo truncate -s 60GB /srv/swift-disk
sudo mkfs.xfs /srv/swift-disk
echo "/srv/swift-disk /mnt/sdb1 xfs loop,noatime,nodiratime,nobarrier,logbufs=8 0 0" | sudo tee -a /etc/fstab
sudo mkdir /mnt/sdb1
sudo mount /mnt/sdb1
sudo mkdir /mnt/sdb1/1 /mnt/sdb1/2 /mnt/sdb1/3 /mnt/sdb1/4
sudo chown ${USER}:${USER} /mnt/sdb1/*
for x in {1..4}; do sudo ln -s /mnt/sdb1/$x /srv/$x; done
sudo mkdir -p /srv/1/node/sdb1 /srv/1/node/sdb5 \
              /srv/2/node/sdb2 /srv/2/node/sdb6 \
              /srv/3/node/sdb3 /srv/3/node/sdb7 \
              /srv/4/node/sdb4 /srv/4/node/sdb8 \
              /var/run/swift
sudo chown -R ${USER}:${USER} /var/run/swift
# **Make sure to include the trailing slash after /srv/$x/**
for x in {1..4}; do sudo chown -R ${USER}:${USER} /srv/$x/; done

sudo sed -i '$i \
mkdir -p /var/cache/swift /var/cache/swift2 /var/cache/swift3 /var/cache/swift4 \
chown '"${USER}"':'"${USER}"' /var/cache/swift* \
mkdir -p /var/run/swift \
chown '"${USER}"':'"${USER}"' /var/run/swift' /etc/rc.local

#Setup from code
cd $HOME;
git clone https://github.com/openstack/python-swiftclient.git
cd $HOME/python-swiftclient; sudo python setup.py develop; cd -
git clone https://github.com/openstack/swift.git
cd $HOME/swift; sudo pip install -r requirements.txt; sudo python setup.py develop; cd -
cd $HOME/swift; sudo pip install -r test-requirements.txt

#RSync setup
sudo cp $HOME/swift/doc/saio/rsyncd.conf /etc/
sudo sed -i "s/<your-user-name>/${USER}/" /etc/rsyncd.conf
sudo sed -i "s/RSYNC_ENABLE=false/RSYNC_ENABLE=true/" /etc/default/rsync
sudo setenforce Permissive
sudo setsebool -P rsync_full_access 1
sudo service rsync restart
rsync rsync://pub@localhost/


#Configure Nodes
sudo rm -rf /etc/swift
cd $HOME/swift/doc; sudo cp -r saio/swift /etc/swift; cd -
sudo chown -R ${USER}:${USER} /etc/swift
find /etc/swift/ -name \*.conf | xargs sudo sed -i "s/<your-user-name>/${USER}/"

#Swift running scripts
mkdir -p $HOME/bin
cd $HOME/swift/doc; cp saio/bin/* $HOME/bin; cd -
chmod +x $HOME/bin/*

echo "export SAIO_BLOCK_DEVICE=/srv/swift-disk" >> $HOME/.bashrc
sed -i "/find \/var\/log\/swift/d" $HOME/bin/resetswift
cp $HOME/swift/test/sample.conf /etc/swift/test.conf
echo "export SWIFT_TEST_CONFIG_FILE=/etc/swift/test.conf" >> $HOME/.bashrc
echo "export PATH=${PATH}:$HOME/bin" >> $HOME/.bashrc
. $HOME/.bashrc

remakerings
startmain

