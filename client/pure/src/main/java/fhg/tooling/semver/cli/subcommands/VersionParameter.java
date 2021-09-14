package fhg.tooling.semver.cli.subcommands;

import picocli.CommandLine.Parameters;

public class VersionParameter {
    @Parameters(index = "0", paramLabel = "version",
            description = "The semantic version information to work on")
    protected String version;

}
