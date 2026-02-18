import java.util.Objects;

public class InvoiceTaxSummary {
    private double ht;
    private double tva;
    private double ttc;

    public InvoiceTaxSummary() {
    }

    public double getHt() {
        return ht;
    }

    public void setHt(double ht) {
        this.ht = ht;
    }

    public double getTtc() {
        return ttc;
    }

    public void setTtc(double ttc) {
        this.ttc = ttc;
    }

    public double getTva() {
        return tva;
    }

    public void setTva(double tva) {
        this.tva = tva;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InvoiceTaxSummary that)) return false;
        return Double.compare(getHt(), that.getHt()) == 0 && Double.compare(getTva(), that.getTva()) == 0 && Double.compare(getTtc(), that.getTtc()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHt(), getTva(), getTtc());
    }

    @Override
    public String toString() {
        return "InvoiceTaxSummary{" +
                "ht=" + ht +
                ", tva=" + tva +
                ", ttc=" + ttc +
                '}';
    }
}
