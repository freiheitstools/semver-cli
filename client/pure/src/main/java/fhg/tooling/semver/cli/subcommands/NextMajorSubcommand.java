package fhg.tooling.semver.cli.subcommands;

import com.vdurmont.semver4j.Semver;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;
import java.util.function.Function;

@Command(name = "nextmajor",
         description = "Return the next major version for a given version")
public class NextMajorSubcommand extends BumpingSubcommand
        implements Callable<Integer> {

    @Override
    Function<Semver, Semver> getBumpingFunction() {
        return Semver::nextMajor;
    }
}
