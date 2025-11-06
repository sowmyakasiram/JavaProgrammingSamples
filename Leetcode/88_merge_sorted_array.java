/*
 Time complexity- O(m+n), as we iterate while loop for m+n times
 Space complexity -O(1)

 The first while loop runs as long as both i and j are ≥ 0.
In total, it executes at most m + n iterations (since each iteration decrements either i or j).

The second while loop runs only when some elements of nums2 remain.
This can happen only when the first loop finishes early — but overall, across both loops,
each element from both arrays is visited exactly once.

*/

import java.util.ArrayList;
import java.util.Arrays;

public class 88_merge_sorted_array {
    public static void main(String[] args) {
        int nums1[]={1,2,3,0,0,0};
        int nums2[]={2,5,6};
        merge(nums1,3,nums2,3);
        System.out.println("Num1 array:"+ Arrays.toString(nums1));

    }

        public static void merge(int[] nums1, int m, int[] nums2, int n) {
            int i=m-1;
            int j=n-1;
            int k=m+n-1;

            while(i>=0 && j>=0){
                if(nums1[i]> nums2[j]){
                    nums1[k]=nums1[i];
                    i--;
                }
                else{
                    nums1[k]=nums2[j];
                    j--;
                }
                k--;
            }

            while(j>=0){
                nums1[k]=nums2[j];
                j--;
                k--;
            }
        }


}
