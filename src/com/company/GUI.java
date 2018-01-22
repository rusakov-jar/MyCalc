package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//Создание элементов окна
public class GUI extends JFrame {
    private JButton button = new JButton("Tap it");
    private JButton button2 = new JButton("Tap it");
    private JTextField input = new JTextField("", 10);
    private JLabel label = new JLabel("Input: ");
    private JRadioButton radio1 = new JRadioButton("radio1");
    private JRadioButton radio2 = new JRadioButton("radio2");
    private JRadioButton radio3 = new JRadioButton("radio3");
    private JCheckBox checkIt = new JCheckBox("check", true);

    private GUI() {//создание окна
        super("Window name here");
        this.setBounds(100, 100, 300, 250); // параметры окна (кординаты верхнего правого угла+ ширина и высота окна)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//условие выхода
        //добавление элементов в окно
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(5, 3, 2, 1));//параметры окна(строки,столбцы,отступ вбок, отступ вверх)
        container.add(label);
        container.add(input);
        ButtonGroup group = new ButtonGroup();//группа для выбора одного из элементов
        group.add(radio1);
        group.add(radio2);
        group.add(radio3);
        container.add(radio1);
        radio1.setSelected(true);
        container.add(radio2);
        container.add(radio3);
        container.add(checkIt);
        button.addActionListener(new Checker());
        button2.addActionListener(new Checker());
        container.add(button);
        container.add(button2);
    }

    public static void main(String[] args) {//вызов окна (потом удалю)
        GUI justATest = new GUI();
        justATest.setVisible(true);
    }

    class Checker implements ActionListener {//не знаю, что такое

        public void actionPerformed(ActionEvent failed) {//не знаюю, что такое
            String teller = "button pressed \n" +//сообщение, которое выведет окно (что нажал, чего выбрал)
                    "You wrote " + input.getText() + "\n" +
                    (radio1.isSelected() ? "Radio 1" : "") + (radio2.isSelected() ? "Radio 2" : "")//что-то вроде if для текста
                    + (radio3.isSelected() ? "Radio 3" : "") +
                    " is selcted." + "\n" + "checkIt " + (checkIt.isSelected() ? "is done" : "failed");
            JOptionPane.showMessageDialog(null, teller, "output", JOptionPane.PLAIN_MESSAGE);//окно,
            // появляющееся при нажатии на кнопку
        }
    }
}


