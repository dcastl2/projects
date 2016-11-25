import java.util.HashMap;

public class Maxwell extends Character {

  Maxwell() {

    sprite    =          '*';
    name      =    "Maxwell";
    color     =  Color.green;

    weapon    = new Staff();
    stats     = new HashMap();

    spells    = new Spell[4];
    spells[0] = new Fire();
    spells[1] = new Drain();
    spells[2] = new Osmose();
    spells[3] = new Death();

    lvl(1);
    stats(new int[]{10, 14, 12, 16, 16, 12});

    derive();
  }

}
