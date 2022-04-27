package engine.display;

import engine.core.math.Position;
import engine.core.math.Size;
import SurvivorGame.map.GameMap;
import engine.game.GameState;
import engine.objects.GameObject;

import java.awt.*;
import java.util.Optional;

public class Camera {
    private Position position;
    private Rectangle viewBound;
    private Optional<GameObject> objectInFocus;

    public Camera(Size cameraSize){
        this.position = new Position(0,0);
        this.objectInFocus = Optional.empty();
        getViewBounds(cameraSize.getWidth(), cameraSize.getHeight());
    }

    public void setFocus(GameObject object){
        this.objectInFocus = Optional.of(object);
    }

    public void update(GameState state){
        if(objectInFocus.isPresent()){
            Position objectPosition = objectInFocus.get().getPosition();

            this.position = new Position(
                    objectPosition.intX() - viewBound.getWidth()/2 + (int)(objectInFocus.get().getSprite().getWidth()/2),
                    objectPosition.intY() - viewBound.getHeight()/2 + (int)(objectInFocus.get().getSprite().getHeight()/2));

            bounderies(state);
            getViewBounds(viewBound.width, viewBound.height);
        }
    }

    private void getViewBounds(int x, int y) {
        this.viewBound = new Rectangle(position.intX(), position.intY(), x, y);
    }

    private void bounderies(GameState state) {
        GameMap gameMap = state.getGameMap();
        if(position.getX() < 0){
            position.setX(0);
        }
        if(position.getX() + viewBound.getWidth() >= gameMap.getWidth()){
            position.setX(gameMap.getWidth() -  viewBound.width);
        }
        if(position.getY() < 0){
            position.setY(0);
        }
        if(position.getY() + viewBound.getHeight() >= gameMap.getHeight()){
            position.setY(gameMap.getHeight() - viewBound.height);
        }

    }

    public Position getPosition() {
        return position;
    }

    public Rectangle getViewBound() {
        return viewBound;
    }

    public boolean isInView(GameObject gameObject) {
        if(gameObject.getSprite() != null) {
            return viewBound.intersects(
                    gameObject.getPosition().intX(),
                    gameObject.getPosition().intY(),
                    gameObject.getSprite().getWidth(),
                    gameObject.getSprite().getHeight());
        }
        return false;
    }
}
