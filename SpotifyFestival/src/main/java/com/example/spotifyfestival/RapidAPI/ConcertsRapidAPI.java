package com.example.spotifyfestival.RapidAPI;

import javafx.scene.control.DatePicker;

import javax.print.attribute.HashPrintJobAttributeSet;
import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ConcertsRapidAPI {
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private int countNumberAttempts;
    private Date lastUpdatedDate;
    private final String filepath = "RapidAPICounter.txt";
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ConcertsRapidAPI(){
        checkIfFileExists();
    }

    private ConcertsRapidAPI(int count, Date date){
        this.countNumberAttempts = count;
        this.lastUpdatedDate = date;
    }

    private static ConcertsRapidAPI instance;

    public static ConcertsRapidAPI getInstance(){
        if(instance==null){

            instance = new ConcertsRapidAPI();
        }
        return instance;
    }

    public void writeCountAndDateToFile(int n, Date date){
        try {
            FileWriter fileWriter = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fileWriter);

            //Write the integer value to the file
            bw.write(String.valueOf(n));
            bw.newLine();

            //Format the Date as String
            String formattedDate = formatter.format(date);
            bw.write(formattedDate);

            //Close the BW and FW
            bw.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateFile(){
        try {
            FileWriter fileWriter = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fileWriter);

            //Write the integer value to the file
            bw.write(String.valueOf(countNumberAttempts));
            bw.newLine();

            //Format the Date as String
            String formattedDate = formatter.format(lastUpdatedDate);
            bw.write(formattedDate);

            //Close the BW and FW
            bw.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void checkIfFileExists(){
        File file =  new File(filepath);
        if(file.exists()){
            readFromFile();
        }else{
            writeCountAndDateToFile(0, new Date());
        }
    }
    private void readFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String count = br.readLine();
            String date = br.readLine();

            countNumberAttempts = Integer.parseInt(count);
            lastUpdatedDate = formatter.parse(date);

        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void incrementIntAndDate(){
        Date date = new Date();

        checkExecutionCondition(checkIf24hHavePassed(date));

        int n = countNumberAttempts;
        n++;

        writeCountAndDateToFile(n, date);
    }
    private boolean checkIf24hHavePassed(Date date){


        //calc the diff
        long timeDiff = date.getTime() - lastUpdatedDate.getTime();
        //convert to hours
        double diffInHours = (double) timeDiff/(1000*60*60);

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double formattedDiff = Double.parseDouble(decimalFormat.format(diffInHours));
        return formattedDiff > 24.00;
    }
    private void checkExecutionCondition(Boolean bool){
        if(countNumberAttempts >= 10){
            if(bool){
                countNumberAttempts = 0;
                updateFile();
            }else{
                System.out.println("You have x amount of hours to wait!");
            }
        }
    }


    public static void main(String[] args) {
        ConcertsRapidAPI concertsRapidAPI = ConcertsRapidAPI.getInstance();
        concertsRapidAPI.incrementIntAndDate();
        System.out.println();
        concertsRapidAPI.readFromFile();
    }


}
