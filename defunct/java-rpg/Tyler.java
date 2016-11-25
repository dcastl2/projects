import java.util.HashMap;

public class Tyler extends Character {

  Tyler() {

    sprite    =          '*';
    name      =      "Tyler";
    color     =  Color.green;

    weapon    = new Sword();
    stats     = new HashMap();
    spells    = new Spell[2];
    spells[0] = new Cure();
    spells[1] = new Fire();

    lvl(1);
    stats(new int[]{13, 12, 12, 14, 14, 15});

    derive();
  }

}
