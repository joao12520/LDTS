package area51.element.trait

import area51.element.hierarchy.Alien
import spock.lang.Specification

class ChangesHpTraitTest extends Specification {
    def "get HP To Change"() {
        given:
        def traits = new ArrayList<Trait>()
        def changeHpTrait = new ChangesHpTrait(-20, new Alien(traits))

        expect:
        changeHpTrait.getHpToChange() == -20
    }

    def "caused by Human"() {
        given:
        def traits = new ArrayList<Trait>()
        def changeHpTrait = new ChangesHpTrait(-20, new Alien(traits))

        expect:
        !changeHpTrait.causedByHuman()
    }

    def "caused by Alien"() {
        given:
        def traits = new ArrayList<Trait>()
        def changeHpTrait = new ChangesHpTrait(-20, new Alien(traits))

        expect:
        changeHpTrait.causedByAlien()
    }
}