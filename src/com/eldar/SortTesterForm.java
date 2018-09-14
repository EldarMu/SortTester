package com.eldar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
Класс, запускающий форму, и связывающий её элементы с кодом
Заметьте, что именно здесь находится метод main()
 */
public class SortTesterForm {
    private JTextField textFieldThou;
    private JTextField textFieldTenThou;
    private JTextField textFieldHunThou;
    private JTextField textFieldMil;
    private JTextField textFieldMilSorted;
    private JTextField textFieldMilSame;
    private JPanel TesterForm;
    private JTextField textFieldSortType;
    private JButton timeMethodButton;

    public SortTesterForm() {
        timeMethodButton.addActionListener(new TimeMethodButtonClicked());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SortTesterForm");
        frame.setContentPane(new SortTesterForm().TesterForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
    private class TimeMethodButtonClicked implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SortTimer st = new SortTimer();
            textFieldSortType.setText("Использованный Метод Сортировки: " + st.sortType);
            textFieldThou.setText(st.TimeSorter(1000,"unsorted"));
            textFieldTenThou.setText(st.TimeSorter(10000,"unsorted"));
            textFieldHunThou.setText(st.TimeSorter(100000,"unsorted"));
            textFieldMil.setText(st.TimeSorter(1000000,"unsorted"));
            textFieldMilSorted.setText(st.TimeSorter(1000000,"sorted"));
            textFieldMilSame.setText(st.TimeSorter(1000000,"same"));
        }
    }
}
