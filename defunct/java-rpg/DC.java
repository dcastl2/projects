import java.util.HashMap;

public class DC extends Character {

  DC() {

    sprite  =          '*';
    name    =         "DC";
    color   =    Color.red;

    weapon    = new Crossbow(99);
    stats     = new HashMap();
    spells    = new Spell[4];

    spells[0] = new Sleep();
    spells[1] = new Cure();
    spells[2] = new Fire();
    spells[3] = new Scan();
    
    lvl(2);
    stats(new int[]{ 8, 13, 12, 16, 18, 14});

    derive();
  }


}
