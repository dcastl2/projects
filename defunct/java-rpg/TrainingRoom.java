public class TrainingRoom extends Map {

  TrainingRoom(Party p) {

    encounter_rate = .15;
    xpos           =  10;  
    ypos           =  10;

    HEIGHT = 20;
    WIDTH  = 40;

    map    = new Tile[HEIGHT][WIDTH];

    egs    = new EnemyGroup[3];
    egs[0] = new Goblin1x(); 
    egs[1] = new Goblin2x();
    egs[2] = new Mage1x();

    int x, y;
    for (int i=0; i<HEIGHT; i++) 
      for (int j=0; j<WIDTH; j++) {
         x = (   i     < 2)  ? -1 : 
	     (HEIGHT-i < 2)  ?  1 : 0;
         y = (   j     < 2)  ? -1 : 
	     (WIDTH-j  < 2)  ?  1 : 0;
	 if (i==1 && j==WIDTH/2)
	   map[i][j] = new Door(x, y);
	 else map[i][j] = new WoodFloor(x, y);
      }
  }

}
