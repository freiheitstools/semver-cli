package fhg.tooling.semver.cli.subcommands;

import java.util.Optional;
import java.util.concurrent.Callable;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import fhg.tooling.semver.cli.ExitCodes;
import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Option;

/**
 * Subcommand to extract single parts, e.g. major number or patch level,
 * of a given version number.
 */
@CommandLine.Command(name = "extract",
        description = "Allows to extract single parts from a version number")
public class ExtractSubcommand
        implements Callable<Integer> {

    private VersionPrinter printer = new VersionPrinter();
    @Mixin
    private VersionParameter versionParameter = new VersionParameter();

    @Mixin
    private OutputOptions outputOptions = new OutputOptions();

    @Mixin
    private FailsafeOption failsafeOption = new FailsafeOption();

    @ArgGroup(exclusive = true, multiplicity = "1")
    Exclusive exclusive;

    static class Exclusive {
        @Option(names = {"-1", "--major"}, required = true,
                description = "Extracts the major number")
        boolean extractMajorNumber;
        @Option(names = {"-2", "--minor"}, required = true,
                description = "Extracts the minor number")
        boolean extractMinorNumber;
        @Option(names = {"-3", "--patch"}, required = true,
                description = "Extracts the patch level")
        boolean extractPatchLevel;
        @Option(names = {"-4", "--prerelease"}, required = true,
                description = "Extracts the prerelease information if present")
        boolean extractPreReleaseIdentifier;
        @Option(names = {"-5", "--build"} , required = true,
                description = "Extracts the build information if present")
        boolean extractBuildInformation;

        @Option(names = {"-6", "--suffix"}  , required = true,
                description = "Extracts the whole suffix build out of prerelease identifier "
                            + "build information if given")
        boolean extractWholeSuffix;
    }

    @Override
    public Integer call() throws Exception {
        try {
            Semver semver = new Semver(versionParameter.getVersion());
            String part = getRequestedPart(exclusive, semver);

            boolean empty = StringUtils.isEmpty(part) || StringUtils.equals("null", part);

            if (empty && failsafeOption.isNotFailsafe()) {
                throw new MissingSegmentException();
            } else if (empty && failsafeOption.isFailsafe()) {
                part = "";
            }

            printer.printVersion(outputOptions.noNewLine, part, System.out);
        } catch (SemverException e) {
            System.err.println(e.getMessage());
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        } catch (MissingSegmentException e) {
            System.err.println(e.getMessage());
            return ExitCodes.MISSING_SEGMENT;
        }

        return ExitCodes.SUCCESS;
    }

    private String getRequestedPart(Exclusive exclusive, Semver semver) {
        if (exclusive.extractMajorNumber) {
            return String.valueOf(semver.getMajor());
        } else if (exclusive.extractMinorNumber) {
            return String.valueOf(semver.getMinor());
        } else if (exclusive.extractPatchLevel) {
            return String.valueOf(semver.getPatch());
        } else if (exclusive.extractBuildInformation) {
            return String.valueOf(semver.getBuild());
        } else if (exclusive.extractPreReleaseIdentifier) {
            return String.join("", semver.getSuffixTokens());
        } else if (exclusive.extractWholeSuffix) {
            Optional<String> build = Optional.ofNullable(semver.getBuild());
            String prerelease = String.join("", semver.getSuffixTokens());
            boolean hasPrerelease = !prerelease.isBlank();

            if (hasPrerelease) {
                return prerelease + build.map(token -> "+" + token).orElse("");
            }

            return build.orElse("");
        }

        throw new IllegalStateException();
    }

    private static class MissingSegmentException extends Exception {
        public MissingSegmentException() {
            super("The requested part of the version information is not present");
        }
    }
}
