package Application;

import API.API;
import API.APIObserver;
import API.APIEventSource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//This class is basically the functionality for all the API.
//With other various methods for calculating etc
public class Functionality implements API
{
    private double minX,maxX,incX;
    private String equation;
    private APIEventSource apiEventSource = new APIEventSource();
    private HashMap<String,String> savedExpressions = new HashMap<>();
    private List<String> expressionNames = new LinkedList<>();

    private double recentResult =0;


    @Override
    public double getMinX()
    {
        return minX;
    }

    @Override
    public double getMaxX()
    {
        return maxX;
    }

    @Override
    public double getIncX()
    {
        return incX;
    }

    @Override
    public String getEquation()
    {
        return equation;
    }

    //Adds a task to do after evaluate finishes
    @Override
    public void doAfterEvaluate(APIObserver os)
    {
        apiEventSource.addObserver(os);
    }

    //Will execute after evaluate finishes
    public void evalFin()
    {
        for(APIObserver observer: apiEventSource.getObeservers())
        {
            observer.callback();
        }
    }

    @Override
    public void setMinX(double minX) {
        this.minX = minX;
    }
    @Override
    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }
    @Override
    public void setIncX(double incX) {
        this.incX = incX;
    }

    @Override
    public void setEquation(String equation) {
        this.equation = equation;
    }

    //Public method for API users to get most recent value calculated
    @Override
    public double getRecentResult()
    {
        return recentResult;
    }

    //No need to be api because our application will only really want to update this value
    public void updateRecent(double recentResult)
    {
        this.recentResult = recentResult;
    }

    //Adds exppresions to hashmap which stores name and equation.
    @Override
    public void addExpressionByName(String expression, String name)
    {
        savedExpressions.put(name,expression);
        expressionNames.add(name);
    }

    //Checks experssion which is esstenial for the addExpressionByName function to work for public users in API.
    public String checkExpression(String expression)
    {
        String returning = expression;

        for (String name: expressionNames)
        {
            if (returning.contains(name))
            {
                returning = returning.replace(name,savedExpressions.get(name) +" ");
                break;
            }
        }

        return returning;
    }


    //Not really used but can be updaed and used by plugins for standard output whatever that may be
    //Simple proof of concept
    @Override
    public void output(String output) {
        System.out.println(output);
    }
}
