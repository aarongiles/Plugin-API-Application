package Plugins;

import API.API;
import API.Plugin;
import API.APIObserver;

//This class is a plugin that will display progress of calculation on screen.
public class DisplayPlugin implements Plugin
{
    private double minX;
    private double maxX;
    private double incX;
    double perIncreasePercent;
    double currentX;
    int count;
    int total;

    //Provides the callback code to provide after every evaluate.
    //Not really consistent only with big numbers.
    private class Progress implements APIObserver
    {
        @Override
        public void callback()
        {
            currentX += perIncreasePercent;
            if (count%10 ==0)
                System.out.println("progress:" + (int) currentX + "%");

            count++;
        }
    }



    //Runs and intliases all variables to avoid inefficent calculation later and not interacting with api during callback
    @Override
    public void start(API api)
    {
        System.out.println("Progress will only be displayed every 10 calculations, so please use big number");
        Progress progress = new Progress();
        minX = api.getMinX();
        maxX = api.getMaxX();
        incX = api.getIncX();
        perIncreasePercent = 100 / ((maxX - minX)/incX);
        currentX=0;
        api.doAfterEvaluate(progress);
    }


}
