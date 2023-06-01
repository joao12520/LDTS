package area51.element.hierarchy;

import area51.element.trait.Trait;

import java.util.ArrayList;

public abstract class Area extends Element {
    private String drawBackgroundColor;

    public Area(ArrayList<Trait> traits, String drawBackgroundColor){
        super(traits, "#ffffff", ' ');
        this.drawBackgroundColor = drawBackgroundColor;
    }

    public String getDrawBackgroundColor() {
        return drawBackgroundColor;
    }
}
