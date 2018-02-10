package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 *  Интерфейс калькулятора
 */


/*
 * Начало класса GUI
 */
// окно и список элементов
public class GUI extends JFrame {
    private JButton button1 = new JButton("1");
    private JButton button2 = new JButton("2");
    private JButton button3 = new JButton("3");
    private JButton button4 = new JButton("4");
    private JButton button5 = new JButton("5");
    private JButton button6 = new JButton("6");
    private JButton button7 = new JButton("7");
    private JButton button8 = new JButton("8");
    private JButton button9 = new JButton("9");
    private JButton button0 = new JButton("0");
    private JButton buttonPlus = new JButton("+");
    private JButton buttonMinus = new JButton("-");
    private JButton buttonPoint = new JButton(".");
    private JButton buttonIncreace = new JButton("*");
    private JButton buttonDevide = new JButton("/");
    private JButton buttonEQW = new JButton("=");
    private JTextField input = new JTextField("", 10);
    private JLabel label = new JLabel("Input: ");
    private JButton buttonClear = new JButton("C");
    private JButton buttonBack = new JButton("Back");

    //создание окна
    GUI() {
        super("Ярс калькулятор");
        // параметры окна (кординаты верхнего правого угла+ ширина и высота окна)
        this.setBounds(100, 100, 300, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        //параметры окна(строки,столбцы,отступ вбок, отступ вверх)
        container.setLayout(new GridLayout(5, 4, 2, 1));

        container.add(label);
        container.add(input);
        // Анонимный класс для кнопки сброса текста в поле ввода
        buttonClear.addActionListener(failed -> {
                    input.setText("0");
                }
        );
        //Анонимный класс для кнопки "стереть последний символ в строке"
        buttonBack.addActionListener(failed -> {
                    String teller = input.getText();
                    input.setText(teller.substring(0, teller.length() - 1));
                }
        );
        container.add(buttonClear);
        container.add(buttonBack);
        button1.addActionListener(new ButtonSymbolChecker());
        button2.addActionListener(new ButtonSymbolChecker());
        button3.addActionListener(new ButtonSymbolChecker());
        button4.addActionListener(new ButtonSymbolChecker());
        button5.addActionListener(new ButtonSymbolChecker());
        button6.addActionListener(new ButtonSymbolChecker());
        button7.addActionListener(new ButtonSymbolChecker());
        button8.addActionListener(new ButtonSymbolChecker());
        button9.addActionListener(new ButtonSymbolChecker());
        button0.addActionListener(new ButtonSymbolChecker());
        buttonPlus.addActionListener(new ButtonSymbolChecker());
        buttonMinus.addActionListener(new ButtonSymbolChecker());
        buttonPoint.addActionListener(new ButtonSymbolChecker());
        buttonIncreace.addActionListener(new ButtonSymbolChecker());
        buttonDevide.addActionListener(new ButtonSymbolChecker());
        // Анонимный класс-обработчик для кнопки "равно"
        buttonEQW.addActionListener(failed -> {
                    String teller = input.getText();
                    Logic writer = new Logic();
                    input.setText(writer.logic(teller));
                }
        );
        container.add(button1);
        container.add(button2);
        container.add(button3);
        container.add(buttonPlus);
        container.add(button4);
        container.add(button5);
        container.add(button6);
        container.add(buttonMinus);
        container.add(button7);
        container.add(button8);
        container.add(button9);
        container.add(buttonIncreace);
        container.add(buttonPoint);
        container.add(button0);
        container.add(buttonEQW);
        container.add(buttonDevide);
    }

    ////////////////////////////////////   КОНЕЦ КЛАССА GUI    //////////////////////////////////////////////////////////
    /*
     * Реакция на кнопки (блок)
     */

    //Нажал на кнопку - в форму ввода добавилось Имя нажатой кнопки
    class ButtonSymbolChecker implements ActionListener {

        public void actionPerformed(ActionEvent failed) {
            String teller = input.getText() + ((JButton) failed.getSource()).getText();
            input.setText(teller);
        }
    }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////