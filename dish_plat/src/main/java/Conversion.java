public class Conversion {
    Ingredient ingredient;
    int value_KG;
    int value_L;
    int value_PCS;

    public Conversion(Ingredient ingredient, int value_KG, int value_L, int value_PCS) {
        this.ingredient = ingredient;
        this.value_KG = value_KG;
        this.value_L = value_L;
        this.value_PCS = value_PCS;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getValue_KG() {
        return value_KG;
    }

    public void setValue_KG(int value_KG) {
        this.value_KG = value_KG;
    }

    public int getValue_L() {
        return value_L;
    }

    public void setValue_L(int value_L) {
        this.value_L = value_L;
    }

    public int getValue_PCS() {
        return value_PCS;
    }

    public void setValue_PCS(int value_PCS) {
        this.value_PCS = value_PCS;
    }
    
    double conversionUnit(StockValue stockValue, Conversion conversion){
        double newValue = 0;
        if(stockValue.getUnit() != Unit.KG && stockValue.getUnit() == Unit.L){
            newValue = stockValue.getQuantity() / conversion.value_L;
        } else if (stockValue.getUnit() != Unit.KG && stockValue.getUnit() == Unit.PCS) {
            newValue = stockValue.getQuantity() / conversion.value_PCS;
        }
        return newValue;
    }
}
