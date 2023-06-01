package area51.arena.engine;

import area51.arena.Arena;
import area51.arena.Position;
import area51.element.hierarchy.AttackedArea;
import area51.element.hierarchy.Element;
import area51.element.trait.ChangesHpTrait;
import area51.element.trait.LifeTrait;

import java.util.ArrayList;

public class LifeEngine implements Engine {
    private Arena arena;

    public LifeEngine(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void execute() {
        ArrayList<Element> areas = arena.getElementsByClass(AttackedArea.class);
        for(Element area: areas){
            causeDamageAndRemove((AttackedArea) area);
        }
    }

    private void causeDamageAndRemove(AttackedArea area){
        Position position = arena.getPosition(area);
        Element elementOnTop = arena.getFirstNonAreaElement(position);

        if(elementOnTop != null){
            LifeTrait lifeTrait = (LifeTrait) elementOnTop.getTrait(LifeTrait.class);
            if(lifeTrait != null) {
                causeDamage(lifeTrait, area);
                checkHpAndRemoveElement(lifeTrait, elementOnTop);
            }
        }

        arena.removeElement(area);
    }

    private void causeDamage(LifeTrait lifeTrait, AttackedArea area){
        ChangesHpTrait changesHpTrait = (ChangesHpTrait) area.getTrait(ChangesHpTrait.class);

        if(changesHpTrait != null)
            lifeTrait.setLife(lifeTrait.getLife() + changesHpTrait.getHpToChange());
    }

    private void checkHpAndRemoveElement(LifeTrait lifeTrait, Element elementOnTop){
        if(lifeTrait.getLife() <= 0)
            arena.removeElement(elementOnTop);
    }
}
