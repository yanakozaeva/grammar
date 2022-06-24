package info.mermakov.dev.itmo.fg.hw5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        final Map<Character, List<String>> grammar = new HashMap<>();
        final List<String> s = new ArrayList<>();
        s.add("cS");
        s.add("cA");
        s.add("c");
        grammar.put('S', s);

        final List<String> a = new ArrayList<>();
        a.add("aDbC");
        a.add("aDb");
        a.add("abC");
        a.add("ab");
        grammar.put('A', a);

        final List<String> d = new ArrayList<>();
        d.add("S");
        grammar.put('D', d);

        final List<String> c = new ArrayList<>();
        c.add("dC");
        c.add("d");
        grammar.put('C', c);

        final Map<Character, Integer> map = new HashMap<>();
        map.put('S', 0);
        map.put('A', 1);
        map.put('D', 2);
        map.put('C', 3);
        map.put('c', 4);
        map.put('a', 5);
        map.put('b', 6);
        map.put('d', 7);
        map.put('#', 8);

        final Integer[][] matrix = new Integer[][]{
            {null, null, null, null, null, null, 1, null, 1},
            {null, null, null, null, null, null, 1, null, 1},
            {null, null, null, null, null, null, 0, null, 1},
            {null, null, null, null, null, null, 1, null, 1},
            {0, 0, null, null, -1, -1, 1, null, 1},
            {-1, null, 0, null, -1, null, 0, null, 1},
            {null, null, null, 0, null, null, 1, -1, 1},
            {null, null, null, 0, null, null, 1, -1, 1},
            {-1, -1, -1, -1, -1, -1, -1, -1, null}
        };

        final Checker checker = new Checker(grammar, map, matrix);

        final Scanner in = new Scanner(System.in);
        final String line = in.nextLine();
        System.out.println(checker.isDerivable(line));
    }
}
