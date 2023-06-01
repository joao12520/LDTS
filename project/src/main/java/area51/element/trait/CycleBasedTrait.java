package area51.element.trait;

import area51.singleton.CycleSingleton;

public abstract class CycleBasedTrait extends Trait {
    private int cyclesPerAction;
    private long latestCycleActed = -1;

    public CycleBasedTrait(int cyclesPerAction){
        this.cyclesPerAction = cyclesPerAction;
    }

    public boolean canActThisCycle(){
        CycleSingleton singleton = CycleSingleton.call();
        long cycle = singleton.getCycle();

        if(isInsideCyclePerAction(cycle))
            return false;
        else {
            latestCycleActed = cycle;
            return true;
        }
    }

    public void setCyclesPerAction(int cyclesPerAction) {
        this.cyclesPerAction = cyclesPerAction;
    }

    private boolean isInsideCyclePerAction(long cycle){
        return latestCycleActed >= (cycle - cyclesPerAction) && latestCycleActed <= cycle;
    }
}
