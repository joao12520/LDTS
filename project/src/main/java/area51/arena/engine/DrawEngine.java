package area51.arena.engine;

import area51.arena.Arena;
import area51.arena.Position;
import area51.element.hierarchy.Area;
import area51.element.hierarchy.Element;
import area51.element.trait.AttackTrait;
import area51.element.trait.LifeTrait;
import area51.singleton.GuiSingleton;

import java.awt.*;
import java.util.ArrayList;

public class DrawEngine implements Engine {
    private Arena arena;

    public DrawEngine(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void execute() {
        GuiSingleton screen = GuiSingleton.call();

        screen.prepareGraphics();
        drawAllElements(screen);
        drawBottomUi(screen);
        screen.sendGraphics();
    }

    private void drawAllElements(GuiSingleton screen){
        ArrayList<Position> positions = arena.getPositions();

        for(Position position: positions){
            ArrayList<Element> elements = arena.getElements(position);

            Area area = extractFirstAreaElement(elements);
            Element element = extractFirstNonAreaElement(elements);
            if(element == null)
                element = area; // draw empty area only

            if(element == null)
                continue; // not possible, but needed due to warning of null pointer

            String backgroundColor = area != null ? area.getDrawBackgroundColor() : "#000000";
            String foregroundColor = darkenForegroundColorByHp(element);
            screen.drawChar(position.getX(), position.getY(), backgroundColor, foregroundColor, element.getDrawChar());
        }
    }

    private Area extractFirstAreaElement(ArrayList<Element> elements){
        for(Element e: elements){
            if(e instanceof Area)
                return (Area) e;
        }
        return null;
    }

    private Element extractFirstNonAreaElement(ArrayList<Element> elements){
        for(Element e: elements){
            if(!(e instanceof Area))
                return e;
        }
        return null;
    }

    private String darkenForegroundColorByHp(Element element){
        String baseColor = element.getDrawColor();
        LifeTrait lifeTrait = (LifeTrait) element.getTrait(LifeTrait.class);
        if(lifeTrait == null)
            return baseColor;

        float ratio = (float) lifeTrait.getLife() / lifeTrait.getInitialLife();
        return getDarkerByRatio(baseColor, ratio);
    }

    private String getDarkerByRatio(String baseColor, float ratio){
        if(ratio == 1)
            return baseColor;

        Color c = Color.decode(baseColor);
        if(ratio > 0.66)
            c = c.darker();
        else
            c = ratio > 0.33 ? c.darker() : c.darker().darker();

        return convertColorToHex(c);
    }

    private String convertColorToHex(Color color){
        return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }

    private void drawBottomUi(GuiSingleton screen){
        Element hero = arena.getHero();
        if(hero == null) return;

        AttackTrait attackTrait = (AttackTrait) hero.getTrait(AttackTrait.class);
        LifeTrait lifeTrait = (LifeTrait) hero.getTrait(LifeTrait.class);

        screen.setBackground("#000000");
        screen.setForeground("#ffffff");
        String weaponString = "Weapon: " + attackTrait.getWeaponName();
        String hpString = "HP: " + lifeTrait.getLife();
        screen.drawString(3,29, weaponString);
        screen.drawString(30,29, hpString);
    }
}
