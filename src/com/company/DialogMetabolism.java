package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogMetabolism extends JFrame{
    private JPanel rootPanel;
    private JButton btnOK;
    private JRadioButton radioMan;
    private JRadioButton radioWoman;
    private JComboBox comboBoxGreutate;
    private JComboBox comboBoxInaltime;
    private JComboBox comboBoxVarsta;

    public DialogMetabolism(FirstGUIForm firstGUIForm) {
        add(rootPanel);
        this.setSize(350, 600);
        this.setLocationRelativeTo(null);

            for (int i = 1; i <= 150; ++i) {
                comboBoxGreutate.addItem(i);
                comboBoxInaltime.addItem(i + 100);
                comboBoxVarsta.addItem(i);
            }
        radioMan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioMan.isSelected() == true)
                    radioWoman.setSelected(false);
            }
        });
        radioWoman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioWoman.isSelected() == true)
                    radioMan.setSelected(false);
            }
        });

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioMan.isSelected()) {
                    firstGUIForm.setMetabolsim(getMetabolism(true));
                    JOptionPane.showMessageDialog(null, "Metabolsimul tau: " + getMetabolism(true).toString() + "kcal/zi", null, JOptionPane.INFORMATION_MESSAGE);

                }
                else {
                    firstGUIForm.setMetabolsim(getMetabolism(false));
                    JOptionPane.showMessageDialog(null, "Metabolsimul tau: " + getMetabolism(false).toString() + " kcal/zi", null, JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
    }

    Double getMetabolism(boolean man) {
        if (man == true) {
            return 88.362 + (13.397 * Integer.parseInt(comboBoxGreutate.getSelectedItem().toString()))
                    + (4.799 * Integer.parseInt(comboBoxInaltime.getSelectedItem().toString()))
                    - (5.677 * Integer.parseInt(comboBoxVarsta.getSelectedItem().toString()));
        }

        return 447.593 + (9.247 * Integer.parseInt(comboBoxGreutate.getSelectedItem().toString()))
                + (3.098 * Integer.parseInt(comboBoxInaltime.getSelectedItem().toString()))
                - (4.330 * Integer.parseInt(comboBoxInaltime.getSelectedItem().toString()));
    }
}
