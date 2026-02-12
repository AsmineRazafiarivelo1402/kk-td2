import java.sql.*;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Ingredient {
    private Integer id;
    private String name;
    private CategoryEnum category;
    private Double price;
    private List<StockMovement> stockMovementList;




    public Ingredient() {
    }

    public Ingredient(Integer id) {
        this.id = id;
    }

    public Ingredient( Integer id, String name, Double price,CategoryEnum category, List<StockMovement> stockMovementList) {
        this.category = category;
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockMovementList = stockMovementList;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<StockMovement> getStockMovementList() {
        return stockMovementList;
    }

    public void setStockMovementList(List<StockMovement> stockMovementList) {
        this.stockMovementList = stockMovementList;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ingredient that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && getCategory() == that.getCategory() && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getStockMovementList(), that.getStockMovementList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getCategory(), getPrice(), getStockMovementList());
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "category=" + category +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stockMovementList=" + stockMovementList +
                '}';
    }
    // CORRECTION FOR STOCKMOVEMENT
    public StockValue getStockValueAt(Instant t) {

        // ✅ Toujours retourner un StockValue
        if (stockMovementList == null || stockMovementList.isEmpty()) {
            StockValue stockValue = new StockValue();
            stockValue.setQuantity(0.0);
            stockValue.setUnit(Unit.KG); // ou unité par défaut
            return stockValue;
        }
        Map<Unit, List<StockMovement>> unitSet =
                stockMovementList.stream()
                        .collect(Collectors.groupingBy(sm -> sm.getValue().getUnit()));

        if (unitSet.keySet().size() > 1) {
            throw new RuntimeException("Multiple unit found and not handled");
        }


        List<StockMovement> stockMovements =
                stockMovementList.stream()
                        .filter(sm -> !sm.getCreationDateTime().isAfter(t))
                        .toList();
//        double changeUnitIn =
//                stockMovements.stream()
//                        .filter(sm -> sm.getMovementtype() == Movementtype.IN && sm.getValue().getUnit() != Unit.KG)

        double movementIn =
                stockMovements.stream()
                        .filter(sm -> sm.getMovementtype() == Movementtype.IN)
                        .mapToDouble(sm -> sm.getValue().getQuantity())
                        .sum();

        double movementOut =
                stockMovements.stream()
                        .filter(sm -> sm.getMovementtype() == Movementtype.OUT)
                        .mapToDouble(sm -> sm.getValue().getQuantity())
                        .sum();

        StockValue stockValue = new StockValue();
        stockValue.setQuantity(movementIn - movementOut);
        stockValue.setUnit(unitSet.keySet().iterator().next());

        return stockValue;
    }


}
