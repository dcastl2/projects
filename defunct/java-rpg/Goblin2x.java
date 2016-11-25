public class Goblin2x extends EnemyGroup {

  Goblin2x() {
    gold  = 2;
    exp   = 2;
    ms    = new Monster[2]; 
    ms[0] = new Goblin();
    ms[1] = new Goblin();
  }

  public String toString() {
    return "a pair of goblins.";
  }

}
