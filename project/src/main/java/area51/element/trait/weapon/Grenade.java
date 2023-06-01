package area51.element.trait.weapon;

public class Grenade extends AreaWeapon {
    public Grenade() {
        super(0, 100);
    }

    public Grenade(int cyclesPerAttack, int damagePerAttack) {
        super(cyclesPerAttack, damagePerAttack);
    }
}
