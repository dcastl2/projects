#!/bin/bash

source 'smartlog.sh';
echo ""

NAME=$1
if [ -z $NAME ]
then
  helper "usage: remove.sh name";
  exit 1;
fi

#if [ ! -d $NAME ]
#then
#  alert "$NAME.vdi does not exist";
#  exit 1;
#fi

success=1;

log "Detaching IDE device"
if VBoxManage storageattach $NAME --storagectl "IDE Controller"  --port 0 --device 0 --type dvddrive --medium none 2> error.log
then
	ok
else
	fail
fi

log "Detaching SATA device"
if VBoxManage storageattach $NAME --storagectl "SATA Controller" --port 0 --device 0 --type hdd      --medium none 2>> error.log
then
	ok
else
	fail
fi

log "Closing DVD"
if VBoxManage closemedium dvd  ./$NAME.iso 2>> error.log
then
	ok
else
	fail
fi

log "Closing HD"
if VBoxManage closemedium disk --delete HardDisks/$NAME.vdi 2>> error.log
then
	ok
else
	fail
fi

log "Unregistering VM"
if VBoxManage unregistervm $NAME --delete 2>> error.log
then
	ok
else
	fail
	warn "VM not in records";
fi

if [ $success == 0 ]
then
	tip "Check error.log for details";
fi
