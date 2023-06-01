package area51.game

import area51.element.hierarchy.Alien
import area51.element.hierarchy.Element
import area51.singleton.CycleSingleton
import area51.singleton.GuiSingleton
import area51.singleton.Key
import spock.lang.Specification

class PlayingStateTest extends Specification {
    def setup() {
        def mockedScreen = Mock(GuiSingleton)
        GuiSingleton.injectSingleton(mockedScreen)
    }

    def preparePlayingState(game) {
        game.setState(new StartingState(game))
        game.runState()
    }

    def "run should run in cycles"() {
        given:
        def game = new Game()
        def mockedCycle = Mock(CycleSingleton)

        when:
        CycleSingleton.injectSingleton(mockedCycle)
        preparePlayingState(game)
        1 * GuiSingleton.call().getLatestCurrentKey() >> Key.Q // quit when asked
        game.runState()

        then:
        2 * mockedCycle.getCycle() >> 2

        cleanup:
        CycleSingleton.injectSingleton(null)
    }

    def "run should get Keypress"() {
        given:
        def game = new Game()

        when:
        preparePlayingState(game)
        game.runState()

        then:
        1 * GuiSingleton.call().getLatestCurrentKey() >> Key.Q
    }

    def "run with Keypress Q or ESC, should change to ending state"() {
        given:
        def game = new Game()

        when:
        preparePlayingState(game)
        1 * GuiSingleton.call().getLatestCurrentKey() >> Key.Q
        game.runState()

        then:
        game.getState() instanceof EndingState
    }

    def "if hero killed, should change game to ending state"() {
        given:
        def game = new Game()

        when:
        preparePlayingState(game)
        def arena = game.getArena()
        arena.removeElement(arena.getHero())
        game.runState()

        then:
        game.getState() instanceof EndingState
    }

    def "run, when Aliens life is 0, should increase wave, kills and change game to starting state"() {
        given:
        def game = new Game()

        when:
        preparePlayingState(game)
        def arena = game.getArena()
        def aliens = arena.getElementsByClass(Alien.class)
        for (Element alien : aliens) {
            arena.removeElement(alien)
        }
        game.runState()

        then:
        game.getWave() == 2
        game.getState() instanceof StartingState
    }
}