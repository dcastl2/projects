import java.util.HashMap;

public class JeanPaul extends Character {

  JeanPaul() {

    sprite    =          '*';
    name      =  "Jean-Paul";
    color     =  Color.gray;

    weapon    = new Sword();
    stats     = new HashMap();
    spells    = new Spell[1];
    spells[0] = new Cure();


    lvl(1);
    stats(new int[]{18, 10, 12, 14, 16, 10});

    derive();
  }

}
