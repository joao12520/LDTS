package area51.arena.builder;

import area51.arena.Arena;
import area51.element.hierarchy.Alien;
import area51.element.hierarchy.Human;
import area51.element.hierarchy.Wall;
import area51.arena.Position;
import area51.element.trait.*;
import area51.element.trait.weapon.Bite;
import area51.element.trait.weapon.LightSaber;
import area51.element.trait.weapon.Grenade;

import java.util.ArrayList;

public class ElementsBuilder {
    private Arena arena;

    public ElementsBuilder(Arena arena) {
        this.arena = arena;
    }

    public void generateHero() {
        ArrayList<Trait> traits = new ArrayList<>();
        traits.add(new LifeTrait(100));
        traits.add(new MovementTrait(0, new MoveType[]{MoveType.BY_DIRECTION}));
        AttackTrait attackTrait = new AttackTrait(new LightSaber());
        attackTrait.addWeapon(new Grenade());
        traits.add(attackTrait);

        Human hero = new Human(traits);
        Position position = arena.getRandomAvailablePosition();

        arena.upsertElement(hero, position);
    }

    public void generateWalls(int wallsNr) {
        for (int i = 0; i < wallsNr; i++) {
            ArrayList<Trait> traits = new ArrayList<>();
            Wall wall = new Wall(traits);
            Position position = arena.getRandomAvailablePosition();
            arena.upsertElement(wall, position);
        }
    }

    public void generateAliens(int alienNr) {
        for (int i = 0; i < alienNr; i++) {
            ArrayList<Trait> traits = new ArrayList<>();
            traits.add(new LifeTrait(80));
            traits.add(new MovementTrait(16, new MoveType[]{MoveType.RANDOM, MoveType.BY_TARGET}));
            traits.add(new AttackTrait( new Bite()));
            Alien alien = new Alien(traits);
            Position position = arena.getRandomAvailablePosition();
            arena.upsertElement(alien, position);
        }
    }

    public void generateFastAliens(int alienNr) {
        for (int i = 0; i < alienNr; i++) {
            ArrayList<Trait> traits = new ArrayList<>();
            traits.add(new LifeTrait(160));
            traits.add(new MovementTrait(8, new MoveType[]{MoveType.RANDOM, MoveType.BY_TARGET}));
            traits.add(new AttackTrait( new Bite()));
            Alien alien = new Alien(traits, "#FC0FC0", 'F');
            Position position = arena.getRandomAvailablePosition();
            arena.upsertElement(alien, position);
        }
    }

    public void generateBossAliens(int alienNr) {
        for (int i = 0; i < alienNr; i++) {
            ArrayList<Trait> traits = new ArrayList<>();
            traits.add(new LifeTrait(450));
            traits.add(new MovementTrait(12, new MoveType[]{MoveType.RANDOM, MoveType.BY_TARGET}));
            traits.add(new AttackTrait( new Bite(24, 50)));
            Alien alien = new Alien(traits, "#31c6d6", 'B');
            Position position = arena.getRandomAvailablePosition();
            arena.upsertElement(alien, position);
        }
    }

    public void generateBorderWalls() {
        Position edgePosition = arena.getMaxEdgePosition();

        for (int c = 0; c <= edgePosition.getX(); c++) {
            ArrayList<Trait> traits = new ArrayList<>();
            arena.upsertElement(new Wall(traits), new Position(c, 0)); // top
            arena.upsertElement(new Wall(traits), new Position(c, edgePosition.getY())); // bottom
        }

        for (int r = 1; r < edgePosition.getY(); r++) {
            ArrayList<Trait> traits = new ArrayList<>();
            arena.upsertElement(new Wall(traits), new Position(0, r)); // left
            arena.upsertElement(new Wall(traits), new Position(edgePosition.getX(), r)); // right
        }
    }
}
