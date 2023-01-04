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

class ExtractSubcommandTest {
    @Nested
    class CommandlineArguments {
        @ParameterizedTest
        @ValueSource(strings = {"-1", "--major"})
        void callWithEachOptionToGetTheMajorVersionWorks(String option) {
            ExtractSubcommand command = new ExtractSubcommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs(option, "4.5.6");

            assertThat(parseResult.matchedOptions()).hasSize(1);
            assertThat(parseResult.matchedOption(option)).isNotNull();

            assertThat(parseResult.matchedPositionals()).hasSize(1);
            assertThat(parseResult.hasMatchedPositional(0)).isTrue();
            assertThat(parseResult.matchedPositional(0).<String>getValue()).isEqualTo("4.5.6");
        }


        @ParameterizedTest
        @ValueSource(strings = {"-2", "--minor"})
        void callWithEachOptionToGetTheMinorVersionWorks(String option) {
            ExtractSubcommand command = new ExtractSubcommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs(option, "4.5.6");

            assertThat(parseResult.matchedOptions()).hasSize(1);
            assertThat(parseResult.matchedOption(option)).isNotNull();

            assertThat(parseResult.matchedPositionals()).hasSize(1);
            assertThat(parseResult.hasMatchedPositional(0)).isTrue();
            assertThat(parseResult.matchedPositional(0).<String>getValue()).isEqualTo("4.5.6");
        }


        @ParameterizedTest
        @ValueSource(strings = {"-3", "--patch"})
        void callWithEachOptionToGetThePatchVersionWorks(String option) {
            ExtractSubcommand command = new ExtractSubcommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs(option, "4.5.6");

            assertThat(parseResult.matchedOptions()).hasSize(1);
            assertThat(parseResult.matchedOption(option)).isNotNull();

            assertThat(parseResult.matchedPositionals()).hasSize(1);
            assertThat(parseResult.hasMatchedPositional(0)).isTrue();
            assertThat(parseResult.matchedPositional(0).<String>getValue()).isEqualTo("4.5.6");
        }

        @ParameterizedTest
        @ValueSource(strings = {"-4", "--prerelease"})
        void callWithEachOptionToGetThePreReleaseIdentifierWorks(String option) {
            ExtractSubcommand command = new ExtractSubcommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs(option, "4.5.6-SNAPSHOT");

            assertThat(parseResult.matchedOptions()).hasSize(1);
            assertThat(parseResult.matchedOption(option)).isNotNull();

            assertThat(parseResult.matchedPositionals()).hasSize(1);
            assertThat(parseResult.hasMatchedPositional(0)).isTrue();
            assertThat(parseResult.matchedPositional(0).<String>getValue()).isEqualTo("4.5.6-SNAPSHOT");
        }

        @ParameterizedTest
        @ValueSource(strings = {"-5", "--build"})
        void callWithEachOptionToGetTheBuildInformationWorks(String option) {
            ExtractSubcommand command = new ExtractSubcommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs(option, "4.5.6+1");

            assertThat(parseResult.matchedOptions()).hasSize(1);
            assertThat(parseResult.matchedOption(option)).isNotNull();

            assertThat(parseResult.matchedPositionals()).hasSize(1);
            assertThat(parseResult.hasMatchedPositional(0)).isTrue();
            assertThat(parseResult.matchedPositional(0).<String>getValue()).isEqualTo("4.5.6+1");
        }

        @ParameterizedTest
        @ValueSource(strings = {"-1", "-2", "-3", "-4", "-5"})
        void callWithOptionToOmidTheNewlineWorks(String option) {
            ExtractSubcommand command = new ExtractSubcommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs(option, "-n", "4.5.6-SNAPSHOT+1");

            assertThat(parseResult.matchedOptions()).hasSize(2);
            assertThat(parseResult.matchedOption("-n")).isNotNull();
        }
    }


    @Nested
    class Functional {
        @ParameterizedTest
        @ValueSource(strings = {"-1", "--major"})
        void extractingOfMajorNumberWorks(String option) {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute(option, "--no-newline", "4.6.0-pre+build");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("4");
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-2", "--minor"})
        void extractingOfMinorNumberWorks(String option) {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute(option, "--no-newline", "4.6.0-pre+build");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("6");
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-3", "--patch"})
        void extractingOfPatchLevelWorks(String option) {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute(option, "--no-newline", "4.6.0-pre+build");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("0");
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-4", "--prerelease"})
        void extractingOfPreReleaseWorks(String option) {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute(option, "--no-newline", "4.6.0-pre+build");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("pre");
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-5", "--build"})
        void extractingOfBuildWorks(String option) {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute(option, "--no-newline", "4.6.0-pre+build");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("build");
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
        }

        @Test
        void extractingOfBuildIfBuildIsNotPresentLeadsToAFailureIfNotFailsafe() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("--no-newline", "--build",
                                           "--fail-safe", "1.4.5-alpha");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isNotNull().isEmpty();
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
        }

        @Test
        void extractingOfBuildIfBuildIsNotPresentReturnsEmptyStringIfFailsafe() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("--no-newline", "--build",
                                           "--fail-safe", "1.4.5-alpha");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isNotNull().isEmpty();
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
        }

        @Test
        void extractingOfMajorNumberMajorNumberIsNotPresentReturnsEmptyString() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("--no-newline", "--major", ".4.5-alpha");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isNotNull().isEmpty();
            assertThat(exitCode).isEqualTo(ExitCodes.INVALID_VERSION_IDENTIFIER);
        }

        @Test
        void extractingOfMinorNumberMinorNumberIsNotPresentReturnsEmptyString() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("--no-newline", "--minor", "1-alpha");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isNotNull().isEmpty();
            assertThat(exitCode).isEqualTo(ExitCodes.INVALID_VERSION_IDENTIFIER);
        }

        @Test
        void extractingOfPatchLevelIfPatchLevelIsNotPresentReturnsEmptyString() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("--no-newline", "--patch", "1.4.");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isNotNull().isEmpty();
            assertThat(exitCode).isEqualTo(ExitCodes.INVALID_VERSION_IDENTIFIER);
        }

        @Test
        void extractingOfPreReleaseIfPreReleaseIsNotPresentLeadsToAFailureIfNotFailsafe() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("--no-newline", "--prerelease", "4.6.0");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEmpty();
            assertThat(exitCode).isEqualTo(ExitCodes.MISSING_SEGMENT);
        }

        @Test
        void extractingOfPreReleaseIfPreReleaseIsNotPresentReturnsEmptyStringIfFailsafe() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("--no-newline", "--prerelease",
                                           "--fail-safe", "4.6.0");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEmpty();
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
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
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                cmdline.execute("--major", "4.5.6");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("4\n");
        }

        @Test
        void optionMinusNSuppressesNewLine() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                cmdline.execute("-n", "--major", "4.5.6");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("4");
        }
    }
}