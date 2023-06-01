package area51.element.hierarchy;

import area51.element.trait.Trait;

import java.util.ArrayList;

public abstract class Element {
    private ArrayList<Trait> traits;
    private String drawColor;
    private Character drawChar;

    public Element(ArrayList<Trait> traits, String drawColor, Character drawChar) {
        this.traits = traits;
        this.drawColor = drawColor;
        this.drawChar = drawChar;
    }

    public Trait getTrait(Object traitClass) {
        Trait foundTrait = null;
        for(Trait t: traits){
            if(t.getClass() == traitClass) {
                foundTrait = t;
                break;
            }
        }
        return foundTrait;
    }

    public String getDrawColor() {
        return drawColor;
    }

    public Character getDrawChar() {
        return drawChar;
    }
}