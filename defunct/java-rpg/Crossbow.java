public class Crossbow extends Weapon {

  int bolts;

  Crossbow(int bolts) {
    name = "Crossbow";
    dice.mul = 2;
    dice.max = 8;
    dice.plus= 1;
    this.bolts = bolts;
  }

  Crossbow() {
    name = "Crossbow";
    dice.mul = 2;
    dice.max = 8;
    dice.plus= 1;
    bolts = 30;
  }

  public void action(Character c, Character t) {
    if (bolts>0) {
      bolts--;
      t.hp_dec(
       dice.roll( 
                  c.lvl()-t.lvl()+c.stat("dex")-t.stat("dex"),
                  c.lvl()/5,
                  c.mod("dex"),
		  c.mod("dex")
                )
      );
    }
    Text.typeoutln(c.name+" has "+bolts+" bolts left in the "+name+".");
  }

}
