package fhg.tooling.semver.cli.subcommands;

import io.github.freiheitstools.semver.parser.api.SemVerBuilder;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;
import java.util.function.Function;

@Command(name = "nextpatch",
         description = "Return the next patch version for a given version")
public class NextPatchSubcommand extends BumpingSubcommand
        implements Callable<Integer> {
    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;

    @Override
    Function<SemVerBuilder, SemVerBuilder> getBumpingFunction() {
        return SemVerBuilder::nextPatch;
    }
}
