package area51.element.trait.weapon;

public abstract class Weapon {
    private final int damagePerAttack;
    private final int cyclesPerAttack;

    public Weapon(int cyclesPerAttack, int damagePerAttack) {
        this.cyclesPerAttack = cyclesPerAttack;
        this.damagePerAttack = damagePerAttack;
    }

    public int getDamagePerAttack() {
        return damagePerAttack;
    }

    public int getCyclesPerAttack() {
        return cyclesPerAttack;
    }

    public Object getWeaponClass(){
        return getClass().getSuperclass();
    }

    public String getWeaponName(){
        String fullName = getClass().toString();
        return fullName.substring(fullName. lastIndexOf('.') + 1);
    }
}
