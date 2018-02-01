package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

///////////////////////////////   Создание элементов окна   ///////////////////////////////////////////////////////
public class GUI extends JFrame {
    private JButton button = new JButton("+1");
    private JButton button2 = new JButton("=");
    private JTextField input = new JTextField("", 10);
    private JLabel label = new JLabel("Input: ");

    //------------------------------  создание окна, добавление элементов   -------------------------------------------
    GUI() {//создание окна
        super("Ярс калькулятор");// Вячеслав, если я ещё не спрашивал, объясни, пожалуйста, что это за команда "super ()".
        this.setBounds(100, 100, 300, 250); // параметры окна (кординаты верхнего правого угла+ ширина и высота окна)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//условие выхода
//----------------------------------------------------------------------------------------------------------------
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(5, 3, 2, 1));//параметры окна(строки,столбцы,отступ вбок, отступ вверх)
//----------------------------------------------------------------------------------------------------------------
        container.add(label);  // добавление элементов в окно
        container.add(input);
        button.addActionListener(new Checker());
        button2.addActionListener(new CheckerForButtonEQW());
        container.add(button);
        container.add(button2);
    }

    ////////////////////////////////////   КОНЕЦ КЛАССА GUI    //////////////////////////////////////////////////////////
////////////////////////////////////   реакция на кнопки   //////////////////////////////////////////////////////////
    class Checker implements ActionListener {//не знаю, что такое

        //-------------------------------------------------------------------------------------------------------------------
        public void actionPerformed(ActionEvent failed) {//не знаюю, что такое
            String teller = input.getText() + "+1"; //Нажал на кнопку - к строке добавилось "+1"
            input.setText(teller);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------
    class CheckerForButtonEQW implements ActionListener {//не знаю, что такое

        public void actionPerformed(ActionEvent failed) {//не знаюю, что такое
            String teller = input.getText(); // Нажал на кнопку - посчитал выражение в input
            Logic writer = new Logic();
            input.setText(writer.logic(teller));
        }
    }
}
//-------------------------------------------------------------------------------------------------------------------
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////