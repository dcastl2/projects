import static org.kohsuke.args4j.ExampleMode.ALL;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import edu.lsu.cct.piraha.*;
import jutils.*;
import java.io.IOException;
import java.io.File;
import java.util.List;


public class ParseGrader {

    String  s_scheme;
    String  s_output;
    Grammar g_scheme; 
    Grammar g_agrade;

    /**
     * @param path_gram_scheme  path to the scheme grammar
     * @param path_gram_output  path to the output grammar
     * @param path_file_scheme  path to the scheme file
     * @param path_file_output  path to the output file
     */
    ParseGrader (String peg_scheme,
                 String peg_grade,
                 String scheme,
                 String output) throws IOException {
 
	g_agrade = new Grammar();
	g_scheme = new Grammar();
        g_agrade.compileFile(new File(peg_grade )); 
        g_scheme.compileFile(new File(peg_scheme));
        s_output = FileUtils.readFile(output);
        s_scheme = FileUtils.readFile(scheme);
    }



    public double Grade () {
	double  grade      = 0.0;
	double  total      = 0.0;
	Matcher statements = null;
	try {
		statements = g_scheme.matcher("statement", s_scheme);
	} catch (ParseException e) {
		System.err.println("No statements could be parsed from the scheme file!");
		System.exit(1);
	}
	//PirahaUtils.dumpNear(statements);
	List<String> suggester  = PirahaUtils.pairedListOf(statements, "expr",  "tag");
	List<String> agenda     = PirahaUtils.pairedListOf(statements, "grade", "expr");
	int          num_items  = agenda.size()/2;
	for (int score_i=0; score_i<num_items; score_i++) {
		boolean bonus   = (agenda.get(score_i).indexOf('+')!=-1);
		boolean deduct  = (agenda.get(score_i).indexOf('-')!=-1);
		boolean message = false;
		int     item_i  = num_items + score_i;
		double  score   = Double.parseDouble(agenda.get(score_i));
		Matcher check   = g_agrade.matcher(agenda.get(item_i), s_output);
		String  flag    = "";
		String  report  = "\t%2d:  %-12s  %8s"; 
		String addendum = ": %4.0f  %s\n";
		String  tag = "";
		if (check.find()) {
			if (bonus) {
			   flag  += "[  OK  ]";
			   grade += score;
			   message = true;
			} else if (deduct) {
			   flag += "[ FAIL ]";
			   grade += score;
			   message = true;
			} else {
			   flag  += "[  OK  ]";
			   grade += score;
			   message = true;
			}
		} else {
			if (bonus) {
			   flag  += "[  OK  ]";
			} else if (deduct) {
			   flag += "[  OK  ]";
			} else {
			   flag  += "[ FAIL ]";
			   message = true;
			}
		//PirahaUtils.dumpNear(check);
		}
		if (message) tag += suggester.get(item_i);
		if (message) System.out.printf(report+addendum, score_i+1, agenda.get(item_i), flag, score, tag);
		else         System.out.printf(report+"\n"    , score_i+1, agenda.get(item_i), flag);
		if (!bonus && !deduct) total += score; 
		
		//System.out.println(check.getTextPos());
	}
	if (grade < 0) grade = 0;
	System.out.println("\tThe grade is "+grade+" out of "+total+".\n");
	return grade;
    }



    public static void main(String[] args) throws IOException {
	ParseOptions  opts   = new ParseOptions();
	CmdLineParser parser = new CmdLineParser(opts);
	try {
		parser.parseArgument(args);
	} catch( CmdLineException e ) {
  		System.err.println(e.getMessage());
	        System.err.println("java -jar myprogram.jar [options...] arguments...");
		parser.printUsage(System.err);
		return;
	}

	// Concatenate autograde+scheme pegs into ./autograde.peg.
        FileUtils.catInto(
			  opts.config_path+"/module/"    +opts.module     +"/"+opts.module+".peg"
			 ,opts.config_path+"/assignment/"+opts.assign_path+"/"+opts.scheme+".peg"
			 ,"autograde.peg"
			 );

        ParseGrader pg = 
	  new ParseGrader(
			  opts.config_path+"/autograde.peg"
			 ,                  "autograde.peg"
			 ,opts.config_path+"/assignment/"+opts.assign_path+"/"+opts.scheme+".scheme"
                         ,opts.target
			 );
	System.out.println("For "+opts.assign_path+", "+opts.scheme+":\n");
	pg.Grade();
	System.out.println();
    }



}
