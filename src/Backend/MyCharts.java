/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author canti
 */
public class MyCharts {
    public ChartPanel myPieChart(String[][] mydbdata){
        String[][] datas = mydbdata;
        String[] category_names = new String[6];
        int[] category_nums = new int[6];
        for (int row = 0; row < datas.length; row++) {
            category_names[row] = datas[row][0];
            category_nums[row] = Integer.parseInt(datas[row][1]);
        }
        DefaultPieDataset pieData = new DefaultPieDataset( );
        for(int i = 0; i < category_names.length; i++){
            pieData.setValue( category_names[i] , category_nums[i] ); 
        }
        JFreeChart piechart = ChartFactory.createPieChart("Donation Categories Analytics", pieData, true,true,false);//explain
        piechart.setBackgroundPaint(Color.WHITE);
        Plot piePlot =(Plot) piechart.getPlot();
    
        piePlot.setBackgroundPaint(Color.WHITE);
        
        ChartPanel pieChartPanel = new ChartPanel(piechart);
        return pieChartPanel;
    }
    public ChartPanel myBarChart(String[] mydbdata){
        String[] datas = mydbdata;
        DefaultCategoryDataset barData = new DefaultCategoryDataset( );
        barData.addValue(Integer.valueOf(datas[0]), "Donations", "SHS"); 
        barData.addValue(Integer.valueOf(datas[1]), "Donations", "SECA"); 
        barData.addValue(Integer.valueOf(datas[2]), "Donations", "SBMA"); 
        barData.addValue(Integer.valueOf(datas[3]), "Donations", "SASE"); 
        JFreeChart chart = ChartFactory.createBarChart(
                "School Donations Analytics",
                "Schools",
                "Amount",
                barData,
                PlotOrientation.VERTICAL,
                false, 
                true,
                false
        );
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        
        ChartPanel barChartPanel = new ChartPanel(chart);
        return barChartPanel;
    }
    public ChartPanel myStationChart(ArrayList<ArrayList<String>> mydbdata) throws SQLException{
        ArrayList<ArrayList<String>> datas = mydbdata;
        DefaultCategoryDataset barData = new DefaultCategoryDataset( );
        for (int i = 0; i < datas.size(); i++) {
            barData.addValue(Integer.valueOf(datas.get(i).get(1)), "Donations", datas.get(i).get(0)); 
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Station Donations Analytics",
                "Stations",
                "Amount",
                barData,
                PlotOrientation.VERTICAL,
                false, 
                true,
                false
        );
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        
        ChartPanel barChartPanel = new ChartPanel(chart);
        return barChartPanel;
    }
}
