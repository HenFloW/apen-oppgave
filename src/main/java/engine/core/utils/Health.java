package engine.core.utils;

import engine.core.math.Size;
import engine.game.GameState;

import java.awt.*;

public class Health {
    private int health, maxHealth;
    private Size offsets, size;
    private int regenSpeed, regenAmount;
    private double damageTime;

    public Health(int hp) {
        this.maxHealth = hp;
        this.health = hp;
        this.regenSpeed = 3;
        this.regenAmount = 5;
        this.size = new Size(100,5);
        this.offsets = new Size(0,0);
    }

    public void damage(int hp){
        this.damageTime = System.currentTimeMillis();
        health -= hp;
    }

    public int hp() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setOffsets(Size offsets) {
        this.offsets = offsets;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setRegenSpeed(int regenSpeed) {
        this.regenSpeed = regenSpeed;
    }

    public void setRegenAmount(int i) {
        this.regenAmount = i;
    }
    public void render(Graphics g){
        if(health < maxHealth){
            g.setColor(Color.red);
            g.drawRect(offsets.getWidth(), offsets.getHeight(), size.getWidth(), size.getHeight()-1);
            g.setColor(Color.green);
            g.fillRect(offsets.getWidth(), offsets.getHeight(), Math.max((int)(((double)size.getWidth()/(double)maxHealth)*health), 0), size.getHeight());
        }
    }
    public void update(GameState state){
        if(regenSpeed*1000d <= state.currentTime()-damageTime && health < maxHealth){
            health = Math.min(health+regenAmount, maxHealth);
            damageTime = state.currentTime();
        }
    }

}
