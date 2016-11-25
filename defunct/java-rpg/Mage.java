import java.util.HashMap;

public class Mage extends Monster {

  Mage() {

    sprite  =          '*';
    name    =       "Mage";
    color   =     Color.green;
    weapon  =   new   Staff();
    item    =   new  Potion();
    stats   =   new HashMap();

    spells  =   new Spell[3];
    spells[0] = new Drain();
    spells[1] = new Cure();
    spells[2] = new Fire();

    stat("lvl", (new Dice(1, 3, 1).roll()));
    stats(new int[]{8, 12, 10, 15, 12, 12});
    randomize();
    derive();

    loadImage("Goblin.txt");
  }

  public void attack(EnemyGroup eg, Party p, Map m) {
    Character c = p.cs[p.rand()];
    if (mp() >= 5)
           if (hp() <   maxhp()/3) spell("cure" ).act(this, this);
      else if (hp() < 2*maxhp()/3) spell("drain").act(this, c);
      else                         spell("fire" ).act(this, c);
    else weapon.act(this, c);
  }

}
