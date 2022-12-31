package fhg.tooling.semver.cli.subcommands;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import fhg.tooling.semver.cli.ExitCodes;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;

import java.util.concurrent.Callable;


@Command(name = "isreleaseversion",
         description = "Checks if version is a release version")
public class ReleaseVersionSubcommand implements Callable<Integer> {
    @Mixin
    private VersionParameter versionParameter = new VersionParameter();


    public Integer call() throws Exception {
        try {
            Semver givenVersion = new Semver(versionParameter.getVersion());
            Semver releaseVersion = new Semver(versionParameter.getVersion()).withClearedSuffix();

            if (!givenVersion.isEqualTo(releaseVersion)) {
                return ExitCodes.VERSION_IS_NOT_A_RELEASE_VERSION;
            }
        } catch (SemverException e) {
            System.err.println(e.getMessage());
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        return ExitCodes.SUCCESS;
    }
}
