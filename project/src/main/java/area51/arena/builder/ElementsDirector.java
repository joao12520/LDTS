package area51.arena.builder;

import area51.arena.Arena;

import static java.lang.Math.min;

public class ElementsDirector {
    private Arena arena;
    private ElementsBuilder builder;

    public ElementsDirector(Arena arena, ElementsBuilder builder) {
        this.arena = arena;
        this.builder = builder;
    }

    // wave >= 1
    public void generateWave(int wave) {
        builder.generateBorderWalls();
        builder.generateHero();

        generateEasyElements(wave);
        if(wave < 4) return;

        generateMediumElements(wave);
        if(wave < 8) return;

        generateHardElements(wave);
    }

    private void generateEasyElements(int wave){
        int nrAliens = min(arena.nrAvailablePositions(), wave * 2);
        builder.generateAliens(nrAliens);

        int nrWalls = min(arena.nrAvailablePositions(), wave * 3);
        builder.generateWalls(nrWalls);
    }

    private void generateMediumElements(int wave){
        int nrAliens = min(arena.nrAvailablePositions(), (wave - 3) * 2);
        builder.generateFastAliens(nrAliens);
    }

    private void generateHardElements(int wave){
        int nrAliens = min(arena.nrAvailablePositions(), (wave - 7) * 2);
        builder.generateBossAliens(nrAliens);
    }
}
