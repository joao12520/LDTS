package area51.element.hierarchy;

import area51.element.trait.Trait;

import java.util.ArrayList;

public class Alien extends Element {
    public Alien(ArrayList<Trait> traits){
        super(traits, "#339933", 'A');
    }

    public Alien(ArrayList<Trait> traits, String drawColor, Character drawChar){
        super(traits, drawColor, drawChar);
    }
}
