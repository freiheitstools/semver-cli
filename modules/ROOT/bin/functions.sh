# Attempt of a generic way to find the generated binary, whose name may change from
# version to version.
function find_semver_binary() {
  find ${SCRIPT_DIR}/../../../ \
    -type f \
    \( -name "semver*.bin" -or -name "semver" \) \
    \! -path "*node_modules*" \
    \( -perm -u=x -and -perm -g=x -and -perm -o=x \) \
    -print \
    -quit
}

function semver() {
  ${SEMVER} $@
}
