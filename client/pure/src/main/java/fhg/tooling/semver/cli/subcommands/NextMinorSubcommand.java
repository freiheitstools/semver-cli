package fhg.tooling.semver.cli.subcommands;

import io.github.freiheitstools.semver.parser.api.SemVerBuilder;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;
import java.util.function.Function;

@Command(name = "nextminor",
         description = "Return the next minor version for a given version")
public class NextMinorSubcommand extends BumpingSubcommand
        implements Callable<Integer> {
    @Override
    Function<SemVerBuilder, SemVerBuilder> getBumpingFunction() {
        return SemVerBuilder::nextMinor;
    }
}
