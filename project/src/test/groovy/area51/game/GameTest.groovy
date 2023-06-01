package area51.game

import area51.arena.Arena
import area51.game.Game
import area51.game.ClosingState
import area51.game.IntroducingState
import area51.singleton.GuiSingleton
import area51.singleton.Key
import spock.lang.Specification

class GameTest extends Specification {
    def setup() {
        def mockedScreen = Mock(GuiSingleton)
        GuiSingleton.injectSingleton(mockedScreen)
    }

    def "run() should run until ClosingState"() {
        given:
        def game = new Game();

        when:
        game.run()

        then:
        game.getState() instanceof ClosingState
        1 * GuiSingleton.call().close() // proves that ran ClosingState
    }

    def "runState() should change state"() {
        given:
        def game = new Game();

        when:
        1 * GuiSingleton.call().getYesNoInput() >> Key.N
        game.runState()

        then:
        game.getState() instanceof ClosingState
    }

    def "runState() should call state.run()"() {
        given:
        def game = new Game();
        def mockedState = Mock(IntroducingState)

        when:
        game.setState(mockedState)
        game.runState()

        then:
        1 * mockedState.run()
    }

    def "allows to get and set arena"() {
        given:
        def game = new Game();
        def arena = new Arena()

        when:
        game.setArena(arena)

        then:
        game.getArena() == arena
    }

    def "allows to get and set wave"() {
        given:
        def game = new Game();

        when:
        game.setWave(2)

        then:
        game.getWave() == 2
    }

    def "allows to get and set kills"() {
        given:
        def game = new Game();

        when:
        game.setKills(2)

        then:
        game.getKills() == 2
    }
}