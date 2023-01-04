package fhg.tooling.semver.cli.subcommands;

import java.util.concurrent.Callable;

import fhg.tooling.semver.cli.ExitCodes;
import io.github.freiheitstools.semver.parser.api.SemVer;
import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;

/**
 * Subcommand to extract single parts, e.g. major number or patch level,
 * of a given version number.
 */
@Command(name = "extract",
        description = "Allows to extract single parts from a version number")
public class ExtractSubcommand
        implements Callable<Integer> {

    private VersionPrinter printer = new VersionPrinter();

    @Mixin
    private OutputOptions outputOptions = new OutputOptions();

    @Mixin
    private FailsafeOption failsafeOption = new FailsafeOption();

    @ArgGroup(exclusive = true, multiplicity = "1")
    Exclusive exclusive;

    @Mixin
    private VersionParameter versionParameter = new VersionParameter();

    static class Exclusive {
        @Option(names = {"-1", "--major"}, required = true,
                description = "Extracts the major version")
        boolean extractMajorNumber;
        @Option(names = {"-2", "--minor"}, required = true,
                description = "Extracts the minor version")
        boolean extractMinorNumber;
        @Option(names = {"-3", "--patch"}, required = true,
                description = "Extracts the patch version")
        boolean extractPatchLevel;
        @Option(names = {"-4", "--prerelease"}, required = true,
                description = "Extracts the prerelease version if present")
        boolean extractPreReleaseIdentifier;
        @Option(names = {"-5", "--build"}, required = true,
                description = "Extracts the build information if present")
        boolean extractBuildInformation;
    }

    @Override
    public Integer call() {
        try {
            SemVer given = SemVer.parser().parse(versionParameter.getVersion());

            if (given.isValid()) {
                String part = getRequestedPart(exclusive, given);

                boolean empty = StringUtils.isEmpty(part);

                if (empty && failsafeOption.isNotFailsafe()) {
                    throw new MissingSegmentException();
                }

                printer.printVersion(outputOptions.noNewLine, part, System.out);
            } else {
                System.err.println(versionParameter.getVersion() + " is not a valid semantic version");
                return ExitCodes.INVALID_VERSION_IDENTIFIER;
            }
        } catch (MissingSegmentException e) {
            System.err.println(e.getMessage());
            return ExitCodes.MISSING_SEGMENT;
        }

        return ExitCodes.SUCCESS;
    }

    private String getRequestedPart(Exclusive exclusive, SemVer semver) {
        if (exclusive.extractMajorNumber) {
            return String.valueOf(semver.getMajor());
        } else if (exclusive.extractMinorNumber) {
            return String.valueOf(semver.getMinor());
        } else if (exclusive.extractPatchLevel) {
            return String.valueOf(semver.getPatch());
        } else if (exclusive.extractBuildInformation) {
            return semver.getBuild().orElse("");
        } else if (exclusive.extractPreReleaseIdentifier) {
            return semver.getPreRelease().orElse("");
        }

        throw new IllegalStateException();
    }

    private static class MissingSegmentException extends Exception {
        public MissingSegmentException() {
            super("The requested part of the version information is not present");
        }
    }
}
