package Application;

import API.Plugin;
import org.python.core.PyFloat;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

//The main class which is responsible for starting program.
public class Evauluator
{

    private static List<String> pluginsList = new LinkedList<>();

    private static Functionality api = new Functionality();


    public static void main(String[] args)
    {

        System.out.println("type 'plugin pluginName' to add plugin\n" +
                "type 'list' to list all plugins loaded\n" +
                "type 'clear' to clear all plugins loaded\n" +
                "once all that is set up type 'start' to actually start");
        boolean started = false;
        String input = null;
        Scanner scanner = new Scanner(System.in);
        //Basic loop to keep going while entering input
        while (!started)
        {
            //Read all the plugins we got intstalled
            try {
                pluginsList.clear();
                BufferedReader bf  = new BufferedReader(new FileReader("plugins.txt"));
                String line = bf.readLine();
                while (line != null)
                {
                    pluginsList.add(line);
                    line = bf.readLine();
                }
                bf.close();
            }
            catch (IOException e)
            {
                System.out.println("Something went wrong with file");
            }


            input = scanner.nextLine();
                //if it is loading a plugin
                if (input.contains("plugin")) {
                    //pluginName 2nd half of "plugin name"
                    String pluginName = input.split(" ")[1];
                    try {
                        FileWriter myWriter = new FileWriter("plugins.txt", true);
                        myWriter.write(pluginName + '\n');
                        myWriter.close();
                        System.out.println("Added plugin");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (input.contains("list")) {
                    System.out.println("Current plugins loaded");
                    for (String plugin : pluginsList) {
                        System.out.println(plugin);
                    }
                }
                if (input.contains("clear")) {
                    System.out.println("Getting rid of all plugins loaded");
                    pluginsList.clear();
                    try {
                        FileWriter myWriter = new FileWriter("plugins.txt");
                        myWriter.write("");
                        myWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (input.contains("start"))
                {
                    started = true;
                }
        }


        System.out.println("Enter expression: ");
        String expression = scanner.nextLine();
        System.out.println("Enter min X: ");
        double minX = Double.parseDouble(scanner.nextLine());
        api.setMinX(minX);
        System.out.println("Enter max X: ");
        double maxX = Double.parseDouble(scanner.nextLine());
        api.setMaxX(maxX);
        System.out.println("Enter increment X: ");
        double incX = Double.parseDouble(scanner.nextLine());
        api.setIncX(incX);

        try
        {
            //Load and start all plugins
            for (String plugin: pluginsList)
            {
                Class<?> pluginClass = Class.forName("Plugins."+plugin);
                Plugin pluginObj = (Plugin) pluginClass.getConstructor().newInstance();
                pluginObj.start(api);
            }
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            System.out.println("Plugin not loaded properly");
        }
        catch (NoSuchMethodException e)
        {
            System.out.println("No plugin with that method name");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Plugin with that name not found");
        }


        //Check if any expression has been entered
        expression = api.checkExpression(expression);
        api.setEquation(expression);
        evaluate(expression,minX,maxX,incX);

    }




    public static void evaluate(String expression, double minX, double maxX, double incX)
    {

        double y = 0;
        PythonInterpreter interpreter = new PythonInterpreter();
        for (double x =minX; x<=maxX;x+=incX)
        {
            interpreter.set("x",x);
            y = ((PyFloat) interpreter.eval("float(" + expression + ")")).getValue(); //Calculate something
            api.evalFin();
            api.updateRecent(y);

        }

    }


}
