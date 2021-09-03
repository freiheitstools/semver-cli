package fhg.tooling.semver.cli.subcommands;

import picocli.CommandLine.Option;

public class OutputOptions {
    @Option(names = {"-n", "--no-newline"}, description = {
            "Print no newline after the version"})
    protected boolean noNewLine;
}
