#! /bin/bash
##############################################################################
source "/usr/lib/smartlog/smartlog.sh"

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


