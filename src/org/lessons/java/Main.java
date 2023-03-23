package org.lessons.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        //Creare la connessione al db
        String url = "jdbc:mysql://localhost:3306/nations_db";
        String user = "root";
        String password = "root";

        Scanner s = new Scanner(System.in);
        System.out.println("Inserisci una stringa di ricerca");
        String inputUser = s.nextLine();
        s.close();

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connessione al database effettuata");

            //Scrivere la query sql
            String sql = "SELECT c.name AS name_country, c.country_id AS id_country, r.name AS name_region, c2.name AS name_continent\r\n"
                    + "FROM countries c \r\n"
                    + "INNER JOIN regions r \r\n"
                    + "	ON r.region_id = c.region_id \r\n"
                    + "INNER JOIN continents c2 \r\n"
                    + "	ON c2.continent_id = r.continent_id\r\n"
                    + "WHERE c.name LIKE ?\r\n"
                    + "ORDER BY c.name";

            try (PreparedStatement ps=con.prepareStatement(sql)){
                //utilizzare il preparestatement (istruzione che devo eseguire)

                ps.setString(1, "%"+inputUser+"%");

                try(ResultSet rs = ps.executeQuery()) {
                    //utilizzare resultSet (risultato dell'istruzione)

                    while (rs.next()) { //fintanto c'è qualcosa da leggere

                        String nameCountry = rs.getString("name_country");
                        int idCountry = rs.getInt("id_country");
                        String nameRegion = rs.getString("name_region");
                        String nameContinent = rs.getString("name_continent");
                        System.out.println("Id_Country: " + idCountry + "\t Country: " + nameCountry + "\t Region: " + nameRegion + "\t Continent: " + nameContinent + "\n");

                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}