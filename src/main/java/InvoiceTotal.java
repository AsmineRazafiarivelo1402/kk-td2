import jdk.jshell.Snippet;

import java.util.Objects;

public class InvoiceTotal {
//    Signature méthode en Java : List<InvoiceTotal> findInvoiceTotals(); où
//    InvoiceTotal est la classe comportant les attributs identifiant, nom du client et status de type
//    enum.

    private Integer identifiant;
    private  String customer_name;
    private StatusType status;

    public InvoiceTotal(String customer_name, Integer identifiant, StatusType status) {
        this.customer_name = customer_name;
        this.identifiant = identifiant;
        this.status = status;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public Integer getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(Integer identifiant) {
        this.identifiant = identifiant;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof InvoiceTotal that)) return false;
        return Objects.equals(getIdentifiant(), that.getIdentifiant()) && Objects.equals(getCustomer_name(), that.getCustomer_name()) && getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifiant(), getCustomer_name(), getStatus());
    }

    @Override
    public String toString() {
        return "InvoiceTotal{" +
                "customer_name='" + customer_name + '\'' +
                ", identifiant=" + identifiant +
                ", status=" + status +
                '}';
    }
}
