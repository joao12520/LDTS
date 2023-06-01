package area51.arena.engine

import area51.arena.Arena
import area51.arena.builder.ElementsBuilder
import area51.arena.builder.ElementsDirector
import area51.element.hierarchy.Alien
import area51.element.hierarchy.AttackedArea
import area51.element.trait.ChangesHpTrait
import area51.element.trait.LifeTrait
import area51.element.trait.Trait
import area51.singleton.GuiSingleton
import spock.lang.Specification

class LifeEngineTest extends Specification {
    def "changes life according to all attacked areas" (){
        given:
        def arena = new Arena()
        def director = new ElementsDirector(arena, new ElementsBuilder(arena))
        director.generateWave(1)
        def engine = new LifeEngine(arena)
        def hero = arena.getHero()
        def alien = arena.getElementsByClass(Alien.class).last()
        def lifeTrait = (LifeTrait) hero.getTrait(LifeTrait.class)
        def traits = new ArrayList<Trait>()

        when:
        def heroPosition = arena.getPosition(hero)
        def changesHpTrait = new ChangesHpTrait(-20, alien)
        traits.add(changesHpTrait)
        def attackedArea = new AttackedArea(traits)
        arena.upsertElement(attackedArea, heroPosition)
        engine.execute()

        then:
        lifeTrait.getLife() == 80
    }

    def "destroys all attacked areas" (){
        given:
        def arena = new Arena()
        def director = new ElementsDirector(arena, new ElementsBuilder(arena))
        director.generateWave(1)
        def engine = new LifeEngine(arena)
        def alien = arena.getElementsByClass(Alien.class).last()
        def traits = new ArrayList<Trait>()

        when:
        def heroPosition = arena.getPosition(arena.getHero())
        def changesHpTrait = new ChangesHpTrait(-20, alien)
        traits.add(changesHpTrait)
        def attackedArea = new AttackedArea(traits)
        def attackedArea2 = new AttackedArea(traits)
        arena.upsertElement(attackedArea, heroPosition)
        arena.upsertElement(attackedArea2, heroPosition)
        engine.execute()

        then:
        arena.getElementsByClass(AttackedArea.class).isEmpty()
    }

    def "removes element if life <= 0" (){
        given:
        def arena = new Arena()
        def director = new ElementsDirector(arena, new ElementsBuilder(arena))
        director.generateWave(1)
        def engine = new LifeEngine(arena)
        def hero = arena.getHero()
        def alien = arena.getElementsByClass(Alien.class).last()
        def traits = new ArrayList<Trait>()

        when:
        def heroPosition = arena.getPosition(hero)
        def changesHpTrait = new ChangesHpTrait(-100, alien)
        traits.add(changesHpTrait)
        def attackedArea = new AttackedArea(traits)
        arena.upsertElement(attackedArea, heroPosition)
        engine.execute()

        then:
        arena.getHero() == null
    }
}

