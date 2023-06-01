package area51.element.trait.weapon;

public class AssaultRifle extends RangeWeapon {
    public AssaultRifle() {
        super(0, 30);
    }

    public AssaultRifle(int cyclesPerAttack, int damagePerAttack) {
        super(cyclesPerAttack, damagePerAttack);
    }
}
