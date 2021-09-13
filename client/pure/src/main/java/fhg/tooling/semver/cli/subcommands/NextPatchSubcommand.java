package fhg.tooling.semver.cli.subcommands;

import com.vdurmont.semver4j.Semver;
import com.vdurmont.semver4j.SemverException;
import fhg.tooling.semver.cli.ExitCodes;
import fhg.tooling.semver.cli.SemVer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Mixin;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Stream;

@Command(name = "nextpatch",
         description = "Return the next patch version for a given version")
public class NextPatchSubcommand extends BumpingSubcommand
        implements Callable<Integer> {

    @Override
    Function<Semver, Semver> getBumpingFunction() {
        return Semver::nextPatch;
    }
}
