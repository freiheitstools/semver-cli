package fhg.tooling.semver.cli.subcommands;

import fhg.tooling.semver.cli.ExitCodes;
import io.github.freiheitstools.semver.parser.api.SemVer;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;

import static picocli.CommandLine.*;

/**
 * Subcommand to check if a given semantic version represents a valid Maven snapshot version or not.
 */
@Command(name = "ismavensnapshot",
        description = "Checks if the given semantic version is a Maven snapshot version or not",
        exitCodeList = {
                ExitCodes.SUCCESS + ": Given semantic version is a Maven snapshot version",
                ExitCodes.NEGATIVE_EXECUTION_RESULT + ": Given semantic version isn't a Maven snapshot version",
                ExitCodes.INVALID_VERSION_IDENTIFIER + ": Given version is not a valid semantic version"
        }
)
public class IsMavenSnapshotSubCommand
        implements Callable<Integer> {

    /**
     * Textual representation of the suffix {@code SNAPSHOT} used by
     * Apache Maven to mark a given version as snapshot version.
     */
    private static final String SNAPSHOT = "SNAPSHOT";

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;

    @Mixin
    private VersionParameter versionParameter = new VersionParameter();

    @Override
    public Integer call() {
        SemVer given = SemVer.parser().parse(versionParameter.getVersion());

        if (given.isInvalid()) {
            System.err.println(versionParameter.getVersion() + " is not a valid semantic version");
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        Optional<String> preRelease = given.getPreRelease();

        if (preRelease.isPresent()) {
            String prerelease = preRelease.get();

            if (Objects.equals(SNAPSHOT, prerelease)) {
                System.err.println(versionParameter.getVersion() + " is a Maven snapshot version");
                return ExitCodes.SUCCESS;
            }
        }

        System.err.println(versionParameter.getVersion() + " is not a Maven snapshot version");
        return ExitCodes.NEGATIVE_EXECUTION_RESULT;
    }
}
