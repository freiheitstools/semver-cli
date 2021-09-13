package fhg.tooling.semver.cli.subcommands;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import fhg.tooling.semver.cli.ExitCodes;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;
import java.util.function.Function;

@Command(name = "strip",
         description = "Returns the version without suffix and build number")
public class StripSubcommand implements Callable<Integer> {
    @Parameters(index = "0")
    private String version;

    @Override
    public Integer call() throws Exception {
        try {
            Semver semver = new Semver(version);
            Semver stripped = semver.withClearedSuffixAndBuild();
            System.out.println(stripped);
        } catch (SemverException e) {
            System.err.println(e.getMessage());
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        return ExitCodes.SUCCESS;
    }
}
