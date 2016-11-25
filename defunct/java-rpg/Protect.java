public class Protect extends Spell {

  Protect() {
    name       = "protect";
    id         =      1;
    mp         =      2;
    dice.mul  =      2;
    dice.max  =      2;
    dice.plus =      1;
    target     = Target.ALLY;
  }

  public void action(Character c, Character t) {
    t.hp_inc(
      (dice.roll() + c.mod("wis") + t.mod("con")) * c.lvl()
    );
  }

}
