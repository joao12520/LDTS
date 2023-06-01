package area51.element.trait;

public class LifeTrait extends Trait {
    private final int initialLife;
    private int currentLife;

    public LifeTrait(int life) {
        this.initialLife = life;
        this.currentLife = life;
    }

    public int getInitialLife() {
        return this.initialLife;
    }

    public int getLife() {
        return this.currentLife;
    }

    public void setLife(int life) {
        this.currentLife = life;
    }
}