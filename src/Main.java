import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ScannerException{
        Scanner scanner = new Scanner(System.in);
        String result = "введено неверное выражение!";
        System.out.println("Введите арифметическое выражение из двух чисел в арабской или римской системе исчисления, " +
                  "\n" + "каждое число должно быть от 1 до 10 включительною " +
                  "\n" + "Выражение вводится в виде /х1 + х2/, между операндами и операцией должны быть пробелы " +
                  "\n" + "Допустимые арифметические операции: +,-,/,*");
        String num = scanner.nextLine();
        num = num.trim();

        String [] arrayNum = num.split(" ");

        if (arrayNum.length!=3){
            throw new ScannerException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *),"+
                                       "\n" + "или не поставлены пробелы между операндами и оператором." );
        }

        if (!((arrayNum[1].equals("+") || arrayNum[1].equals("-") || arrayNum[1].equals("*") || arrayNum[1].equals("/")))){
            throw new ScannerException("Должна быть одна из этих арифметических операций: +, -, *, /");
        }

        boolean arab1;
        boolean roman1;
        boolean arab2;
        boolean roman2;

        roman1 = examRoman(arrayNum[0]);
        roman2 = examRoman(arrayNum[2]);

        if (roman1 && roman2){
            result = calcRoman(arrayNum[0], arrayNum[1], arrayNum[2]);
        }

        arab1 = examArab(arrayNum[0]);
        arab2 = examArab(arrayNum[2]);

        if (arab1 && arab2){
            int x = Integer.parseInt(arrayNum[0]);
            int y = Integer.parseInt(arrayNum[2]);
            int interResult;
            interResult = calcNum(x, arrayNum[1], y);
            result = String.valueOf(interResult);
        }

        if (roman1 && arab2 || arab1 && roman2){
            result = "Нельзя одновременно использовать разные системы исчисления";
            throw new ScannerException(result);
        }
        System.out.println(result);
    }

    static boolean examRoman(String n){
        boolean bool = false;
        for (RomanNumDemo romanComp : RomanNumDemo.values()){
            String RomanNumDem = romanComp.name();
            if(n.equals(RomanNumDem)){
                bool = true;
            }
        }
        return bool;
    }

    static boolean examArab(String n){
        boolean bool = false;
        for (int i=1; i<=10; i++){
            String strI = String.valueOf(i);
            if(strI.equals(n)){
                bool = true;
            }
        }
        return bool;
    }

    static int calcNum(int n1, String op, int n2) throws ScannerException {
        int res;
        switch (op) {
            case "+":
                res = n1+n2;
                break;
            case "-":
                res = n1-n2;
                break;
            case "*":
                res = n1*n2;
                break;
            case "/":
                res = n1/n2;
                break;
            default:
                throw new ScannerException("Ошибка!");
        }
        return res;
    }

    static String calcRoman(String s1, String s2, String s3) throws ScannerException{
        String rom = null;
        RomanNum x = RomanNum.valueOf(s1);
        RomanNum y = RomanNum.valueOf(s3);
        int intermediateRes = calcNum(x.getTranArab(), s2, y.getTranArab());
        if (intermediateRes <= 0){
            throw new ScannerException("В римской системе исчисления нет отрицательных чисел");
        }
        for (RomanNum romanComp : RomanNum.values()) {
            if (intermediateRes == romanComp.getTranArab()) {
                rom = romanComp.name();
            }
        }
        return rom;
    }
}