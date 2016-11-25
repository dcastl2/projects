public class Inventory {

  int gold;

  final static int max=256;
  Item item[];

  Inventory(int gold) {
    this.gold = gold;
  }

  Inventory(int gold, Item item[]) {
    this.gold = gold;
    this.item = item;
  }

  Item find(String name) {
    for (Item i : item)
      if (name.equals(i.name))
        return i;
    return null;
  }

}
