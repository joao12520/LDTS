package area51.arena.builder

import area51.arena.Arena
import spock.lang.Specification

class ElementsDirectorTest extends Specification {
    def "generates wave with a builder" () {
        given:
        def arena = new Arena()
        def builder = Mock(ElementsBuilder)
        def director = new ElementsDirector(arena, builder)

        when:
        director.generateWave(1)

        then:
        1 * builder.generateHero()
    }

}
