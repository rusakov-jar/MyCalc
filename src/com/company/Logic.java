package com.company;

/*
 * класс Logic: просчёт  выражений (логика калькулятора)
 */
public class Logic {

    /*
     *Обрабртка исходной строки
     */

    public static String parc(String StartExpression) {
        String expressionInput;
        int expStart;
        int expEnd;
        StartExpression = "$" + StartExpression + "$"; //символы начала/конца строки
        // поиск выражений со знаками *,/
        while (StartExpression.contains("*") || StartExpression.contains("/")) {
            expStart = 0;
            expressionInput = "";
            // посимвольно собираем строку для первой части кода
            while (StartExpression.charAt(expStart) != '/'
                    && StartExpression.charAt(expStart) != '*') {
                expStart++;
            }
            expEnd = expStart;
            expressionInput = expressionInput + StartExpression.charAt(expStart);
            if (StartExpression.charAt(expEnd + 1) == '-') //этот if чтобы считать второй операнд, если он <0
            {
                expressionInput += '-';
                expEnd++;
            }
            //поиск правого конца выражения для первой части кода
            while (StartExpression.charAt(expEnd + 1) != '-'
                    && StartExpression.charAt(expEnd + 1) != '+' &&
                    StartExpression.charAt(expEnd + 1) != '/' &&
                    StartExpression.charAt(expEnd + 1) != '*' &&
                    StartExpression.charAt(expEnd + 1) != '$') {
                expressionInput = expressionInput + StartExpression.charAt(expEnd + 1);
                expEnd++;
            }
            //поиск левого конца выражения для первой части кода
            while (StartExpression.charAt(expStart - 1) != '+' &&
                    StartExpression.charAt(expStart - 1) != '-' &&
                    StartExpression.charAt(expStart - 1) != '$') {
                expressionInput = StartExpression.charAt(expStart - 1) + expressionInput;
                expStart--;
            }
            // if operand 1 for first part of code <0
            if (StartExpression.charAt(expStart - 1) == '-' && (StartExpression.charAt(expStart - 2) == '-' ||
                    StartExpression.charAt(expStart - 2) == '$' ||
                    StartExpression.charAt(expStart - 2) == '+')) {
                expressionInput = StartExpression.charAt(expStart - 1) + expressionInput;
                expStart--;
            }

// замена выражения в исходной строке на результат вычислений
            StartExpression = StartExpression.replaceFirst
                    ("\\d+\\.?(\\d+)?[/*]-?\\d+\\.?(\\d+)?", tested(expressionInput));
            System.out.println(StartExpression);           // промежуточный результат (доп вывод)
        }

        while (StartExpression.contains("+") || StartExpression.contains("-")
                //Это условие(строка ниже) исключает зацикливание при ответе <0
                && !StartExpression.matches("\\$-\\d+\\.?(\\d+)?\\$")) {
            //поиск выражений со знаками +,-
            expStart = 2;// чтобы первый "-" (если с него начинается строка) не ломал систему
            while (StartExpression.charAt(expStart) != '-' &&
                    StartExpression.charAt(expStart) != '+') {
                expStart++;
            }
            expressionInput = "" + StartExpression.charAt(expStart);
            expEnd = expStart;
            // проверка знака перед вторым числом
            if (StartExpression.charAt(expEnd + 1) == '-') {
                expEnd++;
                expressionInput += '-';
            }
// поиск конца выражения
            while (StartExpression.charAt(expEnd + 1) != '+' &&
                    StartExpression.charAt(expEnd + 1) != '-' &&
                    StartExpression.charAt(expEnd + 1) != '$') {
                expEnd++;
                expressionInput += StartExpression.charAt(expEnd);
            }
            //поиск начала выражения
            while (StartExpression.charAt(expStart - 1) != '$') {
                expStart--;
                expressionInput = StartExpression.charAt(expStart) + expressionInput;
            }


            StartExpression = StartExpression.replaceFirst
                    ("-?\\d+\\.?(\\d+)?[-\\+]-?\\d+\\.?(\\d+)?", tested(expressionInput));
            System.out.println(StartExpression);// промежуточный результат
        }
        // итог
        StartExpression = StartExpression.replace(".0$", "");
        StartExpression = StartExpression.replaceAll("\\$", "");//удаление доп символов (для красоты)
        System.out.println(StartExpression);
        return (StartExpression);
    }

    /*
     * Просчёт выражений с двумя операндами и одним знаком
     */
    private static String tested(String inputString) // название метода логического смысла не несёт
    {
        String expression = inputString;
        expression = expression.replaceAll(" ", "");// корректировка строки
        String resultString;
        char operator;

        // обработка неправильных данных (если на входе просто число (2, -23 ...)
        if (expression.matches("-?\\d+\\.?(\\d+)?")) {
            resultString = expression;
        } else {

            //выделение операндов
            String[] tester = expression.split("(?<=\\d)[-*/\\+]");
            double result = 0;
            double operand1 = Double.parseDouble(tester[0]);
            double operand2 = Double.parseDouble(tester[1]);


            //проверка знака между операндами и вычасление значения выражения
            operator = expression.charAt(tester[0].length());
            switch (operator) {
                case '+':
                    result = operand1 + operand2;
                    break;
                case '-':
                    result = operand1 - operand2;
                    break;
                case '*':
                    result = operand1 * operand2;
                    break;
                case '/':
                    result = operand1 / operand2;
                    break;
                default:
                    System.out.println("Неподдерживаемая операция");//а в моём коде default вообще нужен, или
                    // и без него заработало бы?
            }

            resultString = "" + result;
        }
        return resultString; //Возврат значения
    }
}