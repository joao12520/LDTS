package area51.element.trait.weapon;

public class Gun extends RangeWeapon {
    public Gun() {
        super(0, 30);
    }

    public Gun(int cyclesPerAttack, int damagePerAttack) {
        super(cyclesPerAttack, damagePerAttack);
    }
}
