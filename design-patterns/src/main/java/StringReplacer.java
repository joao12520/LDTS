public class StringReplacer implements StringTransformer {
    private char a;
    private char x;

    public StringReplacer(char a, char x) {
        this.a = a;
        this.x = x;
    }

    @Override
    public void execute(StringDrink drink) {
        drink.setText(drink.getText().replace(a,x));
    }

    @Override
    public void undo(StringDrink drink) {
        StringReplacer sr = new StringReplacer(a, x);
        sr.execute(drink);
    }
}