/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.Account;
import Backend.DBController;
import Backend.MyCharts;
import Backend.PdfExporter;
import Frontend.components.Donations;
import Frontend.components.ViewAcc;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author canti
 */
public class Admin extends javax.swing.JFrame {
    //ADMIN PAGE
    public Color whiteColor = new Color(0x323E8F);
    public Color selectedColor = new Color(0x6666FF);
    DBController dbc = null;
    /**
     * Creates new form Admin
     */
    public Admin() {
        
        initComponents();
        b1.setBase(selectedColor); 
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        showCharts(); 
    }
    //DASHBOARD METHODS
    private JPanel recents = new JPanel();
    private void showRecents(){
        try (DBController dbc = new DBController()) {
            recents.removeAll(); 
            ArrayList<String> datas = dbc.getRecent();
            for (String info : datas) {
                JLabel label = new JLabel(info);
                label.setFont(new Font("Arial", Font.PLAIN, 24)); 
                recents.add(label);
            }
            recents.setLayout(new BoxLayout(recents, BoxLayout.Y_AXIS));
            recents.revalidate(); 
            recents.repaint(); 
        } catch (SQLException ex) {
            recents.removeAll(); 
            recents.add(new JLabel("Failed to connect with the database"));
            recents.revalidate(); 
            recents.repaint(); 
        } 
    }
    private void showTotalDonation(){
        try (DBController dbc = new DBController()) {
            String text = dbc.getTotalDonate();
            TotalDonation.setForeground(new Color(0x000000));
            TotalDonation.setText(text);
        } catch (SQLException ex) {
            TotalDonation.setText("Can't connect with database");
            TotalDonation.setForeground(new Color(0xcf4040));
            
        }
    }
    private JLabel showSQLError(){
        JLabel errorLabel = new JLabel("Can't connect with database", SwingConstants.CENTER);
        errorLabel.setForeground(new Color(0xcf4040)); 
        errorLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        return errorLabel;
    }
    private final void showCharts(){
        showTotalDonation();
        showPie();
        showSchool();
        showStation();
        showRecents();
    }
    private void showPie(){
        pieChart.removeAll();
        try (DBController dbc = new DBController()) {
            String[][] datas = dbc.getCategory();
            pieChart.add(new MyCharts().myPieChart(datas), BorderLayout.CENTER);
        } catch (SQLException ex) {
            pieChart.add(showSQLError(), BorderLayout.CENTER);
        }
        pieChart.validate();
    }
    private void showSchool(){
        schoolCharts.removeAll();
        try (DBController dbc = new DBController()) {
            String[] datas = dbc.getSchools();
            schoolCharts.add(new MyCharts().myBarChart(datas), BorderLayout.CENTER);
        } catch (SQLException ex) {
            schoolCharts.add(showSQLError(), BorderLayout.CENTER);
        }
        schoolCharts.validate();
    }
    private void showStation(){
        stationChart.removeAll();
        try (DBController dbc = new DBController()) {
            ArrayList<ArrayList<String>> datas = dbc.getStations();
            stationChart.add(new MyCharts().myStationChart(datas), BorderLayout.CENTER);
        } catch (SQLException ex) {
            stationChart.add(showSQLError(), BorderLayout.CENTER);
        }
        stationChart.validate();
    }
    
    
    //VIEW DONATIONS
    public JPanel donationContent = new JPanel();
    public void showDonates() {
        try (DBController dbc = new DBController()) {
            donationContent.removeAll(); 

            ArrayList<ArrayList<String>> datas = dbc.getDonates();
            for (ArrayList<String> donation : datas) {
                Donations donationPanel = new Donations(
                    donation.get(0),  // ref
                    donation.get(1),  // item
                    donation.get(2),  // qty
                    donation.get(3),  // name
                    donation.get(4),  // section
                    donation.get(5)   // date
                );
                donationContent.add(donationPanel);
            }

            donationContent.setLayout(new BoxLayout(donationContent, BoxLayout.Y_AXIS));
            donationContent.revalidate();
            donationContent.repaint();

        } catch (SQLException ex) {
            ex.printStackTrace();
            donationContent.removeAll();
            donationContent.add(new JLabel("Failed to connect with the database"));
            donationContent.revalidate();
            donationContent.repaint();
        }
    }

    
    
    
    
    
    //VIEW ACCOUNT
    public JPanel mainScreen = new JPanel();
    public void showAccounts(){
        try (DBController dbc = new DBController()) {
            mainScreen.removeAll(); 
            ArrayList<ArrayList<String>> datas = dbc.getAccounts();
            for (ArrayList<String> arrs : datas) {
                    ViewAcc accPanel = new ViewAcc(arrs.get(0), arrs.get(1), arrs.get(2), this);
                    mainScreen.add(accPanel);
            }
            mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.Y_AXIS));
            mainScreen.revalidate(); 
            mainScreen.repaint(); 
        } catch (SQLException ex) {
            mainScreen.removeAll(); 
            mainScreen.add(new JLabel("Failed to connect with the database"));
            mainScreen.revalidate(); 
            mainScreen.repaint(); 
        } 
    }
    
    
    //ADD ACCOUNT PAGE
    private void addAcc(String name, String pass, int role){
        try (DBController dbc = new DBController()) {
            int myrole = 1;
            if(role == 1){
                myrole = 0;
            }
            if(name.isEmpty() || pass.isEmpty()){
                addErrorMessage.setText("Please add username and password");
            }else{  
                dbc.insertUser(name, pass, myrole);
            }
        }catch(SQLException e){
            addErrorMessage.setText("failed to add data from database");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        b1 = new Frontend.components.MyDashboard();
        b2 = new Frontend.components.MyDashboard();
        b3 = new Frontend.components.MyDashboard();
        b4 = new Frontend.components.MyDashboard();
        b5 = new Frontend.components.MyDashboard();
        b6 = new Frontend.components.MyDashboard();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        SchoolChart = new javax.swing.JPanel();
        schoolCharts = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        TotalDonation = new javax.swing.JLabel();
        CategoryChart = new javax.swing.JPanel();
        pieChart = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        recentPanel = new javax.swing.JScrollPane(recents);
        StationsChart = new javax.swing.JPanel();
        stationChart = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane(donationContent);
        jPanel5 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        filterField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        optionFilter = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        errorExport = new javax.swing.JLabel();
        myButton2 = new Frontend.components.MyButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        EditAdmin = new Frontend.components.MyButton();
        myButton3 = new Frontend.components.MyButton();
        AdminName = new javax.swing.JTextField();
        adminPassLabel = new javax.swing.JLabel();
        SaveAdmin = new Frontend.components.MyButton();
        jLabel7 = new javax.swing.JLabel();
        AdminPass = new javax.swing.JTextField();
        AdminError = new javax.swing.JLabel();
        success = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane(mainScreen);
        jPanel7 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        addUserName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        addPassword = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        myButton1 = new Frontend.components.MyButton();
        dropDownRole = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        addErrorMessage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin");

        jPanel1.setBackground(new java.awt.Color(28, 28, 34));

        jPanel2.setBackground(new java.awt.Color(50, 62, 143));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Frontend/resources/Group 17.png"))); // NOI18N

        b1.setForeground(new java.awt.Color(255, 255, 255));
        b1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Frontend/resources/dashboard.png"))); // NOI18N
        b1.setText("Dashboard");
        b1.setBase(new java.awt.Color(102, 102, 255));
        b1.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        b1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        b1.setHover(new Color(0x6666FF));
        b1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ActionPerformed(evt);
            }
        });

        b2.setForeground(new java.awt.Color(255, 255, 255));
        b2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Frontend/resources/gift.png"))); // NOI18N
        b2.setText("View Donations");
        b2.setBase(new java.awt.Color(50, 62, 143));
        b2.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        b2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        b2.setHover(new Color(0x6666FF));
        b2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b2ActionPerformed(evt);
            }
        });

        b3.setForeground(new java.awt.Color(255, 255, 255));
        b3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Frontend/resources/export.png"))); // NOI18N
        b3.setText("Export");
        b3.setBase(new java.awt.Color(50, 62, 143));
        b3.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        b3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        b3.setHover(new Color(0x6666FF));
        b3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b3ActionPerformed(evt);
            }
        });

        b4.setForeground(new java.awt.Color(255, 255, 255));
        b4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Frontend/resources/acc.png"))); // NOI18N
        b4.setText("Profile");
        b4.setBase(new java.awt.Color(50, 62, 143));
        b4.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        b4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        b4.setHover(new Color(0x6666FF));
        b4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b4ActionPerformed(evt);
            }
        });

        b5.setForeground(new java.awt.Color(255, 255, 255));
        b5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Frontend/resources/view list.png"))); // NOI18N
        b5.setText("View Station Accounts");
        b5.setBase(new java.awt.Color(50, 62, 143));
        b5.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        b5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        b5.setHover(new Color(0x6666FF));
        b5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b5ActionPerformed(evt);
            }
        });

        b6.setForeground(new java.awt.Color(255, 255, 255));
        b6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Frontend/resources/edit.png"))); // NOI18N
        b6.setText("Add Station Account");
        b6.setBase(new java.awt.Color(50, 62, 143));
        b6.setFont(new java.awt.Font("Segoe UI", 3, 20)); // NOI18N
        b6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        b6.setHover(new Color(0x6666FF));
        b6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b6ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Home");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Account");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator2)
                    .addComponent(b1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(b1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(b2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(b3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(b4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(b5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(b6, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected int calculateTabAreaHeight(int tabPlacement, int runCount, int maxTabHeight) {
                return 0; // Hide the tab headers
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        SchoolChart.setBackground(new java.awt.Color(255, 255, 255));
        SchoolChart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        SchoolChart.setPreferredSize(new java.awt.Dimension(702, 2));

        schoolCharts.setBackground(new java.awt.Color(255, 255, 255));
        schoolCharts.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout SchoolChartLayout = new javax.swing.GroupLayout(SchoolChart);
        SchoolChart.setLayout(SchoolChartLayout);
        SchoolChartLayout.setHorizontalGroup(
            SchoolChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(schoolCharts, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        SchoolChartLayout.setVerticalGroup(
            SchoolChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(schoolCharts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel15.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Total Donations:");

        TotalDonation.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        TotalDonation.setForeground(new java.awt.Color(0, 0, 0));
        TotalDonation.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TotalDonation.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TotalDonation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(TotalDonation, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        CategoryChart.setBackground(new java.awt.Color(64, 64, 64));
        CategoryChart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        pieChart.setBackground(new java.awt.Color(255, 255, 255));
        pieChart.setMaximumSize(new java.awt.Dimension(304, 202));
        pieChart.setMinimumSize(new java.awt.Dimension(304, 202));
        pieChart.setPreferredSize(new java.awt.Dimension(304, 202));
        pieChart.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout CategoryChartLayout = new javax.swing.GroupLayout(CategoryChart);
        CategoryChart.setLayout(CategoryChartLayout);
        CategoryChartLayout.setHorizontalGroup(
            CategoryChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pieChart, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
        );
        CategoryChartLayout.setVerticalGroup(
            CategoryChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pieChart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        jLabel16.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText("Recent Donations:");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
            .addComponent(recentPanel)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recentPanel))
        );

        StationsChart.setBackground(new java.awt.Color(64, 64, 64));
        StationsChart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        stationChart.setBackground(new java.awt.Color(255, 255, 255));
        stationChart.setMaximumSize(new java.awt.Dimension(663, 307));
        stationChart.setMinimumSize(new java.awt.Dimension(663, 307));
        stationChart.setPreferredSize(new java.awt.Dimension(663, 307));
        stationChart.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout StationsChartLayout = new javax.swing.GroupLayout(StationsChart);
        StationsChart.setLayout(StationsChartLayout);
        StationsChartLayout.setHorizontalGroup(
            StationsChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stationChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        StationsChartLayout.setVerticalGroup(
            StationsChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(stationChart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(273, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(StationsChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(SchoolChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CategoryChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(273, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(CategoryChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(SchoolChart, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(StationsChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Dashboard", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setMaximumSize(new java.awt.Dimension(1006, 680));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(1006, 680));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(1006, 680));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(305, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1027, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(290, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("View Donations", jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        filterField.setBackground(new java.awt.Color(255, 255, 255));
        filterField.setForeground(new java.awt.Color(0, 0, 0));
        filterField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(127, 127, 127)));
        filterField.setMaximumSize(new java.awt.Dimension(270, 22));
        filterField.setMinimumSize(new java.awt.Dimension(270, 22));
        filterField.setPreferredSize(new java.awt.Dimension(270, 22));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Filter Donations by ");

        optionFilter.setForeground(new java.awt.Color(255, 255, 255));
        optionFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Section", "Category", "School", "Program" }));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Keyword ");

        errorExport.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        errorExport.setForeground(new java.awt.Color(255, 102, 102));

        myButton2.setForeground(new java.awt.Color(255, 255, 255));
        myButton2.setText("Export to PDF");
        myButton2.setBase(new java.awt.Color(50, 62, 143));
        myButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        myButton2.setHover(new java.awt.Color(88, 102, 196));
        myButton2.setRadius(16);
        myButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(optionFilter, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(filterField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(errorExport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(myButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(optionFilter))
                .addGap(12, 12, 12)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filterField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(errorExport, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(myButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(368, 368, 368)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(772, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(303, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(367, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Export", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        EditAdmin.setBackground(new java.awt.Color(50, 62, 143));
        EditAdmin.setForeground(new java.awt.Color(255, 255, 255));
        EditAdmin.setText("Edit Username and Password");
        EditAdmin.setBase(new java.awt.Color(50, 62, 143));
        EditAdmin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        EditAdmin.setHover(new java.awt.Color(81, 95, 194));
        EditAdmin.setRadius(12);
        EditAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditAdminActionPerformed(evt);
            }
        });

        myButton3.setBackground(new java.awt.Color(0, 0, 0));
        myButton3.setForeground(new java.awt.Color(255, 255, 255));
        myButton3.setText("Logout");
        myButton3.setBase(new java.awt.Color(0, 0, 0));
        myButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        myButton3.setHover(new java.awt.Color(102, 102, 102));
        myButton3.setRadius(12);
        myButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton3ActionPerformed(evt);
            }
        });

        AdminName.setEditable(false);
        Account.getCreds();
        AdminName.setText(Account.getName());
        AdminName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminNameActionPerformed(evt);
            }
        });

        adminPassLabel.setForeground(new java.awt.Color(0, 0, 0));
        adminPassLabel.setText("New Password:");
        adminPassLabel.setVisible(false);

        SaveAdmin.setBackground(new java.awt.Color(50, 62, 143));
        SaveAdmin.setForeground(new java.awt.Color(255, 255, 255));
        SaveAdmin.setText("Save Changes");
        SaveAdmin.setBase(new java.awt.Color(50, 62, 143));
        SaveAdmin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        SaveAdmin.setHover(new java.awt.Color(81, 95, 194));
        SaveAdmin.setRadius(12);
        SaveAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveAdminActionPerformed(evt);
            }
        });
        SaveAdmin.setVisible(false);

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Admin Name: ");

        AdminPass.setVisible(false);
        AdminPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AdminPassActionPerformed(evt);
            }
        });

        AdminError.setForeground(new java.awt.Color(255, 51, 51));

        success.setForeground(new java.awt.Color(51, 255, 51));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(SaveAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(adminPassLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(AdminName, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(AdminPass, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(EditAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(myButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(AdminError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(success, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AdminName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adminPassLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AdminPass, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AdminError, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(success, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(EditAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(SaveAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(myButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(694, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(581, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(257, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(200, 200, 200))
        );

        jTabbedPane1.addTab("Profile", jPanel6);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(418, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(404, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(114, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("View Accounts", jPanel8);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Add Station Accounts");

        addUserName.setBackground(new java.awt.Color(255, 255, 255));
        addUserName.setForeground(new java.awt.Color(0, 0, 0));
        addUserName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(127, 127, 127)));

        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Username");

        addPassword.setBackground(new java.awt.Color(255, 255, 255));
        addPassword.setForeground(new java.awt.Color(0, 0, 0));
        addPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(127, 127, 127)));

        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Password");

        myButton1.setForeground(new java.awt.Color(255, 255, 255));
        myButton1.setText("Add Account");
        myButton1.setBase(new java.awt.Color(50, 62, 143));
        myButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        myButton1.setHover(new java.awt.Color(88, 102, 196));
        myButton1.setRadius(16);
        myButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myButton1ActionPerformed(evt);
            }
        });

        dropDownRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User", "Admin" }));

        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Role");

        addErrorMessage.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addErrorMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addUserName)
                    .addComponent(addPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(myButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dropDownRole, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(addUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(addPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel14)
                .addGap(3, 3, 3)
                .addComponent(dropDownRole, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(addErrorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(myButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(669, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(642, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(249, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(237, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Add Account", jPanel7);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jTabbedPane1)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b1ActionPerformed
        resetSelected();
        b1.setBase(selectedColor);
        jTabbedPane1.setSelectedIndex(0);
        showCharts();
    }//GEN-LAST:event_b1ActionPerformed

    private void b2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b2ActionPerformed
        showDonates();
        resetSelected();
        b2.setBase(selectedColor);
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_b2ActionPerformed

    private void b3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b3ActionPerformed
        resetSelected();
        b3.setBase(selectedColor);
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_b3ActionPerformed

    private void b4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b4ActionPerformed
        resetSelected();
        b4.setBase(selectedColor);
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_b4ActionPerformed

    private void b5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b5ActionPerformed
        showAccounts();
        resetSelected();
        b5.setBase(selectedColor);
        jTabbedPane1.setSelectedIndex(4);
    }//GEN-LAST:event_b5ActionPerformed

    private void b6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b6ActionPerformed
        resetSelected();
        b6.setBase(selectedColor);
        jTabbedPane1.setSelectedIndex(5);
    }//GEN-LAST:event_b6ActionPerformed

    private void myButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton2ActionPerformed
        String option = String.valueOf(optionFilter.getSelectedItem()).toLowerCase().trim();
        String filter = filterField.getText().toLowerCase().trim();

        if (!filter.isEmpty()) {
           try (DBController db = new DBController()) {
               ArrayList<ArrayList<String>> donations = db.getFilteredDonations(option, filter);

               if (donations.isEmpty()) {
                   errorExport.setText("No results found for this filter.");
               } else {
                   PdfExporter exporter = new PdfExporter();
                   exporter.exportToPDF(null, donations, "Donations - " + option);
                   errorExport.setText("");
               }
           } catch (Exception e) {
               e.printStackTrace(); 
               errorExport.setText("Failed to connect to the database or export PDF.");
           }
       } else {
           errorExport.setText("Please enter a keyword to filter.");
       }

    }//GEN-LAST:event_myButton2ActionPerformed

    private void myButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton1ActionPerformed
        String name = addUserName.getText();
        String pass = addPassword.getText();
        int role = dropDownRole.getSelectedIndex();
        addErrorMessage.setText("");
        addAcc(name, pass, role);
        addUserName.setText("");
        addPassword.setText("");
    }//GEN-LAST:event_myButton1ActionPerformed

    private void SaveAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveAdminActionPerformed
        AdminError.setText("");
        success.setText("");
        AdminName.setEditable(false);
        adminPassLabel.setVisible(false);
        AdminPass.setVisible(false);
        String name = Account.getName();
        String newPass = AdminPass.getText();
        String newName = AdminName.getText();
        try (DBController dbc = new DBController()) {
            dbc.updatePass(name, newPass, newName);
            success.setText("Changed successfully");
        } catch (SQLException ex) {
            AdminError.setText("Failed to connect with the database");
        }
        SaveAdmin.setVisible(false);
    }//GEN-LAST:event_SaveAdminActionPerformed

    private void EditAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditAdminActionPerformed
        success.setText("");
        AdminError.setText("");
        AdminName.setEditable(true);
        adminPassLabel.setVisible(true);
        AdminPass.setVisible(true);
        SaveAdmin.setVisible(true);
        
    }//GEN-LAST:event_EditAdminActionPerformed

    private void AdminPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdminPassActionPerformed

    private void myButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myButton3ActionPerformed
        Account.logoutSaved();
        this.dispose();
        new GUI().setVisible(true);
    }//GEN-LAST:event_myButton3ActionPerformed

    private void AdminNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AdminNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AdminNameActionPerformed
    //ADMIN PAGE
    public void resetSelected(){
        b1.setBase(whiteColor);
        b2.setBase(whiteColor);
        b3.setBase(whiteColor);
        b4.setBase(whiteColor);
        b5.setBase(whiteColor);
        b6.setBase(whiteColor);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AdminError;
    private javax.swing.JTextField AdminName;
    private javax.swing.JTextField AdminPass;
    private javax.swing.JPanel CategoryChart;
    private Frontend.components.MyButton EditAdmin;
    private Frontend.components.MyButton SaveAdmin;
    private javax.swing.JPanel SchoolChart;
    private javax.swing.JPanel StationsChart;
    private javax.swing.JLabel TotalDonation;
    private javax.swing.JLabel addErrorMessage;
    private javax.swing.JTextField addPassword;
    private javax.swing.JTextField addUserName;
    private javax.swing.JLabel adminPassLabel;
    private Frontend.components.MyDashboard b1;
    private Frontend.components.MyDashboard b2;
    private Frontend.components.MyDashboard b3;
    private Frontend.components.MyDashboard b4;
    private Frontend.components.MyDashboard b5;
    private Frontend.components.MyDashboard b6;
    private javax.swing.JComboBox<String> dropDownRole;
    private javax.swing.JLabel errorExport;
    private javax.swing.JTextField filterField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private Frontend.components.MyButton myButton1;
    private Frontend.components.MyButton myButton2;
    private Frontend.components.MyButton myButton3;
    private javax.swing.JComboBox<String> optionFilter;
    private javax.swing.JPanel pieChart;
    private javax.swing.JScrollPane recentPanel;
    private javax.swing.JPanel schoolCharts;
    private javax.swing.JPanel stationChart;
    private javax.swing.JLabel success;
    // End of variables declaration//GEN-END:variables
}
