import java.util.HashMap;

public class Cliff extends Character {

  Cliff() {

    sprite  =        '*';
    name    =    "Cliff";
    color   =  Color.red;

    weapon    = new Sword();
    stats     = new HashMap();
    spells    = new Spell[1];
    spells[0] = new Cure();

    lvl(1);
    stats(new int[]{16, 14, 14, 10, 12, 14});
    derive();

  }


}
