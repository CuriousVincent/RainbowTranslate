package com.mvp.vincentwang.rainbowtranslate;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void lengthTest() {

        String[] aa = new String[]{"flower", "flow", "flight"};
        String ans = longestCommonPrefix(aa);

        System.out.print(ans);
    }

    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }

    int lengthTest1(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128];

        for (int j = 0, i = 0; j < n; j++) {
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }

    public String longestCommonPrefix(String[] strs) {
        String result = "";
        Map<Integer, Character> map = new HashMap<>();
        String str0 = strs[0];
        int n = str0.length();
        for (int i = 0; i < n; i++) {
            map.put(i, str0.charAt(i));
        }
        int all = strs.length;
        for (int x = 1; x < all; x++) {
            String strX = strs[x];
            int single = strX.length();
            String temp = "";
            for (int y = 0; y < n; y++) {
                if (y < single) {
                    char singlechar = strX.charAt(y);
                    if (map.get(y) != singlechar) {
                        break;
                    }
                    temp += Character.toString(singlechar);
                } else {
                    break;
                }
            }
            int tempLength = temp.length();
            int resultLength = result.length();
            if (resultLength > tempLength || resultLength == 0) {
                result = temp;
            }
        }
        return result;
    }

    public int[] solution(int[] A, int K) {
        // write your code in Java SE 8
        int n = A.length;
        if (K >= n) {
            K = K % n;
            if (K == 0) {
                return A;
            }
        }
        return count(A, K);
    }

    private int[] count(int[] A, int K) {
        int n = A.length;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            int L = i + K;
            if (L > n - 1) {
                L = L - n;
            }
            res[L] = A[i];
        }
        return res;
    }

    public int permmissing(int[] A) {
        // write your code in Java SE 8
        int n = A.length;
        if (n == 0) return 0;
        Arrays.sort(A);
        int count = A[0];
        int res = 0;
        for (int i = 1; i < n; i++) {
            if (A[i] - count > 1) {
                res = count + 1;
                break;
            }
            count = A[i];
        }
        if (res == 0) {
            if (A[0] == 1) {
                res = count + 1;
            } else {
                res = A[0] - 1;
            }
        }
        return res;
    }

    public int solution(int[] A) {
        // write your code in Java SE 8
        int res = 1;
        int n = A.length;
        Arrays.sort(A);
        int count = A[0];
        for (int i = 1; i < n; i++) {
            if (A[i] - count > 1) {
                res = 0;
                break;
            }
            count = A[i];
        }
        int a = (int) 'A';
        return res;
    }

    @Test
    public void reverse() {
       String ans = longestPalindrome("abacab");
        System.out.println(ans);

//        int a = 1234567893;
//        System.out.println(Integer.toBinaryString(a));
//        int b = reverseBits(a);
//        System.out.println(b);
//        System.out.println(Integer.toBinaryString(b));
    }

    private int reverseBits(int n) {
        int res = 0;
        for (int i = 0; i < 32; i++, n >>= 1) {
            System.out.println("n&1  " + (n & 1));
            System.out.println("res  " + (res << 1));
            res = res << 1 | (n & 1);
        }
        return res;
    }

    public String longestPalindrome(String s) {
        String res = "";
        int n =s.length();
        int max = 0;

        for(int start = 0;start<n;start++){
            for(int end = start;end<n;end++){
                if((end -start+1)<max) continue;
                String sub = s.substring(start,end+1);
                if(checkisP(sub)){
                    if((end -start+1)>max){
                        max = end-start+1;
                        res = s.substring(start,end+1);
                    }
                }
        }
    }
        return res;
}


    private boolean checkisP(String s) {
        int n = s.length();
        if (n == 1) return true;
        String front = "";
        String back = "";
        int m = n / 2;
        if (n % 2 == 0) {
            front = s.substring(0, m);
            back = s.substring(m, n);
        } else {
            front = s.substring(0,m);
            back = s.substring(m+1,n);
        }
        int start = 0;
        int end = back.length()-1;
        while (start<front.length()){
            if(front.charAt(start) != back.charAt(end)){
                return false;
            }
             start++;
            end--;
        }
        return true;
    }
}