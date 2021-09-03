package fhg.tooling.semver.cli.subcommands;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import fhg.tooling.semver.cli.ExitCodes;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "nextmajor",
         description = "Return the next major version for a given version")
public class NextMajorSubcommand implements Callable<Integer> {
    @Parameters(index = "0")
    private String version;


    public Integer call() {
        try {
            Semver semver = new Semver(version);
            Semver nextSemver = semver.nextMajor();

            System.out.println(nextSemver);
        } catch (SemverException e) {
            System.err.println(e.getMessage());
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        return ExitCodes.SUCCESS;
    }
}
