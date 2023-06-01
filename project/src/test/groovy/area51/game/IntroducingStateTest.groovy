package area51.game


import area51.singleton.GuiSingleton
import area51.singleton.Key
import spock.lang.Specification

class IntroducingStateTest extends Specification {
    def setup() {
        def mockedScreen = Mock(GuiSingleton)
        GuiSingleton.injectSingleton(mockedScreen)
    }

    def "run should open screen and render introduction message"() {
        given:
        def game = new Game()
        def state = new IntroducingState(game)

        when:
        state.run()

        then:
        1 * GuiSingleton.call().open()
        1 * GuiSingleton.call().sendGraphics()
    }

    def "run with Keypress N, should change to closing state"() {
        given:
        def game = new Game()
        def state = new IntroducingState(game)

        when:
        1 * GuiSingleton.call().getYesNoInput() >> Key.N
        state.run()

        then:
        game.getState() instanceof ClosingState
    }

    def "run with Keypress Y, should start game"() {
        given:
        def game = new Game()
        def state = new IntroducingState(game)

        when:
        1 * GuiSingleton.call().getYesNoInput() >> Key.Y
        state.run()

        then:
        game.getState() instanceof StartingState
    }
}