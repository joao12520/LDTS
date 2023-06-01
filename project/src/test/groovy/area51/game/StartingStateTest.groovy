package area51.game


import area51.singleton.CycleSingleton
import area51.singleton.GuiSingleton
import spock.lang.Specification

class StartingStateTest extends Specification {
    def setup() {
        def mockedScreen = Mock(GuiSingleton)
        GuiSingleton.injectSingleton(mockedScreen)
    }

    def "run should generate an Arena and Elements"() {
        given:
        def game = new Game()
        def state = new StartingState(game)

        when:
        state.run()

        then:
        game.getArena().getElements().size() != 0
    }

    def "run should change game to play state"() {
        given:
        def game = new Game()
        def state = new StartingState(game)

        when:
        state.run()

        then:
        game.getState() instanceof PlayingState
    }

    def "run should set epoch of CycleSingleton"() {
        given:
        def game = new Game()
        def state = new StartingState(game)
        def mockedCycle = Mock(CycleSingleton)

        when:
        CycleSingleton.injectSingleton(mockedCycle)
        state.run()

        then:
        1 * mockedCycle.setEpoch(_)

        cleanup:
        CycleSingleton.injectSingleton(null)
    }
}