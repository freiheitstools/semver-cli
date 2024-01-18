package fhg.tooling.semver.cli.subcommands;

import picocli.CommandLine;
import picocli.CommandLine.Option;

public class FailsafeOption {
    private boolean failsafe;

    public boolean isFailsafe() {
        return failsafe;
    }

    public boolean isNotFailsafe() {
        return !isFailsafe();
    }

    @Option(names = {"-s", "--fail-safe"}, required = false,
            description = "Don't fail if requested segment of the version information "
                        + "does not exist")
    public void setFailsafe(boolean failsafe) {
        this.failsafe = failsafe;
    }
}
