import java.util.Objects;

public class Facture {
    private Integer id;
    private  InvoiceTotal invoiceTotal;
    private  double amount;

    public Facture() {
    }

    public Facture(double amount, Integer id, InvoiceTotal invoiceTotal) {
        this.amount = amount;
        this.id = id;
        this.invoiceTotal = invoiceTotal;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InvoiceTotal getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceTotal(InvoiceTotal invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Facture facture)) return false;
        return Double.compare(getAmount(), facture.getAmount()) == 0 && Objects.equals(getId(), facture.getId()) && Objects.equals(getInvoiceTotal(), facture.getInvoiceTotal());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getInvoiceTotal(), getAmount());
    }

    @Override
    public String toString() {
        return "Facture{" +
                "amount=" + amount +
                ", id=" + id +

                '}';
    }
}
