package fhg.tooling.semver.cli.subcommands;

import picocli.CommandLine.Option;

import java.util.Optional;

public class SuffixOptions {
    private String suffix;

    private String buildNumber;

    @Option(names = { "-s", "--suffix" }, paramLabel = "suffix",
            description = {"String to be used as pre-release identifier"})
    public void setSuffix(String value) {
        suffix = value;
    }

    public Optional<String> getSuffix() {
        return Optional.ofNullable(suffix);
    }

    @Option(names = {"-b", "--build"}, paramLabel = "build",
            description = {"String to be used as build identifier"})
    public void setBuildNumber(String value) {
        buildNumber = value;
    }

    public Optional<String> getBuildNumber() {
        return Optional.ofNullable(buildNumber);
    }
}
