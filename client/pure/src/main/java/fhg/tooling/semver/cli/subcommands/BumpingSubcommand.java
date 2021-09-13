package fhg.tooling.semver.cli.subcommands;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import fhg.tooling.semver.cli.ExitCodes;
import picocli.CommandLine;

import java.util.function.Function;
import java.util.stream.Stream;

import static picocli.CommandLine.*;

abstract class BumpingSubcommand {
    @Parameters(index = "0", paramLabel = "version",
            description = "The semantic version information to work on")
    protected String version;

    @Mixin
    private SuffixOptions suffixOptions = new SuffixOptions();



    abstract Function<Semver, Semver> getBumpingFunction();

    public Integer call() {
        try {
            Semver result = Stream.of(version).map(s -> new Semver(version))
                    .map(getBumpingFunction())
                    .map(semver -> suffixOptions.suffix.map(semver::withSuffix).orElse(semver))
                    .map(semver -> suffixOptions.buildNummer.map(semver::withBuild).orElse(semver))
                    .findFirst().get();

            System.out.println(result);
        } catch (SemverException e) {
            System.err.println(e.getMessage());
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        return ExitCodes.SUCCESS;
    }


}
