import csv
import json
import sys

#usage:
#csv2json.py INPUTPATH.CSV OUTPUTPATH.JSON

# Constants to make everything easier
CSV_PATH = sys.argv[1]
JSON_PATH = sys.argv[2]
   

# Open the CSV  
f = open(CSV_PATH, 'rU' )  
# Change each fieldname to the appropriate field name. I know, so difficult.  
reader = csv.DictReader( f, fieldnames = ( "fieldname0","fieldname1","fieldname2","fieldname3" ))  
# Parse the CSV into JSON  
out = json.dumps( [ row for row in reader ] )  
print "JSON parsed!"  
# Save the JSON  
f = open( JSON_PATH, 'w')  
f.write(out)  
print "JSON saved!"  