package fhg.tooling.semver.cli.subcommands;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import fhg.tooling.semver.cli.ExitCodes;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "nextpatch",
         description = "Return the next patch version for a given version")
public class NextPatchSubcommand implements Callable<Integer> {
    @Parameters(index = "0")
    private String version;


    public Integer call() {
        try {
            Semver semver = new Semver(version);
            Semver nextSemver = semver.nextPatch();

            System.out.println(nextSemver);
        } catch (SemverException e) {
            System.err.println(e.getMessage());
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        return ExitCodes.SUCCESS;
    }
}
