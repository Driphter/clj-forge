#!/usr/bin/env bash
gradlew clean jar
for f in build/libs/*.jar
  do
    name=`echo ${f} | sed -E 's/(.*)(-([0-9]\.)*[0-9])\.jar/\1-deobf\2.jar/'`
    cp "$f" "$name"
  done
gradlew build
