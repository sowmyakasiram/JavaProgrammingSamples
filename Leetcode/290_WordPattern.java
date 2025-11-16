//time complexity O(n)
//space complexity O(n)+O(n)=O(2n)=O(n)
// we use set and map in same loop, so it makes O(n)


class Solution {
    public boolean wordPattern(String pattern, String s) {
        int length=pattern.length();
        String[] words=s.split(" ");
        HashMap<Character, String> map = new HashMap<>();
        HashSet<String> usedWords=new HashSet<>();
        if(length == words.length) {
            for (int i = 0; i < length; i++) {
                Character letter=pattern.charAt(i);
                String word=words[i];

                if(!map.containsKey(letter)) {
                    if(usedWords.contains(word)) return false;
                    map.put(letter, word);
                    usedWords.add(word);
                }else{
                    if(!map.get(letter).equals(word)){
                        return false;
                    }
                }

            }
        }else return false;
        return true;
    }
}
