package fhg.tooling.semver.cli.subcommands;

import fhg.tooling.semver.cli.ExitCodes;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import picocli.CommandLine;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class IsMavenSnapshotSubCommandTest {
    @Nested
    class CommandlineArguments {
        @Test
        void positionalParameterForVersionStringIsMandatory() {
            IsMavenSnapshotSubCommand command = new IsMavenSnapshotSubCommand();
            CommandLine cmdline = new CommandLine(command);

            Assertions.assertThatThrownBy(cmdline::parseArgs)
                      .isExactlyInstanceOf(CommandLine.MissingParameterException.class)
                      .extracting(ex -> ((CommandLine.MissingParameterException) ex).getMissing())
                      .asInstanceOf(InstanceOfAssertFactories.list(CommandLine.Model.ArgSpec.class))
                      .hasSize(1)
                      .first()
                      .extracting(CommandLine.Model.ArgSpec::paramLabel).asString()
                      .isEqualTo("version");
        }

        @Test
        void positionalParameterForVersionStringIsMatched() {
            IsMavenSnapshotSubCommand command = new IsMavenSnapshotSubCommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs("4.5.6-SNAPSHOT");

            assertThat(parseResult.hasMatchedPositional(0)).isTrue();
            assertThat(parseResult.matchedPositional(0).<String>getValue()).isEqualTo("4.5.6-SNAPSHOT");
        }

        @ParameterizedTest
        @ValueSource(strings = {"-h", "--help"})
        void callWithHelpOptionIsPossible(String option) {
            IsMavenSnapshotSubCommand command = new IsMavenSnapshotSubCommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs(option);

            assertThat(parseResult.matchedOptions()).hasSize(1);
            assertThat(parseResult.matchedOption(option).isOption()).isTrue();
        }

    }

    @Nested
    class Functional {
        @Test
        void realSnapshotVersionIsRecognizedAsSnapshotVersion() {
            PrintStream stdoutStream = System.out;
            PrintStream stderrStream = System.err;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            System.setErr(new PrintStream(errorStreamCaptor));
            int exitCode;

            try {
                IsMavenSnapshotSubCommand command = new IsMavenSnapshotSubCommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("4.6.0-SNAPSHOT");
            } finally {
                System.setOut(stdoutStream);
                System.setErr(stderrStream);
            }

            assertThat(outputStreamCaptor.toString()).isEmpty();
            assertThat(errorStreamCaptor.toString()).isEqualToNormalizingNewlines("4.6.0-SNAPSHOT is a Maven snapshot version\n");
            assertThat(exitCode).isEqualTo(ExitCodes.SUCCESS);
        }

        @Test
        void nonSnapshotVersionIsNotRecognizedAsSnapshotVersion() {
            PrintStream stdoutStream = System.out;
            PrintStream stderrStream = System.err;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            System.setErr(new PrintStream(errorStreamCaptor));
            int exitCode;

            try {
                IsMavenSnapshotSubCommand command = new IsMavenSnapshotSubCommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("4.6.0-DELTA");
            } finally {
                System.setOut(stdoutStream);
                System.setErr(stderrStream);
            }

            assertThat(outputStreamCaptor.toString()).isEmpty();
            assertThat(errorStreamCaptor.toString()).isEqualToNormalizingNewlines("4.6.0-DELTA is not a Maven snapshot version\n");
            assertThat(exitCode).isEqualTo(ExitCodes.NEGATIVE_EXECUTION_RESULT);
        }

        @Test
        void anInvalidSemanticVersionIsRecognized() {
            PrintStream stdoutStream = System.out;
            PrintStream stderrStream = System.err;
            ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStreamCaptor = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStreamCaptor));
            System.setErr(new PrintStream(errorStreamCaptor));
            int exitCode;

            try {
                IsMavenSnapshotSubCommand command = new IsMavenSnapshotSubCommand();
                CommandLine cmdline = new CommandLine(command);

                exitCode = cmdline.execute("a4.6.0");
            } finally {
                System.setOut(stdoutStream);
                System.setErr(stderrStream);
            }

            assertThat(outputStreamCaptor.toString()).isEmpty();
            assertThat(errorStreamCaptor.toString()).isEqualToNormalizingNewlines("a4.6.0 is not a valid semantic version\n");
            assertThat(exitCode).isEqualTo(ExitCodes.INVALID_VERSION_IDENTIFIER);
        }
    }
}
