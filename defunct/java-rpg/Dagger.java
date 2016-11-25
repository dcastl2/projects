public class Dagger extends Weapon {

  Dagger() {
    name = "Dagger";
    dice.mul = 1;
    dice.max = 4;
    dice.plus= 1;
  }

  public void action(Character c, Character t) {
    t.hp_dec(
        dice.roll(
	  c.lvl()-t.lvl()+c.stat("dex")-t.stat("dex"),
	  c.lvl()/5,
	  c.stat("str")-t.stat("con")+c.stat("dex")-t.stat("dex"),
	  c.stat("str")-t.stat("con")+c.stat("dex")-t.stat("dex")
        )
    );
  }

}
