package area51.element.hierarchy;

import area51.element.trait.Trait;

import java.util.ArrayList;

public class Human extends Element {
    public Human(ArrayList<Trait> traits){
        super(traits, "#ffffff", 'H');
    }
}