package area51.arena.engine

import area51.arena.Arena
import area51.arena.builder.ElementsBuilder
import area51.arena.builder.ElementsDirector
import area51.element.hierarchy.Alien
import area51.element.hierarchy.AttackedArea
import area51.element.hierarchy.Element
import area51.element.trait.AttackTrait
import area51.element.trait.Direction
import area51.element.trait.weapon.AreaWeapon
import area51.element.trait.weapon.LightSaber
import area51.element.trait.weapon.Grenade
import area51.singleton.GuiSingleton
import area51.singleton.Key
import spock.lang.Specification

class AttackEngineTest extends Specification {
    def setup() {
        def mockedScreen = Mock(GuiSingleton)
        GuiSingleton.injectSingleton(mockedScreen)
    }

    def arenaWithWaveGenerated(){
        def arena = new Arena()
        def director = new ElementsDirector(arena, new ElementsBuilder(arena))
        director.generateWave(1)
        return arena
    }

    def removeAliens(Arena arena){
        def aliens = arena.getElementsByClass(Alien.class)
        for(Element a: aliens)
            arena.removeElement(a)
    }

    def "creates AttackedAreas" (){
        given:
        def arena = arenaWithWaveGenerated()
        def engine = new AttackEngine(arena)
        def hero = arena.getHero()
        def attackTrait = (AttackTrait) hero.getTrait(AttackTrait.class)
        def heroPosition = arena.getPosition(hero)
        def alienPosition = arena.getPosition(arena.getElementsByClass(Alien.class).last())

        when:
        2 * GuiSingleton.call().getLatestCurrentKey() >> Key.SPACE
        attackTrait.setAttackDirection(Direction.DOWN)
        alienPosition.setXnY(heroPosition.getX(), heroPosition.getY() + 1)
        engine.execute()

        then:
        arena.getElementsByClass(AttackedArea.class).size() == 2
    }

    def "sets attack direction of Aliens towards Hero" (){
        given:
        def arena = arenaWithWaveGenerated()
        def engine = new AttackEngine(arena)
        def heroPosition = arena.getPosition(arena.getHero())
        def alien = arena.getElementsByClass(Alien.class).last()
        def alienPosition = arena.getPosition(alien)
        def attackTrait = (AttackTrait) alien.getTrait(AttackTrait.class)

        when:
        alienPosition.setXnY(heroPosition.getX() + 1, heroPosition.getY())
        engine.execute()

        then:
        attackTrait.getAttackDirection() == Direction.LEFT
    }

    def "creates AttackedAreas on AreaWeapon" (){
        given:
        def arena = arenaWithWaveGenerated()
        def engine = new AttackEngine(arena)
        def hero = arena.getHero()
        def heroPosition = arena.getPosition(arena.getHero())
        def attackTrait = (AttackTrait) hero.getTrait(AttackTrait.class)

        when:
        2 * GuiSingleton.call().getLatestCurrentKey() >> Key.SPACE
        heroPosition.setXnY(10,10)
        removeAliens(arena)
        attackTrait.setAttackDirection(Direction.DOWN)
        attackTrait.addWeapon(new Grenade())
        attackTrait.selectNextWeapon()
        engine.execute()

        then:
        arena.getElementsByClass(AttackedArea.class).size() == 9
    }

    def "sets next weapon when C pressed" (){
        given:
        def arena = arenaWithWaveGenerated()
        def engine = new AttackEngine(arena)
        def hero = arena.getHero()
        def attackTrait = (AttackTrait) hero.getTrait(AttackTrait.class)

        when:
        2 * GuiSingleton.call().getLatestCurrentKey() >> Key.C
        engine.execute()

        then:
        attackTrait.getWeaponClass() == AreaWeapon.class
    }
}

