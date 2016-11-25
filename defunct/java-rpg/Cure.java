import java.util.Random;

public class Cure extends Spell {

  Cure() {
    name       = "cure";
    mp         =     1;
    dice.mul  =      2;
    dice.max  =      4;
    dice.plus =      1;
    target     = Target.ALLY;
    color      = Color.boldwhite;
  }

  public void action(Character c, Character t) {
    t.hp_inc
      ( dice.roll(
          c.lvl()/4+c.mod("wis")/2,
          c.mod("wis")/2,
          c.mod("wis")
        )
    );
  }

}
