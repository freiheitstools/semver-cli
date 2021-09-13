package fhg.tooling.semver.cli.subcommands;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
            assertThat(parseResult.matchedOption("--suffix").isOption());
            assertThat(parseResult.matchedOption("--suffix").<Optional<String>>getValue()).hasValue("DELTA");
        }
    }

}