package area51.arena.engine

import area51.arena.Arena
import area51.arena.builder.ElementsBuilder
import area51.arena.builder.ElementsDirector
import area51.element.hierarchy.Alien
import area51.element.trait.AttackTrait
import area51.element.trait.Direction
import area51.element.trait.MovementTrait
import area51.singleton.CycleSingleton
import area51.singleton.GuiSingleton
import area51.singleton.Key
import spock.lang.Specification

class MovementEngineTest extends Specification {
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

    def "moves hero" (){
        given:
        def arena = arenaWithWaveGenerated()
        def move = new MovementEngine(arena)
        def hero = arena.getHero()
        def availablePosition = arena.getRandomAvailablePosition()
        arena.getPosition(hero).setXnY(availablePosition.getX() - 1,availablePosition.getY())
        def originalPosition = arena.getPosition(hero).clone()

        when:
        1 * GuiSingleton.call().getLatestCurrentKey() >> Key.RIGHT
        move.execute()

        then:
        arena.getPosition(hero) != originalPosition
    }

    def "moves aliens" (){
        given:
        def arena = arenaWithWaveGenerated()
        def move = new MovementEngine(arena)
        def alien = arena.getElementsByClass(Alien.class).last()
        def originalPosition = arena.getPosition(alien).clone()

        when:
        move.execute()

        then:
        arena.getPosition(alien) != originalPosition
    }

    def "does not move over other elements"(){
        def arena = new Arena(4,4)
        def director = new ElementsDirector(arena, new ElementsBuilder(arena))
        director.generateWave(1)
        def move = new MovementEngine(arena)
        def hero = arena.getHero()
        def originalPosition = arena.getPosition(hero).clone()

        when:
        1 * GuiSingleton.call().getLatestCurrentKey() >> Key.RIGHT
        move.execute()

        then:
        arena.getPosition(hero) == originalPosition
    }

    def "respects cycles per action"(){
        given:
        def arena = arenaWithWaveGenerated()
        def move = new MovementEngine(arena)
        def hero = arena.getHero()
        def originalPosition = arena.getPosition(hero).clone()
        def mockedCycle = Mock(CycleSingleton)
        CycleSingleton.injectSingleton(mockedCycle)

        when:
        1 * GuiSingleton.call().getLatestCurrentKey() >> Key.RIGHT
        _ * CycleSingleton.call().getCycle() >> 1
        MovementTrait movementTrait = (MovementTrait) hero.getTrait(MovementTrait.class)
        movementTrait.canActThisCycle() // by checking, is setting the latestCycleActed
        move.execute()

        then:
        arena.getPosition(hero) == originalPosition

        cleanup:
        CycleSingleton.injectSingleton(null)
    }

    def "stores key as attack direction"(){
        given:
        def arena = arenaWithWaveGenerated()
        def move = new MovementEngine(arena)
        def hero = arena.getHero()
        AttackTrait attackTrait = (AttackTrait) hero.getTrait(AttackTrait.class)

        when:
        1 * GuiSingleton.call().getLatestCurrentKey() >> Key.DOWN
        move.execute()

        then:
        attackTrait.getAttackDirection() == Direction.DOWN
    }
}


