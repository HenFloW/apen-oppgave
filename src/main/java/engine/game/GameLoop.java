package engine.game;

public class GameLoop implements Runnable{
    private IGame game;

    private boolean running;
    public final double updatesPerSec = 1.0d / 60.0d;

    private int fps, ups;
    private long nextStatTime;

    public GameLoop(IGame game){
        this.game = game;
    }

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
            if(timeAccumulator >= updatesPerSec){
                while(timeAccumulator >= updatesPerSec){
                    update();
                    timeAccumulator -= updatesPerSec;
                    printStats();
                }
            }
            render();
            lastUpdate = currentTime;
        }
    }

    private void printStats() {
        if(System.currentTimeMillis() >= nextStatTime){
            System.out.println(String.format(
                    "FPS: %d, UPS: %d",
                    fps,
                    ups));
            fps = 0;
            ups = 0;
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
        ups++;
    }
}
