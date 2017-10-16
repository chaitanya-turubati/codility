package CodilityClocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Chaitanya S. Turubati on 1/8/17.
 */
public class Solution {

  private int N;
  private int M;
  private int P;

  private int[][] handMatrix;
  private int[] startIdx;

  private void printA() {
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < M; ++j) {
        System.out.printf("%d ", handMatrix[i][j]);
      }
      System.out.println("");
    }
  }

  public int solution(int[][] A, int P) {
    handMatrix = A;
    N = A.length;
    M = A[0].length;
    this.P = P;

    this.handMatrix = new int[N][M];
    this.startIdx = new int[N];

    for (int i = 0; i < N; ++i) {
      Arrays.sort(A[i]);

      int highestDiff = 0;
      int highestIndex = -1;
      for (int j = 1; j < M; ++j) {
        handMatrix[i][j] = A[i][j] - A[i][j - 1];

        if (handMatrix[i][j] > highestDiff) {
          highestDiff = handMatrix[i][j];
          highestIndex = j;
        }
      }

      handMatrix[i][0] = P + A[i][0] - A[i][M - 1];

      if (handMatrix[i][0] > highestDiff) {
        highestDiff = handMatrix[i][0];
        highestIndex = 0;
      }

      int j = highestIndex + 1;
      int currL = 1;
      int currIndex = highestIndex;
      int maxL = 0;

      Set<Integer> candidates = new HashSet<>();
      while (true) {
        int jActual = j % M;

        if (handMatrix[i][jActual] == highestDiff) {
          if (currL > maxL) {
            maxL = currL;
            candidates.clear();
            candidates.add(currIndex);
          } else if (currL == maxL) {
            candidates.add(currIndex);
          }

          currIndex = jActual;
          currL = 0;
        }

        if (jActual == highestIndex) {
          break;
        }

        j++;
        currL++;
      }

      for (int l = 0; l < maxL; ++l) {
        int maxV = 0;
        for (Integer cand : candidates) {
          if (handMatrix[i][cand + l] > maxV) {
            maxV = handMatrix[i][cand + l];
          }
        }

        for (Integer cand : candidates) {
          if (handMatrix[i][cand + l] != maxV) {
            candidates.remove(cand);
          }
        }

        if (candidates.size() == 1) {
          break;
        }
      }

      this.startIdx[i] = (int) candidates.toArray()[0];
    }

    List<Integer> indices = new ArrayList<>();
    for (int i = 0; i < N; ++i) {
      indices.add(i);
    }

    Comparator<Integer> comparator = new ClockComparator();
    indices.sort(comparator);

    int totalCombs = 0;
    int base = 0;
    int currMatches = 1;
    for (int i = 1; i < N; ++i) {
      if (comparator.compare(indices.get(i), indices.get(base)) == 0) {
        currMatches++;
      } else {

        totalCombs += currMatches * (currMatches - 1) / 2;

        base = i;
        currMatches = 1;
      }
    }
    totalCombs += currMatches * (currMatches - 1) / 2;

    return totalCombs;
  }

  public class ClockComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
      for (int j = 0; j < M; ++j) {
        int val1 = handMatrix[o1][(j + startIdx[o1]) % M];
        int val2 = handMatrix[o2][(j + startIdx[o2]) % M];

        if (val1 != val2) {
          return Integer.compare(val1, val2);
        }
      }

      return 0;
    }
  }

  public static void main(String[] argc) {
    int[][] A = {
        {1, 2},
        {2, 4},
        {4, 3},
        {2, 3},
        {1, 3}
    };

    int P = 4;

    System.out.print(new Solution().solution(A, P));
  }
}