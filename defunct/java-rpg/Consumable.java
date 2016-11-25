public abstract class Consumable extends Item {

  Dice dice = new Dice();

  public void act(Character c, Character t) {
    if (c.equals(t)) {
      Text.typeoutln(c.name()+" drank the "+this.name+".");
      action(c);
    }
    else {
      Text.typeoutln(c.name()+" used the "+this.name+"on "+t.name()+".");
      action(t);
    }
  }

  abstract void action(Character c);

}
