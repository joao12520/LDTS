package area51.arena;

import area51.element.hierarchy.Alien;
import area51.element.hierarchy.Area;
import area51.element.hierarchy.Element;
import area51.element.hierarchy.Human;

import java.util.*;

public class Arena {
    private HashMap<Element, Position> elementsAndPositions = new HashMap<>();
    private final int width;
    private final int height;

    public Arena() {
        this.width = 40;
        this.height = 29;
    }

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void upsertElement(Element element, Position position) {
        if (!validPosition(position))
            return;

        elementsAndPositions.put(element, position);
    }

    public void removeElement(Element element) {
        elementsAndPositions.remove(element);
    }

    public Position getPosition(Element element) {
        return elementsAndPositions.get(element);
    }

    public boolean validPosition(Position position){
        return position.getX() < width && position.getY() < height && position.getX() >= 0 && position.getY() >= 0;
    }

    public ArrayList<Element> getElements() {
        return new ArrayList<>(elementsAndPositions.keySet());
    }

    public ArrayList<Position> getPositions() {
        HashSet<Position> positionsSet = new HashSet<>(elementsAndPositions.values());
        return new ArrayList<>(positionsSet);
    }

    public Element getFirstElement(Position position) {
        ArrayList<Element> elements = getElements(position);
        if(elements.isEmpty())
            return null;

        return elements.get(0);
    }

    public Element getFirstNonAreaElement(Position position){
        ArrayList<Element> elements = getElements(position);
        for(Element e: elements){
            if(!(e instanceof Area))
                return e;
        }
        return null;
    }

    public ArrayList<Element> getElements(Position position){
        ArrayList<Element> elements = new ArrayList<>();
        for (Map.Entry<Element, Position> entry : elementsAndPositions.entrySet()) {
            if (position.equals(entry.getValue())) {
                elements.add(entry.getKey());
            }
        }
        return elements;
    }

    public Area getAreaElement(Position position){
        ArrayList<Element> elements = getElements(position);
        elements.removeIf(e -> !(e instanceof Area));
        if(elements.isEmpty())
            return null;

        return (Area) elements.get(0);
    }

    public Human getHero() {
        ArrayList<Element> elements = getElements();
        for (Element e : elements) {
            if (e instanceof Human)
                return (Human) e;
        }
        return null;
    }

    public ArrayList<Element> getElementsByTrait(Object traitClass) {
        ArrayList<Element> elements = getElements();
        ArrayList<Element> elementsWithTrait = new ArrayList<>();

        for (Element e : elements) {
            if(e.getTrait(traitClass) != null)
                elementsWithTrait.add(e);
        }

        return elementsWithTrait;
    }

    public int nrAliensToKill() {
        return getElementsByClass(Alien.class).size();
    }

    public boolean aliensKilled(){
        return getElementsByClass(Alien.class).isEmpty();
    }

    public ArrayList<Element> getElementsByClass(Object elementClass) {
        ArrayList<Element> elements = getElements();
        ArrayList<Element> filteredElements = new ArrayList<>();

        for (Element e : elements) {
            if (e.getClass() == elementClass)
                filteredElements.add(e);
        }

        return filteredElements;
    }

    public Position getMaxEdgePosition() {
        return new Position(width - 1, height - 1);
    }

    public int nrAvailablePositions() {
        return width * height - elementsAndPositions.size();
    }

    public Position getRandomAvailablePosition() {
        // protection against infinite loop
        if (nrAvailablePositions() <= 0)
            return null;

        Random random = new Random();
        Position position;
        do {
            position = new Position(random.nextInt(width), random.nextInt(height));
        } while (getFirstElement(position) != null);
        return position;
    }
}
