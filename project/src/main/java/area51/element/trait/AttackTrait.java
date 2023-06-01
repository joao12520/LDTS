package area51.element.trait;

import area51.element.trait.weapon.Weapon;

import java.util.ArrayList;

public class AttackTrait extends CycleBasedTrait {
    private Direction attackDirection = Direction.UP;
    private ArrayList<Weapon> weapons = new ArrayList<>();
    private int selectedWeaponIndex = 0;

    public AttackTrait(Weapon firstWeapon){
        super(firstWeapon.getCyclesPerAttack());
        weapons.add(firstWeapon);
    }

    public int getDamagePerAttack(){
        return weapons.get(selectedWeaponIndex).getDamagePerAttack();
    }

    public Direction getAttackDirection(){
        return attackDirection;
    }

    public void setAttackDirection(Direction direction){
        attackDirection = direction;
    }

    public Object getWeaponClass() {
        return weapons.get(selectedWeaponIndex).getWeaponClass();
    }

    public String getWeaponName() {
        return weapons.get(selectedWeaponIndex).getWeaponName();
    }

    public void addWeapon(Weapon weapon){
        for(Weapon w: weapons){
            if(w.getClass() == weapon.getClass())
                return;
        }

        weapons.add(weapon);
    }

    public void removeWeapon(Object weaponClass){
        if(weapons.size() <= 1)
            return;

        weapons.removeIf(w -> w.getClass() == weaponClass);
    }

    public void selectNextWeapon(){
        selectedWeaponIndex++;
        if(selectedWeaponIndex >= weapons.size())
            selectedWeaponIndex = 0;
    }
}
