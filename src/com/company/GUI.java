package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.BorderLayout;
import java.io.File;
import java.util.Scanner;


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

    int charCounter = 0;
    String result = "";
    private JTextArea editor;

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
    private JTextField status;
    private JFrame journalFrame = new JFrame();
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
            String result = Logic.parse(teller);
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
            Journal();
            input.grabFocus();
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

    // ЖУРНАЛ
    public String Journal() {
        journalFrame.setTitle("Журнал");
        journalFrame.setBounds(500, 250, 500, 500);

        // Lets create a border layout to make positioning of items easy and quick.
        journalFrame.setLayout(new BorderLayout());
        journalFrame.setDefaultCloseOperation(HIDE_ON_CLOSE);

        journalFrame.setVisible(true);

        String text = ""; //переменная для вывода текста из файла
        File created = new File("Journal\\test.txt");
        try {

            Scanner reader = new Scanner(created);
            while (reader.hasNextLine()) {
                text = text + reader.nextLine() + "\n";
            }
        } catch (FileNotFoundException e) {
            text = "";
        }

        String[] text1 = text.split("\n");
        String result = "";
        editor = new JTextArea(text);

        // Add a caretListener to the editor. This is an anonymous class because it is inline and has no specific name.
        // Each time the caret is moved, it will trigger the listener and its method caretUpdate.
// It will then pass the event to the update method including the source of the event (which is our textarea control)
        editor.addCaretListener(e -> {
            JTextArea editArea = (JTextArea) e.getSource();

            // Lets start with some default values for the line and column.
            int linenum = 1;
            int columnnum = 1;

            // We create a try catch to catch any exceptions. We will simply ignore such an error for our demonstration.
            try {
                // First we find the position of the caret. This is the number of where the caret is in relation to the start of the JTextArea
                // in the upper left corner. We use this position to find offset values (eg what line we are on for the given position as well as
                // what position that line starts on.
                int caretpos = editArea.getCaretPosition();
                linenum = editArea.getLineOfOffset(caretpos);

                // We subtract the offset of where our line starts from the overall caret position.
                // So lets say that we are on line 5 and that line starts at caret position 100, if our caret position is currently 106
                // we know that we must be on column 6 of line 5.
                columnnum = caretpos - editArea.getLineStartOffset(linenum);

                // We have to add one here because line numbers start at 0 for getLineOfOffset and we want it to start at 1 for display.
                linenum += 1;
                System.out.println(text1[linenum - 1]);
                charCounter = text1[linenum - 1].length() - 1; //переменная для поиска индекса знака "="
                while (text1[linenum - 1].charAt(charCounter) != '=') {
                    charCounter--;
                }
                input.setText(text1[linenum - 1].substring(charCounter + 1));
                journalFrame.dispose();
            } catch (Exception ex) {
            }

            // Once we know the position of the line and the column, pass it to a helper function for updating the status bar.
            updateStatus(linenum, columnnum);
        });

        // Add the fields to the layout, the editor in the middle and the status at the bottom.
        journalFrame.add(editor, BorderLayout.CENTER);

        status = new JTextField();
        journalFrame.add(status, BorderLayout.SOUTH);

        // Give the status update value
        updateStatus(1, 1);
        return result;
    }

    // This helper function updates the status bar with the line number and column number.
    private void updateStatus(int linenumber, int columnnumber) {
        status.setText("Line: " + linenumber + " Column: " + columnnumber);
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