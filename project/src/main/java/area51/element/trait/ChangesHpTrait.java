package area51.element.trait;

import area51.element.hierarchy.Alien;
import area51.element.hierarchy.Element;
import area51.element.hierarchy.Human;

public class ChangesHpTrait extends Trait {
    private final int hpToChange;
    private final boolean causedByHumanFaction;
    private final boolean causedByAlienFaction;

    public ChangesHpTrait(int hpToChange, Element originElement) {
        this.hpToChange = hpToChange;
        this.causedByHumanFaction = originElement instanceof Human;
        this.causedByAlienFaction = originElement instanceof Alien;
    }

    public int getHpToChange() {
        return hpToChange;
    }

    public boolean causedByHuman(){
        return causedByHumanFaction;
    }

    public boolean causedByAlien(){
        return causedByAlienFaction;
    }
}