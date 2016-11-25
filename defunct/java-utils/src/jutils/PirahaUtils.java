package jutils;

import edu.lsu.cct.piraha.*;

import java.io.*;
import java.lang.*;
import java.util.*;

public class PirahaUtils {



/** Surrounds the first half of the list with "$(" and ")" to mark
 * as variable names.
 * @param input the list whose first half is to be surrounded with "$(" and ")"
 * @return input the list whose first half is surrounded with "$(" and ")"
 */
public static List<String> flagVars(List<String> input) {
        for (int i = 0; i < input.size() / 2; i++)
                input.set(i, "$(" + input.get(i) + ")");
        return input;
}



/** Given a matcher and two groups which appear inside each element
 * returned by that matcher, this method constructs a list whose
 * first half contains the elements of the first group, and second
 * half contains the elements of the second group; it is used to
 * create variable-value lists.
 * @param m the matcher to construct the list from
 * @param group1 the first group which appears in each element of m
 * @param group2 the second group which appears in each element of m
 * @return l1 the paired list of group1 and group2 strings
 */
public static List<String> pairedListOf(Matcher m, String group1, String group2) {
        List<String>	l1	= new ArrayList<String>();
        List<String>	l2	= new ArrayList<String>();
        l1	= findTextInMatcher(m, group1);
        l2	= findTextInMatcher(m, group2);
        l1.addAll(l2);
        return l1;
}



/** Given two paired lists, combine them into one.
 * @param l1 the first paired list
 * @param l2 the second paired list
 * @return l3 the paired list 
 */
public static List<String> consolidateLists(List<String> l1, List<String> l2) {
	List<String> l3 = new ArrayList<String>();
	if (l1.isEmpty()) return l2;
	if (l2.isEmpty()) return l1;
	l3.addAll( l1.subList(0, l1.size()/2) );
	l3.addAll( l2.subList(0, l2.size()/2) );
	l3.addAll( l1.subList(l1.size()/2, l1.size()) );
	l3.addAll( l2.subList(l2.size()/2, l1.size()) );
	return l3;
}



/** Given a paired variable-value list and a matcher m which contains the
 * original text, switch out the variable names in the matcher with their
 * corresponding values in the paired list, then return the resulting string.
 * @param pairedListOf the variable-value paired list
 * @param patternname the patternname inside the matcher
 * @param m the matcher
 * @param G the associated grammar
 * @return
 */
public static String swapVarsWithValues(Grammar G, Matcher m, String patternname, List<String> pairedlist) {
        Matcher M = matchInMatcher(G, m, patternname);
        M.startReplacement();
        while (M.find()) {
                Group g = M.group();
                if (pairedlist.contains(g.substring())) {
                        int index = pairedlist.indexOf(g.substring()) + pairedlist.size() / 2;
			//System.out.println(pairedlist.get(index));
                        M.appendReplacement( pairedlist.get(index) );
                }
        }
        return M.appendTail();
}



/** Given a matcher and a patternname, find the list of strings of type
 * patternname inside the matcher text.
 * @param  m           the matcher
 * @param  patternname the name of the group
 * @return             the string list
 */
public static List<String> findTextInMatcher(Matcher m, String patternname) {
        List<String> l = new ArrayList<String>();
	m.reset();
	//System.out.println("findTextInMatcher: "+patternname);
        while (m.find()) {
                Group g = m.group();
	        //System.out.println("findTextInMatcher: "+g.getPatternName()+":"+patternname);
                l.addAll(findTextInGroup(g, patternname));
        }
        return l;
}



/** Given a group and a patternname, find the list of strings of type
 * patternname inside the group text.
 * @param g the parent group
 * @param patternname the name of the subgroup
 * @return l the string list
 */
public static List<String> findTextInGroup(Group g, String patternname) {
        List<String> l = new ArrayList<String>();
	//System.out.println("findTextInGroup: "+g.getPatternName()+":"+patternname);
        if (g.getPatternName().equals(patternname)) {
                l.add(g.substring());
	}
        else {
                for (int i = 0; i < g.groupCount(); i++) {
                        l.addAll(findTextInGroup(g.group(i), patternname));
		}
	}
        return l;
}



/** Given a matcher and a patternname, tell if the matcher
 * contains a group by that name.
 * @param m the matcher
 * @param patternname the name of the group
 * @return l the string list
 */
public static boolean matcherHasPattern(Matcher m, String patternname) {
        while (m.find()) {
		return groupHasPattern(m.group(), patternname);
        }
        return false;
}



/** Given group, tell if the group contains another group by patternname.
 * @param m the matcher
 * @param patternname the name of the group
 * @return l the string list
 */
public static boolean groupHasPattern(Group g, String patternname) {
                if (g.getPatternName().equals(patternname)) {
			return true;
		} else {
                	for (int i = 0; i < g.groupCount(); i++)
                       		return groupHasPattern(g.group(i), patternname);
		}
        return false;
}


/** Given group, tell if the group contains another group by patternname.
 * @param m the matcher
 * @param patternname the name of the group
 * @return l the string list
 */
public static int numPatterns(Group g, String patternname) {
                if (g.getPatternName().equals(patternname)) {
			return 1;
		} else {
                	for (int i = 0; i < g.groupCount(); i++)
                       		return 1+numPatterns(g.group(i), patternname);
		}
        return 0;
}



/** Given a matcher, find the first group residing in the matcher.
 * @param m the matcher
 * @return
 */
public static Group firstGroupOf(Matcher m) throws NullPointerException {
        try {
                if (m.matches())
                        return m.group();
        } catch (NullPointerException e) {
                System.out.println(m);
                System.out.println(e);
        }
        return m.group();
}



/** In matcher m, return Matcher where group = patternname.
 * @param patternname the name of the subgroup
 * @param m the matcher
 * @param G the associated gramar
 * @return
 */
public static Matcher matchInMatcher(Grammar G, Matcher m, String patternname) {
        String text = "";
        while (m.find()) {
                Group g = m.group();
                text += g.substring();
        }
        //System.out.println(text);
        return match(G, patternname, text);
}



/** In Matcher m, return Matcher for {parentgroup} where {childgroup} = value.
 * @param parentgroup group of the matcher returned (the parent group)
 * @param childgroup name of the subgroup
 * @param value value that the subgroup string should have
 * @param m the parent matcher
 * @param G the associated grammar
 * @return
 */
public static Matcher matchWhere(Grammar G, Matcher m, String parentgroup, String childgroup, String value) {
        String text = "";
	m.reset();
        try {
                while (m.find()) {
                        Group		g	= m.group();
                        List<String>	l	= new ArrayList<String>();
                        l = findTextInGroup(g, childgroup);
                        if (l.contains(value))
                                text += g.substring() + "\n";
                }
        } catch (NullPointerException e) {
                System.out.println(e);
        }
        return match(G, parentgroup, text);
}


/** In Matcher m, return Matcher for {parentgroup} which has {childgroup}.
 * @param parentgroup group of the matcher returned (the parent group)
 * @param childgroup  name of the subgroup
 * @param m the parent matcher
 * @param G the associated grammar
 * @return
 */
public static Matcher matchIn(Grammar G, Matcher m, String parentgroup, String childgroup) {
        String text = "";
	m.reset();
        try {
                while (m.find()) {
                        Group		g	= m.group();
			if (groupHasPattern(g, parentgroup)) {
				text += g.substring() + "\n";
			}
			//System.out.println(g.substring());
                }
        } catch (NullPointerException e) {
                System.out.println(e);
        }
	//System.out.println(text);
        return match(G, childgroup, text);
}



/** Returns a matcher for needle in haystack.
 * @param haystack the string to search
 * @param needle the string to find
 * @param g the associated grammar
 * @return
 */
public static Matcher match(Grammar g, String needle, String haystack) {
        return g.matcher(needle, haystack);
}



/** Dumps matches of a matcher by iterating through its groups.
 * @param m the matcher whose values to dump
 */
public static void dumpMatches(Matcher m) {
        while (m.find()) {
                Group g = m.group();
                g.dumpMatches();
        }
}



/** Dumps near-matches of a matcher.
 * @param m the matcher whose values to dump
 */
public static void dumpNear(Matcher m) {
        if (m.matches()) {
                Group g = m.group();
                g.dumpMatches();
        } else
                System.out.println(m.near());
}



/** Writes code into file named infile, then executes code in infile
 * to produce file named outfile.
 * @param command the command to   run
 * @param code    the code    to   run
 * @param infile  the input   file name
 * @param outfile the output  file name
 * @return
public static String execute throws IOException (String command, String code, String infile, String outfile) {
        Process p;
        try {
                FileUtils.writeFile(infile, code);
                ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
                p = pb.start();
                try {
                        p.waitFor();
                } catch (InterruptedException e) {
                        System.out.println(e);
                }
        } catch (IOException e) {
                System.out.println(e);
        }
        return FileUtils.readFile(outfile);
}
*/



}
