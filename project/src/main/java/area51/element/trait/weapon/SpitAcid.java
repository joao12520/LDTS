package area51.element.trait.weapon;

public class SpitAcid extends RangeWeapon {
    public SpitAcid() {
        super(10, 40);
    }

    public SpitAcid(int cyclesPerAttack, int damagePerAttack) {
        super(cyclesPerAttack, damagePerAttack);
    }
}
