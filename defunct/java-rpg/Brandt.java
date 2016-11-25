import java.util.HashMap;

public class Brandt extends Character {

  Brandt() {

    sprite  =          '*';
    name    =         "Brandt";
    color   =    Color.blue;

    weapon    = new Staff();
    stats     = new HashMap();
    spells    = new Spell[2];
    spells[0] = new Cure();
    spells[1] = new Fire();

    lvl(1);
    stats(new int[]{10, 10, 10, 18, 18, 14});

    derive();
  }


}
