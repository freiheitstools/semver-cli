package fhg.tooling.semver.cli.subcommands;

import java.util.concurrent.Callable;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import fhg.tooling.semver.cli.ExitCodes;
import picocli.CommandLine;

@CommandLine.Command(name = "extract",
        description = "Allows to extract single parts from a version number")
public class ExtractSubcommand
        implements Callable<Integer> {

    private VersionPrinter printer = new VersionPrinter();
    @CommandLine.Mixin
    private VersionParameter versionParameter = new VersionParameter();

    @CommandLine.Mixin
    private OutputOptions outputOptions = new OutputOptions();

    @CommandLine.ArgGroup(exclusive = true, multiplicity = "1")
    Exclusive exclusive;

    static class Exclusive {
        @CommandLine.Option(names = {"-1", "--major"}, required = true)
        boolean extractMajorNumber;
        @CommandLine.Option(names = {"-2", "--minor"}, required = true)
        boolean extractMinorNumber;
        @CommandLine.Option(names = {"-3", "--patch"}, required = true)
        boolean extractPatchLevel;
        @CommandLine.Option(names = {"-4", "--prerelease"}, required = true)
        boolean extractPreReleaseIdentifier;
        @CommandLine.Option(names = {"-5", "--build"}  , required = true)
        boolean extractBuildInformation;
    }

    @Override
    public Integer call() throws Exception {
        try {
            Semver semver = new Semver(versionParameter.getVersion());

            

            //Semver stripped = semver.withClearedSuffixAndBuild();
            //printer.printVersion(outputOptions.noNewLine, stripped, System.out);

        } catch (SemverException e) {
            System.err.println(e.getMessage());
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        return ExitCodes.SUCCESS;
    }
}
