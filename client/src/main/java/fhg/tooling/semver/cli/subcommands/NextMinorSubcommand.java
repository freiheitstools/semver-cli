package fhg.tooling.semver.cli.subcommands;

import fhg.tooling.semver.cli.ExitCodes;
import io.github.freiheitstools.semver.parser.api.SemVerBuilder;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;
import java.util.function.Function;

@Command(name = "nextminor",
        description = "Return the next minor version for a given version",
        exitCodeListHeading = "Exit Codes:%n",
        exitCodeList = {
                ExitCodes.SUCCESS + ": Successfully computed the next minor version identifier",
                PreformattedExitCodeDocumentationStrings.EXIT_CODE_DOC_NEGATIVE_EXECUTION_RESULT,
                PreformattedExitCodeDocumentationStrings.EXIT_CODE_DOC_INVALID_OPTIONS_PROVIDED,
                PreformattedExitCodeDocumentationStrings.EXIT_CODE_DOC_INVALID_VERSION_IDENTIFIER,
        }
)
public class NextMinorSubcommand extends BumpingSubcommand
        implements Callable<Integer> {
    @Override
    Function<SemVerBuilder, SemVerBuilder> getBumpingFunction() {
        return SemVerBuilder::nextMinor;
    }
}
