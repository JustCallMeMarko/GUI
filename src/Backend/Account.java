/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Backend;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 *
 * @author Hnnner
 */

public class Account {
    private static String name = "defaultUser";
    private static String id = "-1";
    private static String hashedPass = hashPass("defaultPass");
    
     public static String getId() {
         return id;
    }
 //Hash the password using SHA-256
    private static String hashPass(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Hashing algorithm not found", e);   
        }
    }
    public static String myHash(String pass){
        return hashPass(pass);
    }
    //Pagset ng credentials
    public static void setCreds(String newId, String newName, String plainPass){
        id = newId;
        name = newName;
        hashedPass = hashPass(plainPass);  
    }

    //Pagsave ng file
    public static void savedCreds(){
        try (PrintWriter writer = new PrintWriter(new FileWriter("creds.csv"))){
            writer.println("Id,Name,HashedPass");
            writer.println(id + "," + name + "," + hashedPass);
        } catch (IOException e) {
            System.out.println("Error saving credentials: " + e.getMessage());
        }
    }
    public static void logoutSaved(){
        try (PrintWriter writer = new PrintWriter(new FileWriter("creds.csv"))){
            writer.println("Id,Name,HashedPass");
            writer.println("-1, idk, idk");
            System.out.println("Credentials saved.");
        } catch (IOException e) {
            System.out.println("Error saving credentials: " + e.getMessage());
        }
    }

    //Pagload ng file
    public static void getCreds(){
        try (BufferedReader reader = new BufferedReader(new FileReader("creds.csv"))) {
            reader.readLine(); 
            String line = reader.readLine();
            if (line != null){
                String[] parts = line.split(",");
                id = parts[0];
                name = parts[1];
                hashedPass = parts[2];
            }
        } catch (IOException e) {
            System.out.println("Error loading credentials: " + e.getMessage());
        }
    }
    
    public static String getName(){
        return name;
    }
    
    public static String getHashedPass(){
        return hashedPass;
    }

}

