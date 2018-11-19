package com.mvp.vincentwang.rainbowtranslate;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        int n = s.length();
        int max = 0;

        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {
                if ((end - start + 1) < max) continue;
                String sub = s.substring(start, end + 1);
                if (checkisP(sub)) {
                    if ((end - start + 1) > max) {
                        max = end - start + 1;
                        res = s.substring(start, end + 1);
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
            front = s.substring(0, m);
            back = s.substring(m + 1, n);
        }
        int start = 0;
        int end = back.length() - 1;
        while (start < front.length()) {
            if (front.charAt(start) != back.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    @Test
    public void test3() {
        System.out.print(myAtoi("+1"));
    }

    public int threeSumClosest(int[] nums, int target) {
        int res = 0;
        int max_dis = Integer.MAX_VALUE;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int lo = i + 1;
            int hi = nums.length - 1;
            while (lo < hi) {
                int temp = target - (nums[i] + nums[lo] + nums[hi]);
                if (max_dis > Math.abs(temp)) {
                    res = nums[i] + nums[lo] + nums[hi];
                    max_dis = Math.abs(temp);
                }
                if (temp == 0) {
                    return res;
                } else if (temp > 0) {
                    while (lo < hi && nums[lo] == nums[lo + 1]) lo++;
                    lo++;
                } else {
                    while (lo < hi && nums[hi] == nums[hi - 1]) hi--;
                    hi--;
                }
            }
        }
        return res;
    }

    private int myAtoi(String str) {
        str = str.trim();
        String sRes = "";
        int n = str.length();
        for (int i = 0; i < n; i++) {
            char tChar = str.charAt(i);
            if (sRes.equals("") && tChar == ' ') {
                continue;
            } else if (Character.isDigit(tChar)) {
                sRes += tChar;
            } else if (sRes.equals("") && (tChar == '-' || tChar == '+')) {
                sRes += tChar;
            } else {
                break;
            }
        }
        int res = 0;
        if (sRes.equals("") || sRes.equals("-") || sRes.equals("+")) return res;
        try {
            res = Integer.valueOf(sRes);
        } catch (Exception e) {
            if (sRes.charAt(0) == '-') {
                res = Integer.MIN_VALUE;
            } else {
                res = Integer.MAX_VALUE;
            }
        }
        return res;
    }

    public int divide(int dividend, int divisor) {
        double pDivisor = Math.abs((double) divisor);
        double pDividend = Math.abs((double) dividend);
        double count = 0;
        while (pDividend >= pDivisor) {
            pDividend -= pDivisor;
            count++;
        }
        if ((divisor > 0 && dividend < 0) || (dividend > 0 && divisor < 0)) {
            count *= -1;
        }
        if (count > Integer.MAX_VALUE || count < Integer.MIN_VALUE) return Integer.MAX_VALUE;
        return (int) count;
    }

    public String countAndSay(int n) {
        String res = "1";

        for (int i = 1; i < n; i++) {
            int count = 1;
            String temp = "";
            for (int sc = 0; sc < res.length(); sc++) {
                if (sc > 0) {
                    if (res.charAt(sc) == res.charAt(sc - 1)) {
                        count++;
                    } else {
                        temp = temp + count + res.charAt(sc - 1);
                        count = 1;
                    }
                }
            }
            temp = temp + count + res.charAt(res.length() - 1);
            res = temp;
        }
        return res;
    }

    public int solutions(int[] A) {
        // write your code in Java SE 8
        int res = 0;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < A.length; i++) {
            if (!set.contains(A[i])) {
                set.add(A[i]);
                if (res < A[i]) {
                    res = A[i];
                }
            } else {
                res = 0;
            }
        }
        return res;
    }

    public int solutionsort(int[] A) {
        // write your code in Java SE 8
        Arrays.sort(A);
        for (int i = A.length - 1; i - 2 >= 0; i--) {
            if (A[i - 1] - A[i] + A[i - 2] >= 0) {
                return 1;
            }
        }
        return 0;
    }
    @Test
    public void codility() {
        int[] A  = new int[]{1,2,3,4,5};
//        int[] A = new int[]{1, 2, 5, 9, 9};
//        int[] A = new int[100000];
//        A[3] = 5;
        int a = solutionwwww(A, 5);
//        int a = solution(9);
        System.out.print(a);
    }


    public int solution(int N) {
        // write your code in Java SE 8
        String currentNum = "11";
        if (N == 0) return 1;
        while (N>1){
            N--;
            String ten = currentNum+"0";
            currentNum = "0"+currentNum;
            String powNum = "";
            int carry = 0;
            for(int i = currentNum.length()-1;i>=0;i--){
                int tRes = Character.getNumericValue(ten.charAt(i)) + Character.getNumericValue(currentNum.charAt(i)) + carry;
                carry = tRes / 10;
                tRes = tRes % 10;
                powNum = String.valueOf(tRes) + powNum;
            }
            if (carry == 1){
                powNum = "1" + powNum;
            }
            currentNum = powNum;
        }
        int fRes = 0;
         for(int x = 0;x<currentNum.length();x++){
            if(currentNum.charAt(x) == '1'){
                fRes++;
            }
         }
        return fRes;
    }


    int solutionwwww(int[] A, int X) {
        int N = A.length;
        if (N == 0) {
            return -1;
        }
        //低
        int l = 0;
        //高
        int r = N - 1;
        while (l < r) {
            int m = (l + r) / 2;
            if (A[m] == X) {
                return m;
            } else if (A[m] > X) {
                r = m - 1;
            } else {
                l = m+1;
            }
        }
        if (A[l] == X) {
            return l;
        }
        return -1;
    }

    public int solutionS(int[] A) {
        // write your code in Java SE 8
        Arrays.sort(A);
        int n = A.length;
        if (n == 0) return 1;
        for (int i = 0; i < n - 1; i++) {
            if (A[i + 1] > 0 && A[i] > 0) {
                if (A[i + 1] - A[i] >= 2) {
                    return A[i] + 1;
                }
            }
        }
        if (A[n - 1] <= 0) {
            return 1;
        }
        return A[n - 1] + 1;
    }

    public String solution(String S) {
        // write your code in Java SE 8
        int n = S.length();
        StringBuilder allDigit = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (Character.isDigit(S.charAt(i))) {
                allDigit.append(S.charAt(i));
            }
        }
        StringBuilder res = new StringBuilder();
        for (int j = 0; j < allDigit.length(); j++) {
            if (j % 3 == 0 && j > 0) {
                res.append('-');
            }
            res.append(allDigit.substring(j, j + 1));
        }
        if (res.substring(res.length() - 2, res.length() - 1).equals("-")) {
            res.replace(res.length() - 2, res.length() - 1, "");
            res.insert(res.length() - 2, "-");
        }
        return res.toString();
    }

}