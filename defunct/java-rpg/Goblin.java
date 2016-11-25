import java.util.HashMap;

public class Goblin extends Monster {

  Goblin() {

    sprite  =          '*';
    name    =     "Goblin";
    color   =     Color.green;

    weapon  =   new  Dagger();
    item    =   new  Potion();
    stats   =   new HashMap();

    lvl(1);
    stats(new int[]{12, 12, 12, 10, 10, 10});
    randomize();
    derive();

    loadImage("Goblin.txt");
    printImage();

  }

  public void attack(EnemyGroup eg, Party p, Map m) {
    weapon.act(eg, p, this, p.cs[p.rand()]);
  }

}
