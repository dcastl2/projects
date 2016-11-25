public class BrickWall extends Tile {

  BrickWall(int dx, int dy) {
    T     = new char[][]{
             {'/', '-', '\\'},
             {'|', '=', '|'},
             {'\\', '-', '/'}
            };
    t     = T[1+dx][1+dy];
    can   = true;
    color = Color.red;
  }

  BrickWall() {
    t     = '=';
    T     = new char[][]{
             {'/', '-', '\\'},
             {'|', '=', '|'},
             {'\\', '-', '/'}
            };
    can   = true;
    color = Color.red;
  }

}
