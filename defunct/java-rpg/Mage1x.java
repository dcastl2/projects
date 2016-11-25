public class Mage1x extends EnemyGroup {

  Mage1x() {
    gold  = 4;
    exp   = 3;
    ms    = new Monster[1]; 
    ms[0] = new Mage();
  }

  public String toString() {
    return "a lone mage.";
  }

}
