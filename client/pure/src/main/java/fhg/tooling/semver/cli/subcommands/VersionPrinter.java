package fhg.tooling.semver.cli.subcommands;

import com.vdurmont.semver4j.Semver;
import fhg.tooling.semver.cli.SemVer;

import java.io.PrintStream;

import static java.lang.String.format;

public class VersionPrinter {
    private String FORMAT_WITH_NEWLINE = "%s%n";
    private String FORMAT_WITHOUT_NEWLINE = "%s";

    public void printVersion(boolean skipNewLine, Semver version, PrintStream output) {
        String effectiveFormat = skipNewLine ? FORMAT_WITHOUT_NEWLINE : FORMAT_WITH_NEWLINE;
        String formatResult = format(effectiveFormat, version.getValue());

        output.print(formatResult);
    }
}
