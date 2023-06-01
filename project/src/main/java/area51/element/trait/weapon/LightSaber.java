package area51.element.trait.weapon;

public class LightSaber extends MeleeWeapon {
    public LightSaber() {
        super(0, 1000);
    }

    public LightSaber(int cyclesPerAttack, int damagePerAttack) {
        super(cyclesPerAttack, damagePerAttack);
    }
}
