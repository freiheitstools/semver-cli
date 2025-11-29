package fhg.tooling.semver.cli.subcommands;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import picocli.CommandLine;

import static org.assertj.core.api.Assertions.assertThat;

class ValidateSubcommandTest {
    @Nested
    class CommandlineArguments {
        @ParameterizedTest
        @ValueSource(strings = {"-h", "--help"})
        void callWithHelpOptionIsPossible(String option) {
            ValidateSubcommand command = new ValidateSubcommand();
            CommandLine cmdline = new CommandLine(command);

            CommandLine.ParseResult parseResult = cmdline.parseArgs(option);

            assertThat(parseResult.matchedOptions()).hasSize(1);
            assertThat(parseResult.matchedOption(option).isOption()).isTrue();
        }
    }

    @Nested
    class Functional {
    }
}
