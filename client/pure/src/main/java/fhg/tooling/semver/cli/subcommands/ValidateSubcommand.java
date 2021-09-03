package fhg.tooling.semver.cli.subcommands;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import fhg.tooling.semver.cli.ExitCodes;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import java.util.concurrent.Callable;


@Command(name = "validate",
         description = "Validates a given version")
public class ValidateSubcommand implements Callable<Integer> {
    @Mixin
    private VersionParameter versionParameter = new VersionParameter();


    public Integer call() throws Exception {
        try {
            Semver semver = new Semver(versionParameter.getVersion());
        } catch (SemverException e) {
            System.err.println(e.getMessage());
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        return ExitCodes.SUCCESS;
    }
}
