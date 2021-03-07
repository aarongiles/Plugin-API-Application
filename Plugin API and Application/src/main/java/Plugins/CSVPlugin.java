package Plugins;

import API.API;
import API.Plugin;
import API.APIObserver;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//This plugin is will write the calculated data to a csv file, demonstration of a user implemented plugin
public class CSVPlugin implements Plugin
{
    private static final String CSV_FILE = "data.csv";

    private CSVPrinter csvPrinter;
    private API api;
    private double valueX,incX;


    //Currently prints to the csv file and closes for each line.
    private class CSV implements APIObserver
    {
        @Override
        public void callback()
        {
            try
            {
                csvPrinter.printRecord(valueX ,api.getRecentResult());
                csvPrinter.flush();
            }
            catch (IOException e)
            {
                System.out.println("Something went wrong");
            }

            valueX +=incX;
        }
    }



    @Override
    public void start(API api)
    {
        this.api = api;
        valueX = api.getMinX();
        incX = api.getIncX();
        try
        {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE));
            csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("X","Y"));
            csvPrinter.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        CSV csv = new CSV();
        api.doAfterEvaluate(csv);

    }
}
