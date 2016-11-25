#! /bin/bash

##############################################################################
die() {
	echo "* $1."
	exit 1
}
LOGFILE=/dev/null
ERRORLOG=/dev/null
OUTPUTLOG=/dev/null


if ! git clone http://github.com/dcastl2/smartlog smartlog
then
	die "Could not clone smartlog repository"
fi

if [ -d smartlog ]
then
	pushd . > /dev/null 2> /dev/null
	cd smartlog
	./classes.sh
else
	die "Repository cannot be cloned"
fi

##############################################################################
source "~/lib/smartlog.sh"

popd > /dev/null 2> /dev/null
s_ex "Removing smartlog" \
     "rm -rf smartlog"

s_ex "Installing autograde" \
     "cp autograde.sh ~/bin/"

s_ex "Symlinking" \
     "ln -s ~/bin/autograde.sh ~/bin/autograde"

source "~/lib/smartlog/smartlog.sh"

repo=`pwd`



prompt_default  "Enter autograde dir" \
                "$HOME/.autograde"

autogradedir=$REPLY

offer_to_create "autograde" \
                "$autogradedir"



prompt_default  "Enter grading dir" \
                "$HOME/grading"

gradingdir=$REPLY

offer_to_create "grading" \
                "$gradingdir"
          


s_ex            "Populating autograde directory" \
                "cp -r $repo/spec/* $autogradedir/"

s_ex            "Copying parser-grader" \
                "cp -r $repo/src/autograde.jar $autogradedir/"

s_ex            "Populating autograde directory" \
                "cp -r $repo/content/* $gradingdir/"



echo "rootdir=$gradingdir" >> "$autogradedir/autograde.conf"


#if yesno "Remove repository"
#then
#	s_ex "Removing repository" "rm -rf $repo"
#fi


