package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Graph extends JFrame {

    public Graph(Map<String, Double> averageSalesByPlatform) {
        init(averageSalesByPlatform);
    }

    private void init(Map<String, Double> averageSalesByPlatform) {
        CategoryDataset dataset = createDataset(averageSalesByPlatform);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        int DEFAULT_PADDING = 15;
        chartPanel.setBorder(BorderFactory.createEmptyBorder(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING));
        chartPanel.setBackground(Color.WHITE);
        add(chartPanel);
        pack();
        setTitle("Средние глобальные продажи по платформам");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
                "Средние глобальные продажи по платформам",
                "Платформа",
                "Средние продажи (млн)",
                dataset
        );
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis xAxis = plot.getDomainAxis();
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        return chart;
    }


    private CategoryDataset createDataset(Map<String, Double> averageSalesByPlatform) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        averageSalesByPlatform.forEach((platform, avgSales) -> dataset.setValue(avgSales, "Global Sales", platform));
        return dataset;
    }
}

