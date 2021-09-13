package fhg.tooling.semver.cli.subcommands;

import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.util.Optional;

public class SuffixOptions {
    @Option(names = { "-s", "--suffix" }, description = {
            "String to be used as pre-release identifier"})
    protected Optional<String> suffix;

    @Option(names = {"-b", "--build"}, description = {
            "String to be used as build identifier"})
    protected Optional<String> buildNummer;
}
