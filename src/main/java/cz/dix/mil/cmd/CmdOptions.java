package cz.dix.mil.cmd;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;

import java.io.File;

/**
 * Represents command line options of the application.
 *
 * @author Zdenek Obst, zdenek.obst-at-gmail.com
 */
public class CmdOptions {

    @Parameter(names = {"-g", "--game"}, converter = FileConverter.class, required = true,
            description = "Path to the XML game file")
    private File gameFile;
    @Parameter(names = {"-m", "--manual-audience"}, required = false,
            description = "Allows manual audience (moderator must insert results of voting)")
    private boolean isManualAudience = false;
    @Parameter(names = {"-h", "--help"}, help = true,
            description = "Displays help")
    private boolean help = false;

    public File getGameFile() {
        return gameFile;
    }

    public boolean isManualAudience() {
        return isManualAudience;
    }

    public boolean isHelp() {
        return help;
    }

    public static class FileConverter implements IStringConverter<File> {
        @Override
        public File convert(String value) {
            File file = new File(value);
            if (!file.exists()) {
                System.out.println("File does not exist in " + value);
                System.exit(1);
            }
            return file;
        }
    }
}
