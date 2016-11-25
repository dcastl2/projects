import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Text {

  public static void wait(int n) {
      try {
        Thread.sleep(n);
      } catch(InterruptedException e) {
      }
  }


  public static void getEnter() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      br.readLine();
    } catch (Exception e) {}
  }




  public static void typeout(EnemyGroup eg, Party p, String s) {
    for (int i=0; i<s.length(); i++) {
      System.out.print("\033H\033[2J");
      eg.printGroup();
      System.out.println();
      p.printinfo();
      System.out.println();
      System.out.println(s.substring(0, i));
      try {
        Thread.sleep(40);
      } catch(InterruptedException e) {
      }
    }
  }

  public static void typeout(String s) {
    for (int i=0; i<s.length(); i++) {
      try {
        Thread.sleep(12);
      } catch(InterruptedException e) {
      }
      System.out.print(s.charAt(i));
    }
  }


  public static void typeoutln(String s) {
    for (int i=0; i<s.length(); i++) {
      try {
        Thread.sleep(12);
      } catch(InterruptedException e) {
      }
      System.out.print(s.charAt(i));
    }
    System.out.println();
  }

  public static void prompt(Character c) {
    Text.typeout(c.name()+"> "+Color.white);
  }


  public static String getcmd(Character c) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String cmd = "";
    try {
      cmd = br.readLine();
    } catch (Exception e) {}
    return cmd;
  }

}
