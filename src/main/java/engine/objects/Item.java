package engine.objects;

import engine.core.utils.Dict;

import java.awt.image.BufferedImage;

public abstract class Item extends GameObject {
    protected BufferedImage sprite;
    protected Dict<String, Integer> stats;
    protected String description;

    public Item(){
        init();
    }

    protected void init() {
        this.sprite = null;
        this.stats = new Dict<>();
        this.description = null;
        this.collideable = false;
    }

    public abstract BufferedImage getSprite();

    public String getDescription() {
        return description;
    }

    public int getStat(String name) {
        return stats.key(name);
    }

    public void setStat(String name, int value){
        this.stats.put(name, value);
    }

}
