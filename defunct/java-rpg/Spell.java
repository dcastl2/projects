import java.util.Map;
import java.util.Random;

public abstract class Spell {

  int         id, mp;
  String      color;
  String      name;
  Dice        dice = new Dice();
  Target      target;
  
  public boolean act(Character c, Character t) {
    if (c.mp() >= mp)
      c.mp(c.mp() - mp);
    else return false;
    cast_message(c, t);
    action(c, t);
    return true;
  }

  public String spellname() {
    return color+"* "+Color.boldwhite+capitalize(name)+Color.white;
  }

  public String capitalize(final String string) {
     return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
  }

  public void cast_message(Character c, Character t) {
    Text.typeoutln(c.name+" cast "+spellname()+" on "+t.name+".");
  }

  public abstract void action(Character c, Character t);

}
