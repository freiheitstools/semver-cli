package fhg.tooling.semver.cli;

public class ExitCodes {
    public static final int SUCCESS = 0;

    /**
     * At least one of the given version identifiers is not a valid semantic version identifier.
     */
    public static final int INVALID_VERSION_IDENTIFIER = 10;
}
