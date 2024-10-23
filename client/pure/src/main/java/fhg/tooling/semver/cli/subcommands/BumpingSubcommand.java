package fhg.tooling.semver.cli.subcommands;

import fhg.tooling.semver.cli.ExitCodes;
import io.github.freiheitstools.semver.parser.api.SemVer;
import io.github.freiheitstools.semver.parser.api.SemVerBuilder;

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

    abstract Function<SemVerBuilder, SemVerBuilder> getBumpingFunction();

    public Integer call() {
        SemVer given = SemVer.parser().parse(versionParameter.getVersion());

        if (given.isValid()) {
            SemVer result = Stream.of(given)
                                  .map(SemVer.builder()::startFrom)
                                  .map(getBumpingFunction())
                                  .map(builder -> suffixOptions.getSuffix().map(builder::setPrerelease).orElse(builder))
                                  .map(builder -> suffixOptions.getBuildNumber().map(builder::setBuild).orElse(builder))
                                  .map(SemVerBuilder::build)
                                  .findFirst().get();

            printer.printVersion(outputOptions.noNewLine, result, System.out);
        } else {
            System.err.println(versionParameter.getVersion() + " is not a valid semantic version");
            return ExitCodes.INVALID_VERSION_IDENTIFIER;
        }

        return ExitCodes.SUCCESS;
    }


}
