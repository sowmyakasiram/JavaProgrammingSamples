/*
| Variable / Structure      | Space          | Why                               |
| ------------------------- | -------------- | --------------------------------- |
| `sum`, `digit`, `n`       | O(1)           | fixed-size primitives             |
| Temporary loop state      | O(1)           | reused                            |
| HashSet (seen)            | O(k) (bounded) | stores visited numbers            |
| **Total Auxiliary Space** | **O(1)**       | bounded, does not grow with input |
*/

class Solution {
    public boolean isHappy(int n) {
       HashSet<Integer> seen=new HashSet<Integer>();
       while(n!=1 && !seen.contains(n)){
           seen.add(n);
            //get next squared sum of digits
            n=squaredSum(n);
       }
     return n==1;
    }

    public int squaredSum(int n){
        int sum=0;
        while(n>0){
           int digit=n%10;
           sum+=digit*digit;
           n/=10;
        }
        return sum;
    }
}
