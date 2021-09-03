package fhg.tooling.semver.cli.subcommands;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import fhg.tooling.semver.cli.ExitCodes;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Stream;

import static picocli.CommandLine.*;

@Command(name = "nextminor",
         description = "Return the next minor version for a given version")
public class NextMinorSubcommand extends BumpingSubcommand
        implements Callable<Integer> {

    @Override
    Function<Semver, Semver> getBumpingFunction() {
        return Semver::nextMinor;
    }
}
