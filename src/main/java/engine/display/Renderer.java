package engine.display;

import SurvivorGame.map.GameMap;
import SurvivorGame.objects.entities.nature.Rock;
import SurvivorGame.objects.entities.nature.TreeEntity;
import SurvivorGame.objects.entities.player.Player;
import SurvivorGame.state.PlayState;
import engine.core.math.Position;
import engine.game.GameState;
import engine.objects.GameObject;
import engine.objects.Item;
import engine.objects.moving.MovingEntity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Comparator;

public class Renderer {

    public Renderer() {}

    public void render(GameState state, Graphics graphics){
        if(state instanceof PlayState){
            renderMap(state, graphics);
            renderObjects(state, graphics);

            if(state.debugController.renderLines){
                renderLines(state, graphics);
            }
            if(state.debugController.renderColliders){
                renderBoxColliders(state, graphics);
            }
            if(state.debugController.renderObjectPoint) {
                renderPositions(state, graphics);
            }
        } else {
            state.render(graphics);
        }
    }

    private void renderLines(GameState state, Graphics graphics) {
        Camera cam = state.getCamera();
        for(var obj : state.getGameObjects().stream()
                .filter(cam::isInView)
                .filter(gameObject -> gameObject instanceof Player)
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
                .forEach(gameObject -> graphics.drawOval(
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

            if(obj instanceof GameObject gameObj)
                for (var other : state.getGameObjects().stream()
                        .filter(cam::isInView)
                        .filter(gameObject -> !gameObject.equals(obj))
                        .filter(gameObject -> gameObject instanceof MovingEntity).toArray()) {
                    if(other instanceof GameObject otherObject){
                        if (gameObj.isColliding(otherObject) && !(gameObj instanceof Item)) {
                            graphics.setColor(Color.green);
                        }
                    }

                    graphics.drawRect(
                            gameObj.getCollider().x - cam.getPosition().intX(),
                            gameObj.getCollider().y - cam.getPosition().intY(),
                            gameObj.getCollider().width,
                            gameObj.getCollider().height);
            }
        }
    }

    private void sortByPosition(GameState state) {
        state.getGameObjects()
                .sort(Comparator
                        .comparing(x -> x.getObjectPoint().intY()));

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

        int VIEW_BOUND_PERIMETER = 1;

        for (int y = cameraStart.intY(); y < cameraEnd.getY()+ VIEW_BOUND_PERIMETER; y++) {
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
