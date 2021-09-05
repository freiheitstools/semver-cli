package fhg.tooling.semver.cli.subcommands;

import picocli.CommandLine;
import picocli.CommandLine.Option;

public class SuffixOptions {
    @Option(names = { "-s", "--suffix" }, description = {
            "String to be used as pre-release identifier"})
    protected String suffix;

    @Option(names = {"-b", "--build"}, description = {
            "String to be used as build identifier"})
    protected int buildNummer;
}
