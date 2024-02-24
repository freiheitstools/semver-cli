#!/usr/bin/env sh

# Small script to run all scripts for generation of examples for
# the webpage, the readme as well the documentation

# todo
SEMVER=/Users/obf/code/semver-cli/client/pure/target/semver-0.1.0-SNAPSHOT-mac.x86_64.bin
SCRIPT_PREFIX="gen-example-"
OUTPUT_EXTENSION="incl"

function semver() {
  ${SEMVER} $@
}

function run_example() {
  local SCRIPT=$1
  local OUTOUT=$(basename -s .sh ${SCRIPT}).${OUTPUT_EXTENSION}

  if [ ! -x ${SCRIPT} ]; then
    echo ${SCRIPT} is not executable. Aborting
    exit 1;
  fi

  echo "Executing ${SCRIPT} and writing output to ${OUTOUT}"

  printf '$ ' > ${OUTOUT}
  tail -n +3 ${SCRIPT} >> ${OUTOUT}
  ${SCRIPT} >> ${OUTOUT}
}

export SEMVER
export -f semver

LANG=C find . \
  -type f \
  -name "${SCRIPT_PREFIX}*" \
  -not -name "*.${OUTPUT_EXTENSION}" \
  -print \
  | while read file; do run_example ${file} ; done


