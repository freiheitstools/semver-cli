#!/usr/bin/env bash
#-------------------------------------------------------------------------
# Small script to run all scripts for generation of examples for
# the webpage, the readme as well the documentation.
# Examples will be written to files in the root directory with the
# extension .incl
#

set -e -u -o pipefail

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

source ${SCRIPT_DIR}/functions.sh

export -f semver
export -f find_semver_binary

SEMVER=$(find_semver_binary)
SCRIPT_PREFIX="gen-example-"
OUTPUT_EXTENSION="incl"

SEMVER_SUBCOMMANDS+=' extract'
SEMVER_SUBCOMMANDS+=' validate'
SEMVER_SUBCOMMANDS+=' strip'
SEMVER_SUBCOMMANDS+=' nextpatch'
SEMVER_SUBCOMMANDS+=' nextminor'
SEMVER_SUBCOMMANDS+=' nextmajor'
SEMVER_SUBCOMMANDS+=' ismavensnapshot'

export SEMVER

be_verbose=""

while getopts ":v" OPTION; do
  case ${OPTION} in
    v)
      true
      be_verbose=$?
      ;;
    ?)
      echo "Invalid option: -${OPTARG}."
      exit 1
      ;;
  esac
done

function run_generated_synopsis() {
  local SUBCOMMAND=$1
  local TARGET_DIR="${SCRIPT_DIR}/../generated-files/"

  [ ! -x ${TARGET_DIR} ] && mkdir ${TARGET_DIR}

  local OUTOUT="${TARGET_DIR}/gen-synopsis-${SUBCOMMAND}.${OUTPUT_EXTENSION}"

  echo "Executing subcommand ${SUBCOMMAND} and writing output to ${OUTOUT}"

  printf "\$ semver ${SUBCOMMAND} --help\n" > ${OUTOUT}
  ${SEMVER} ${SUBCOMMAND} --help >> ${OUTOUT} 2>&1 || true

  if [ ${be_verbose} ]; then
    echo "-----------------------------------------------------------"
    echo "Generated content for semver ${SUBCOMMAND} --help"
    echo "-----------------------------------------------------------"
    cat ${OUTOUT}
    echo "-----------------------------------------------------------"
    echo
  fi
}

[ -z "${SEMVER}" ] && echo semver binary not found && exit 1
[ ${be_verbose} ] && echo Using ${SEMVER} as semver binary


for sc in ${SEMVER_SUBCOMMANDS}; do
  run_generated_synopsis $sc
done

[ ! ${be_verbose} ] && echo "Run with -v to see the output generated for each subcommand"
