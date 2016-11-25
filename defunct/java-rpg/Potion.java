public class Potion extends Consumable {

  Potion() {
    name = "potion";
    dice.mul  = 5;
    dice.max  = 4;
    dice.plus = 1;
  }

  void action(Character c) {
    c.hp_inc(
      dice.roll() + dice.ge1(c.mod("con")/3 * c.lvl())
    );
  }

}
