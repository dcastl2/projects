public abstract class Weapon extends Item {

  Dice   dice = new Dice();
  String name;

  public abstract void action(Character c, Character t);

  public void act(EnemyGroup eg, Party p, Character c, Character t) {
    attack_message(eg, p, c, t);
    action(c, t);
  }

  public void attack_message(EnemyGroup eg, Party p, Character c, Character t) {
    Text.typeout(eg, p, c.name+" attacks "+t.name+" with the "+name+".");
  }

}
