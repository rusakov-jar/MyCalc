package com.company;

/*
 * класс Logic: просчёт  выражений (логика калькулятора)
 */
public class Logic {

    /*
     *Обрабртка исходной строки
     */

    //Обработка начальной строки(первый уровень)
    public static String parse(String baseExpression) {
        int expStart = 0; // индекс начала блока ( ... )
        int expEnd; // индекс конца блока ( ... )
        int counter; // счётчик скобок
        String result = "Error";
        String value = baseExpression;
        String nextString = ""; // переменная  для отправки по рекурсии
        // поиск блока ( ."выражение". ) (будет записываться в переменную nextString)
        if (baseExpression.contains("(") || baseExpression.contains(")")) {
            while (baseExpression.charAt(expStart) != '(') {
                expStart++;
            }
            counter = 1;
            expEnd = expStart;
            while (counter > 0 && expEnd < baseExpression.length() - 1) {
                expEnd++;
                nextString += value.charAt(expEnd);
                if (baseExpression.charAt(expEnd) == ')') {
                    counter--;
                }
                if (baseExpression.charAt(expEnd) == '(') {
                    counter++;
                }
            }
            if (counter != 0) {
                return ("Неправильные входные данные: несовпадает колличество открывающих и закрывающих скобок");
            }
            if (counter == 0) {
                nextString = nextString.substring(0, nextString.length() - 1);
                result = baseExpression.substring(0, expStart) +
                        parse(nextString) + baseExpression.substring(expEnd + 1);
                result = parse(result);
            }
        } else {
            result = parc(baseExpression);
        }
        return result;
    }

    private static String parc(String StartExpression) {
        String expressionInput;
        int expStart;
        int expEnd;
        double x; // аргумент для "Х в степени functionArgument"
        double functionArgument;
        StartExpression = "$" + StartExpression + "$"; //символы начала/конца строки
        // реализация функций
        // log (x)
        while (StartExpression.contains("log")) {
            expStart = 0;
            expressionInput = "";
            while (StartExpression.charAt(expStart) != 'l') { //ищем спец. символ функции
                expStart++;
            }
            expEnd = expStart + 3; // ищем аргумент
            // посимвольно собираем аргумент (while ниже)
            while (StartExpression.charAt(expEnd) != '+' && StartExpression.charAt(expEnd) != '-' &&
                    StartExpression.charAt(expEnd) != '*' && StartExpression.charAt(expEnd) != '/' &&
                    StartExpression.charAt(expEnd) != '$') {
                expressionInput += StartExpression.charAt(expEnd);
                expEnd++;
            }
            // заменяем в исходной строке функцию на результат
            functionArgument = Double.parseDouble(expressionInput);
            StartExpression = StartExpression.substring(0, expStart)
                    + Math.log(functionArgument) + StartExpression.substring(expEnd);
        }
        //sqrt(x) - реализованная в этом while функция
        while (StartExpression.contains("sqrt")) {// пок есть спец. символ функции
            expStart = 0;
            expressionInput = "";
            while (StartExpression.charAt(expStart) != 's') {// ищем начало функции (номер первого символа)
                expStart++;
            }
            // ищем аргумент
            expEnd = expStart + 4;
            while (StartExpression.charAt(expEnd) != '+' && StartExpression.charAt(expEnd) != '-' &&
                    StartExpression.charAt(expEnd) != '*' && StartExpression.charAt(expEnd) != '/' &&
                    StartExpression.charAt(expEnd) != '$') {
                expressionInput += StartExpression.charAt(expEnd);
                expEnd++;
            }
            // заменяем в исходной строке функцию на результат
            functionArgument = Double.parseDouble(expressionInput);
            StartExpression = StartExpression.substring(0, expStart)
                    + Math.sqrt(functionArgument) + StartExpression.substring(expEnd);
        }
        // x^functionArgument - реализованная в этом while функция (Х в степени functionArgument)
        while (StartExpression.contains("^")) {
            expStart = 0;
            expressionInput = "";
            while (StartExpression.charAt(expStart) != '^') { // ищем спец символ (номер позиции)
                expStart++;
            }
            expEnd = expStart + 1;
            expStart--;
            // ищем в какую степень возводить Х (переменная functionArgument)
            while (StartExpression.charAt(expEnd) != '+' && StartExpression.charAt(expEnd) != '-' &&
                    StartExpression.charAt(expEnd) != '*' && StartExpression.charAt(expEnd) != '/' &&
                    StartExpression.charAt(expEnd) != '$') {
                expressionInput += StartExpression.charAt(expEnd);
                expEnd++;
            }
            functionArgument = Double.parseDouble(expressionInput);
            expressionInput = "";
            // ищем Х
            while (StartExpression.charAt(expStart) != '+' && StartExpression.charAt(expStart) != '-' &&
                    StartExpression.charAt(expStart) != '*' && StartExpression.charAt(expStart) != '/' &&
                    StartExpression.charAt(expStart) != '$') {
                expressionInput = StartExpression.charAt(expStart) + expressionInput;
                expStart--;
            }
            // заменяем в исходной строке функцию на результат
            x = Double.parseDouble(expressionInput);
            StartExpression = StartExpression.substring(0, expStart)
                    + Math.pow(x, functionArgument) + StartExpression.substring(expEnd);
        }

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
        }
        // итог
        StartExpression = StartExpression.replace(".0$", "");
        StartExpression = StartExpression.replaceAll("\\$", "");//удаление доп символов (для красоты)
        return (StartExpression);
    }

    /*
     * Просчёт выражений с двумя операндами и одним знаком
     */
    private static String tested(String inputString) // название метода логического смысла не несёт
    {
        String expression = inputString;
        expression = expression.replaceAll(" ", "");// корректировка строки
        expression = expression.replaceAll("\\.", ".");
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


            //проверка знака между операндами и вычисление значения выражения
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