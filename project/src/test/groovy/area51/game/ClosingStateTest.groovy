package area51.game


import area51.singleton.GuiSingleton
import spock.lang.Specification

class ClosingStateTest extends Specification {
    def setup() {
        def mockedScreen = Mock(GuiSingleton)
        GuiSingleton.injectSingleton(mockedScreen)
    }

    def "run should close screen"() {
        given:
        def state = new ClosingState()

        when:
        state.run()

        then:
        1 * GuiSingleton.call().close()
    }
}