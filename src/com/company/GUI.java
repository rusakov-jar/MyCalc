package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;


/*
 *  Интерфейс калькулятора
 */


/*
 * Начало класса GUI
 */
// окно и список элементов
public class GUI extends JFrame {

    Image imgSQRTX = new ImageIcon("images\\sqr(x).png").getImage();
    Image imgAX = new ImageIcon("images\\A(x).png").getImage();
    Image imgLeftShift = new ImageIcon("images\\LeftShift.png").getImage();
    Image imgRightShift = new ImageIcon("images\\RightShift.png").getImage();
    Image imgPicture = new ImageIcon("images\\picture.png").getImage();
    Image imgLog_x = new ImageIcon("images\\log_x.png").getImage();

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
    private JButton buttonSQRT = new JButton(new ImageIcon(imgSQRTX));
    private JButton buttonLeft = new JButton("(");
    private JButton buttonRight = new JButton(")");
    private JButton buttonLG = new JButton(new ImageIcon(imgLog_x));
    private JButton buttonAVX = new JButton(new ImageIcon(imgAX));
    private JButton buttonLeftShift = new JButton(new ImageIcon(imgLeftShift));
    private JButton buttonRightShift = new JButton(new ImageIcon(imgRightShift));
    private JButton buttonJurnal = new JButton("Journal");
    private JLabel picture = new JLabel(new ImageIcon(imgPicture));
    //создание окна
    GUI() {
        super("Ярс калькулятор");
        // параметры окна (кординаты верхнего правого угла+ ширина и высота окна)
        this.setBounds(100, 75, 400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());
        JPanel top = new JPanel();
        JPanel center = new JPanel();
        JPanel ground = new JPanel();
        //параметры окна(строки,столбцы,отступ вбок, отступ вверх)
        center.setLayout(new GridLayout(5, 4, 2, 1));
        top.setLayout(new BorderLayout());
        ground.setLayout(new GridLayout(3, 2, 2, 1));
        ground.add(buttonLeftShift);
        ground.add(buttonEQW);
        ground.add(buttonRightShift);
        ground.add(buttonJurnal);
        ground.add(picture);
        ground.add(buttonBack);
        container.add(top, BorderLayout.NORTH);
        container.add(ground, BorderLayout.SOUTH);
        container.add(center, BorderLayout.CENTER);
        top.add(label, BorderLayout.WEST);
        top.add(input, BorderLayout.CENTER);
        // Анонимный класс для кнопки сброса текста в поле ввода
        buttonClear.addActionListener(failed -> {
                    input.setText("0");
            input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
                }
        );
        //Анонимный класс для кнопки "стереть символ перед курсором в строке"
        buttonBack.addActionListener(failed -> {
                    int caretIndex = input.getCaretPosition();
                    String teller = input.getText();
            if (input.getText().length() != 0) {
                input.setText(teller.substring(0, caretIndex - 1) + teller.substring(caretIndex));
                input.setCaretPosition(caretIndex - 1);
            }
                    input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
                }
        );
        buttonLeftShift.addActionListener(failed -> {
            if (input.getCaretPosition() > 0) {
                input.setCaretPosition(input.getCaretPosition() - 1);
            }
                    input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
                }
        );
        buttonRightShift.addActionListener(failed -> {
            if (input.getCaretPosition() < input.getText().length()) {
                input.setCaretPosition(input.getCaretPosition() + 1);
            }
            input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
                }
        );
        buttonLeft.addActionListener(new ButtonSymbolChecker());
        buttonRight.addActionListener(new ButtonSymbolChecker());
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
            String result = Logic.parc(teller);
            File journal = new File("Journal\\test.txt");
            try {
                PrintWriter journalWriter = new PrintWriter(
                        new BufferedWriter(new FileWriter(journal, true)));
                journalWriter.println(teller + "=" + result);
                journalWriter.flush(); //чистка буфера (комментарий для себя)
                journalWriter.close(); //закрытие потока/возвращение ресурсов
                // (тоже для себя)
            } catch (IOException ex) {
                result = "";
            }
            input.setText(result);
            input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
                }
        );
        buttonJurnal.addActionListener(e -> {
            Journal JournalDemoApp = new Journal();
            JournalDemoApp.setVisible(true);
        });
        // добавление кнопок на панель в середине
        center.add(button1);
        center.add(button2);
        center.add(button3);
        center.add(buttonPlus);
        center.add(button4);
        center.add(button5);
        center.add(button6);
        center.add(buttonMinus);
        center.add(button7);
        center.add(button8);
        center.add(button9);
        center.add(buttonIncreace);
        center.add(buttonPoint);
        center.add(button0);
        center.add(buttonAVX);
        center.add(buttonDevide);
        center.add(buttonLeft);
        center.add(buttonRight);
        center.add(buttonSQRT);
        center.add(buttonLG);
    }

    ////////////////////////////////////   КОНЕЦ КЛАССА GUI    //////////////////////////////////////////////////////////
    /*
     * Реакция на кнопки (блок)
     */

    //Нажал на кнопку - в форму ввода добавилось Имя нажатой кнопки (этот класс)
    class ButtonSymbolChecker implements ActionListener {

        public void actionPerformed(ActionEvent failed) {
            StringBuffer addText = new StringBuffer(input.getText());
            int stringIndex = input.getCaretPosition();
            addText.insert(stringIndex, ((JButton) failed.getSource()).getText());
            input.setText(String.valueOf(addText));
            input.setCaretPosition(stringIndex + 1);
            input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
        }
    }
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////