//Increase the score every second by 10
public class IncreaseScore implements Runnable
{

    private int score=0;
    private boolean running = true;

    @Override
    public void run()
    {

            while (running)
            {
                try
                {
                    score += 10;
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    running = false;
                    System.out.println("Score Thread ended");
                }
            }

    }

    public int getScore()
    {
        return score;
    }
}
