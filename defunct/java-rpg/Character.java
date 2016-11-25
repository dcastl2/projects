import java.util.HashMap;

public class Character {

  final static String[] ef = 
    {"sleep",   "confuse", "hold", "mute", 
     "petrify", "poison"};

  final static String[] st = 
    {"str", "dex", "con", "int", "wis", "cha"};

  final static String[] dm = 
    {"math", "phys", "chem", "biol", 
     "psyc", "phil", "art",  "mus" };


  char    sprite;
  String  color;
  String  name;
  Weapon  weapon;

  public HashMap stats;
  public Spell[] spells;


  public void stats(int[] stats) {
    int i = 0;
    for (String s : st)
      stat(s, (int)stats[i++]);
  }


  public void domains(int[] domains) {
    int i = 0;
    for (String d : dm)
      stat(d, domains[i++]);
  }


  public void grant(int exp) {
    stat("exp",  (stat("exp") +exp));
    stat("next", (stat("next")-exp));
    if (stat("next") < 0)
      lvl_up();
  }


  public void lvl_up() {
    Text.typeoutln(name+" gained a level!");
    stat("lvl",  stat("lvl")  + 1);
    stat("next", stat("next") + (int)Math.pow(2, lvl()));
    derive();
  }


  public void randomize() {
    Dice d = new Dice(2, 4, 0);
    for (String s : st) 
      randomize(d, s);
  }


  public void randomize(Dice d, String s) {
    stats.put(s, stat(s)+d.roll()-5);
  }


  public void derive() {

    for (String s : st) 
      stats.put('b'+s, stat(s));

    stat("mhp", (lvl() *  Dice.ge1(6 + mod("dex")/3
                                     + mod("str")/2
			             + mod("con")
			    )
                )
        );

    stat("mmp", (lvl() * Dice.ge0(0 + mod("cha")/4
                                    + mod("int")/3
	   		            + mod("wis")/2
			    )
                )
        );

    stat("exp",  (int)Math.pow(2, stat("lvl")));
    stat("next", (int)Math.pow(2, stat("lvl")+1)-stat("exp"));

    hp(stat("mhp"));
    mp(stat("mmp"));

  }


  public Spell spell(String spellname) {
    for (Spell s : spells) 
      if (s.name.equals(spellname))
        return s;
    return null;
  }


  public int stat_inc(String s, int gain) {
    if (gain < 0) gain = 0;
    Text.typeoutln(name()+" gained "+gain+" "+s+".");
    stats.put(s, stat(s)+gain);
    return stat(s);
  }


  public int stat_dec(String s, int loss) {
    if (loss > 0) loss = 0;
    Text.typeoutln(name()+" lost "+loss+" "+s+".");
    stats.put(s, stat(s)+loss);
    return stat(s);
  }


  public int hp_inc(int gain) {
         if (     gain < 0      ) gain = 0;
    else if (hp()+gain > maxhp()) gain = maxhp()-hp();
    Text.typeoutln(name()+" gained "+gain+" hp.");
    hp(hp()+gain);
    return hp();
  }


  public int mp_inc(int gain) {
         if (     gain < 0      ) gain = 0;
    else if (mp()+gain > maxmp()) gain = maxmp()-mp();
    Text.typeoutln(name()+" gained "+gain+" mp.");
    mp(mp()+gain);
    return mp();
  }


  public int hp_dec(int loss) {
         if (     loss >    hp()) loss = hp();
    else if (     loss <     0  ) loss = 0;
    Text.typeoutln(name()+" lost "+loss+" hp.");
    hp(hp()-loss);
    return hp();
  }


  public int mp_dec(int loss) {
         if (     loss >    mp()) loss = mp();
    else if (     loss <     0  ) loss = 0;
    Text.typeoutln(name()+" lost "+loss+" mp.");
    mp(mp()-loss);
    return mp();
  }


  public void printstatus(int code) {
    if (code >= 1)
      System.out.print(name()+"  HP: "+hp()+"/"+maxhp()+"  MP: "+mp()+"/"+maxmp());
    if (code >= 2)
      for (String s : st)
        System.out.print("  "+stat(s));
    System.out.println("");
  }


  public void typestatus(int code) {
    if (code >= 1)
      Text.typeout(name()+"  HP: "+hp()+"/"+maxhp()+"  MP: "+mp()+"/"+maxmp());
    if (code >= 2)
      for (String s : st)
        Text.typeout("  "+stat(s));
    Text.typeoutln("");
  }


  public void printspells() {
    System.out.println("");
    for (Spell s : spells)
      System.out.println("\t"+s.spellname());
    System.out.println("");
  }


  public void typespells() {
    Text.typeoutln("");
    for (Spell s : spells)
      Text.typeoutln("\t"+s.spellname());
    Text.typeoutln("");
  }

  public void typeinfo() {
    typestatus(1);
  }

  public void printinfo() {
    printstatus(1);
  }


  public boolean dead() {
    return (hp() <= 0);
  }


  public int mod(String s) {
   return (stat(s)-10);
  }


  public int stat(String s) {
   return (int)stats.get(s);
  }

  public void stat(String s, int n) {
    stats.put(s, n);
  }


  public String name() {
    return color+name+Color.white;
  }


  public String tostring() {
    return color+sprite;
  }


  public boolean sleep() {
    if (stats.get("sleep") != null) {
      stats.put("sleep", (int)stats.get("sleep")-1);
      if (stats.get("sleep")==0) {
        Text.typeout(name+" woke up.");
        return true;
      }
      Text.typeoutln(name+" is sleeping.");
      return true;
    } else return false;
  }


  public int  lvl()        { return stat("lvl");  }
  public void lvl(int x)   { stats.put("lvl", x); }

  public int  hp()         { return stat("chp");  }
  public void hp(int x)    { stats.put("chp", x); }
  public int  maxhp()      { return stat("mhp");  }

  public int  mp()         { return stat("cmp");  }
  public void mp(int x)    { stats.put("cmp", x); }
  public int  maxmp()      { return stat("mmp");  }

}
