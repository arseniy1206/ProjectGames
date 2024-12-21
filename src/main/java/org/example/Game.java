package org.example;
import com.opencsv.bean.CsvBindByName;

public class Game {
    @CsvBindByName(column = "Rank")
    private int rank;

    @CsvBindByName(column = "Name")
    private String name;

    @CsvBindByName(column = "Platform")
    private String platform;

    @CsvBindByName(column = "Year")
    private String year;

    @CsvBindByName(column = "Genre")
    private String genre;

    @CsvBindByName(column = "Publisher")
    private String publisher;

    @CsvBindByName(column = "NA_Sales")
    private double naSales;

    @CsvBindByName(column = "EU_Sales")
    private double euSales;

    @CsvBindByName(column = "JP_Sales")
    private double jpSales;

    @CsvBindByName(column = "Other_Sales")
    private double otherSales;

    @CsvBindByName(column = "Global_Sales")
    private double globalSales;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getNaSales() {
        return naSales;
    }

    public void setNaSales(double naSales) {
        this.naSales = naSales;
    }

    public double getEuSales() {
        return euSales;
    }

    public void setEuSales(double euSales) {
        this.euSales = euSales;
    }

    public double getJpSales() {
        return jpSales;
    }

    public void setJpSales(double jpSales) {
        this.jpSales = jpSales;
    }

    public double getOtherSales() {
        return otherSales;
    }

    public void setOtherSales(double otherSales) {
        this.otherSales = otherSales;
    }

    public double getGlobalSales() {
        return globalSales;
    }

    public void setGlobalSales(double globalSales) {
        this.globalSales = globalSales;
    }

    @Override
    public String toString() {
        return "Игра {" +
                "Рейтинг: " + rank +
                ", Название: '" + name + '\'' +
                ", Платформа: '" + platform + '\'' +
                ", Год: '" + year + '\'' +
                ", Жанр: '" + genre + '\'' +
                ", Издатель: '" + publisher + '\'' +
                ", Продажи в Северной Америке: " + naSales +
                ", Продажи в Европе: " + euSales +
                ", Продажи в Японии: " + jpSales +
                ", Продажи в других регионах: " + otherSales +
                ", Мировые продажи: " + globalSales +
                '}';
    }
}

