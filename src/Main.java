import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String result = "";

        System.out.println("Введите значения, с которыми нужно произвести арифмитические действия действия, например: 4 + 6, или I + X:");
        String[] data = reader.readLine().split(" ");

        if(data.length > 3)
            throw new Exception("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        if(data.length < 3)
            throw new Exception("Cтрока не является математической операцией");

        char operation = data[1].charAt(0);
        String typeNumbers = CheckingTypeNumbers(data);
        if(typeNumbers.equals("Arabic")){
            int num1 = Integer.parseInt(data[0]);
            int num2 = Integer.parseInt(data[2]);
            result = String.valueOf(Calculate(num1, num2, operation));
        }
        if(typeNumbers.equals("Roman")){
            int num1ToArabic = RomanToArabic(data[0].toUpperCase());
            int num2ToArabic = RomanToArabic(data[2].toUpperCase());
            int arabicResult = Calculate(num1ToArabic, num2ToArabic, operation);
            result = ArabicToRoman(arabicResult);
        }
        System.out.println(result);
    }

    public static String CheckingTypeNumbers(String[] mas){
        String typeOperation = "";
        boolean errorNotation = true;
        try {
            int num1 = Integer.parseInt(mas[0]);
            char operation = mas[1].charAt(0);
            int num2 = Integer.parseInt(mas[2]);
            typeOperation = "Arabic";
        }
        catch (NumberFormatException ex){
            String num1 = mas[0];
            char operation = mas[1].charAt(0);
            String num2 = mas[2];

            try{
                try{
                    int expNumber1 = Integer.parseInt(mas[0]);
                }
                catch (NumberFormatException ex2){
                    int expNumber2 = Integer.parseInt(mas[2]);
                }
                errorNotation = false;
            }
            catch (NumberFormatException ex3){
                typeOperation = "Roman";
            }
        }

        if(!errorNotation)
            throw new NumberFormatException("Используются одновременно разные системы счисления");

        return typeOperation;

    }
    public static String ArabicToRoman(int num) throws Exception {
        if(num < 0){
            throw new Exception("в римской системе нет отрицательных чисел");
        }

        String[] keys = new String[] {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        int[] vals = new int[] {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

        StringBuilder ret = new StringBuilder();
        int ind = 0;
        while (ind < keys.length){
            while (num >= vals[ind]){
                double d = num / vals[ind];
                num = num % vals[ind];
                for (int i = 0; i < d; i++){
                    ret.append(keys[ind]);
                }
            }
            ind++;
        }

        return ret.toString();
    }

    private static int RomanToArabic (String roman) {
        final char[] masRoman = new char[] {'O', 'I', 'V', 'X', 'L', 'C', 'D', 'M'};
        //final String[] masRoman = new String[] {"0", "I", "V", "L", "C", "M", "MX"};
        final int[] masArabic = new int[] {0, 1, 5, 10, 50, 100, 500, 1000};
        int num = 0;
        for (char c : roman.toCharArray()) {
            for (int i = 0; i < masRoman.length; i++) {
                if (c == masRoman[i]) {
                    num += masArabic[i];
                }
            }
        }
        return num;
    }

    public static int Calculate(int num1, int num2, char oper){
        int result;

        switch (oper) {
            case '+' -> result = Operation.SUM.action(num1, num2);
            case '-' -> result = Operation.SUBTRACT.action(num1, num2);
            case '/' -> result = Operation.QUOTIENT.action(num1, num2);
            case '*' -> result = Operation.MULTIPLY.action(num1, num2);
            default -> {
                throw new IllegalArgumentException("Не верный знак операции");
            }
        }
        return result;
    }

}