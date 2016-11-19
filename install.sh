#!/usr/bin/env bash
sudo wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
sudo sh -c 'echo "deb http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list'
sudo apt-get update
sudo apt-get install google-chrome-stable
sudo wget -O neo4j-community-3.0.1.tar.gz http://neo4j.com/artifact.php?name=neo4j-community-3.0.1-unix.tar.gz
sudo tar -xzvf neo4j-community-3.0.1.tar.gz -o neo4j-community-3.0.1/
neo4j-community-3.0.1/bin/neo4j start
sudo sleep 20
sudo curl -H "Content-Type: application/json" -X POST -d '{"password":"tanzania"}' -u neo4j:neo4j http://localhost:7474/user/neo4j/password
./neo4j-community-3.0.1/bin/neo4j-shell -c < setup.cql