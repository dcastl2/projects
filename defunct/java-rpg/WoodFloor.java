public class WoodFloor extends Tile {

  WoodFloor(int dx, int dy) {
    T     = new char[][]{
             {'/', '-', '\\'},
             {'|', '=', '|'},
             {'\\', '-', '/'}
            };
    t     = T[1+dx][1+dy];
    can   = true;
    color = Color.yellow;
  }

  WoodFloor() {
    t     = '=';
    T     = new char[][]{
             {'/', '-', '\\'},
             {'|', '=', '|'},
             {'\\', '-', '/'}
            };
    can   = true;
    color = Color.yellow;
  }

}
