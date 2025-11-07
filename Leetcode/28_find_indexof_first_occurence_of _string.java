//space complexity O(1), time complexity O(n*m) -- two pointer approach

public class Main {
    public static void main(String[] args) {
        String haystack="samissadly";
        String needle="sad";
        System.out.println("Flag:"+strStr(haystack,needle));
    }
    public static int strStr(String haystack, String needle) {
        int n=haystack.length();
        int m=needle.length();
        if(m>n) return -1;

        for(int i=0;i<=n-m;i++){
            int j=0;
            while(j<m && haystack.charAt(i+j)==needle.charAt(j)){
                j++;
            }
            if(j==m){
                return i;
            }
        }
        return -1;

    }
}

//inbuild function
public class Main {
    public static void main(String[] args) {
        String haystack="iamsadtoday";
        String needle="sad";
        System.out.println("Flag:"+haystack.indexOf(needle));
    }
}
