public class Club extends Weapon {

  Club() {
    name = "Club";
    dice.mul = 1;
    dice.max = 6;
    dice.plus= 0;
  }

  public void action(Character c, Character t) {
    t.hp_dec(
      (dice.roll() + c.mod("str") - t.mod("con"))
    );
  }

}
