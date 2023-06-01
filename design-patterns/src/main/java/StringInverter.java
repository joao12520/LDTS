public class StringInverter implements StringTransformer {

    @Override
    public void execute(StringDrink drink) {
        drink.setText((new StringBuffer(drink.getText()).reverse()).toString());
    }

    @Override
    public void undo(StringDrink drink) {
        execute(drink);
    }
}