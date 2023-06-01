import java.util.ArrayList;
import java.util.List;

public class SmartStrategy implements OrderingStrategy {
    private List<StringDrink> drinks;
    private List<StringRecipe> recipes;

    public SmartStrategy() {drinks = new ArrayList<>(); recipes = new ArrayList<>(); }

    @Override
    public void wants(StringDrink drink, StringRecipe recipe, StringBar bar) {
        if (bar.isHappyHour())
            bar.order(drink, recipe);
        else {
            this.drinks.add(drink);
            this.recipes.add(recipe);
        }
    }

    @Override
    public void happyHourStarted(StringBar bar) {
        if (!drinks.isEmpty() && !recipes.isEmpty()) {
            bar.order(drinks.get(0), recipes.get(0));
            drinks.remove(0);
            recipes.remove(0);
        }
    }

    @Override
    public void happyHourEnded(StringBar bar) {
    }
}
