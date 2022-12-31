package fhg.tooling.semver.cli.subcommands;

import fhg.tooling.semver.cli.ExitCodes;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ReleaseVersionSubcommandTest {
    @Nested
    class CommandlineArguments {
        @Test
        void callWithVersionString() {
            ReleaseVersionSubcommand command = new ReleaseVersionSubcommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs("4.5.6");

            assertThat(parseResult.hasMatchedPositional(0)).isTrue();
            assertThat(parseResult.matchedPositional(0).<String>getValue()).isEqualTo("4.5.6");
            assertThat(parseResult.matchedOptions()).isEmpty();
        }

        @Test
        void callWithOptionForSuffix() {
            ReleaseVersionSubcommand command = new ReleaseVersionSubcommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs("4.5.6");

            assertThat(parseResult.hasMatchedPositional(0)).isTrue();
            assertThat(parseResult.matchedPositional(0).<String>getValue()).isEqualTo("4.5.6");
            assertThat(parseResult.matchedOptions()).isEmpty();
        }
    }

    @Nested
    class Functional {

        @ParameterizedTest
        @ValueSource(strings = {"4.5.6", "4.5.6+build-393-1", "4.5.6+HighPerf"})
        void releaseVersionIsRecognized(String givenVersionNumber) {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;

            try {
                ReleaseVersionSubcommand command = new ReleaseVersionSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute(givenVersionNumber);
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEmpty();
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
        }

        @ParameterizedTest
        @ValueSource(strings = {"4.5.6-SNAPSHOT", "4.5.6-MS-1", "4.5.6-ALPHA-3"})
        void nonReleaseVersionIsRecognized(String givenVersionNumber) {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;

            try {
                ReleaseVersionSubcommand command = new ReleaseVersionSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute(givenVersionNumber);
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEmpty();
            assertThat(exitCode).isEqualTo(ExitCodes.VERSION_IS_NOT_A_RELEASE_VERSION);
        }
    }
}
