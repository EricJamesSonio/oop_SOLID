package POS.Inventory.Ingredients;

public class IngredientAmount {
    private final Ingredient ingredient; // immutable reference
    private double amount;

    public IngredientAmount(Ingredient ingredient, double amount) {
        if (ingredient == null) throw new IllegalArgumentException("Ingredient cannot be null");
        if (amount < 0) throw new IllegalArgumentException("Amount cannot be negative");
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
