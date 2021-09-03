package fhg.tooling.semver.cli.subcommands;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import fhg.tooling.semver.cli.ExitCodes;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import java.util.concurrent.Callable;

@Command(name = "strip",
         description = "Returns the version without suffix and build number")
public class StripSubcommand implements Callable<Integer> {
    @Mixin
    private VersionParameter versionParameter = new VersionParameter();

    @Mixin
    private OutputOptions outputOptions = new OutputOptions();

    private VersionPrinter printer = new VersionPrinter();

    @Override
    public Integer call() throws Exception {
        try {
            Semver semver = new Semver(versionParameter.getVersion());
            Semver stripped = semver.withClearedSuffixAndBuild();
            printer.printVersion(outputOptions.noNewLine, stripped, System.out);

        } catch (SemverException e) {
            System.err.println(e.getMessage());
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        return ExitCodes.SUCCESS;
    }
}
