package fhg.tooling.semver.cli.subcommands;

import fhg.tooling.semver.cli.ExitCodes;
import io.github.freiheitstools.semver.parser.api.SemVer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import java.util.concurrent.Callable;

import static picocli.CommandLine.*;

@Command(name = "strip",
        description = "Returns the version without suffix and build number")
public class StripSubcommand implements Callable<Integer> {
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;

    @Mixin
    private VersionParameter versionParameter = new VersionParameter();

    @Mixin
    private OutputOptions outputOptions = new OutputOptions();

    private VersionPrinter printer = new VersionPrinter();

    @Override
    public Integer call() {
        SemVer given = SemVer.parser().parse(versionParameter.getVersion());

        if (given.isInvalid()) {
            System.err.println(versionParameter.getVersion() + " is not a valid semantic version");
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        SemVer stripped = SemVer.builder().startFrom(given)
                                .removeBuild()
                                .removePrerelease()
                                .build();

        printer.printVersion(outputOptions.noNewLine, stripped, System.out);

        return ExitCodes.SUCCESS;
    }
}
