#!/bin/sh

load_base(){
	echo "Dropping db ${1}"
   	mysqladmin -h ${2} -u ${3} --password=${4} --force drop ${1}
   	
	echo "Creating db ${1}"
  	mysqladmin -h ${2} -u ${3} --password=${4} create ${1}
  	
  	echo "Loading main_db.sql to ${1}"     
	if [ -f main_db.sql ]; then
   		mysql -h ${2} -u ${3} --password=${4} ${1} < main_db.sql
	else
		echo "ERROR **** File main_db.sql is missing ***"   
	fi
	
	
	echo "Done Loading db ${1}"
}
	
all() {
	load_base waverunner2_main localhost cyborg c7b8rg
}

all
