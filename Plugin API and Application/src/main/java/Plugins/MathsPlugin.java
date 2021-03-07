package Plugins;

import API.API;
import API.Plugin;
//Proof of concept plugin that user can add specific functions with a name. Not mathematically correct.
public class MathsPlugin implements Plugin
{

    //Very basically adds these hardcoded functions because it is a plugin
    @Override
    public void start(API api)
    {
        api.addExpressionByName("x*(x - 1)","factorial");
        //I AM NOT SURE OF THE MATHS BEHIND FIBONACCI
        api.addExpressionByName("x + x + x","fibonacci");
    }
}
