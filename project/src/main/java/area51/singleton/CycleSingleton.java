package area51.singleton;

public class CycleSingleton {
    public long epoch;
    public int minCycleTime = 30;

    public static CycleSingleton cycleSingleton;

    // Test Only
    public static void injectSingleton(CycleSingleton singleton) {
        cycleSingleton = singleton;
    }

    public static CycleSingleton call() {
        if (cycleSingleton == null)
            cycleSingleton = new CycleSingleton();
        return cycleSingleton;
    }

    public void setEpoch(long epoch){ this.epoch = epoch; }

    public long getCycle() {
        return (System.currentTimeMillis() - epoch) / minCycleTime;
    }
}
