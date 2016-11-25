public class Death extends Spell {

  Death () {
    mp         = 9;
    dice.mul  = 1;
    dice.max  = 100;
    dice.plus = 0;
    target     = Target.ENEMY;
    name       = "death";
    color      = Color.black;
  }

  public void action(Character c, Character t) {
    if (dice.roll() > (c.mod("int") - t.mod("con"))*10)
      t.hp_dec(t.hp());
    else 
      Text.typeoutln("But nothing happened.");
  }

}
