package fhg.tooling.semver.cli.subcommands;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ExtractSubcommandTest {

    @Nested
    class CommandlineArguments {
        // todo Aufruf mit mehr als einer Option ist nicht möglich

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



        // todo -4 und --prerelease ist identisch

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


        // todo -5 und --build ist identisch

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
        @ValueSource(strings = {"-6", "--suffix"})
        void callWithEachOptionToGetTheSuffixWorks(String option) {
            ExtractSubcommand command = new ExtractSubcommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs(option, "4.5.6-SNAPSHOT+1");

            assertThat(parseResult.matchedOptions()).hasSize(1);
            assertThat(parseResult.matchedOption(option)).isNotNull();

            assertThat(parseResult.matchedPositionals()).hasSize(1);
            assertThat(parseResult.hasMatchedPositional(0)).isTrue();
            assertThat(parseResult.matchedPositional(0).<String>getValue()).isEqualTo("4.5.6-SNAPSHOT+1");
        }

        @ParameterizedTest
        @ValueSource(strings = {"-1", "-2", "-3", "-4", "-5", "-6"})
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
            int exitCode = -1;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute(option, "--no-newline", "4.6.0-pre+build");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("4");
            assertThat(exitCode).isEqualTo(0);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-2", "--minor"})
        void extractingOfMinorNumberWorks(String option) {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;


            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute(option, "--no-newline", "4.6.0-pre+build");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("6");
            assertThat(exitCode).isEqualTo(0);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-3", "--patch"})
        void extractingOfPatchLevelWorks(String option) {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;


            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute(option, "--no-newline", "4.6.0-pre+build");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("0");
            assertThat(exitCode).isEqualTo(0);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-4", "--prerelease"})
        void extractingOfPreReleaseWorks(String option) {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;


            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute(option, "--no-newline", "4.6.0-pre+build");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("pre");
            assertThat(exitCode).isEqualTo(0);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-5", "--build"})
        void extractingOfBuildWorks(String option) {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;


            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute(option, "--no-newline", "4.6.0-pre+build");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("build");
            assertThat(exitCode).isEqualTo(0);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-6", "--suffix"})
        void extractingOfrSuffixWorks(String option) {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute(option, "--no-newline", "4.6.0-pre+build");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo("pre+build");
            assertThat(exitCode).isEqualTo(0);
        }

        static Stream<Arguments> provideVersionsAndExpectedResultForSuffixExtraction() {
            return Stream.of(
                    arguments("1.2.3", ""),
                    arguments("1.2.3-pre", "pre"),
                    arguments("1.2.3+build", "build"),
                    arguments("1.2.3-pre+build", "pre+build")
            );
        }

        @ParameterizedTest
        @MethodSource("provideVersionsAndExpectedResultForSuffixExtraction")
        void extractingOfSuffixWorksAsExpectedForDifferentVersionsWorksWorks(String version, String expected) {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("-6", "--no-newline", version);
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo(expected);
            assertThat(exitCode).isEqualTo(0);
        }
    }

}