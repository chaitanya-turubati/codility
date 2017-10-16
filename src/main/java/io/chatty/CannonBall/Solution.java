package CodilityCannonBall;

/**
 * Created by Chaitanya S. Turubati on 31/7/17.
 */
public class Solution {

  public int solution(int[] A, int[] B) {
    int index = A.length - 1;

    for (int i = 0; i < B.length; ++i) {
      while (A[index] < B[i]) {
        index--;

        if (index <= 0) {
          return i;
        }
      }

      if (index-- == 0) {
        return i + 1;
      }
    }

    return B.length;
  }

  public static void main(String argc[]) {
    int[] A = {5, 6, 4, 3, 6, 2, 3};
    int[] B = {2, 3, 3, 3, 4};

    System.out.println((new Solution()).solution(A, B));
  }
}
