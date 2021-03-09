package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FirstGUIForm extends JFrame {
    private JButton btnNewFood;
    private JPanel panelFirst;
    private JButton btn2;
    private JButton btnTerminaZi;
    private JButton btnAmMancat;
    private JTextField txtAlegeMancare;
    private JTextField txtAlegeGreutate;
    private JTable tableFood;
    private JPanel panelAdauga;
    private JPanel panelRetineMancare;
    private JTable tableRetineZi;
    private JLabel lblKcal;
    private JLabel lblProteins;
    private JLabel lblFats;
    private JLabel lblCarbs;
    private JScrollPane jspFood;
    private JScrollPane jspRetineZi;
    private JLabel lblGramaj;
    private JLabel lblMancare;
    private JLabel lblTotalProteine;
    private JLabel lblTotalGrasimi;
    private JLabel lblTotalCarbs;
    private JLabel lblTotalKcal;
    private JButton btnInsereazaInGrafic;
    private JButton btnBMR;
    private JButton btnShowBMRChart;
    private JTextField textField;
    private BazaDeDate bazaDeDate;
    double metabolism;

    public FirstGUIForm()
    {
        bazaDeDate = new BazaDeDate();
        this.add(panelFirst);
        this.setSize(900, 1000);
        this.setTitle("Metabolism tracker");

        Color myColor = new Color( 68,124,162);
        panelFirst.setBackground(myColor);
        creeazaTabel(tableFood);
        txtAlegeGreutate.setPreferredSize(txtAlegeMancare.getSize());
        panelAdauga.setBackground(myColor);
        lblMancare.setForeground(Color.orange);
        lblGramaj.setForeground(Color.orange);
        creeazaTabelEvidentaZi();
        panelRetineMancare.setBackground(myColor);
        myColor = new Color(254, 112, 195);
        lblProteins.setForeground(myColor);
        lblTotalProteine.setForeground(myColor);
        myColor = new Color(255, 191, 0);
        lblTotalGrasimi.setForeground(myColor);
        lblFats.setForeground(myColor);
        lblTotalCarbs.setForeground(myColor);
        lblCarbs.setForeground(myColor);
        myColor = new Color(64, 255, 0);
        lblTotalKcal.setForeground(myColor);
        lblKcal.setForeground(myColor);
        calculeazaTotaluri();
        btnAmMancat.setVisible(false);

        /*textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBounds(0, 0, 200, 30);
        panelText.setLayout(null);
        panelText.setBackground(myColor);
        panelText.add(textField); */

        //deschiderea dialogului secundar
        btnNewFood.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deschideFereastra2();
            }
        });

        //filtrarea dupa nume in momentul scrierii in textbox
        txtAlegeMancare.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String query = txtAlegeMancare.getText();
                filter(query);
            }
        });

        //actiunea se petrece atunci cand selectam mancarea ce vrem sa o adaugam, daca txtGreutate != null, altfel, arunca exceptie
        tableFood.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel model = (DefaultTableModel)tableFood.getModel();
                int selectedRowIndex = tableFood.getSelectedRow();
                if (tableFood.getRowSorter() != null) {
                    selectedRowIndex = tableFood.getRowSorter().convertRowIndexToModel(selectedRowIndex);
                }

                if (!(txtAlegeGreutate.getText().equals(""))) {
                    Integer greutate = Integer.parseInt(txtAlegeGreutate.getText());
                    Integer calorii = greutate / 100 * Integer.parseInt(model.getValueAt(selectedRowIndex, 1).toString());
                    Integer proteine = greutate / 100 * Integer.parseInt(model.getValueAt(selectedRowIndex, 2).toString());
                    Integer grasimi = greutate / 100 * Integer.parseInt(model.getValueAt(selectedRowIndex, 3).toString());
                    Integer carbohidrati = greutate / 100 * Integer.parseInt(model.getValueAt(selectedRowIndex, 4).toString());

                    adaugaRand(model.getValueAt(selectedRowIndex, 0).toString(), greutate.toString(), proteine.toString(),
                            grasimi.toString(), carbohidrati.toString(), calorii.toString());
                    calculeazaTotaluri();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Introdu mai intai greutatea!", "Atentie!", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        //deschidem chartul de greutate
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChartDialog chartDialog = new ChartDialog("tabelProgres");
                chartDialog.setVisible(true);
            }
        });
        //graficul greutatii
        btnInsereazaInGrafic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Double greutate = Double.parseDouble(JOptionPane.showInputDialog(null, "Introdu greutatea"));
                String data = JOptionPane.showInputDialog(null, "Introdu data de astazi in formatul MM-dd");
                if (greutate != null && data != null)
                    bazaDeDate.adaugaInTabelProgres("tabelProgres", data, greutate);
            }
        });

        //calculam metabolismul
        btnBMR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deschideFereastraMetabolism();
            }
        });

        //introducem in tabelMetabolism, aportul caloric din data respectiva
        btnTerminaZi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                java.util.Date date = (java.util.Date)Calendar.getInstance().getTime();
                SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                String data = format.format(date);
                Double aport = Double.parseDouble(lblTotalKcal.getText());

              bazaDeDate.adaugaInTabelMetabolism("tabelMetabolism", data, aport);
              DefaultTableModel model = (DefaultTableModel) tableRetineZi.getModel();
              model.setRowCount(0);
            }
        });

        btnShowBMRChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChartDialog chartDialogMetabolism = new ChartDialog("tabelMetabolism");
                chartDialogMetabolism.setVisible(true);
            }
        });
    }   //aici iesim din constructor

    public JTable getTableFood() {
        return this.tableFood;
    }

    public void deschideFereastraMetabolism() {
        DialogMetabolism dialogMetabolism = new DialogMetabolism(this);
        dialogMetabolism.setVisible(true);
    }

    public void deschideFereastra2() {
        DialogSecundar secondDialog = new DialogSecundar(bazaDeDate, this);
        secondDialog.setVisible(true);
    }

    //crearea tabelului principal care afiseaza toata mancarea din baza de date
    public void creeazaTabel(JTable tableFood) {
        String coloane[] = {"Mancare", "Kcal", "Proteine", "Grasimi", "Carbohidrati"};
        String data[][] = null;

        tableFood.setModel(new DefaultTableModel(data, coloane));
        tableFood.setBackground(new Color(135,206,250));
        tableFood.setForeground(Color.black);
        jspFood.getViewport().add(tableFood);
        jspFood.getViewport().setBackground(new Color(135,206,250));

        bazaDeDate.getTableData(tableFood);
    }
    //crearea tabelului secundar(de jos) care retine ce mancam intr-o zi
    public void creeazaTabelEvidentaZi() {
        String coloane[] = {"Mancare", "Gramaj", "Proteine", "Grasimi", "Carbohidrati", "Kcal"};
        String data[][] = null;
        tableRetineZi.setModel(new DefaultTableModel(data, coloane));
        tableRetineZi.setBackground(new Color(135,206,250));
        tableRetineZi.setForeground(Color.black);
        jspRetineZi.getViewport().add(tableRetineZi);
        jspRetineZi.getViewport().setBackground(new Color(135,206,250));

         bazaDeDate.getTableDataParametered(tableRetineZi, "tabelZiSQL");
    }

    //filtrarea tabelului dupa nume
    public void filter(String query) {
        DefaultTableModel model = (DefaultTableModel)tableFood.getModel();

        TableRowSorter<DefaultTableModel> tableModelTableRowSorter = new TableRowSorter<DefaultTableModel>(model);
        tableFood.setRowSorter(tableModelTableRowSorter);
        tableModelTableRowSorter.setRowFilter(RowFilter.regexFilter(query));
    }

    //Adaugarea unei mancari noi pentru ZIUA respectiva (in tabelul de jos). Gretutatea trebuie sa fie nenula, altfel - exceptie
    public void adaugaRand(String v1, String v2, String v3, String v4, String v5, String v6) {

        DefaultTableModel model = (DefaultTableModel)tableRetineZi.getModel();

        String cale = System.getProperty("user.dir");
        cale += "\\db.sql";
        String url = "jdbc:sqlite:" + cale;
        try {
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Conectat cu succes");
            Statement statement = connection.createStatement();
            if (v2 != null) {
                statement.execute("INSERT INTO tabelZiSQL (nume, gramaj, proteins, fats, carbs, kcal) VALUES('" + v1 + "', '" + v2 + "', '" + v3 + "', '" + v4 + "', '" + v5 + "', '" + v6 + "');");
                model.addRow(new String[]{v1, v2, v3, v4, v5, v6});
            }

            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    //calcularea totalurilor in parte, si afisarea lor in label-uri
    void calculeazaTotaluri() {
        Integer totalProteine = 0, totalGrasimi = 0, totalCarbohidrati = 0, totalCalorii = 0;
        DefaultTableModel model = (DefaultTableModel)tableRetineZi.getModel();
        for (int i = 0; i < tableRetineZi.getRowCount(); ++i) {
            totalProteine += Integer.parseInt(model.getValueAt(i, 2).toString());
            totalGrasimi += Integer.parseInt(model.getValueAt(i, 3).toString());
            totalCarbohidrati += Integer.parseInt(model.getValueAt(i, 4).toString());
            totalCalorii += Integer.parseInt(model.getValueAt(i, 5).toString());
        }

        lblTotalProteine.setText(totalProteine.toString());
        lblTotalGrasimi.setText(totalGrasimi.toString());
        lblTotalCarbs.setText(totalCarbohidrati.toString());
        lblTotalKcal.setText(totalCalorii.toString());
    }

    public void setMetabolsim(double metabolism) {
        this.metabolism = metabolism;
    }
}
