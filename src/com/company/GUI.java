package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 *   Интерфейс калькулятора
 */


/*
 * Начало класса GUI
 */
// окно и список элементов
public class GUI extends JFrame {
    private JButton button = new JButton("+1");
    private JButton button2 = new JButton("=");
    private JTextField input = new JTextField("", 10);
    private JLabel label = new JLabel("Input: ");

    //создание окна
    GUI() {
        super("Ярс калькулятор");
        // параметры окна (кординаты верхнего правого угла+ ширина и высота окна)
        this.setBounds(100, 100, 300, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//условие выхода

        Container container = this.getContentPane();
        //параметры окна(строки,столбцы,отступ вбок, отступ вверх)
        container.setLayout(new GridLayout(5, 3, 2, 1));
        // добавление элементов в окно
        container.add(label);
        container.add(input);
        button.addActionListener(new Checker());
        button2.addActionListener(new CheckerForButtonEQW());
        container.add(button);
        container.add(button2);
    }

    ////////////////////////////////////   КОНЕЦ КЛАССА GUI    //////////////////////////////////////////////////////////
    /*
     * Реакция на кнопки (блок)
     */
    class Checker implements ActionListener {//не знаю, что такое

        public void actionPerformed(ActionEvent failed) {
            String teller = input.getText() + "+1"; //Нажал на кнопку - к строке добавилось "+1"
            input.setText(teller);
        }
    }

    // Нажал на кнопку - посчитал выражение в input и вывел внутри формы ввода
    class CheckerForButtonEQW implements ActionListener {
        public void actionPerformed(ActionEvent failed) {
            String teller = input.getText();
            Logic writer = new Logic();
            input.setText(writer.logic(teller));
        }
    }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////