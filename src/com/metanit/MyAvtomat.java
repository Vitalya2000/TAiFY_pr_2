package com.metanit;


import java.util.*;


public class MyAvtomat {

    private static final int START = 0;
    private static final int DIGIT1 = 1;
    private static final int PLUS = 2;
    private static final int DIGIT2 = 3;
    private final Set<Integer> endStates;
    private final Map<Integer, Map<Character, Integer>> transitions = new HashMap<>();

    public MyAvtomat(int startState, Set<Integer> endStates) {
        this.endStates = endStates;
    }


    public void add(Integer from, Character ch, Integer to) {
        Map<Character, Integer> fromMap = transitions.computeIfAbsent(from, k -> new HashMap<>());
        fromMap.put(ch, to);
    }

    public void add(Integer from, List<Character> chars, Integer to) {
        for (Character ch : chars) {
            add(from, ch.charValue(), to);
        }
    }

    public Integer getNext(Integer state, Character ch) {
        Map<Character, Integer> stateTransitions = transitions.get(state);
        return stateTransitions.get(ch);
    }


    public int match(String str, int from) {
        int state = START;
        int i = from;
        while (i < str.length()) {
            char ch = str.charAt(i);
            Integer nextState = getNext(state, ch);
            if (nextState == null)
                break;
            state = nextState;
            i++;
        }
        if (endStates.contains(state)) {
            return i;
        } else {
            return -1;
        }
    }



    public void findAll(String str) {
        int i = 0;
        while (i < str.length()) {
            int match = match(str, i);
            if (match < 0) {
                i++;
            } else {
                System.out.println(str.substring(i, match));
                i = match;
            }
        }
    }


        public static void main(String[] args) {
        List<Character> digits = Arrays.asList('0', '1', '2', '3','4','5','6','7','8','9');
        Set<Integer> endStates = Collections.singleton(DIGIT2);
        MyAvtomat ma = new MyAvtomat(START, endStates);
        ma.add(START, digits, DIGIT1);
        ma.add(DIGIT1, digits, DIGIT1);
        ma.add(DIGIT1, '+', PLUS);
        ma.add(PLUS, digits, DIGIT2);
        ma.add(DIGIT2, digits, DIGIT2);


        // Поиск всех вхождений в строке:
        String str;
        System.out.println("Введите строку: ");
        Scanner in = new Scanner(System.in);
        str = in.nextLine();
        ma.findAll(str);
        in.close();
    }

}
