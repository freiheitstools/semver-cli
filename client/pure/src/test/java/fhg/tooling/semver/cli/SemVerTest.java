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

            StringBuffer expectedOutput = new StringBuffer();

            expectedOutput.append("Usage: semver [-hV] [COMMAND]\n");
            expectedOutput.append("  -h, --help      Show this help message and exit.\n");
            expectedOutput.append("  -V, --version   Print version information and exit.\n");
            expectedOutput.append("Commands:\n");
            expectedOutput.append("  extract    Allows to extract single parts from a version number\n");
            expectedOutput.append("  nextmajor  Return the next major version for a given version\n");
            expectedOutput.append("  nextminor  Return the next minor version for a given version\n");
            expectedOutput.append("  nextpatch  Return the next patch version for a given version\n");
            expectedOutput.append("  strip      Returns the version without suffix and build number\n");
            expectedOutput.append("  validate   Validates a given version\n");

            assertThat(outputStreamCaptor.toString()).isEqualTo(expectedOutput.toString());
            assertThat(exitCode).isEqualTo(0);
        }
    }

}