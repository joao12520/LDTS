public class StringCaseChanger implements StringTransformer{
    @Override
    public void execute(StringDrink drink) {
        StringBuilder str = new StringBuilder(drink.getText());
        for (int i = 0; i < str.length(); i++) {
            char act = str.charAt(i);
            if (Character.isLowerCase(act)) {
                str.replace(i, i+1, String.valueOf(Character.toUpperCase(act)));
            }
            else {
                str.replace(i, i+1, String.valueOf(Character.toLowerCase(act)));
            }
        }
        drink.setText(str.toString());
    }

    @Override
    public void undo(StringDrink drink) {
        execute(drink);
    }
}