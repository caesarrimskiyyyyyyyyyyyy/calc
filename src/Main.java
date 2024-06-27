import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static String left, right;
    static int leftIntOperand, rightIntOperand;
    static char operation;
    static String result;

    public static void main(String[] args) throws Exception {
        System.out.print("введите выражение: ");
        String userInput = sc.nextLine().replaceAll("\\s+", "");
        System.out.println("итог выражения: " + calc(userInput));
    }

    public static String calc(String input) throws Exception {
        splitOfExpression(input);

        Converter converter = new Converter();
        // проверка пары: рим + рим / араб + араб
        if (converter.isRoman(left) == converter.isRoman(right)) {
            boolean isRoman = converter.isRoman(left);
            int resultRomanInt;
            // римское?
            if (isRoman) {
                leftIntOperand = converter.romanMap.get(left);
                rightIntOperand = converter.romanMap.get(right);
                resultRomanInt = calculationOfExpression(leftIntOperand, operation, rightIntOperand);
                if (resultRomanInt >= 1) {
                    result = converter.toRoman(resultRomanInt);
                } else throw new Exception("римское число должно быть > 0");
                // арабское?
            } else {
                leftIntOperand = Integer.parseInt(left);
                rightIntOperand = Integer.parseInt(right);
                if (leftIntOperand <= 10 && rightIntOperand <= 10 &&
                        leftIntOperand >= 1 && rightIntOperand >= 1) {
                    result = String.valueOf(calculationOfExpression(leftIntOperand, operation, rightIntOperand));
                } else throw new Exception();
            }
        } else throw new Exception();

        return result;
    }

    public static char detectOperation(String userInput) {
        if (userInput.contains("+")) return '+';
        else if (userInput.contains("-")) return '-';
        else if (userInput.contains("*")) return '*';
        else if (userInput.contains("/")) return '/';
        else return '0';
    }

    public static void splitOfExpression(String userInput) throws Exception {
        String[] split = userInput.split("[+\\-*/]");
        if (split.length > 2) throw new Exception("калькулятор принимает только два операнда и один оператор");

        if (detectOperation(userInput) != '+' && detectOperation(userInput) != '-'
                && detectOperation(userInput) != '*' && detectOperation(userInput) != '/') {
            throw new Exception("не является допустимой математической операцией");
        } else {
            left = split[0];
            right = split[1];
            operation = detectOperation(userInput);
        }
    }

    public static int calculationOfExpression(int left, char operation, int right) {
        switch (operation) {
            case '+': return left + right;
            case '-': return left - right;
            case '*': return left * right;
            case '/': if (left == 0 || right == 0) {
                throw new IllegalArgumentException("на ноль не делим :)");
            } else return left / right;
            default: return 0;
        }
    }
}

class Converter {
    TreeMap<String, Integer> romanMap = new TreeMap<>();
    TreeMap<Integer, String> arabicMap = new TreeMap<>();

    Converter() {
        romanMap.put("I", 1);
        romanMap.put("II", 2);
        romanMap.put("III", 3);
        romanMap.put("IV", 4);
        romanMap.put("V", 5);
        romanMap.put("VI", 6);
        romanMap.put("VII", 7);
        romanMap.put("VIII", 8);
        romanMap.put("IX", 9);
        romanMap.put("X", 10);

        arabicMap.put(100, "C");
        arabicMap.put(90, "XC");
        arabicMap.put(50, "L");
        arabicMap.put(40, "XL");
        arabicMap.put(10, "X");
        arabicMap.put(9, "IX");
        arabicMap.put(5, "V");
        arabicMap.put(4, "IV");
        arabicMap.put(1, "I");
    }

    public boolean isRoman(String number) {
        return romanMap.containsKey(number);
    }

    public String toRoman(int key) {
        StringBuilder romanResult = new StringBuilder();
        int arabianKey;
        do {
            arabianKey = arabicMap.floorKey(key);
            romanResult.append(arabicMap.get(arabianKey));
            key -= arabianKey;
        } while (key != 0);
        return romanResult.toString();
    }
}