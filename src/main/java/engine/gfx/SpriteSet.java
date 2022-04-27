package engine.gfx;

import engine.core.utils.Dict;

import java.io.File;

public class SpriteSet {
    private Dict<String, ImageSheet> animationSheets;
    private final int DEFAULT_SPRITE_SIZE = 100, DEFAULT_FRAME_SPEED = 10;

    public SpriteSet(ResourceLibrary resourceLibrary){
        this.animationSheets = new Dict<>();
        animationSheets.put("placeholder", new ImageSheet(
                ImageUtils.loadImage(resourceLibrary.getFileFromKey("sprites/error", "placeholder")),
                DEFAULT_FRAME_SPEED,
                DEFAULT_SPRITE_SIZE)
        );
    }

    public void addSheet(Dict<String, File> dict){
        for (var k : dict.keys()) {
            if(dict.key(k).isFile()) {
                animationSheets.put(
                        k,
                        new ImageSheet(
                                ImageUtils.loadImage(dict.key(k)),
                                DEFAULT_FRAME_SPEED,
                                DEFAULT_SPRITE_SIZE));
            }
        }
    }

    public ImageSheet get(String name){
        try{
            return animationSheets.key(name);
        }
        catch (NullPointerException e) {
            System.out.println(String.format("No image named %s in folders\nCurrent sheets are:", name));
            animationSheets.keys().forEach(System.out::println);
            System.out.println("\nErrormessage: "+e);
            System.exit(0);
        }
        return null;
    }
}
