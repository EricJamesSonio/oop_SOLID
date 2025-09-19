#!/bin/bash
mkdir -p bin
javac -d bin POS/src/pos/*.java POS/src/pos/model/*.java POS/src/pos/service/*.java POS/src/pos/discount/*.java POS/src/pos/ui/*.java POS/src/pos/ui/tui/*.java POS/src/pos/ui/gui/*.java
if [ $? -ne 0 ]; then
  echo "Compilation failed"
  exit 1
fi
echo "Running..."
java -cp bin pos.Main
