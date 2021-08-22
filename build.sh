#!/usr/bin/env bash

set -euxo pipefail

cd "$(dirname "$0")"

rm -rf bin jar
mkdir -p bin jar

find src -name '*.java' | xargs javac -d bin

cd bin
find . -path '**/calculusart/*.class' | xargs jar cvef com.apprisingsoftware.mathviewers.calculusart.CalculusArtGenerator2 ../jar/calculusart.jar
find . -path '**/complex/*.class' | xargs jar cvef com.apprisingsoftware.mathviewers.complex.ComplexViewerApplet ../jar/complex.jar
find . -path '**/diffeq/*.class' | xargs jar cvef com.apprisingsoftware.mathviewers.diffeq.DiffEqGrapher ../jar/diffeq.jar
