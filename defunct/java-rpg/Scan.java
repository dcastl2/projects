public class Scan extends Spell {

  Scan () {
    mp        = 1;
    target    = Target.ENEMY;
    name      = "scan";
    color     = Color.gray;
  }

  public void action(Character c, Character t) {
    t.printstatus(2);
  }

}
