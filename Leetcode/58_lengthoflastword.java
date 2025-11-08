//since we do not create new objects. time complexity=O(n), space complexity O(1)
class Solution {
    public int lengthOfLastWord(String s) {
       int length=0;
       int i=s.length() -1;
       //skip any spaces at end of string
       while (i>=0 && s.charAt(i)==' '){
        i--;
       }
    //find length of last word
       while(i>=0 && s.charAt(i)!=' '){
        length++;
        i--;
       }
       return length;

    }
}
