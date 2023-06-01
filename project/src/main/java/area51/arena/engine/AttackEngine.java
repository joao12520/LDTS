package area51.arena.engine;

import area51.arena.Arena;
import area51.arena.Position;
import area51.element.hierarchy.*;
import area51.element.trait.AttackTrait;
import area51.element.trait.ChangesHpTrait;
import area51.element.trait.Direction;
import area51.element.trait.Trait;
import area51.element.trait.weapon.AreaWeapon;
import area51.element.trait.weapon.MeleeWeapon;
import area51.singleton.GuiSingleton;
import area51.singleton.Key;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class AttackEngine implements Engine {
    private Arena arena;
    private PositionsCalculator calculator = new PositionsCalculator();
    private Position heroPosition;

    public AttackEngine(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void execute() {
        ArrayList<Element> elements = arena.getElementsByTrait(AttackTrait.class);
        Element hero = arena.getHero();
        heroPosition = arena.getPosition(hero);

        checkAndChangeWeapon(hero);

        for (Element e : elements) {
            processAttack(e, hero);
        }
    }

    private void checkAndChangeWeapon(Element hero){
        if(GuiSingleton.call().getLatestCurrentKey() == Key.C) {
            GuiSingleton.call().clearLatestCurrentKey();
            AttackTrait attackTrait = (AttackTrait) hero.getTrait(AttackTrait.class);
            attackTrait.selectNextWeapon();
        }
    }

    private void processAttack(Element e, Element hero){
        AttackTrait attackTrait = (AttackTrait) e.getTrait(AttackTrait.class);
        if (!attackTrait.canActThisCycle())
            return;

        if (e != hero)
            setAttackDirection(e);

        if (attackTrait.getWeaponClass() == MeleeWeapon.class)
            processMeleeAttack(e, attackTrait);

            // process range attacks

        else if (attackTrait.getWeaponClass() == AreaWeapon.class)
            processAreaAttack(e, attackTrait);
    }

    private void setAttackDirection(Element element) {
        Direction direction = calculator.directionByTarget(arena.getPosition(element), heroPosition);
        AttackTrait attackTrait = (AttackTrait) element.getTrait(AttackTrait.class);
        attackTrait.setAttackDirection(direction);
    }

    private void processMeleeAttack(Element element, AttackTrait attackTrait) {
        ArrayList<Position> attackedPositions = new ArrayList<>();
        attackedPositions.add(calculator.byDirection(arena.getPosition(element), attackTrait.getAttackDirection()));
        if(shouldGenerateAttackedArea(element, attackedPositions))
            generateAttackedArea(element, attackedPositions.get(0), attackTrait.getDamagePerAttack());
    }

    private void processAreaAttack(Element element, AttackTrait attackTrait) {
        ArrayList<Position> attackedPositions = calculator.areaByDirection(arena.getPosition(element), attackTrait.getAttackDirection());
        if(shouldGenerateAttackedArea(element, attackedPositions)) {
            for (Position p : attackedPositions) {
                generateAttackedArea(element, p, attackTrait.getDamagePerAttack());
            }
        }
    }

    private boolean shouldGenerateAttackedArea(Element element, ArrayList<Position> attackedPositions){
        if(element instanceof Human)
            return shouldHeroAttack();
        else
            return shouldAlienAttack(attackedPositions);
    }

    private boolean shouldHeroAttack(){
        if(GuiSingleton.call().getLatestCurrentKey() == Key.SPACE) {
            GuiSingleton.call().clearLatestCurrentKey();
            return true;
        }
        return false;
    }

    private boolean shouldAlienAttack(ArrayList<Position> attackedPositions){
        for(Position p: attackedPositions){
            if(p.equals(heroPosition))
                return true;
        }
        return false;
    }

    private void generateAttackedArea(Element originElement, Position position, int damagedCaused){
        ArrayList<Trait> traits = new ArrayList<>();
        traits.add(new ChangesHpTrait(damagedCaused * -1, originElement));
        AttackedArea attackedArea = new AttackedArea(traits);
        arena.upsertElement(attackedArea, position);
    }
}
