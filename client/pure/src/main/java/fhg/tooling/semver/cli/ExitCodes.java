package fhg.tooling.semver.cli;

public class ExitCodes {
    public static final int SUCCESS = 0;

    /**
     * At least one of the given version identifiers is not a valid semantic version identifier.
     */
    public static final int INVALID_VERSION_IDENTIFIER = 10;

    /**
     * The given version number is not a release version number.
     */
    public static final int VERSION_IS_NOT_A_RELEASE_VERSION = 20;
}
