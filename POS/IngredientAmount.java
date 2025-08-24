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
}
