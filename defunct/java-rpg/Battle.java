import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Battle {

  EnemyGroup eg;
  boolean flee = false;


  Battle(Party p, Map m) {
    Random rand = new Random();
    int r = rand.nextInt(m.egs.length);
    m.fadeout(p);
    Text.typeoutln("Determining random group...");
    eg = (EnemyGroup)m.egs[r].clone();
    restore(eg);
    engage(p, eg, m);
    m.fadein(p);
  }


  public void restore(EnemyGroup eg) {
    for (Monster m : eg.ms) {
      m.randomize();
      m.derive();
    }
  }


  public void engage(Party p, EnemyGroup eg, Map map) {

    Text.typeoutln("Engaging "+eg);
    int    r;
    String cmd;
    Character mtarget, ctarget;
    Random rand = new Random();
    eg.loadGroup();
    eg.printGroup();

    win:
    while (!alldead()) {

      p.printinfo();
      for (Character c : p.cs) {
	if (!c.dead()) {
          Text.prompt(c);
          cmd = Text.getcmd(c);
	  interpret(c, eg, p, map, cmd);
	  if (flee) {
            Text.wait(400);
	    return;
	  }
	}
        if (alldead()) 
	  break win;
      }

      for (Monster m : eg.ms) {
        if (m.sleep()) break;
	if (!m.dead()) {
          m.attack(eg, p, map);
	}
	if (p.dead()) 
	  break;
      }

    }
    p.inv.gold += eg.gold;
    for (Character c : p.cs) {
      c.grant(eg.exp);
    }
    Text.typeoutln("You got "+eg.gold+" gold!");
    Text.wait(200);
  }


  public void printboard() {
    for (Monster m : eg.ms) 
      System.out.print(m);
  }


  public boolean alldead() {
    for (Monster m : eg.ms) 
      if (!m.dead())
        return false;
    return true;
  }


  public void interpret(Character c, EnemyGroup eg, Party p, Map m, String cmd) {

    Text.typeout(eg, p, "< "+cmd+"");


    Pattern pat = Pattern.compile("fight ([0-9])\\1");
    Matcher mat = pat.matcher(cmd);
    if (mat.find()) {
      //c.weapon.act(c, t);
      return;
    }


    pat = Pattern.compile("cast ([a-z]*)\\1 (|([0-9])\\2)");
    mat = pat.matcher(cmd);
    if (mat.find()) {
      String spell = mat.group(1);
      if (c.spell(spell)==null)
        Text.typeout(eg, p, "Spell "+spell+" not recognized.");
      else
        if (mat.groupCount()>1) {
	  int idx = Integer.parseInt(mat.group(2));
	  c.spell(spell).act(c, eg.ms[idx]);
	}
      return;
    }


    Text.typeout(eg, p, "Command "+cmd+" not recognized.");

  }



}
