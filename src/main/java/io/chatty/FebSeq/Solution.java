package CodilityFebSeq;

import java.util.Scanner;

/**
 * Created by Chaitanya S. Turubati on 28/7/17.
 */

public class Solution {

  public class TwoTwoMatrix {

    private final static long BASE = 10000103;

    private long a11 = 1;
    private long a12 = 0;
    private long a21 = 0;
    private long a22 = 1;

    private void normalize() {
      if (a11 >= BASE && a12 >= BASE && a21 >= BASE && a22 >= BASE) {
        a11 %= BASE;
        a12 %= BASE;
        a21 %= BASE;
        a22 %= BASE;
      }
    }

    public void multiply(TwoTwoMatrix other) {
      long a11 = this.a11 * other.a11 + this.a12 * other.a21;
      long a12 = this.a11 * other.a12 + this.a12 * other.a22;
      long a21 = this.a21 * other.a11 + this.a22 * other.a21;
      long a22 = this.a21 * other.a12 + this.a22 * other.a22;

      this.a11 = a11;
      this.a12 = a12;
      this.a21 = a21;
      this.a22 = a22;

      this.normalize();
    }

    public void add(TwoTwoMatrix other) {
      long a11 = this.a11 + other.a11;
      long a12 = this.a12 + other.a12;
      long a21 = this.a21 + other.a21;
      long a22 = this.a22 + other.a22;

      this.a11 = a11;
      this.a12 = a12;
      this.a21 = a21;
      this.a22 = a22;

      this.normalize();
    }
  }

  public int solution(int N, int M) {

    TwoTwoMatrix baseMatrix = new TwoTwoMatrix();
    baseMatrix.a11 = 1;
    baseMatrix.a12 = 1;
    baseMatrix.a21 = 1;
    baseMatrix.a22 = 0;

    TwoTwoMatrix powN = new TwoTwoMatrix();  // identity matrix

    if ((N & 1) == 1) {
      powN.multiply(baseMatrix);
    }

    long NTemp = N >> 1;
    while (NTemp > 0) {
      baseMatrix.multiply(baseMatrix);

      if ((NTemp & 1) == 1) {
        powN.multiply(baseMatrix);
      }

      NTemp = NTemp >> 1;
    }

    System.out.printf("[%d, %d, %d, %d]\n", powN.a11, powN.a12, powN.a21, powN.a22);

    return (int) powN.a12;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Solution solution = new Solution();

    while (true) {
      solution.solution(scanner.nextInt(), 0);
    }
  }
}
