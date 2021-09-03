package fhg.tooling.semver.cli.subcommands;

import picocli.CommandLine.Parameters;

public class VersionParameter {
    private String version;

    public String getVersion() {
        return version;
    }

    @Parameters(index = "0", paramLabel = "version",
            description = "The semantic version information to work on")
    public void setVersion(String value) {
        version = value;
    }
}
