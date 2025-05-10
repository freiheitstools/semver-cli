package fhg.tooling.semver.cli.subcommands;

import fhg.tooling.semver.cli.ExitCodes;
import io.github.freiheitstools.semver.parser.api.SemVer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import java.util.concurrent.Callable;

import static picocli.CommandLine.*;

@Command(name = "validate",
         mixinStandardHelpOptions = true,
         description = "Validates a given version")
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
