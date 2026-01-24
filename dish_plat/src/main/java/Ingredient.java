import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

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

    public StockValue getStockValueAt(Instant instant){
        String findStock ="select StockMovement.quantity, StockMovement.type from StockMovement where creation_datetime = ?";
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();
    try{
        PreparedStatement ps = connection.prepareStatement(findStock);
        ps.setString(1, String.valueOf(instant));
        ResultSet rs = ps.executeQuery();
        StockValue value = new StockValue();
        while(rs.next()){

           value.setQuantity(rs.getDouble("quantity"));
           value.setUnit(Unit.valueOf(rs.getString("type")));

        }
        return value;
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    }
}
