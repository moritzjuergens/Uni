package ITsecurity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Koinzidenzindex {

    public static void main(String[] args) throws Exception {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String message = bufferedReader.readLine().toLowerCase();
        String[] sonderzeichen = {
                ",", " ", "?", "!", "\"", "'", "=", ";", "-", "_", ":"
        };

        for (String s : sonderzeichen) {
            message = message.replace(s, "");
        }
        //System.out.println(message);
        char[] messageChar = message.toCharArray();
        Set<Character> charSet = new LinkedHashSet<>();
        float coIndex = 0;
        HashMap<Character, Integer> lettermap = new HashMap<>();


        for (char c : messageChar) {
            if (lettermap.containsKey(c)) {
                lettermap.put(c, lettermap.get(c) + 1);
            } else {
                lettermap.put(c, 1);
                charSet.add(c);
            }
        }

        for (char i : charSet) {
            if (lettermap.containsKey(i)) {
                //System.out.println(lettermap.get(i));
                float p = (float) lettermap.get(i) / (float) messageChar.length;
                coIndex += (p * p);
                //System.out.println(coIndex);
            }
        }
        System.out.println(lettermap);
        System.out.println("Koinzidenzindex: " + (coIndex * 100) + "%");
    }
}