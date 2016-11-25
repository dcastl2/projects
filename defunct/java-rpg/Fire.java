public class Fire extends Spell {

  Fire () {
    mp         = 1;
    dice.mul  = 2;
    dice.max  = 8;
    dice.plus = 2;
    name       = "fire";
    target     = Target.ENEMY;
    color      = Color.black;
  }

  public void action(Character c, Character t) {
    t.hp_dec(
      dice.roll() + c.mod("int") * c.lvl()
    );
  }

}
