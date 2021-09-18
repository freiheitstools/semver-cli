package fhg.tooling.semver.cli.subcommands;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StripSubcommandTest {
    @Nested
    class Functional {
        @Test
        void strippingASuffixWorks() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;

            try {
                StripSubcommand command = new StripSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("-n", "4.6.0-RC-1");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("4.6.0");
            assertThat(exitCode).isEqualTo(0);
        }

        @Test
        void strippingBuildNumberWorks() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;

            try {
                StripSubcommand command = new StripSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("4.5.6+34");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("4.5.6\n");
            assertThat(exitCode).isEqualTo(0);
        }

        @Test
        void strippingWorksForSimpleVersionInformation() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;

            try {
                StripSubcommand command = new StripSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("4.5.6");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("4.5.6\n");
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
                StripSubcommand command = new StripSubcommand();
                CommandLine cmdline = new CommandLine(command);

                cmdline.execute("4.5.6");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("4.5.6\n");
        }

        @Test
        void optionMinusNSuppressesNewLine() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));

            try {
                StripSubcommand command = new StripSubcommand();
                CommandLine cmdline = new CommandLine(command);

                cmdline.execute("-n", "4.5.6");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("4.5.6");
        }
    }

}