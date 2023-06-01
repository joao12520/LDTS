package area51.arena.engine

import area51.arena.Arena
import area51.arena.builder.ElementsBuilder
import area51.arena.builder.ElementsDirector
import area51.element.hierarchy.AttackedArea
import area51.element.trait.Trait
import area51.singleton.GuiSingleton
import spock.lang.Specification

class DrawEngineTest extends Specification {
    def setup() {
        def mockedScreen = Mock(GuiSingleton)
        GuiSingleton.injectSingleton(mockedScreen)
    }

    def "draws all elements" (){
        given:
        def arena = new Arena()
        def director = new ElementsDirector(arena, new ElementsBuilder(arena))
        def engine = new DrawEngine(arena)
        director.generateWave(1)
        def nrElements = arena.getElements().size()

        when:
        engine.execute()

        then:
        (nrElements) * GuiSingleton.call().drawChar(_, _, _, _, _)
    }

    def "draws arena behind element" (){
        given:
        def arena = new Arena()
        def director = new ElementsDirector(arena, new ElementsBuilder(arena))
        director.generateWave(1)
        def engine = new DrawEngine(arena)
        def nrElements = arena.getElements().size()

        when:
        def heroPosition = arena.getPosition(arena.getHero())
        def attackedArea = new AttackedArea(new ArrayList<Trait>())
        arena.upsertElement(attackedArea, heroPosition)
        engine.execute()

        then:
        (nrElements) * GuiSingleton.call().drawChar(_, _, _, _, _)
    }
}

