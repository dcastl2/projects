#!/bin/bash
#
# This auto-grades the progN directory whose number N is given.
# It should be invoked within the grading/ folder.

# Get time as a UNIX timestamp (seconds elapsed since Jan 1, 1970 0:00 UTC)
T="$(date +%s)"


source '/usr/lib/smartlog/smartlog.sh'


###############################################################################
# Usage.
###############################################################################
usage() {
	echo ""
	echo ""
	echo "${BOLD}usage${NORMAL}: autograde ${ULINE}assignment${NORMAL}"
	echo ""
	echo " -p                 do ${BOLD}not${NORMAL} pre-process";
	echo " -c                 do ${BOLD}not${NORMAL} compile";
	echo " -r                 do ${BOLD}not${NORMAL} run";
	echo " -q                 do ${BOLD}not${NORMAL} post-process";
	echo ""
	echo " -v                 display version and exit";
	echo " -a ${ULINE}dir${NORMAL}             autograde spec directory";
	echo ""
	echo ""
}


###############################################################################
# Welcome message.
###############################################################################
signature () {
	TYPEOUT=true
	helper "${BOLD}Autograde${NORMAL} v. 1.3, GNU GPL v. 2.0"
	echo ""
	TYPEOUT=false
	tip "Author: Dennis Castleberry" 
	tip "E-mail: <dcastl2@tigers.lsu.edu>"
	sleep .3
}
signature


###############################################################################
# Thanks & exit.
###############################################################################
goodbye () {
	sleep .3
	TYPEOUT=true
	helper "Thank you for using ${BOLD}Autograde${NORMAL}.";
	echo ""
	sleep .3
	exit 0;
}


###############################################################################
# Set options.
###############################################################################
log "Checking options"
while getopts "a:cpqrv" opt; do
	case $opt in
		a)
			autogradedir=$OPTARG
			;;
		c)
			nocompile="true"
			;;
		p)
			nopreprocess="true"
			;;
		q)
			nopostprocess="true"
			;;
		r)
			norun="true"
			;;
		v)
			ok
			goodbye
			;;
		?)
			fail
			usage
			exit 1
			;;
	esac
done
ok


###############################################################################
# Determine the course and assignment numbers from the argument.
###############################################################################
require_var "assignment" $1
dissolve() {
	coursenum=`echo $1 | sed 's/\/.*//'`
	assignnum=`echo $1 | sed 's/.*\///'`
}
dissolve $1
require_var "assignment number" "$assignnum"



###############################################################################
# Zero in on the autograde.conf file for the assignment.
###############################################################################
require_var_dir_default "autograde" 'autogradedir' "$HOME/.autograde"
autogradeconf=$autogradedir/autograde.conf
require_var_file "autograde configuration" "$autogradeconf"


###############################################################################
# Load variables from it, in particular the rootdir. 
###############################################################################
load_vars_from_stdin() {
	while [ "$line" != "done" ]
	do
		echo -n "> "
		read line
		if [ "$line" == "done" ]
		then
			break
		fi
		name=`echo $line | sed 's/=.*//'`
		val=` echo $line | sed 's/.*=//'`
		eval export $line
		s_ex "Setting $name to $val" \
	    		"eval export $line"
	done
}


###############################################################################
# Load variables from it, in particular the rootdir. 
###############################################################################
load_vars_from_file() {
	down
	for line in `cat $1`
	do
		name=`echo $line | sed 's/=.*//'`
		val=` echo $line | sed 's/.*=//'`
		eval export $line
		s_ex "Setting $name to $val" \
		     "eval export $line"
	done
	up
}
load_vars_from_file "$autogradedir/autograde.conf"
require_var_dir "autograde root" "$rootdir"
require_var "course number" "$coursenum"
require_dir "course"        "$rootdir/$coursenum"


###############################################################################
# Load variables from assignment autograde.conf, in particular the module.
###############################################################################
# TODO Offer to read config from stdin
assignment=$autogradedir/assignment/$1
require_dir "assignment" "$assignment"
if ! check_file "assignment configuration" "$assignment/autograde.conf"
then
	warn "No assignment-level autograde.conf file"
	if ! yesno "Proceed?"
	then
		exit 1
	fi
	nlog "Please enter configuration variables in the form name=value (type 'done' when finished):"; echo ""
	load_vars_from_stdin
else
	load_vars_from_file $assignment/autograde.conf
fi
require_var "module" $module


files=( ".pre"  ".comp"  ".exec"  ".post" )
#echo $files


###############################################################################
# Here we check for module overrides in the assignment directory.
###############################################################################
check_file_override() {
	if ! check_file "$3" "$4"
	then
		if ! check_file "$1" "$2"
		then
			die "Could not find $1 file"
		else
			FILE="$2"
		fi
	else
		FILE="$4"
	fi
}


###############################################################################
# Check to see if file overrides for any of the steps already exist.
###############################################################################
modulepath="$autogradedir/module/$module"
require_dir "module" "$modulepath"
nlog "For the overrides:"; ok
down
for file in ${files[@]}
do
	check_file_override "$file module"              "$modulepath/$module$file" \
                            "$file assignment override" "$assignment/$module$file" 
	exefiles+=($FILE)
done
up

###############################################################################
# Also for the module configuration files. Load the module-level one first,
# then the assignment-level.
###############################################################################
moduleconf=$modulepath/$module.conf
if check_file "module configuration" $moduleconf
then
	load_vars_from_file $moduleconf
fi

assignmentconf=$assignment/$module.conf
if check_file "assignment-override module configuration" $assignmentconf
then
	load_vars_from_file $assignmentconf
fi




###############################################################################
# Collect the student directories.
###############################################################################
for studentdir in `ls -d $rootdir/$coursenum/$assignnum/*`
do
	studentdirs+=($studentdir)
done


LOGFILE=$rootdir/$coursenum.$assignnum.log
if check_file "old log" $LOGFILE
then
	rm "$LOGFILE"
fi

autogradejar="$autogradedir/autograde*jar"
for studentdir in ${studentdirs[@]}
do
	progdir=$studentdir
	id=`echo $studentdir | sed 's/.*\///'`
	if ! cd $progdir 2> /dev/null
	then
		alert "Directory $id/$assignnum does not exist"
		fail
	else
		nlog "For $id/$assignnum:"; ok
		down
		for file in ${exefiles[@]}
		do
			if source $file
			then
				true
			fi
		done
		up
	fi
done


T="$(($(date +%s)-T))"
tip "The total execution took $T seconds"
goodbye
