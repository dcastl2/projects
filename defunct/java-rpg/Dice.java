import java.util.Random;

public class Dice {

  Random  rand;
  Element element;

  int mul;
  int max; 
  int plus;
  int times;
  int sum;


  Dice() {
    rand = new Random(System.currentTimeMillis());
  }


  Dice(int mul, int max, int plus) {
    rand = new Random(System.currentTimeMillis());
    this.mul  = mul;
    this.max  = max;
    this.plus = plus;
  }


  int roll(int hit, int mul, int max, int plus) {
    times = sum = 0;
    mul  += this.mul;
    max  += this.max;
    plus += this.plus;
    if (max < 1 || mul < 1)
      return 0;
    for (int i=0; i<mul; i++) 
      if ((2.0*Math.atan(hit/5.0)/3.141592+1.0) > rand.nextInt(100)/100.0) { 
           times++;
           sum += (rand.nextInt(max)+1);
      }
    return ge0(sum+plus);
  }


  int roll(int mul, int max, int plus) {
    int sum = 0;
    mul  += this.mul;
    max  += this.max;
    plus += this.plus;
    if (max < 1 || mul < 1)
      return 0;
    for (int i=0; i<mul; i++) {
      sum += (rand.nextInt(max)+1);
      System.out.print(sum+" ");
    }
    return ge0(sum+plus);
  }


  int roll() {
    int sum = 0;
    for (int i=0; i<mul; i++)
      sum += (rand.nextInt(max)+1);
    return sum+plus;
  }

  static int ge1(int x) {
    return (x <= 0) ? 1 : x;
  }

  static int ge0(int x) {
    return (x <= 0) ? 1 : x;
  }

}
