public class Drain extends Spell {

  Drain () {
    mp         = 3;
    dice.mul  = 1;
    dice.max  = 4;
    dice.plus = 1;
    target     = Target.ENEMY;
    name       = "drain";
    color      = Color.black;
  }

  public void action(Character c, Character t) {
    int hp = dice.roll(
      c.lvl()/3,
      c.mod("int")/2,
      c.mod("int")/2
    );
    t.hp_dec(hp);
    c.hp_inc(hp);
  }

}
