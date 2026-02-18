import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataRetriver {
    public List<InvoiceTotal> findInvoiceTotals() {

        DBConnection dbConnection = new DBConnection();


        List<InvoiceTotal> invoiceTotals = new ArrayList<>();

        String sql = """
            SELECT
                i.id,
                i.customer_name,
                i.status,
                SUM(il.quantity * il.unit_price) AS total_amount
            FROM invoice i
            JOIN invoice_line il ON i.id = il.invoice_id
            GROUP BY i.id, i.customer_name, i.status
            ORDER BY i.id
            """;

        try(Connection connection = dbConnection.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String customerName = rs.getString("customer_name");
                String statusStr = rs.getString("status");
                double totalAmount = rs.getDouble("total_amount");

                // Conversion String → Enum
                StatusType status = StatusType.valueOf(statusStr);

                InvoiceTotal invoiceTotal =
                        new InvoiceTotal(id, customerName, status, totalAmount);

                invoiceTotals.add(invoiceTotal);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return invoiceTotals;
    }
    public List<InvoiceTotal> findConfirmedAndPaidInvoiceTotals() {

        DBConnection dbConnection = new DBConnection();


        List<InvoiceTotal> invoiceTotals = new ArrayList<>();

        String sql = """
            SELECT
                i.id,
                i.customer_name,
                i.status,
                SUM(il.quantity * il.unit_price) AS total_amount
            FROM invoice i
            JOIN invoice_line il ON i.id = il.invoice_id
            WHERE i.status IN ('CONFIRMED', 'PAID')
            GROUP BY i.id, i.customer_name, i.status
            ORDER BY i.id
            """;

        try (  Connection connection = dbConnection.getConnection()){
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String customerName = rs.getString("customer_name");
                String statusStr = rs.getString("status");
                double totalAmount = rs.getDouble("total_amount");

                StatusType status = StatusType.valueOf(statusStr);

                InvoiceTotal invoiceTotal = new InvoiceTotal(id, customerName, status, totalAmount);

                invoiceTotals.add(invoiceTotal);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return invoiceTotals;
    }
    InvoiceStatusTotals computeStatusTotals(){
        DBConnection dbConnection = new DBConnection();
        InvoiceStatusTotals invoiceStatusTotals = new InvoiceStatusTotals();
        String sql = """
                SELECT
                    SUM(CASE\s
                            WHEN i.status = 'PAID'\s
                            THEN il.quantity * il.unit_price\s
                            ELSE 0\s
                        END) AS total_paid,
                
                    SUM(CASE\s
                            WHEN i.status = 'CONFIRMED'\s
                            THEN il.quantity * il.unit_price\s
                            ELSE 0\s
                        END) AS total_confirmed,
                
                    SUM(CASE\s
                            WHEN i.status = 'DRAFT'\s
                            THEN il.quantity * il.unit_price\s
                            ELSE 0\s
                        END) AS total_draft
                
                FROM invoice i
                JOIN invoice_line il ON i.id = il.invoice_id;
                
                """;
        try(Connection connection = dbConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
           ) {
            while(rs.next()){
                invoiceStatusTotals.setTotal_paid(rs.getDouble("total_paid"));
                invoiceStatusTotals.setTotal_confirmed(rs.getDouble("total_confirmed"));
                invoiceStatusTotals.setTotal_draft(rs.getDouble("total_draft"));
            }
            dbConnection.closeConnection(connection);
            return invoiceStatusTotals;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
