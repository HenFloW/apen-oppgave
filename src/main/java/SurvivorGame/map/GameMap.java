package SurvivorGame.map;

import SurvivorGame.objects.entities.zombie.Zombie;
import engine.core.math.Position;
import engine.core.math.Size;
import engine.core.utils.Grid;
import engine.game.GameState;
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

    public Position getRandomPosition(GameObject gameObject){
        double x = Math.random()*getWidth();
        double y = Math.random()*getHeight();

        return new Position(
                x+gameObject.getSize().getWidth() < getWidth() ? x : x-gameObject.getSize().getWidth(),
                y+gameObject.getSize().getHeight() < getHeight() ? y : y-gameObject.getSize().getHeight());
    }
    public int getTileSize() {
        return this.objectType.getSprite().getWidth();
    }
}
