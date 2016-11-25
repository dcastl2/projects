public class Osmose extends Spell {

  Osmose () {
    mp         = 1;
    dice.mul  = 1;
    dice.max  = 2;
    dice.plus = 0;
    target     = Target.ENEMY;
    name       =     "osmose";
    color      =  Color.black;
  }

  public void action(Character c, Character t) {
    int mp = dice.roll(
     (c.mod("int")+c.mod("wis")
     -t.mod("int")-t.mod("wis"))/2,  // to-hit
      c.lvl()/4,                     // mul+
      c.lvl()/3,                     // max+
     (c.mod("int")+c.mod("wis")      // plus+
     -t.mod("int")-t.mod("wis"))/2
    );
    if (mp > t.mp()) 
      mp = t.mp();
    t.mp_dec(mp);
    c.mp_inc(mp);
  }

}
