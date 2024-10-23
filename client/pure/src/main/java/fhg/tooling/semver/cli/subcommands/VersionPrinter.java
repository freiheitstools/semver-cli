package fhg.tooling.semver.cli.subcommands;

import io.github.freiheitstools.semver.parser.api.SemVer;

import java.io.PrintStream;

import static java.lang.String.format;

public class VersionPrinter {
    private static final String FORMAT_WITH_NEWLINE = "%s%n";
    private static final String FORMAT_WITHOUT_NEWLINE = "%s";

    public void printVersion(boolean skipNewLine, SemVer version, PrintStream output) {
        String effectiveFormat = skipNewLine ? FORMAT_WITHOUT_NEWLINE : FORMAT_WITH_NEWLINE;
        String formatResult = format(effectiveFormat, version);

        output.print(formatResult);
    }
}
