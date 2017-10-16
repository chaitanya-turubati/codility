package CodilityWireBurn;

/**
 * Created by Chaitanya S. Turubati on 31/7/17.
 */
public class Solution {

  private Integer N;

  private Integer left;
  private Integer right;

  private UnionFind channels;

  private void initialize(Integer N) {
    this.N = N;
    this.left = (N - 1) * (N - 1);
    this.right = this.left + 1;

    this.channels = new UnionFind((N - 1) * (N - 1) + 2);
  }

  private void printState() {
    System.out.println("LeftSeaId: " + channels.find(this.left));
    System.out.println("RightSeaId: " + channels.find(this.right));

    for (int i = N - 2; i >= 0; i--) {
      for (int j = 0; j < N - 1; j++) {
        System.out.printf("%1$5d ", channels.find(i * (N - 1) + j));
      }
      System.out.println("");
    }
    System.out.println("");
  }

  public int solution(int N, int[] A, int[] B, int[] C) {

    initialize(N);

    for (int i = 0; i < A.length; ++i) {

      // Opens left sea by left door.
      if (A[i] == 0 && C[i] == 0) {
        channels.union(this.left, B[i] * (N - 1));
      } else if (B[i] == (N - 1) && C[i] == 1) {
        channels.union(this.left, A[i] + (N - 1) * (N - 2));
      }
      // Opens the right sea
      else if (B[i] == 0 && C[i] == 1) {
        channels.union(this.right, A[i]);
      } else if (A[i] == (N - 1) && C[i] == 0) {
        channels.union(this.right, (N - 2) + B[i] * (N - 1));
      } else if (C[i] == 0) {
        channels.union(A[i] + B[i] * (N - 1), A[i] - 1 + B[i] * (N - 1));
      } else {
        channels.union(A[i] + (B[i] - 1) * (N - 1), A[i] + B[i] * (N - 1));
      }

//      printState();

      if (channels.find(left).equals(channels.find(right))) {
        return i + 1;
      }
    }

    return -1;
  }
//
//  public static void main(String[] args) {
//    Integer N = 4;
//    int[] A = {0, 1, 1, 2, 3, 2, 1, 0, 0};
//    int[] B = {0, 1, 1, 1, 2, 2, 3, 1, 0};
//    int[] C = {0, 1, 0, 0, 0, 1, 1, 0, 1};
//
//    Solution solution = new Solution();
//    System.out.println("Ended with: " + solution.solution(N, A, B, C));
//  }

  public static class UnionFind {

    private Integer[] parent;
    private Integer[] rank;

    private Integer count;

    public UnionFind(Integer count) {
      this.count = count;
      this.parent = new Integer[count];
      this.rank = new Integer[count];

      for (Integer i = 0; i < count; ++i) {
        this.parent[i] = i;
        this.rank[i] = 0;
      }
    }

    private Integer find(Integer x) {
      while (!x.equals(parent[x])) {
        parent[x] = parent[parent[x]];
        x = parent[x];
      }

      return x;
    }

    public Integer count() {
      return this.count;
    }

    public void union(Integer p, Integer q) {
      Integer rootP = find(p);
      Integer rootQ = find(q);

      if (rootP.equals(rootQ)) {
        return;
      }

      if (rank[rootP] < rank[rootQ]) {
        parent[rootP] = rootQ;
      } else if (rank[rootP] > rank[rootQ]) {
        parent[rootQ] = rootP;
      } else {
        parent[rootQ] = rootP;
        rank[rootP]++;
      }

      count--;
    }
  }

}
