package area51.element.trait


import spock.lang.Specification

class LifeTraitTest extends Specification {
    def "has and sets life"() {
        given:
        def lifeTrait = new LifeTrait(100)

        when:
        lifeTrait.setLife(50)

        then:
        lifeTrait.getLife() == 50
    }

    def "get initial life"() {
        given:
        def lifeTrait = new LifeTrait(100)

        when:
        lifeTrait.setLife(50)

        then:
        lifeTrait.getInitialLife() == 100
    }
}