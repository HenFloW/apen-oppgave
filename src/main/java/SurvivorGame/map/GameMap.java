package SurvivorGame.map;

import engine.core.math.Size;
import engine.core.utils.Grid;
import engine.gfx.ResourceLibrary;
import SurvivorGame.objects.tiles.Tile;
import engine.objects.GameObject;


public class GameMap extends Grid<Tile> {

    public GameMap(Size size, ResourceLibrary resourceLibrary) {
        super(size.getHeight(), size.getWidth(), new Tile(resourceLibrary));
    }

    public int getWidth(){
        return this.getCol()*this.objectType.getSprite().getWidth();
    }

    public int getHeight(){
        return this.getRow()*this.objectType.getSprite().getHeight();
    }

    public boolean isOnGrid(GameObject object) {
        double objectX = object.getPosition().getX();
        double objectY = object.getPosition().getY();

        double objectWidth = object.getSprite().getWidth();
        double objectHeight = object.getSprite().getHeight();

        return objectX + objectWidth <= getWidth()
                && objectX + objectHeight <= getHeight()
                && objectX >= 0 && objectY >= 0;
    }

    public int getTileSize() {
        return this.objectType.getSprite().getWidth();
    }
}
