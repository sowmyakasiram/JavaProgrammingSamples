/*
 Time complexity- O(n), as 1 for loop for n elements
 Space complexity -O(1)

*/

class Solution {
    public int removeDuplicates(int[] nums) {
      int k=0;
      for(int i=1;i<nums.length;i++){
        if(nums[i]!=nums[k]){
            k++;
            nums[k]=nums[i];
        }
      }
      return k+1;
    }
}
