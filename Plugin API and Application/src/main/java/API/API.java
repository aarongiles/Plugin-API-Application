package API;
//The API that the main app extends from
public interface API
{

    //Allow for data to be retrieved.
     double getMinX();
     double getMaxX();
     double getIncX();
     String getEquation();


     //Just incase for whatever reason they want to change these values
     //with a custom plugin
     void setMinX(double minX);

     void setMaxX(double maxX);

     void setIncX(double incX);

     void setEquation(String equation);
     //This allows to run a callback function(that extends APIObserver) after evaluate in the app is ran
     void doAfterEvaluate(APIObserver os);

     //Retrives most recent y result value calculated
     double getRecentResult();

     //Gives the user ability to add a expressions by name
     //eg "factorial" whenever enterted by user could be set to an equation equivelent
     void addExpressionByName(String expression, String name);
     //Standard way of outputting things to screen, weather that be through a log or something
     void output(String output);
}

