package area51.element.trait.weapon;

public class Bite extends MeleeWeapon {
    public Bite() {
        super(24, 30);
    }

    public Bite(int cyclesPerAttack, int damagePerAttack) {
        super(cyclesPerAttack, damagePerAttack);
    }
}
