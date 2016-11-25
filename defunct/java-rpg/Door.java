public class Door extends Tile {

  Door(int dx, int dy) {
    T     = new char[][]{
             {'/', '^', '\\'},
             {'(', 'o', ')'},
             {'\\', 'v', '/'}
            };
    t     = T[1+dx][1+dy];
    can   = true;
    color = Color.magenta;
  }

  Door() {
    T     = new char[][]{
             {'/', '^', '\\'},
             {'(', 'o', ')'},
             {'\\', 'v', '/'}
            };
    t     = T[1][1];
    can   = true;
    color = Color.magenta;
  }

}
