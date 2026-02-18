import java.util.Objects;

public class InvoiceStatusTotals {
   private  double total_paid ;
    private  double total_confirmed;
    private double total_draft ;

    public InvoiceStatusTotals() {
    }

    public InvoiceStatusTotals(double total_confirmed, double total_draft, double total_paid) {
        this.total_confirmed = total_confirmed;
        this.total_draft = total_draft;
        this.total_paid = total_paid;
    }

    public double getTotal_confirmed() {
        return total_confirmed;
    }

    public void setTotal_confirmed(double total_confirmed) {
        this.total_confirmed = total_confirmed;
    }

    public double getTotal_draft() {
        return total_draft;
    }

    public void setTotal_draft(double total_draft) {
        this.total_draft = total_draft;
    }

    public double getTotal_paid() {
        return total_paid;
    }

    public void setTotal_paid(double total_paid) {
        this.total_paid = total_paid;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InvoiceStatusTotals that)) return false;
        return Double.compare(getTotal_paid(), that.getTotal_paid()) == 0 && Double.compare(getTotal_confirmed(), that.getTotal_confirmed()) == 0 && Double.compare(getTotal_draft(), that.getTotal_draft()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTotal_paid(), getTotal_confirmed(), getTotal_draft());
    }

    @Override
    public String toString() {
        return "InvoiceStatusTotals{" +
                "total_confirmed=" + total_confirmed +
                ", total_paid=" + total_paid +
                ", total_draft=" + total_draft +
                '}';
    }
}
