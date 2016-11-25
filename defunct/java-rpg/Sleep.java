public class Sleep extends Spell {

  Sleep () {
    mp        = 1;
    dice.mul  = 1;
    dice.max  = 4;
    dice.plus = 1;
    target    = Target.ENEMY;
    name      = "sleep";
    color     = Color.gray;
  }

  public void action(Character c, Character t) {
    int rounds = dice.roll(
                      c.lvl()-t.lvl()+c.stat("cha")+c.stat("wis")-2*t.stat("wis"), 
		      c.lvl()/5,
		      0,
		      0
		 );
    if (dice.times > 0 && dice.sum > 0) {
      Text.typeoutln(t.name+" feel asleep.");
      t.stats.put("sleep", rounds);
    }
    else Text.typeoutln("But nothing happened.");
  }

}
