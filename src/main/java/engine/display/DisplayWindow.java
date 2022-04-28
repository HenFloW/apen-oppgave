package engine.display;

import engine.controller.DebugController;
import engine.controller.Input;
import engine.core.math.Size;
import engine.game.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class DisplayWindow extends JFrame {
    private Canvas canvas;
    private Renderer renderer;

    public final String windowName;
    private Size windowSize;

    private boolean isResizeable = false;
    private final int frameBuffer = 2;

    private DebugController debugController;

    public DisplayWindow(Size windowSize, String windowName, Input input){
        this.windowName = windowName;
        this.windowSize = windowSize;
        this.debugController = new DebugController(input);
        this.renderer = new Renderer(this);
        this.addKeyListener(input);
        setTitle(this.windowName);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(isResizeable);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(windowSize.getWidth(), windowSize.getHeight()));
        canvas.setFocusable(false);

        add(canvas);
        pack();

        canvas.createBufferStrategy(this.frameBuffer); //buffer-strategy is a way to prerender a frame so the game can seamlessly switch to the next frame

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public Size getWindowSize() {
        return windowSize;
    }

    /**
     * this function does all the rendering logic of a scene depending on the state it gets
     * it also call for the renderer render which is in charge drawing all the games objects
     * @param state takes in the game-state
     */
    public void render(GameState state) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        renderer.render(state, graphics);

        graphics.dispose();
        bufferStrategy.show();
    }

    public DebugController debugController() {
        return debugController;
    }
}
