package com.company;
import javax.sound.midi.Soundbank;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.*;

public class BazaDeDate {

    public BazaDeDate() {
       // String cale = "D:\\INFO\\Java Proiecte\\GUI basics\\db.sql";
        String cale = System.getProperty("user.dir");
        cale += "\\db.sql";
       // System.out.println(cale + "    12345");

        // String cale = "product.db.sql";
        String url = "jdbc:sqlite:" + cale;

        try {
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Conectat cu succes");
            Statement statement = connection.createStatement();
           // statement.execute("DROP TABLE tabelMancareSQL");
            statement.execute("CREATE TABLE IF NOT EXISTS  tabelMancareSQL(" +
                    "nume TEXT UNIQUE," +
                    "kcal int," +
                    "proteins int," +
                    "fats int," +
                    "carbs int" +
                    ");");

            //creem tabloul care retine mancarea pentru ziua curenta
           // statement.execute("DROP TABLE tabelZiSQL");
            statement.execute("CREATE TABLE IF NOT EXISTS tabelZiSQL(" +
                    "nume TEXT," +
                    "gramaj int," +
                    "proteins int," +
                    "carbs int," +
                    "fats int," +
                    "kcal int" +
                    ");");


            //creerea tabelului de progres
         //  statement.execute("DROP TABLE tabelProgres");
            statement.execute("CREATE TABLE IF NOT EXISTS tabelProgres(" +
                    "data TEXT NOT NULL," +
                    "greutate double" +
                    ");");

//            statement.execute("DROP TABLE tabelMetabolism");
            statement.execute("CREATE TABLE IF NOT EXISTS tabelMetabolism(" +
                    "data TEXT NOT NULL," +
                    "aport double" +
                    // "deficit double" +        ramane de adaugat in tabel si deficitul / surplusul caloric, dupa ce calculez metabolismul
                    ");");


            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void getTableData (JTable table) {
        //String cale = "D:\\INFO\\Java Proiecte\\GUI basics\\db.sql";
        String cale = System.getProperty("user.dir");
        cale += "\\db.sql";
        String url = "jdbc:sqlite:" + cale;

        try {
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Conectat cu succes");
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM tabelMancareSQL");
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            while (rs.next()) {
                Object[] rows = new Object[]{rs.getString(1), rs.getString(2), rs.getString(3),
                                             rs.getString(4), rs.getString(5)};
                model.addRow(rows);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getTableDataParametered (JTable table, String sqlTable) {
        //String cale = "D:\\INFO\\Java Proiecte\\GUI basics\\db.sql";
        String cale = System.getProperty("user.dir");
        cale += "\\db.sql";
        String url = "jdbc:sqlite:" + cale;

        try {
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Conectat cu succes");
            Statement statement = connection.createStatement();

            DefaultTableModel model = (DefaultTableModel)table.getModel();
            ResultSet rs = statement.executeQuery("SELECT * FROM " + sqlTable);
            while (rs.next()){
                Object[] rows = new Object[]{rs.getString(1), rs.getString(2), rs.getString(3),
                                             rs.getString(4), rs.getString(5), rs.getString(6)};
                model.addRow(rows);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void adaugaInTabelProgres(String tabel, String data, Double greutate) {
        //String cale = "D:\\INFO\\Java Proiecte\\GUI basics\\db.sql";
        String cale = System.getProperty("user.dir");
        cale += "\\db.sql";
        String url = "jdbc:sqlite:" + cale;

        try {
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Conectat cu succes");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO " + tabel + "(data, greutate) VALUES ('" + data +"', '" + greutate.toString() + "');");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void adaugaInTabelMetabolism(String tabel, String data, Double aport) {
        //String cale = "D:\\INFO\\Java Proiecte\\GUI basics\\db.sql";
        String cale = System.getProperty("user.dir");
        cale += "\\db.sql";
        String url = "jdbc:sqlite:" + cale;

        try {
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Conectat cu succes");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO " + tabel + "(data, aport) VALUES ('" + data +"', '" + aport.toString() + "');");
            statement.execute("DELETE FROM tabelZiSQL");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
