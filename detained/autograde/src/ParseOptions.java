import static org.kohsuke.args4j.ExampleMode.ALL;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
public class ParseOptions {


    @Option(name="-s",    usage = "scheme name")
    String  scheme              = "output";

    // Definitely need
    @Option(name="-t",    usage = "target file")
    String  target              = "Hello.java";

    // Just need module name
    @Option(name="-m",   usage = "module name")
    String  module              = "java";

    // Just need module name
    @Option(name="-c",   usage = "configuration directory path")
    String  config_path         = "~/.autograde";
 
    // Just need module name
    @Option(name="-a",   usage = "assignment path")
    String  assign_path         = "1350/prog1";


    public static void main(String[] args) throws Exception {

	ParseOptions  bean   = new ParseOptions();
	CmdLineParser parser = new CmdLineParser(bean);
	try {
		parser.parseArgument(args);
	} catch( CmdLineException e ) {
  		System.err.println(e.getMessage());
	        //System.err.println("java -jar myprogram.jar [options...] arguments...");
		parser.printUsage(System.err);
		return;
	}

    }


}
