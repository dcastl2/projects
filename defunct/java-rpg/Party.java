public class Party {

  Inventory inv;
  Character cs[];
  Direction dir;

  Party(Character cs[], Inventory inv) {
    this.inv = inv;
    this.cs  = cs;
  }

  public int rand() {
    Dice d = new Dice(1, cs.length, -1);
    return d.roll();
  }

  public String mapcursor() {
    String s = cs[0].color;
    if (dir == null) 
      dir = Direction.SOUTH;
         if (dir == Direction.NORTH) s+="^";
    else if (dir == Direction.SOUTH) s+="v";
    else if (dir == Direction.WEST ) s+="<";
    else if (dir == Direction.EAST ) s+=">";
    return s+Color.white;
  }

  public void typeinfo() {
    for (Character c : cs)
      c.typeinfo();
    //Text.getEnter();
  }

  public void printinfo() {
    for (Character c : cs)
      c.printinfo();
    //Text.getEnter();
  }

  public boolean dead() {
    for (Character c : cs)
      if (!c.dead())
        return false;
    return true;
  }

}
