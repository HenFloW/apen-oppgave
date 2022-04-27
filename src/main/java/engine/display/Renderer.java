package engine.display;

import engine.controller.DebugController;
import engine.core.math.Position;
import SurvivorGame.map.GameMap;
import engine.game.GameState;
import engine.objects.GameObject;
import engine.objects.Item;
import engine.objects.moving.MovingEntity;
import SurvivorGame.objects.entities.Rock;
import SurvivorGame.objects.entities.TreeEntity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;

public class Renderer {
    private final int VIEW_BOUND_PERIMETER = 1;
    private DebugController debugController;

    public Renderer(DisplayWindow displayWindow) {
        this.debugController = displayWindow.debugController();
    }

    public void render(GameState state, Graphics graphics){
        debugController.update();

        renderMap(state, graphics);
        renderObjects(state, graphics);

        if(debugController.renderLines){
            renderLines(state, graphics);
        }
        if(debugController.renderColliders){
            renderBoxColliders(state, graphics);
        }
        if(debugController.renderObjectPoint) {
            renderPositions(state, graphics);
        }

    }

    private void renderLines(GameState state, Graphics graphics) {
        Camera cam = state.getCamera();
        for(var obj : state.getGameObjects().stream()
                .filter(cam::isInView)
                .filter(gameObject -> gameObject instanceof MovingEntity)
                .toArray()) {

            graphics.setColor(Color.RED);
            GameObject thisObject = (GameObject) obj;

            state.getGameObjects().stream()
                    .filter(cam::isInView)
                    .filter(gameObject -> !(gameObject.equals(obj))).forEach(gameObject -> {
                        if(gameObject instanceof Rock){
                            graphics.setColor(Color.ORANGE);
                        }
                        else if(gameObject instanceof TreeEntity){
                            graphics.setColor(Color.GREEN);
                        } else {
                            graphics.setColor(Color.RED);
                        }
                        graphics.drawLine(
                                gameObject.getObjectPoint().intX() - cam.getPosition().intX(),
                                gameObject.getObjectPoint().intY() - cam.getPosition().intY(),
                                thisObject.getObjectPoint().intX() - cam.getPosition().intX(),
                                thisObject.getObjectPoint().intY() - cam.getPosition().intY());
                    });
            }
    }

    private void renderPositions(GameState state, Graphics graphics) {
        Camera cam = state.getCamera();
        graphics.setColor(Color.RED);
        sortByPosition(state);
        state.getGameObjects().stream()
                .filter(cam::isInView)
                .forEach(gameObject ->
                                graphics.drawOval(
                                        gameObject.getObjectPoint().intX() - cam.getPosition().intX()-1,
                                        gameObject.getObjectPoint().intY() - cam.getPosition().intY()-1,
                                        3,
                                        3));
    }

    private void renderBoxColliders(GameState state, Graphics graphics) {
        Camera cam = state.getCamera();
        for(var obj : state.getGameObjects().stream()
                .filter(cam::isInView)
                .toArray() ) {

            graphics.setColor(Color.RED);

            for (var other : state.getGameObjects().stream()
                    .filter(cam::isInView)
                    .filter(gameObject -> !gameObject.equals(obj))
                    .filter(gameObject -> gameObject instanceof MovingEntity)
                    .toArray()) {
                if (((GameObject) other).isColliding((GameObject) obj) && !(((GameObject) obj) instanceof Item)) {
                    graphics.setColor(Color.green);
                }
            }

            GameObject gameObject = ((GameObject) obj);


            graphics.drawRect(
                    gameObject.getCollider().x - cam.getPosition().intX(),
                    gameObject.getCollider().y - cam.getPosition().intY(),
                    gameObject.getCollider().width,
                    gameObject.getCollider().height);
        }
    }

    private void sortByPosition(GameState state) {
        state.getGameObjects()
                .sort(Comparator
                        .comparing(x -> ((GameObject) x).getObjectPoint().intY()));

    }

    private void renderObjects(GameState state, Graphics graphics){
        Camera cam = state.getCamera();
        sortByPosition(state);
        state.getGameObjects().stream().filter(cam::isInView).toList().forEach(obj -> {
            Graphics g = graphics.create(
                    obj.getPosition().intX() - cam.getPosition().intX(),
                    obj.getPosition().intY() - cam.getPosition().intY(),
                    obj.getSize().getWidth(),
                    obj.getSize().getHeight()
            );
            obj.render(g);
            g.dispose();
        });
    }

    private void renderMap(GameState state, Graphics graphics) {
        Camera cam = state.getCamera();
        GameMap gameMap = state.getGameMap();
        int camX = cam.getPosition().intX() / gameMap.getTileSize();
        int camY = cam.getPosition().intY() / gameMap.getTileSize();

        Position cameraStart = new Position(camX, camY);
        Position cameraEnd = new Position(
                camX + (int)(cam.getViewBound().getWidth() / gameMap.getTileSize()),
                camY + (int)(cam.getViewBound().getHeight() / gameMap.getTileSize()));

        for (int y = cameraStart.intY(); y < cameraEnd.getY()+VIEW_BOUND_PERIMETER; y++) {
            for (int x = cameraStart.intX(); x < cameraEnd.intX() + VIEW_BOUND_PERIMETER; x++) {
                if(x < gameMap.getCol() && y < gameMap.getRow())
                {
                    BufferedImage sprite = gameMap.get(x,y).getSprite();
                    graphics.drawImage(
                            sprite,
                            sprite.getWidth() * x - cam.getPosition().intX(),
                            sprite.getHeight() * y - cam.getPosition().intY(),
                            null
                    );
                }
            }
        }
    }
}
