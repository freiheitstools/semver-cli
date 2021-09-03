package fhg.tooling.semver.cli;


import fhg.tooling.semver.cli.subcommands.NextMajorSubcommand;
import fhg.tooling.semver.cli.subcommands.NextMinorSubcommand;
import fhg.tooling.semver.cli.subcommands.NextPatchSubcommand;
import fhg.tooling.semver.cli.subcommands.ValidateSubcommand;
import picocli.CommandLine;

@CommandLine.Command(
        mixinStandardHelpOptions = true,
        subcommands = {
                NextMajorSubcommand.class, NextMinorSubcommand.class,
                NextPatchSubcommand.class, ValidateSubcommand.class
})
public class SemVer
{
    public static void main(String[] args) {

        SemVer command = new SemVer();
        System.exit(new CommandLine(command).execute(args));
    }
}
