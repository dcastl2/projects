#! /bin/bash

##############################################################################
die() {
	echo "* $1."
	exit 1
}
LOGFILE=/dev/null
ERRORLOG=/dev/null
OUTPUTLOG=/dev/null

who=`whoami`
if [ $who != "root" ]
then
	die "You need to be root to run the installer"
fi

if command git > /dev/null
then
	echo "* You need to install git."
fi

if [ -d /tmp/smartlog ]
then
	rm -rf /tmp/smartlog
fi

if ! git clone http://github.com/dcastl2/smartlog /tmp/smartlog
then
	die "Could not clone smartlog repository"
fi

if [ -d /tmp/smartlog ]
then
	pushd . > /dev/null 2> /dev/null
	cd /tmp/smartlog
	./install.sh
else
	die "Repository cannot be cloned into /tmp"
fi

##############################################################################
source "/usr/lib/smartlog/smartlog.sh"

s_ex "Removing /tmp/smartlog" \
     "rm -rf /tmp/smartlog"

popd > /dev/null 2> /dev/null
s_ex "Installing autograde" \
     "cp autograde.sh /usr/bin/"

s_ex "Symlinking" \
     "ln -s /usr/bin/autograde.sh /usr/bin/autograde"
