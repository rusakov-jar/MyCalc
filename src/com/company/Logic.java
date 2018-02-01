package com.company;

public class Logic {
    // просчёт выражений с двумя операндами и одним прератором
    public static String tested(String intro) // название логического смысла не несёт
    {
        String expression = intro;
        expression = expression.replaceAll(" ", "");// корректировка строки
        String resultString;
        char operator;
        // обработка неправильных данных (если на входе просто число (2, -23 ...) (этот if)
        if (expression.matches("-?\\d+\\.?(\\d+)?")) {
            resultString = expression;
        } else {
            String[] tester = expression.split("(?<=\\d)[-*/\\+]");
            double result = 0;
            double operand1 = Double.parseDouble(tester[0]);
            double operand2 = Double.parseDouble(tester[1]);
            // получение промежуточного результата
            operator = expression.charAt(tester[0].length());
            switch (operator) // а switch комментировать надо? (по правилам оформления кода)
            {
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
        return resultString;
    }

    //обработка исходной строки
    public static String logic(String StartExpression) {
        String expressionInput;
        int expStart;
        int expEnd;
        StartExpression = "$" + StartExpression + "$"; //символы начала/конца строки
        while (StartExpression.contains("*") || StartExpression.contains("/")) {// поиск выражений со знаками *,/
            expStart = 0;
            expressionInput = "";
            while (StartExpression.charAt(expStart) != '/' // посимвольно собираем строку для tested
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
            //поиск правого конца выражения для tested
            while (StartExpression.charAt(expEnd + 1) != '-'
                    && StartExpression.charAt(expEnd + 1) != '+' &&
                    StartExpression.charAt(expEnd + 1) != '/' &&
                    StartExpression.charAt(expEnd + 1) != '*' &&
                    StartExpression.charAt(expEnd + 1) != '$') {
                expressionInput = expressionInput + StartExpression.charAt(expEnd + 1);
                expEnd++;
            }
            //поиск левого конца выражения для tested
            while (StartExpression.charAt(expStart - 1) != '+' &&
                    StartExpression.charAt(expStart - 1) != '-' &&
                    StartExpression.charAt(expStart - 1) != '$') {
                expressionInput = StartExpression.charAt(expStart - 1) + expressionInput;
                expStart--;
            }
            // if operand 1 for "tested" <0
            if (StartExpression.charAt(expStart) == '-' && (StartExpression.charAt(expStart - 1) == '-' ||
                    StartExpression.charAt(expStart - 1) == '$' ||
                    StartExpression.charAt(expStart - 1) == '+')) {
                expStart--;
                expressionInput = StartExpression.charAt(expStart) + expressionInput;
            }
            StartExpression = StartExpression.replaceFirst // замена выражения для tested в исходной строке на результат вычислений
                    ("\\d+\\.?(\\d+)?[/*]\\d+\\.?(\\d+)?", tested(expressionInput));
            System.out.println(StartExpression);// промежуточный результат (доп вывод)
        }

        while (StartExpression.contains("+") || StartExpression.contains("-")
                && !StartExpression.matches("\\$-\\d+\\.?(\\d+)?\\$")) //Это условие(эта строка) исключает зацикливание при ответе <0
        { //поиск выражений со знаками +,-
            expStart = 2;// чтобы первый "-" (если с него начинается строка) не ломал систему
            while (StartExpression.charAt(expStart) != '-' &&
                    StartExpression.charAt(expStart) != '+') {
                expStart++;
            }
            expressionInput = "" + StartExpression.charAt(expStart);
            expEnd = expStart;
            if (StartExpression.charAt(expEnd + 1) == '-') { // проверка знака перед вторым числом
                expEnd++;
                expressionInput += '-';
            }
            while (StartExpression.charAt(expEnd + 1) != '+' && // поиск конца выражения
                    StartExpression.charAt(expEnd + 1) != '-' &&
                    StartExpression.charAt(expEnd + 1) != '$') {
                expEnd++;
                expressionInput += StartExpression.charAt(expEnd);
            }
            while (StartExpression.charAt(expStart - 1) != '$') { //поиск начала выражения
                expStart--;
                expressionInput = StartExpression.charAt(expStart) + expressionInput;
            }
            StartExpression = StartExpression.replaceFirst
                    ("-?\\d+\\.?(\\d+)?[-\\+]-?\\d+\\.?(\\d+)?", tested(expressionInput));
            System.out.println(StartExpression);// промежуточный результат
        }
        StartExpression = StartExpression.replaceAll("\\$", "");//удаление доп символов (для красоты)
        System.out.println(StartExpression); // итог
        // для проверки коммита
        return (StartExpression);
    }
}