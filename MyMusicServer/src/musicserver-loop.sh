#!/bin/bash

# gehe in die directory, in der sich das skript befindet
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd $DIR
#pwd

while true
do
    java -jar MyMusicServer.jar
done