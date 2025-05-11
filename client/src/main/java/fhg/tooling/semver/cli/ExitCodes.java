package fhg.tooling.semver.cli;

public class ExitCodes {
    public static final int SUCCESS = 0;

    /**
     * Generic error code value used to indicate that the execution of the command
     * was not successful in the context of the executed subcommand.
     */
    public static final int NEGATIVE_EXECUTION_RESULT = 1;

    /**
     * Illegal commandline options or arguments specified.
     */
    public static final int INVALID_OPTIONS_PROVIDED = 2;

    /**
     * The given version identifier or identifiers is not a valid semantic version identifier.
     */
    public static final int INVALID_VERSION_IDENTIFIER = 10;

    /**
     * The executed command requires a non-existing segment in the semantic version identifier
     * which is not present.
     */
    public static final int MISSING_SEGMENT = 20;
}
