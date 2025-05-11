package fhg.tooling.semver.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class SemVerTest {
    @BeforeEach
    void disableAnsi() {
        System.setProperty("picocli.ansi", "false");
    }

    @Nested
    class HelpAndVersion {
        @Test
        void helpOutputIsCorrect() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;

            try {
                SemVer command = new SemVer();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("--help");
            } finally {
                System.setOut(stdoutStream);
            }

            String expectedOutput =
                    """
                    Usage: semver [-hV] [COMMAND]
                      -h, --help      Show this help message and exit.
                      -V, --version   Print version information and exit.
                    Commands:
                      extract          Extract single segment from a version identifier
                      ismavensnapshot  Check if the given semantic version is a Maven snapshot
                                         version or not
                      nextmajor        Return the next major version for a given version
                      nextminor        Return the next minor version for a given version
                      nextpatch        Return the next patch version for a given version
                      strip            Return the version without prerelease identifier and build
                                         number
                      validate         Validate a given version
                    """;

            assertThat(outputStreamCaptor.toString()).isEqualTo(expectedOutput);
            assertThat(exitCode).isEqualTo(0);
        }
    }

}
