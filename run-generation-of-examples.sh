#!/usr/bin/env bash
#-------------------------------------------------------------------------
# Small script to run all scripts for generation of examples for
# the webpage, the readme as well the documentation.
# Examples will be written to files in the root directory with the
# extension .incl
#

set -u -e -o pipefail

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

# Attempt of a generic way to find the generated binary, whose name may change from
# version to version.
function find_semver_binary() {
  find . \
    -type f \
    \( -name "semver*.bin" -or -name "semver" \) \
    \! -path "*_site*" \
    \( -perm -u=x -and -perm -g=x -and -perm -o=x \) \
    -print \
    -quit
}

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
  ${SCRIPT} >> ${OUTOUT} 2>&1 || true

  if [ ${be_verbose} ]; then
    echo "-----------------------------------------------------------"
    echo "Generated output by ${SCRIPT}"
    echo "-----------------------------------------------------------"
    cat ${OUTOUT}
    echo "-----------------------------------------------------------"
    echo
  fi
}

# export -f exports a shell function
export -f semver
export -f find_semver_binary


SEMVER=$(find_semver_binary)
SCRIPT_PREFIX="gen-example-"
OUTPUT_EXTENSION="incl"

export SEMVER

[ -z "${SEMVER}" ] && echo semver binary not found && exit 1
[ ${be_verbose} ] && echo Using ${SEMVER} as semver binary

rm -f *.${OUTPUT_EXTENSION} || true

LANG=C find . \
  -type f \
  -name "${SCRIPT_PREFIX}*" \
  -not -name "*.${OUTPUT_EXTENSION}" \
  -print \
  | grep -v _site \
  | while read file; do run_example ${file} ; done

[ ! ${be_verbose} ] && echo "Run with -v to see the output generated by each script"
