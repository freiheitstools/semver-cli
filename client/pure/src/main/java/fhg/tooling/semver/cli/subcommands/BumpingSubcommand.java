package fhg.tooling.semver.cli.subcommands;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import fhg.tooling.semver.cli.ExitCodes;

import java.util.function.Function;
import java.util.stream.Stream;

import static picocli.CommandLine.*;

abstract class BumpingSubcommand {
    @Mixin
    private SuffixOptions suffixOptions = new SuffixOptions();

    @Mixin
    private VersionParameter versionParameter = new VersionParameter();

    @Mixin
    private OutputOptions outputOptions = new OutputOptions();

    private VersionPrinter printer = new VersionPrinter();

    abstract Function<Semver, Semver> getBumpingFunction();

    public Integer call() {
        try {
            Semver result = Stream.of(versionParameter.getVersion())
                    .map(s -> new Semver(versionParameter.getVersion()))
                    .map(getBumpingFunction())
                    .map(semver -> suffixOptions.getSuffix().map(semver::withSuffix).orElse(semver))
                    .map(semver -> suffixOptions.getBuildNumber().map(semver::withBuild).orElse(semver))
                    .findFirst().get();

            printer.printVersion(outputOptions.noNewLine, result, System.out);
        } catch (SemverException e) {
            System.err.println(e.getMessage());
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        return ExitCodes.SUCCESS;
    }


}
