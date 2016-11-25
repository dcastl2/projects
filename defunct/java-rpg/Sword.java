public class Sword extends Weapon {

  Sword() {
    name = "Sword";
    dice.mul = 1;
    dice.max = 8;
    dice.plus= 1;
  }

  public void action(Character c, Character t) {
    t.hp_dec(
     dice.roll( 
                c.stat("dex")-t.stat("dex")+c.lvl()-t.lvl(),
                c.lvl()/5,
		c.stat("str")-t.stat("con"),
		c.stat("str")-t.stat("con")
              )
    );
  }

}
