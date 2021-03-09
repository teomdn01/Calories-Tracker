package com.company;

import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChartDialog extends JFrame {
    private JPanel rootPanel;

    public ChartDialog(String tabelSQL) {
        add(rootPanel);
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        List<Double> kglist = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        String cale = System.getProperty("user.dir");
        cale += "\\db.sql";
        String url = "jdbc:sqlite:" + cale;

        try {
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Conectat cu succes");
            Statement statement = connection.createStatement();

            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
            ResultSet rs = statement.executeQuery("SELECT * FROM " + tabelSQL);
            while (rs.next()){
                dateList.add(rs.getString(1));
                kglist.add(Double.parseDouble(rs.getString(2).toString()));
            }
            statement.close();
            connection.close();
        } catch (SQLException  e) {
            System.out.println(e.getMessage());
        }

       GraphPanel graphPanel = new GraphPanel(kglist, dateList);
        add(graphPanel);
    }
}
