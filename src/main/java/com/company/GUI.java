package com.company;

import javax.sound.sampled.*;


import java.io.File;
import java.io.IOException;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.awt.BorderLayout;
import java.net.URL;
import java.util.Scanner;


/*
 *  Интерфейс калькулятора
 */


/*
 * Начало класса GUI
 */
// окно и список элементов
public class GUI extends JFrame {


    // Звук
    Clip soundClip;
    URL soundFile = getClass().getResource("sound/sound.wav");
    AudioFileFormat af;
    private boolean soundIsON = true;

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
        // добавляем элементы в окно
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
        top.add(buttonSound, BorderLayout.EAST);

        // обработчики нажатия на кнопки
        buttonSound.addActionListener(e -> {
            soundIsON = !soundIsON;
            JButton b = (JButton) e.getSource();
            if (soundIsON) {
                b.setIcon(new ImageIcon(imgSoundON));
            } else {
                b.setIcon(new ImageIcon(imgSoundOFF));
            }
            if (soundIsON) play();
        });

        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
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
                    result.replaceAll("=", "");
                    input.setText(result);
                    input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
                }

            }
        });
        //Анонимный класс для кнопки "стереть символ перед курсором в строке"
        buttonBack.addActionListener(failed -> {
                    int caretIndex = input.getCaretPosition();
                    String teller = input.getText();
                    if (caretIndex != 0) {
                        input.setText(teller.substring(0, caretIndex - 1) + teller.substring(caretIndex));
                        input.setCaretPosition(caretIndex - 1);
                    }
                    if (soundIsON) play(); // звук
                    input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
                }
        );
        buttonLeftShift.addActionListener(failed -> {
                    if (input.getCaretPosition() > 0) {
                        input.setCaretPosition(input.getCaretPosition() - 1);
                    }
                    if (soundIsON) play(); // звук
                    input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
                }
        );
        buttonRightShift.addActionListener(failed -> {
                    if (input.getCaretPosition() < input.getText().length()) {
                        input.setCaretPosition(input.getCaretPosition() + 1);
                    }
                    if (soundIsON) play(); // звук
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
                    input.setText(result);
                    File journal = new File("Journal\\test.txt");
                    try {
                        PrintWriter journalWriter = new PrintWriter(
                                new BufferedWriter(new FileWriter(journal, true)));
                        journalWriter.println(teller + "=" + result);
                        journalWriter.flush(); //чистка буфера (комментарий для себя)
                        journalWriter.close(); //закрытие потока/возвращение ресурсов
                        // (тоже для себя)
                    } catch (IOException ex) {
                    }
                    if (soundIsON) play(); // звук
                    input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
                }
        );
        //анонимные классы для кнопок функций

        buttonAVX.addActionListener(e -> {
            String addText = "^"; // спец. символ функции
            String text = input.getText();
            int stringIndex = input.getCaretPosition(); // определяем местоположение курсора в строке
            input.setText(text.substring(0, stringIndex) + addText + text.substring(stringIndex)); // добовляем в указанное место спец. символ функции
            input.setCaretPosition(stringIndex + 1);// сдвигаем курсор
            if (soundIsON) play(); // звук
            input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
        });
        buttonSQRT.addActionListener(e -> {
            String addText = "sqrt("; // спец. символ функции
            String text = input.getText();
            int stringIndex = input.getCaretPosition();// определяем местоположение курсора в строке
            input.setText(text.substring(0, stringIndex) + addText + text.substring(stringIndex));// добовляем в указанное место спец. символ функции
            input.setCaretPosition(stringIndex + 5);// сдвигаем курсор
            if (soundIsON) play(); // звук
            input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
        });
        buttonLG.addActionListener(e -> {
            String addText = "log("; // спец. символ функции
            String text = input.getText();
            int stringIndex = input.getCaretPosition();// определяем местоположение курсора в строке
            input.setText(text.substring(0, stringIndex) + addText + text.substring(stringIndex));// добовляем в указанное место спец. символ функции
            input.setCaretPosition(stringIndex + 4);// сдвигаем курсор
            if (soundIsON) play(); // звук
            input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
        });
        buttonJurnal.addActionListener(e -> {
            Journal();
            if (soundIsON) play(); // звук
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


    Image imgSQRTX = new ImageIcon(getClass().getResource("images/sqr(x).png")).getImage();
    Image imgAX = new ImageIcon(getClass().getResource("images/A(x).png")).getImage();
    Image imgLeftShift = new ImageIcon(getClass().getResource("images/LeftShift.png")).getImage();
    Image imgRightShift = new ImageIcon(getClass().getResource("images/RightShift.png")).getImage();
    Image imgPicture = new ImageIcon(getClass().getResource("images/picture.png")).getImage();
    Image imgLog_x = new ImageIcon(getClass().getResource("images/log_x.png")).getImage();
    Image imgSoundON = new ImageIcon(getClass().getResource("images/soundON.jpg")).getImage();
    Image imgSoundOFF = new ImageIcon(getClass().getResource("images/soundOFF.jpg")).getImage();


    int charCounter = 0;
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
    private JLabel label = new JLabel("Ввод: ");
    private JButton buttonBack = new JButton("Стереть");
    private JButton buttonSQRT = new JButton(new ImageIcon(imgSQRTX));
    private JButton buttonLeft = new JButton("(");
    private JButton buttonRight = new JButton(")");
    private JButton buttonLG = new JButton(new ImageIcon(imgLog_x));
    private JButton buttonAVX = new JButton(new ImageIcon(imgAX));
    private JButton buttonLeftShift = new JButton(new ImageIcon(imgLeftShift));
    private JButton buttonRightShift = new JButton(new ImageIcon(imgRightShift));
    private JButton buttonJurnal = new JButton("Журнал");
    private JButton buttonSound = new JButton(new ImageIcon(imgSoundON));
    private JLabel picture = new JLabel(new ImageIcon(imgPicture));
    private JTextField status;
    private JFrame journalFrame = new JFrame();

    void play() { // класс для проигрывания звука (не понимаю, как он работает)
        try {
            af = AudioSystem.getAudioFileFormat(soundFile);
            AudioFormat f = af.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, f);
            if (AudioSystem.isLineSupported(info)) {
                try {
                    soundClip = (Clip) AudioSystem.getLine(info);
                    AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                    soundClip.open(ais);
                    soundClip.start();
                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    // ЖУРНАЛ
    public String Journal() {
        journalFrame.setTitle("Журнал");
        journalFrame.setBounds(500, 250, 500, 500);

        // Lets create a border layout to make positioning of items easy and quick.
        journalFrame.setLayout(new BorderLayout());
        journalFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// при закрытии журнала не закрывать калькулятор
        // список элементов окна "журнал" и добавление их в окно
        journalFrame.setVisible(true);
        JRadioButton showResult = new JRadioButton("рзультат");
        JRadioButton showExpression = new JRadioButton("выражение");
        showExpression.addActionListener(e -> {
            if (soundIsON) play(); // звук
        });
        showResult.addActionListener(e -> {
            if (soundIsON) play(); // звук
        });
        ButtonGroup returning = new ButtonGroup();
        returning.add(showExpression);
        returning.add(showResult);
        JButton buttonClear = new JButton("C");
        JPanel journalTopPanel = new JPanel();
        journalTopPanel.setLayout(new BorderLayout());
        journalTopPanel.add(showExpression, BorderLayout.CENTER);
        journalTopPanel.add(showResult, BorderLayout.WEST);
        journalTopPanel.add(buttonClear, BorderLayout.EAST);

        // Анонимный класс для кнопки сброса текста в журнале
        buttonClear.addActionListener(failed -> { // когда нажали на кнопку
                    File created = new File("Journal\\test.txt");
                    try {
                        FileWriter journalWriter = new FileWriter(created);
                        journalWriter.write(""); // стереть весь текст в файле журнала
                        journalWriter.close(); // вернуть ресуры системе
                    } catch (IOException e) { //если что-то не так, то
                    }                         // я н епридумал, что он должен делать, если что-то не так.
                    editor.setText(""); // сбросить текст в окне журнала.
                    journalFrame.dispose(); // закрыть окно журнала
                    if (soundIsON) play(); // проиграть звук
                    input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
                }
        );


        String text = ""; //переменная для вывода текста из файла
        File created = new File("Journal\\test.txt");
        try {

            Scanner reader = new Scanner(created);
            while (reader.hasNextLine()) {
                text = text + reader.nextLine() + "\n"; //считываем текст для журнала.
            }
            reader.close(); // возврат ресурсов
        } catch (FileNotFoundException e) { // если нет файла с журналом
            text = "";                      // оставляем пустую строку для окна журнала.
        }

        String result = ""; // возвращаемая строка (то выражение или ответ, который выбирает пользователь)
        editor = new JTextArea(text); // создаём текстовое поле с данными из журнала

        editor.grabFocus();

        // Add a caretListener to the editor. This is an anonymous class because it is inline and has no specific name.
        // Each time the caret is moved, it will trigger the listener and its method caretUpdate.
// It will then pass the event to the update method including the source of the event (which is our textarea control)
        editor.addCaretListener(e -> { // при сдвиге курсора
            JTextArea editArea = (JTextArea) e.getSource();
            String[] text1 = editor.getText().split("\n");

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
                charCounter = text1[linenum - 1].length() - 1; //переменная для поиска индекса знака "="
                // ищем "равно"
                while (text1[linenum - 1].charAt(charCounter) != '=') {
                    charCounter--;
                }
                // В зависимости от выбранной кнопки возвращаем илбо результат вычислений, либо сам пример
                if (showResult.isSelected()) {
                    input.setText(text1[linenum - 1].substring(charCounter + 1));
                    System.out.println(text1[linenum - 1].substring(charCounter + 1));
                } else {
                    input.setText(text1[linenum - 1].substring(0, charCounter));
                    System.out.println(text1[linenum - 1].substring(0, charCounter));
                }
                // закрываем окно
                journalFrame.dispose();
            } catch (Exception ex) {
            }

            // Once we know the position of the line and the column, pass it to a helper function for updating the status bar.
            updateStatus(linenum, columnnum);
        });

        //добавляем полосы прокрутки для журнала, которые будут отображаться если надо
        JScrollPane scrollPane = new JScrollPane(editor);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        // Add the fields to the layout, the editor in the middle and the status at the bottom.
        journalFrame.add(scrollPane, BorderLayout.CENTER);
        journalFrame.add(journalTopPanel, BorderLayout.NORTH);

        status = new JTextField();
        journalFrame.add(status, BorderLayout.SOUTH);


        // Give the status update value
        updateStatus(1, 1);
        return result;
    }

    // эта часть просто осталась от оригинального кода (просто прикольно, да и не мешает вроде)
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
            // получаем имя кнопки
            StringBuffer addText = new StringBuffer(input.getText());
            // получаем позицию курсора (каретки?)
            int stringIndex = input.getCaretPosition();
            addText.insert(stringIndex, ((JButton) failed.getSource()).getText());// вставляем текст туда, где находится курсор
            input.setText(String.valueOf(addText)); //обновляем текст в строке ввода
            input.setCaretPosition(stringIndex + 1);//обновляем позицию курсора
            if (soundIsON) play(); // запускаем звук нажатия на кнопку
            input.grabFocus();//делает курсор ВИДИМЫМ ПОСЛЕ НАЖАТИЯ НА КНОПКУ (комментарий для себя, позже уберу)
        }
    }
}