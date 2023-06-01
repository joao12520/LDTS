package area51

import area51.singleton.GuiSingleton
import spock.lang.Specification

class StartTest extends Specification {
    def setup() {
        def mockedScreen = Mock(GuiSingleton)
        GuiSingleton.injectSingleton(mockedScreen)
    }

    def "main runs until the end"() {
        when:
        def result = Start.main()

        then:
        result == null
    }
}