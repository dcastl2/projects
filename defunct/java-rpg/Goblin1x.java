public class Goblin1x extends EnemyGroup {

  Goblin1x() {
    gold  = 1;
    exp   = 1;
    ms    = new Monster[1]; 
    ms[0] = new Goblin();
  }

  public String toString() {
    return "a lone goblin.";
  }

}
