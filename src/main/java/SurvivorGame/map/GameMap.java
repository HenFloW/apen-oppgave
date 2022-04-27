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

    @Override
    public boolean isOnGrid(GameObject object) {
        return object.getPosition().getX() + object.getSprite().getWidth() < getWidth()
                && object.getPosition().getY() + object.getSprite().getHeight() < getHeight();
    }

    public int getTileSize() {
        return this.objectType.getSprite().getWidth();
    }
}
