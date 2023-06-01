package area51.game


import area51.singleton.GuiSingleton
import area51.singleton.Key
import spock.lang.Specification

class EndingStateTest extends Specification {
    def setup() {
        def mockedScreen = Mock(GuiSingleton)
        GuiSingleton.injectSingleton(mockedScreen)
    }

    def "should render a message"() {
        given:
        def game = new Game()
        def state = new EndingState(game)

        when:
        state.run()

        then:
        (1.._) * GuiSingleton.call().drawString(_ as Integer, _ as Integer, _ as String)
        1 * GuiSingleton.call().sendGraphics()
    }

    def "run, after Keypress Y, should restart game"() {
        given:
        def game = new Game()
        def state = new EndingState(game)

        when:
        1 * GuiSingleton.call().getYesNoInput() >> Key.Y
        state.run()

        then:
        game.getState() instanceof StartingState
    }

    def "run, after Keypress N, should change game to closing state"() {
        given:
        def game = new Game()
        def state = new EndingState(game)

        when:
        1 * GuiSingleton.call().getYesNoInput() >> Key.N
        state.run()

        then:
        game.getState() instanceof ClosingState
    }
}