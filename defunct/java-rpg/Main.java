import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws IOException, InterruptedException {

    Random      rand =  new Random();
    Item        i[]  = {new Potion()};
    Inventory   inv  =  new Inventory(100, i);
    Character[] cs   = {new DC() };
    Party       p    =  new Party(cs, inv);
    Map         m    =  new TrainingRoom(p);

    double r;
    String cmd;

    while (true) {
      m.print(p);
      Text.prompt(p.cs[0]);
      cmd = Text.getcmd(p.cs[0]);
      m.interpret(p, cmd);
      r = (rand.nextInt(100))/100.0; 
      if (r < m.encounter_rate){
        Battle b;
        b = new Battle(p, m);
      }
    }

  }

}
