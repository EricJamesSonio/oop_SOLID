package POS;

public class IngredientAmount {
    private Ingredient ingredient;
    private double amount;

    public IngredientAmount(Ingredient ingredient, double amount) {
        this.ingredient = ingredient;
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public double getAmount() {
        return amount;
    }

    public void addAmount(double value) {
        if (value <= 0) throw new IllegalArgumentException("Value must be positive");
        amount += value;
    }

    public void deductAmount(double value) {
        if (value <= 0) throw new IllegalArgumentException("Value must be positive");
        if (value > amount) throw new IllegalStateException("Not enough stock");
        amount -= value;
    }


    public int getIngredientCode() {
        return ingredient.getCode();
    }


}
