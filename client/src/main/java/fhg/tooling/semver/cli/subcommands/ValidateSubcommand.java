package fhg.tooling.semver.cli.subcommands;

import fhg.tooling.semver.cli.ExitCodes;
import io.github.freiheitstools.semver.parser.api.SemVer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import java.util.concurrent.Callable;

import static picocli.CommandLine.*;

@Command(name = "validate",
        description = "Validate a given version",
        exitCodeListHeading = "Exit Codes:%n",
        exitCodeList = {
                ExitCodes.SUCCESS + ": Given version is a valid semantic version",
                PreformattedExitCodeDocumentationStrings.EXIT_CODE_DOC_NEGATIVE_EXECUTION_RESULT,
                PreformattedExitCodeDocumentationStrings.EXIT_CODE_DOC_INVALID_OPTIONS_PROVIDED,
                PreformattedExitCodeDocumentationStrings.EXIT_CODE_DOC_INVALID_VERSION_IDENTIFIER,
        }
)
public class ValidateSubcommand implements Callable<Integer> {
    @Mixin
    private VersionParameter versionParameter = new VersionParameter();

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;

    public Integer call() throws Exception {
        SemVer given = SemVer.parser().parse(versionParameter.getVersion());

        if (given.isInvalid()) {
            System.err.println(versionParameter.getVersion() + " is not a valid semantic version");
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        return ExitCodes.SUCCESS;
    }
}
