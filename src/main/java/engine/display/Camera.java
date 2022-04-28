package engine.display;

import SurvivorGame.state.PlayState;
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

            boundaries(state); //This is to lock the view within the map boundaries
            getViewBounds(viewBound.width, viewBound.height);   //This is used to set a rectangle in the position of the camera
                                                                // Useful if we will check if an object is in view of the camera
        }
    }

    private void getViewBounds(int x, int y) {
        this.viewBound = new Rectangle(position.intX(), position.intY(), x, y);
    }

    private void boundaries(GameState state) {
        if(state instanceof PlayState playState){
        GameMap gameMap = playState.getGameMap();
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

    }

    public Position getPosition() {
        return position;
    }

    public Rectangle getViewBound() {
        return viewBound;
    }

    public boolean isInView(GameObject gameObject) { //This function uses view bounds and check if the sprite of the object is in view of the camera
        if(gameObject.getSprite() != null) {         // This is for optimizing the rendering methods to only render objects in view
            return viewBound.intersects(
                    gameObject.getPosition().intX(),
                    gameObject.getPosition().intY(),
                    gameObject.getSprite().getWidth(),
                    gameObject.getSprite().getHeight());
        }
        return false;
    }
}
