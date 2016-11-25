#~/bin/bash

source "./smartlog.sh"

who=`whoami`
if [ "$who" != "root" ]
then
	die "You must be root to run the installer"
fi

if ! check_dir "lib" "/usr/lib"
then
	die "No /usr/lib detected"
fi


if ! check_dir "smartlog lib" "/usr/lib/smartlog"
then
	s_ex "Making smartlog directory" "mkdir /usr/lib/smartlog"
fi
cp smartlog.sh /usr/lib/smartlog/


versions=`ls /usr/lib*/python* -d`
for version in `ls $versions -d`
do
	s_ex "Deleting old folder in $version" "rm -rf $version/site-packages/smartlog"
	s_ex "Creating folder in $version" "mkdir $version/site-packages/smartlog"
	s_ex "Installing in $version"      "cp smartlog.py $version/site-packages/smartlog/__init__.py"
done
