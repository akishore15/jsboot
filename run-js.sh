#!/bin/bash
# Set the URL of the website
URL=$1

# Set the output file
OUTPUT_FILE="output.txt"

# Use curl to get the HTML of the website
curl -s -o temp.html $URL

# Use grep to find all the JavaScript files
JS_FILES=$(grep -oE '<script src="[^"]+">' temp.html | cut -d'"' -f2)

# Loop through each JavaScript file
for file in $JS_FILES; do
  # Use curl to get the JavaScript file
  curl -s -o temp.js $file
  
  # Use js2py to execute the JavaScript file
  output=$(js2py temp.js)
  
  # Append the output to the output file
  echo "$output" >> $OUTPUT_FILE
done

# Remove the temporary files
rm temp.html temp.js

# Print the output
cat $OUTPUT_FILE