/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author canti
 */

public class DBController implements AutoCloseable{
    String db = "commodono";
    String url = "jdbc:mysql://localhost:3307/" + db;
    Connection conn;
    Statement stmt;
    PreparedStatement pstmt;
    
    public DBController() throws SQLException{
        this.conn = DriverManager.getConnection(url, "root", "sudoADMIN");
        this.stmt = conn.createStatement();
    }
    public boolean verifySavedCred() throws SQLException{
        Account.getCreds();
        String query = "SELECT username, password FROM users WHERE username = '" +Account.getName() + "';";
        ResultSet res = stmt.executeQuery(query);
        boolean verified= false;
        if(res.next()){
           String hash = Account.getHashedPass();
           verified = hash.equals(Account.myHash(res.getString("password")));
        }
        return verified;
    }
    public ArrayList<ArrayList<String>> getFilteredDonations(String option, String filter) throws SQLException {
        ArrayList<ArrayList<String>> donations = new ArrayList<>();

        // Select program or school depending on the filter
        String selectClause;
        boolean filteringBySchool = option.equalsIgnoreCase("school");

        if (filteringBySchool) {
            selectClause = """
                SELECT 
                    d.refer_id,
                    d.item,
                    d.qty,
                    d.name,
                    c.category_name AS category,
                    CASE
                        WHEN d.program_id IN (1, 2, 3) THEN 'SHS'
                        WHEN d.program_id IN (4, 5, 6, 7, 8) THEN 'SECA'
                        WHEN d.program_id IN (9, 10, 11, 12, 13) THEN 'SBMA'
                        WHEN d.program_id IN (14, 15, 16) THEN 'SASE'
                        ELSE 'Unknown'
                    END AS school,
                    d.section,
                    d.donation_date
            """;
        } else {
            selectClause = """
                SELECT 
                    d.refer_id,
                    d.item,
                    d.qty,
                    d.name,
                    c.category_name AS category,
                    p.program_name AS program,
                    d.section,
                    d.donation_date
            """;
        }

        String fromClause = """
            FROM donations d
            JOIN categories c ON d.category_id = c.category_id
            JOIN programs p ON d.program_id = p.program_id
        """;

        String whereClause;
        switch (option.toLowerCase()) {
            case "category" -> whereClause = "WHERE LOWER(c.category_name) = ?";
            case "program" -> whereClause = "WHERE LOWER(p.program_name) = ?";
            case "section" -> whereClause = "WHERE LOWER(d.section) = ?";
            case "school" -> whereClause = """
                WHERE CASE
                    WHEN d.program_id IN (1, 2, 3) THEN 'shs'
                    WHEN d.program_id IN (4, 5, 6, 7, 8) THEN 'seca'
                    WHEN d.program_id IN (9, 10, 11, 12, 13) THEN 'sbma'
                    WHEN d.program_id IN (14, 15, 16) THEN 'sase'
                    ELSE 'unknown'
                END = ?
            """;
            default -> throw new IllegalArgumentException("Invalid filter option: " + option);
        }

        String query = selectClause + "\n" + fromClause + "\n" + whereClause + "\nORDER BY d.refer_id ASC";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, filter.toLowerCase());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ArrayList<String> row = new ArrayList<>();
                row.add(String.valueOf(rs.getInt("refer_id")));  // ref ID
                row.add(rs.getString("item"));                  // item
                row.add(String.valueOf(rs.getInt("qty")));      // quantity
                row.add(rs.getString("name"));                  // name
                row.add(rs.getString("category"));              // category

                // Only get either program or school depending on the filter
                if (filteringBySchool) {
                    row.add(rs.getString("school"));
                } else {
                    row.add(rs.getString("program"));
                }

                row.add(rs.getString("section"));              // section
                row.add(rs.getDate("donation_date").toString()); // date
                donations.add(row);
            }
        }

        return donations;
    }


    public ArrayList<ArrayList<String>> getDonates() throws SQLException {
        String query = "SELECT refer_id AS ref_num, item, qty, name, section, donation_date FROM donations";
        ResultSet res = stmt.executeQuery(query); 
        ArrayList<ArrayList<String>> datas = new ArrayList<>();

        while(res.next()) {
            ArrayList<String> dataset = new ArrayList<>();
            dataset.add(Integer.toString(res.getInt("ref_num")));  
            dataset.add(res.getString("item"));         
            dataset.add(Integer.toString(res.getInt("qty")));  
            dataset.add(res.getString("name"));  
            dataset.add(res.getString("section"));  
            dataset.add(res.getDate("donation_date").toString());
            datas.add(dataset);
        }

        return datas;
    }
    
    public String getDonateCount() throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM donations";
        ResultSet res = stmt.executeQuery(query); 
        int sum = 0;
        if(res.next()) {
            sum = res.getInt("count");
        }

        return Integer.toString(sum);
    }

    public void deleteUserById(int userId) throws SQLException {
        String query = "DELETE FROM users WHERE user_id = ?";
        pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, userId);
        pstmt.executeUpdate();
    }
    public String[][] getCategory() throws SQLException{
        String query = "SELECT c.category_name, COUNT(*) AS count FROM donations d JOIN categories c ON d.category_id = c.category_id GROUP BY c.category_name;";
        ResultSet res = stmt.executeQuery(query);
        String[][] datas = new String[6][2];  
        int count = 0;

        while(res.next() && count < datas.length) {
            datas[count][0] = res.getString("category_name");  
            datas[count][1] = res.getString("count");         
            count++;
        }
        return datas;
    }
    public String[] getSchools() throws SQLException{
        String query = "SELECT (SELECT COUNT(*) FROM donations WHERE program_id IN (1,2,3)) AS SHS, (SELECT COUNT(*) FROM donations WHERE program_id IN (4,5,6,7,8)) AS SECA, (SELECT COUNT(*) FROM donations WHERE program_id IN (9,10,11,12,13)) AS SBMA, (SELECT COUNT(*) FROM donations WHERE program_id IN (14,15,16)) AS SASE;";
        ResultSet res = stmt.executeQuery(query);
        String[] datas = new String[4];  
        int count = 0;

        while(res.next() && count < datas.length) {
            datas[0] = res.getString("SHS");
            datas[1] = res.getString("SECA");
            datas[2] = res.getString("SBMA");
            datas[3] = res.getString("SASE");
        }
        return datas;
    }
    public ArrayList<ArrayList<String>> getStations() throws SQLException{
        String query = "SELECT u.username, COUNT(*) AS count FROM donations d JOIN users u ON d.user_id = u.user_id GROUP BY u.username;";
        ResultSet res = stmt.executeQuery(query); 
        ArrayList<ArrayList<String>> datas = new ArrayList<>();
        while(res.next()) {
            ArrayList<String> dataset = new ArrayList<>();
            dataset.clear();
            dataset.add(res.getString("username"));  
            dataset.add(res.getString("count"));         
            datas.add(dataset);
        }
        return datas;
    }
    public ArrayList<String> getRecent() throws SQLException{
        String query = "SELECT name FROM donations LIMIT 16;";
        ResultSet res = stmt.executeQuery(query); 
        ArrayList<String> dataset = new ArrayList<>();
        while(res.next()) {
            dataset.add(res.getString("name"));   
        }
        return dataset;
    }
    public ArrayList<ArrayList<String>> getAccounts() throws SQLException {
        String query = "SELECT user_id, username, role_id FROM users";
        ResultSet res = stmt.executeQuery(query);
        ArrayList<ArrayList<String>> datas = new ArrayList<>();

        while(res.next()) {
            ArrayList<String> dataset = new ArrayList<>(); 
            dataset.add(Integer.toString(res.getInt("user_id")));  
            dataset.add(res.getString("username"));  

            int role = res.getInt("role_id");
            String myRole = (role == 0) ? "admin" : "user";
            dataset.add(myRole);

            datas.add(dataset);
        }
        return datas;
    }

    public boolean verifyLogin(String name, String pass) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setString(2, pass);
        ResultSet res = pstmt.executeQuery();
        return res.next();
    }
    public boolean isAdmin(String name, String pass) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setString(2, pass);
        ResultSet res = pstmt.executeQuery();
        if(res.next()){
            int admin = res.getInt("role_id");
            return admin == 0;
        }
        return false;
    }
    public boolean isAdmin(String name) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, name);
        ResultSet res = pstmt.executeQuery();
        if(res.next()){
            int admin = res.getInt("role_id");
            return admin == 0;
        }
        return false;
    }

    public String getTotalDonate() throws SQLException{
        String query = "SELECT COUNT(*) FROM donations";
        ResultSet res = stmt.executeQuery(query);
        int count = 0;
        if(res.next()){
            count = res.getInt(1);
        }
        return Integer.toString(count);
    }

    public void insertData(String item, int qty, String name, int cat, int prog, String section) throws SQLException {
        Account.getCreds();
        String getUserIdQuery = "SELECT user_id FROM users WHERE username = ?";
        pstmt = conn.prepareStatement(getUserIdQuery);
        pstmt.setString(1, Account.getName());
        ResultSet rs = pstmt.executeQuery();

        int userId = -1;
        if (rs.next()) {
            userId = rs.getInt("user_id");
        } else {
            throw new SQLException("Username not found: " + Account.getName());
        }

        // Now insert the donation with the retrieved user_id
        String insertQuery = "INSERT INTO donations (item, qty, user_id, name, category_id, program_id, section, donation_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        pstmt = conn.prepareStatement(insertQuery);

        LocalDate date = LocalDate.now();
        pstmt.setString(1, item);
        pstmt.setInt(2, qty);
        pstmt.setInt(3, userId);                   // use actual user ID from DB
        pstmt.setString(4, name);
        pstmt.setInt(5, cat + 1);                  // adjust index if needed
        pstmt.setInt(6, prog + 1);                 // adjust index if needed
        pstmt.setString(7, section);
        pstmt.setDate(8, java.sql.Date.valueOf(date));

        pstmt.executeUpdate();
    }

    public void insertUser(String user, String pass, int role) throws SQLException{
        String query = "INSERT INTO users (username, password, role_id) VALUES(?, ?, ?)";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, user);
        pstmt.setString(2, pass);
        pstmt.setInt(3, role);
        pstmt.executeUpdate();
    }
    public void updateUser(int userId, String newUsername, int newRoleId) throws SQLException {
        String query = "UPDATE users SET username = ?, role_id = ? WHERE user_id = ?";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, newUsername);
        pstmt.setInt(2, newRoleId);
        pstmt.setInt(3, userId);
        pstmt.executeUpdate();
    }
    public void updatePass(String username, String newPass, String newUsername) throws SQLException {
        String query = "UPDATE users SET username = ?, password = ? WHERE username = ?";
        pstmt = conn.prepareStatement(query);
        pstmt.setString(1, newUsername);
        pstmt.setString(2, newPass);
        pstmt.setString(3, username);
        pstmt.executeUpdate();
    }

    @Override
    public void close() throws SQLException {
        if(stmt != null && !stmt.isClosed()){
            stmt.close();
        }
        if(pstmt != null && !pstmt.isClosed()){
            pstmt.close();
        }
        if(conn != null && !conn.isClosed()){
            conn.close();
        }
    }
}
