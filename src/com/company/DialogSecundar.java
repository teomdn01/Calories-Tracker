package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DialogSecundar extends FirstGUIForm {

    private JPanel panelSecundar;
    private JButton btnExit;
    private JButton btnAdd;
    private JTextField txtFats;
    private JTextField txtProteins;
    private JTextField txtCarbs;
    private JTable table;
    private JScrollPane scrollPane;
    private JTextField txtMancare;
    private JTextField txtKcal;
    private JLabel lblAddMancare;
    private JLabel lblAddCalorii;
    private JLabel lblProteine;
    private JLabel lblGrasimi;
    private JLabel lblCarbohidrati;

    public DialogSecundar(BazaDeDate bazaDeDate, FirstGUIForm firstGUIForm) {
        add(panelSecundar);
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);

        creeazaTabelDoi();
        bazaDeDate.getTableData(table);

        btnExit.setText("Iesire");
        btnAdd.setText("Adauga Rand");
        txtProteins.setPreferredSize(btnAdd.getSize());
        txtFats.setPreferredSize(btnAdd.getSize());
        txtCarbs.setPreferredSize(btnAdd.getSize());
        lblAddCalorii.setForeground(Color.orange);
        lblAddMancare.setForeground(Color.orange);
        lblProteine.setForeground(Color.orange);
        lblGrasimi.setForeground(Color.orange);
        lblCarbohidrati.setForeground(Color.orange);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adaugaRand(txtMancare.getText(), txtKcal.getText(), txtProteins.getText(), txtFats.getText(), txtCarbs.getText());
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                firstGUIForm.creeazaTabel(firstGUIForm.getTableFood());
                dispose();
            }
        });
    }

    public void creeazaTabelDoi() {
        String coloane[] = {"Mancare", "Kcal", "Proteine", "Grasimi", "Carbohidrati"};
        String data[][] = null;

        table.setModel(new DefaultTableModel(data, coloane));

        table.setBackground(Color.orange);
        table.setForeground(Color.black);

        scrollPane.getViewport().add(table);
        scrollPane.getViewport().setBackground(Color.orange);
    }

    public void adaugaRand(String v1, String v2, String v3, String v4, String v5) {

        DefaultTableModel model = (DefaultTableModel)table.getModel();

        String cale = System.getProperty("user.dir");
        cale += "\\db.sql";
        String url = "jdbc:sqlite:" + cale;
        try {
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Conectat cu succes");
            Statement statement = connection.createStatement();

            statement.execute("INSERT INTO tabelMancareSQL (nume, kcal, proteins, fats, carbs) VALUES('" + v1 + "', '" + v2 + "', '" + v3 + "', '" + v4 + "', '" + v5 + "');");
            model.addRow(new String[]{v1, v2, v3, v4, v5});

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
