import java.util.HashMap;

public class Thomas extends Character {

  Thomas() {

    sprite  =          '*';
    name    =         "Thomas";
    color   =    Color.magenta;

    weapon    = new Staff();
    stats     = new HashMap();
    spells    = new Spell[3];
    spells[0] = new Fire();
    spells[1] = new Cure();
    spells[2] = new Scan();

    lvl(2);
    stats(new int[]{13, 9, 14, 18, 14, 12});

    derive();
  }


}
