package fhg.tooling.semver.cli.subcommands;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import fhg.tooling.semver.cli.ExitCodes;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "validate",
         description = "Validates a given version")
public class ValidateSubcommand implements Callable<Integer> {
    @CommandLine.Parameters(index = "0")
    private String version;


    public Integer call() throws Exception {
        try {
            Semver semver = new Semver(version);
        } catch (SemverException e) {
            System.err.println(e.getMessage());
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        return ExitCodes.SUCCESS;
    }
}
