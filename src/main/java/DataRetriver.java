import java.math.BigDecimal;
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
    Double computeWeightedTurnover(){
        DBConnection dbConnection = new DBConnection();
        double compute = 0.0;
        String sql = """
                
                select sum(   (CASE
                                       WHEN i.status = 'PAID'
                                           THEN il.quantity * il.unit_price
                                       ELSE 0
                    END ) +  (CASE
                                                   WHEN i.status = 'CONFIRMED'
                                                       THEN (il.quantity * il.unit_price) / 2
                                                   ELSE 0
                    END) ) as computeWeight
                
                FROM invoice i
                         JOIN invoice_line il ON i.id = il.invoice_id;
                """;
        try(Connection connection = dbConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {
            if(rs.next()){
              compute = rs.getDouble("computeweight");
            }
            dbConnection.closeConnection(connection);
            return compute;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    List<InvoiceTaxSummary>    findInvoiceTaxSummaries(){
        DBConnection dbConnection = new DBConnection();
        String sql = """
               
                SELECT
                   SUM(line_total) AS ht,
                   SUM(line_total * t.rate / 100) AS tva,
                   SUM(line_total * (1 + t.rate / 100)) AS ttc
               FROM (
                   SELECT
                       i.id AS invoice_id,
                       (il.quantity * il.unit_price) AS line_total
                   FROM invoice i
                   JOIN invoice_line il ON i.id = il.invoice_id
               ) sub
               CROSS JOIN tax_config t
               GROUP BY invoice_id;
               
                
                """;
        List<InvoiceTaxSummary> lists = new ArrayList<>();

        try(Connection connection = dbConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {
            while (rs.next()){
                InvoiceTaxSummary invoiceTaxSummary = new InvoiceTaxSummary();
                invoiceTaxSummary.setHt(rs.getDouble("ht"));
                invoiceTaxSummary.setTva(rs.getDouble("tva"));
                invoiceTaxSummary.setTtc(rs.getDouble("ttc"));
                lists.add(invoiceTaxSummary);
            }
            dbConnection.closeConnection(connection);
            return lists;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    BigDecimal computeWeightedTurnoverTtc() {
        DBConnection dbConnection = new DBConnection();

        String sql = """
        SELECT 
            SUM(
                (il.quantity * il.unit_price) *
                CASE
                    WHEN i.status = 'PAID' THEN 1
                    WHEN i.status = 'CONFIRMED' THEN 0.5
                    ELSE 0
                END
                * (1 + t.rate / 100)
            ) AS compute_weighted_ttc
        FROM invoice i
        JOIN invoice_line il ON i.id = il.invoice_id
        CROSS JOIN tax_config t;
    """;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                  return rs.getBigDecimal("compute_weighted_ttc");
            } else {
                return BigDecimal.ZERO;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
