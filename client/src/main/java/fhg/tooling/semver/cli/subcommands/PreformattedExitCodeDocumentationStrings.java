package fhg.tooling.semver.cli.subcommands;

import picocli.CommandLine;

import static fhg.tooling.semver.cli.ExitCodes.*;

/**
 * Set of preformatted exit code documentation string, which can be used
 * as value for the element {@link CommandLine.Command#exitCodeList}.
 *
 * @see CommandLine.Command
 * @see fhg.tooling.semver.cli.ExitCodes
 */
public class PreformattedExitCodeDocumentationStrings {

    private static final String SUCCESS_RESULT_DOC_STRING =
            """
            Command was successful executed\
            """;

    public static final String EXIT_CODE_DOC_SUCCESSFUL_RESULT =
            SUCCESS + ": " + SUCCESS_RESULT_DOC_STRING;

    private static final String NEGATIVE_EXECUTION_RESULT_DOC_STRING =
            """
            Generic error code value used to indicate that the \
            execution of the command was not successful in the context \
            of the executed subcommand.\
            """;

    public static final String EXIT_CODE_DOC_NEGATIVE_EXECUTION_RESULT =
            NEGATIVE_EXECUTION_RESULT + ": " + NEGATIVE_EXECUTION_RESULT_DOC_STRING;

    private static final String INVALID_OPTIONS_PROVIDED_DOC_STRING =
            """
            Illegal commandline options or arguments specified\
            """;

    public static final String EXIT_CODE_DOC_INVALID_OPTIONS_PROVIDED =
            INVALID_OPTIONS_PROVIDED + ": " + INVALID_OPTIONS_PROVIDED_DOC_STRING;

    private static final String INVALID_VERSION_IDENTIFIER_DOC_STRING =
            """
            The given version identifier or identifiers is not a valid \
            semantic version identifier
            """;

    public static final String EXIT_CODE_DOC_INVALID_VERSION_IDENTIFIER =
            INVALID_VERSION_IDENTIFIER + ": " + INVALID_VERSION_IDENTIFIER_DOC_STRING;

    private static final String MISSING_SEGMENT_DOC_STRING =
            """
            The executed command requires a non-existing segment in the\
            semantic version identifier which is not present.\
            """;

    public static final String EXIT_CODE_DOC_MISSING_SEGMENT_RESULT =
            MISSING_SEGMENT + ": " + MISSING_SEGMENT_DOC_STRING;

}
