#!/bin/bash
set -e

# necessary for mpich, but not openmpi
# perl -le 'printf "MPD_SECRETWORD=";print map { ("a".."z","A".."Z")[rand 52] } 1..40' > $HOME/.mpd.conf
# chmod 600 $HOME/.mpd.conf

RELEASE=ET_2013_11
wget --no-check-certificate https://github.com/gridaphobe/CRL/raw/$RELEASE/GetComponents
chmod +x GetComponents
./GetComponents -p -a http://svn.einsteintoolkit.org/manifest/branches/$RELEASE/einsteintoolkit.th
cd Cactus

# figure out how many cores we have
logic_cores=`cat /proc/cpuinfo | grep "model name" | wc -l`
fake_cores_per_core=`cat /proc/cpuinfo | grep "physical id" | sort | uniq | wc -l`
cores=$((($logic_cores-$fake_cores_per_core)/$fake_cores_per_core+1))

./simfactory/bin/sim setup-silent --optionlist=debian.cfg --ppn=$cores --submitscript=debian.sub --runscript=without.run
mkdir -p ~/simulations
set +e
time ./simfactory/bin/sim build --thornlist ./thornlists/einsteintoolkit.th 2>&1 | tee ET_build.log
set -e
make sim
./simfactory/bin/sim create-run tests_1 --testsuite --procs 1
./simfactory/bin/sim create-run tests_2 --testsuite --procs 2

