#!/bin/sh

set -e

temporary_file="slick-util-temp.jar"

echo "Checking for Apache Maven existence"
if ! which mvn > /dev/null; then
    echo "Error. Looks like Apache Maven is not installed"
    exit 2
fi

echo "Downloading jar file"
wget -O ${temporary_file} "http://slick.ninjacave.com/slick-util.jar"

echo "Installing jar file for Maven"
mvn install:install-file \
    -Dfile=${temporary_file} \
    -DgroupId=org.newdawn.slick \
    -DartifactId=slick-util \
    -Dversion=1.0 \
    -Dpackaging=jar

echo "Removing temporary jar file"
rm ${temporary_file}

echo "Done"


