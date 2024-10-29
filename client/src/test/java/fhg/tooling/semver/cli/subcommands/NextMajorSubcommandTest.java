package fhg.tooling.semver.cli.subcommands;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class NextMajorSubcommandTest {
    @Nested
    class CommandlineArguments {
        @Test
        void callWithVersionString() {
            NextMajorSubcommand command = new NextMajorSubcommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs("4.5.6");

            assertThat(parseResult.hasMatchedPositional(0)).isTrue();
            assertThat(parseResult.matchedPositional(0).<String>getValue()).isEqualTo("4.5.6");
            assertThat(parseResult.matchedOptions()).isEmpty();
        }

        @Test
        void callWithOptionForSuffix() {
            NextMajorSubcommand command = new NextMajorSubcommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs("--suffix", "DELTA", "4.5.6");

            assertThat(parseResult.hasMatchedPositional(0)).isTrue();
            assertThat(parseResult.matchedPositional(0).<String>getValue()).isEqualTo("4.5.6");
            assertThat(parseResult.matchedOptions()).hasSize(1);
            assertThat(parseResult.matchedOption("--suffix").isOption()).isTrue();
            assertThat(parseResult.matchedOption("--suffix").<String>getValue()).isEqualTo("DELTA");
        }
    }

    @Nested
    class Functional {
        @Test
        void bumpToNextMajorVersionWorks() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;

            try {
                NextMajorSubcommand command = new NextMajorSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("-n", "4.5.6");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("5.0.0");
            assertThat(exitCode).isEqualTo(0);
        }

        @Test
        void addingSuffixWorks() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;

            try {
                NextMajorSubcommand command = new NextMajorSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("-s=SNAPSHOT", "-n", "4.5.6");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("5.0.0-SNAPSHOT");
            assertThat(exitCode).isEqualTo(0);
        }

        @Test
        void addingBuildWorks() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;

            try {
                NextMajorSubcommand command = new NextMajorSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("-b=23", "-s=SNAPSHOT", "-n", "4.5.6");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("5.0.0-SNAPSHOT+23");
            assertThat(exitCode).isEqualTo(0);
        }
    }

    @Nested
    class Formatting {
        @Test
        void defaultBehaviorIsToAddNewline() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));

            try {
                NextMajorSubcommand command = new NextMajorSubcommand();
                CommandLine cmdline = new CommandLine(command);

                cmdline.execute("4.5.6");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("5.0.0\n");
        }

        @Test
        void optionMinusNSuppressesNewLine() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));

            try {
                NextMajorSubcommand command = new NextMajorSubcommand();
                CommandLine cmdline = new CommandLine(command);

                cmdline.execute("-n", "4.5.6");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("5.0.0");
        }
    }

}