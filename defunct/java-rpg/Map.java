import java.io.InputStream;
import java.io.FileInputStream;

public class Map {

  public int HEIGHT;
  public int WIDTH;
  public int xpos, ypos;

  EnemyGroup[] egs;

  double      encounter_rate;
  public Tile map[][];


  public void interpret(Party p, String cmd) {
    int n = cmd.length();
    if (n==1) 
      walk(p, cmd.charAt(0));
    else if (cmd.equals("gold")) { 
      Text.typeoutln("You have "+p.inv.gold+" gold"); 
    }
    else if (cmd.equals("info")) {
      p.printinfo();
    }
    else if (cmd.startsWith("use")) {
      cmd = cmd.replace("use ", "");
      if (p.inv.find(cmd) != null)
        p.inv.find(cmd).act(p.cs[0], p.cs[0]);
      else Text.typeout("No such item: "+cmd);
      p.printinfo();
    }
  }


  public void walk(Party p, char k) {
    if (k=='w') { 
      if (p.dir == Direction.NORTH)
        ypos = (ypos-1<=0) ? ypos : ypos-1;
      else p.dir = Direction.NORTH;
    }
    else if (k=='a') {
      if (p.dir == Direction.WEST)
       xpos = (xpos-1<=0)    ? xpos : xpos-1;
      else p.dir = Direction.WEST;
    }
    else if (k=='s') {
      if (p.dir == Direction.SOUTH)
       ypos = (ypos+1>=HEIGHT) ? ypos : ypos+1;
      else p.dir = Direction.SOUTH;
    }
    else if (k=='d') {
      if (p.dir == Direction.EAST)
       xpos = (xpos+1>=WIDTH) ? xpos : xpos+1;
      else p.dir = Direction.EAST;
    }
    //Sound.play("footsteps.wav");
  }


  public boolean corner(int i, int j) {
    return (i==0)      && (j==0)     ||
           (i==0)      && (j==WIDTH) ||
           (i==HEIGHT) && (j==WIDTH) ||
           (i==HEIGHT) && (j==0);
  }


  public boolean edge(int i, int j) {
    return (i==0)      && (j>0 && j<WIDTH)  ||
           (i==HEIGHT) && (j>0 && j<WIDTH)  ||
           (j==0)      && (i>0 && i<HEIGHT) ||
           (j==WIDTH)  && (i>0 && i<HEIGHT);
  }


  public void print(Party p) {
    System.out.println("\033H\033[2J");
    for (int i=ypos-HEIGHT-1; i<ypos+HEIGHT; i++) {
      for (int j=xpos-WIDTH-1; j<xpos+WIDTH; j++) {
        if (j==xpos && i==ypos)
	  System.out.print(p.mapcursor());
        else if (i>0 && i<HEIGHT && j>0 && j<WIDTH) 
	  if (map[i][j] == null)
	     System.out.print(i+" "+j);
	  else 
	     System.out.print(map[i][j]);
	else 
	  System.out.print(' ');
      }
      System.out.println();
    }
  }


  public void printdark(Party p) {
    for (int i=ypos-HEIGHT-1; i<ypos+HEIGHT; i++) {
      for (int j=xpos-WIDTH-1; j<xpos+WIDTH; j++) {
        if (j==xpos && i==ypos)
	  System.out.print(p.mapcursor());
        else if (i>0 && i<HEIGHT && j>0 && j<WIDTH) 
	  if (map[i][j] == null)
	     System.out.print(i+" "+j);
	  else 
	     System.out.print(map[i][j].black());
	else 
	  System.out.print(' ');
      }
      System.out.println();
    }
  }


  public void fadeout(Party p) {
    for (int k=3; k>=0; k--) {
      for (int i=ypos-HEIGHT-1; i<ypos+HEIGHT; i++) {
        for (int j=xpos-WIDTH-1; j<xpos+WIDTH; j++) {
          if (j==xpos && i==ypos)                  System.out.print(p.mapcursor());
          else if (i>0 && i<HEIGHT && j>0 && j<WIDTH) {
      	         if (k==0) System.out.print(map[i][j].black());
      	    else if (k==1) System.out.print(map[i][j].gray());
      	    else if (k==2) System.out.print(map[i][j].white());
	  } else           System.out.print(' ');
        }
        System.out.println();
      }
      try { Thread.sleep(150);
      } catch (Exception e) {}
    }
  }


  public void fadein(Party p) {
    for (int k=0; k<3; k++) {
      for (int i=ypos-HEIGHT-1; i<ypos+HEIGHT; i++) {
        for (int j=xpos-WIDTH-1; j<xpos+WIDTH; j++) {
          if (j==xpos && i==ypos)                  System.out.print(p.mapcursor());
          else if (i>0 && i<HEIGHT && j>0 && j<WIDTH) {
  	         if (k==0) System.out.print(map[i][j].black());
  	    else if (k==1) System.out.print(map[i][j].gray());
  	    else if (k==2) System.out.print(map[i][j].white());
	  } 
	  else System.out.print(' ');
        }
        System.out.println();
      }
      try { Thread.sleep(150);
      } catch (Exception e) {}
    }
  }

}
