package fhg.tooling.semver.cli;

import fhg.tooling.semver.cli.subcommands.NextMajorSubcommand;
import fhg.tooling.semver.cli.subcommands.NextMinorSubcommand;
import fhg.tooling.semver.cli.subcommands.NextPatchSubcommand;
import fhg.tooling.semver.cli.subcommands.StripSubcommand;
import fhg.tooling.semver.cli.subcommands.ValidateSubcommand;
import picocli.CommandLine;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import java.util.jar.Attributes;
import java.util.jar.Manifest;

@CommandLine.Command(
        mixinStandardHelpOptions = true,
        name = "semver",
        subcommands = {
                NextMajorSubcommand.class, NextMinorSubcommand.class,
                NextPatchSubcommand.class, StripSubcommand.class,
                ValidateSubcommand.class},
        versionProvider = SemVer.ManifestVersionProvider.class)
public class SemVer
{
    public static void main(String[] args) {
        SemVer command = new SemVer();
        System.exit(new CommandLine(command).execute(args));
    }

    static class ManifestVersionProvider implements CommandLine.IVersionProvider {
        public String[] getVersion() throws Exception {

            Enumeration<URL> resources = CommandLine.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                try {
                    Manifest manifest = new Manifest(url.openStream());
                    if (isApplicableManifest(manifest)) {
                        Attributes attr = manifest.getMainAttributes();
                        return new String[]{get(attr, "cliname") + " version " + get(attr, "Implementation-Version"),
                                            "License: Apache License 2.0",
                                            "Homepage: https://github.com/freiheitstools/semver-cli"};
                    }
                } catch (IOException ex) {
                    return new String[] { "Unable to read from " + url + ": " + ex };
                }
            }
            return new String[0];
        }

        private boolean isApplicableManifest(Manifest manifest) {
            Attributes attributes = manifest.getMainAttributes();
            return "semver".equals(get(attributes, "cliname"));
        }

        private static Object get(Attributes attributes, String key) {
            return attributes.get(new Attributes.Name(key));
        }
    }
}
