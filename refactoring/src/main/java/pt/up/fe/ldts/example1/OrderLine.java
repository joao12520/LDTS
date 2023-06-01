package pt.up.fe.ldts.example1;

public class OrderLine {
    private Product product;
    private int quantity;

    public OrderLine(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotal() {
        return product.getPrice() * quantity;
    }

    public String print() {
        return product.getName() + "(x" + quantity + "): " + getTotal() + "\n";
    }
}
