package CodilityLithium13;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Chaitanya S. Turubati on 11/10/17.
 */
public class Solution {


  public static class Clock {

    private int[] hands;
    private int p;

    private Map<Integer, Integer> handGroups = new HashMap<>();

    private int findWidth(int start, int end) {
      if (start < end) {
        return end - start;
      } else {
        return this.p - end + start;
      }
    }

    public Clock(int[] hands, int p) {
      this.hands = hands;
      this.p = p;

      Arrays.sort(this.hands);

      // Pre-rotate the clocks of the hand in following way:
      // 1. Find the max hand gap - for each hand it is succeeding gap.
      // 2. The clock is rotated such that first hand is, hand is maximum gap.
      // 3. In case of tie, select the hand with maximum next gap and so on.

      // 1. First the maximum hand gap.
      int gap = -1;  // temporary variable.
      int maxGap = 0;
      int maxGapStartIndex = 0;

      for (int i = 1; i < hands.length; ++i) {
        gap = hands[i] - hands[i - 1];
        if (gap > maxGap) {
          maxGap = gap;
          maxGapStartIndex = i - 1;
        }
      }

      gap = this.p - hands[hands.length - 1] + hands[0];
      if (gap > maxGap) {
        maxGap = gap;
        maxGapStartIndex = hands.length - 1;
      }

      // Create first batch of groups.
      int currentStartIndex = maxGapStartIndex;
      for (int i = maxGapStartIndex + 1; i < hands.length - 1; ++i) {
        if ((hands[i] - hands[i - 1]) == maxGap) {
          this.handGroups.put(currentStartIndex, i);
          currentStartIndex = i;
        }
      }

      if ((this.p - hands[hands.length - 1] + hands[0]) == maxGap) {
        this.handGroups.put(currentStartIndex, hands.length - 1);
        currentStartIndex = hands.length - 1;
      }

      this.handGroups.put(currentStartIndex, maxGapStartIndex);

      // Keep only those groups which have highest gap.
      while (true) {
        int maxWidth = -1;

        for (Entry<Integer, Integer> handGroup : this.handGroups.entrySet()) {
          int width = findWidth(handGroup.getKey(), handGroup.getValue());
          if (maxWidth < width) {
            maxWidth = width;
          }
        }

        Map<Integer, Integer> newHandGroups = new HashMap<>();
        for (Entry<Integer, Integer> handGroup : handGroups.entrySet()) {
          if (findWidth(handGroup.getKey(), handGroup.getValue()) == maxWidth) {
            newHandGroups.put(handGroup.getKey(), handGroup.getValue());
          }
        }

        if (newHandGroups.size() == handGroups.size()) {
          break;
        }

        for (Integer groupStart : newHandGroups.keySet()) {
          Integer groupEnd = this.handGroups.get(groupStart);
          while (!newHandGroups.containsKey(groupEnd)) {
            // Break if new group is encountered.
            groupEnd = this.handGroups.get(groupEnd);
          }
          newHandGroups.put(groupStart, groupEnd);
        }

      }


    }
  }

  public static class HandGroup {

    int startIndex;
    int endIndex;
  }

  public int solution(int[][] A, int P) {
    return 0;
  }
}
