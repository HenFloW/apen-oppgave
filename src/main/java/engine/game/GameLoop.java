package engine.game;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

public class GameLoop implements Runnable{
    private IGame game; //the game loop takes inn
    private OperatingSystemMXBean osLogging;

    private boolean running;
    public final double updatesPerSec = 1.0d / 60.0d;

    private int fps;
    private long nextStatTime;

    /**
     * GameLoop will take in a game so the launcher can run a thread of a GameLoop
     * and the gameloop will store the game
     * @param game
     */
    public GameLoop(IGame game){
        this.game = game;
        this.osLogging =  ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    }

    /**
     * The run method is implemented in the runnable and this will be constantly ran in the thread
     * Everything that happens here is what makes the game run
     * I have implemented a timeAccumulator
     */
    @Override
    public void run() {
        running = true;
        double timeAccumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while(running){
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;
            timeAccumulator += lastRenderTimeInSeconds;

            while(timeAccumulator >= updatesPerSec){
                update();
                render();
                timeAccumulator -= updatesPerSec;
            }
            printStats();
            lastUpdate = currentTime;
        }
    }

    private void printStats() {
        DecimalFormat df = new DecimalFormat("0.00");
        if(System.currentTimeMillis() >= nextStatTime){
            System.out.println(String.format(
                    "FPS: %d, CPU: %s",
                    fps,
                    df.format(osLogging.getCpuLoad() * 100)+'%'));
            fps = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void render() {
        game.render();
        fps++;
    }

    private void update() {
        game.update();
        game.cleanup();
    }
}
