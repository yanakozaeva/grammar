package info.mermakov.dev.itmo.fg.hw5;

import java.util.Deque;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Checker {
    private final Map<Character, List<String>> grammar;
    private final Map<Character, Integer> map;
    private final Integer[][] matrix;

    public Checker(
        final Map<Character, List<String>> grammar,
        final Map<Character, Integer> map,
        final Integer[][] matrix
    ) {
        this.grammar = grammar;
        this.map = map;
        this.matrix = matrix;
    }

    public boolean isDerivable(final String line) {
        final var preparedLine = line + "#";
        final var inputChars = preparedLine.toCharArray();
        var position = 0;

        final Deque<Character> stack = new LinkedList<>();
        stack.addFirst('#');
        var failure = false;

        if (inputChars.length < 2) {
            return false;
        }
        while (position <= inputChars.length) {
            var currentChar = inputChars[position];
            if (!map.containsKey(currentChar)) {
                failure = true;
                break;
            }
            if (currentChar == '#' && stack.size() == 2 && stack.peekFirst() == 'S')
                break;
            var type = matrix[map.get(stack.peekFirst())][map.get(inputChars[position])];
            if (type == null) {
                failure = true;
                break;
            }
            if (type <= 0) {
                stack.addFirst(currentChar);
                position++;
            } else {
                final var stringBuilder = new StringBuilder();
                char tempChar;
                do {
                    tempChar = stack.removeFirst();
                    stringBuilder.insert(0, tempChar);
                } while (matrix[map.get(stack.peekFirst())][map.get(tempChar)] >= 0);
                final var iterator = grammar.entrySet().iterator();
                var found = false;
                char leftChar = 0;
                while (iterator.hasNext() && !found) {
                    var entry = iterator.next();
                    if (entry.getValue().contains(stringBuilder.toString())) {
                        found = true;
                        leftChar = entry.getKey();
                    }
                }
                if (!found) {
                    failure = true;
                    break;
                }
                stack.addFirst(leftChar);
            }
        }
        final var stringBuilder = new StringBuilder();
        try {
            stringBuilder.insert(0, stack.pop());
            stringBuilder.insert(0, stack.pop());
        } catch (EmptyStackException e) {
            return false;
        }
        return !failure && stringBuilder.toString().equals("#S");
    }
}
