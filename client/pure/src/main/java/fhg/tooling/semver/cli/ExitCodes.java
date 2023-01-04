package fhg.tooling.semver.cli;

public class ExitCodes {
    public static final int SUCCESS = 0;

    /**
     * At least one of the given version identifiers is not a valid semantic version identifier.
     */
    public static final int INVALID_VERSION_IDENTIFIER = 10;

    /**
     * The executed command requires a non-existing segment in the valie semantic version identifier
     * which is not present.
     */
    public static final int MISSING_SEGMENT = 20;
}
