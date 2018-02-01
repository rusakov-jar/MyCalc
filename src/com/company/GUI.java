package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.company.Logic;

//Создание элементов окна
public class GUI extends JFrame {
    private JButton button = new JButton("+1");
    private JButton button2 = new JButton("=");
    private JTextField input = new JTextField("", 10);
    private JLabel label = new JLabel("Input: ");
    private JRadioButton radio1 = new JRadioButton("radio1");
    private JRadioButton radio2 = new JRadioButton("radio2");
    private JRadioButton radio3 = new JRadioButton("radio3");
    private JCheckBox checkIt = new JCheckBox("check", true);

    GUI() {//создание окна
        super("Window name here");
        this.setBounds(100, 100, 300, 250); // параметры окна (кординаты верхнего правого угла+ ширина и высота окна)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//условие выхода
        //добавление элементов в окно
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(5, 3, 2, 1));//параметры окна(строки,столбцы,отступ вбок, отступ вверх)
        container.add(label);
        container.add(input);
        button.addActionListener(new Checker());
        button2.addActionListener(new CheckerForButtonEQW());
        container.add(button);
        container.add(button2);
    }


    class Checker implements ActionListener {//не знаю, что такое

        public void actionPerformed(ActionEvent failed) {//не знаюю, что такое
            String teller = input.getText() + "+1";
            input.setText(teller);
        }
    }

    class CheckerForButtonEQW implements ActionListener {//не знаю, что такое

        public void actionPerformed(ActionEvent failed) {//не знаюю, что такое
            String teller = input.getText();
            Logic writer = new Logic();
            input.setText(writer.logic(teller));

        }
    }
}