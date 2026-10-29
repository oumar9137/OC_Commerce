/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author oumar camara
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
    //1. infos de la base
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_commerce";
    private static final String USER = "root";
    private static final String PASSWORD = "oumar9137";
    
    //2. Méthode pour se connecter
    public static Connection getConnection(){
        Connection con = null;
        
        try{
            //charge le driver MYSQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Se connecter
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion reussie à gestion Commerce!");
        } catch (ClassNotFoundException | SQLException e){
            System.out.println("Erreur connxion: " + e.getMessage());
        }
        return con;
        
    }
    //3. Pour tester si ça marche
    public static void main (String[]args){
        getConnection();
    }
    
}
