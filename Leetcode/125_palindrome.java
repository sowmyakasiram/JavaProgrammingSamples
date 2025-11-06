//easy method:
//time complexity is O(n), but since i m creating new string, space complexity becomes O(n)
class Solution {
    public boolean isPalindrome(String s) {
        boolean isPalindromStr=false;
        if(s!=null){
            String cleaned = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
            String sb=new StringBuilder(cleaned).reverse().toString();
            if (cleaned.equals(sb)){
                isPalindromStr= true;
            }
        }
        return isPalindromStr;
    }
}

//two pointer method: without creating new string, comparing characters makes Space complexity as O(1)

class Solution {
    public boolean isPalindrome(String s) {
     if(s ==null) {
            return false;
        }
        int left=0;
        int right= s.length()-1;
        while(left<right){
            while(left<right && !Character.isLetterOrDigit(s.charAt(left))){
                left ++;
            }
            while(left<right && !Character.isLetterOrDigit(s.charAt(right))){
                right --;
            }
            if(Character.toLowerCase(s.charAt(left))!=Character.toLowerCase(s.charAt(right))){
                return false;
            }
            left++;
            right --;
        }
        return true;
    }   
    
}

