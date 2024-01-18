package fhg.tooling.semver.cli.subcommands;

import fhg.tooling.semver.cli.ExitCodes;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ExtractSubcommandTest {

    @Nested
    class CommandlineArguments {

        static Stream<Arguments> parameterSourceForCantCallWithMutuallyExclusiveOptions() {
            Function<List<String>, Stream<Arguments>> tupleBuilder = (source) -> {
                Collection<Arguments> arguments = new ArrayList<>();

                for (int outerIndex = 0; outerIndex < source.size(); outerIndex++) {
                    for (int innerIndex = 0; innerIndex < source.size(); innerIndex++) {
                        if (outerIndex == innerIndex) {
                            continue;
                        }

                        Arguments argTuple = arguments(source.get(outerIndex), source.get(innerIndex));

                        arguments.add(argTuple);
                    }
                }

                return arguments.stream();
            };

            List<String> numberOptionVariants = List.of("-1", "-2", "-3", "-4", "-5", "-6");
            List<String> namedOptionVariants = List.of("--major", "--minor", "--patch", "--suffix",
                    "--build", "--prerelease");

            Stream<Arguments> argumentsStream = tupleBuilder.apply(numberOptionVariants);

            return argumentsStream;
        }

        @ParameterizedTest
        @MethodSource("parameterSourceForCantCallWithMutuallyExclusiveOptions")
        void cantCallWithMutuallyExclusiveOptions(String optionA, String optionB) {
            ExtractSubcommand command = new ExtractSubcommand();
            CommandLine cmdline = new CommandLine(command);

            assertThatThrownBy(() -> {
                        CommandLine.ParseResult parseResult = cmdline.parseArgs(optionA, "-n", optionB, "4.5.6");
                    }).isInstanceOf(CommandLine.MutuallyExclusiveArgsException.class);
        }

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
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
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
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
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
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
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
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
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
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
        }

        @ParameterizedTest
        @ValueSource(strings = {"-6", "--suffix"})
        void extractingOfSuffixWorks(String option) {
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
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
        }

        @Test
        void extractingOfBuildIfBuildIsNotPresentLeadsToAFailureIfNotFailsafe() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;

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
            int exitCode = -1;

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
            int exitCode = -1;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("--no-newline", "--suffix", ".4.5-alpha");
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
            int exitCode = -1;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("--no-newline", "--suffix", "1-alpha");
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
            int exitCode = -1;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("--no-newline", "--suffix", "1.4.");
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
            int exitCode = -1;

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
            int exitCode = -1;

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

        @Test
        void extractingOfSuffixIfSuffixIsNotPresentReturnsEmptyStringAndNotNull() {
            PrintStream stdoutStream = System.out;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            int exitCode = -1;

            try {
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("--no-newline", "--suffix", "4.6.0");
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isNotNull().isEmpty();
            assertThat(exitCode).isEqualTo(ExitCodes.MISSING_SEGMENT);
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

                exitCode = cmdline.execute("-6", "--no-newline", "--fail-safe", version);
            } finally {
                System.setOut(stdoutStream);
            }

            assertThat(outputStreamCaptor.toString()).isEqualTo(expected);
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
                ExtractSubcommand command = new ExtractSubcommand();
                CommandLine cmdline = new CommandLine(command);

                cmdline.execute("--major",  "4.5.6");
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