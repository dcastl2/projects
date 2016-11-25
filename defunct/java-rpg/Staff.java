public class Staff extends Weapon {

  Staff() {
    name = "Staff";
    dice.mul = 1;
    dice.max = 6;
    dice.plus= 1;
  }

  public void action(Character c, Character t) {
    t.hp_dec(
      dice.roll(
        c.lvl()-t.lvl()+c.stat("dex")-t.stat("dex"),
        c.lvl()/4,
	c.stat("str")-t.stat("con"),
	c.stat("str")-t.stat("con")
      )
    );
  }

}
