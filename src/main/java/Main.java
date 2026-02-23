import java.util.List;
import io.github.cdimascio.dotenv.Dotenv;
public static void main(String[] args) {

    Dotenv dotenv = Dotenv.load();
    DataRetriver  dataRetriver= new DataRetriver();
//
//    List<InvoiceTotal> totals = dataRetriver.findInvoiceTotals();
//
//    for (InvoiceTotal invoice : totals) {
//        System.out.println(invoice);
//    }

//    List<InvoiceTotal> totals = dataRetriver.findConfirmedAndPaidInvoiceTotals();
//
//    for (InvoiceTotal invoice : totals) {
//        System.out.println(invoice);
//    }
//    InvoiceStatusTotals invoiceStatusTotals = dataRetriver.computeStatusTotals();
//    System.out.println(invoiceStatusTotals);
//    double compute = dataRetriver.computeWeightedTurnover();
//    System.out.println(compute);

    List<InvoiceTaxSummary> lists = dataRetriver.findInvoiceTaxSummaries();
    for(InvoiceTaxSummary invoiceTaxSummary : lists){
        System.out.println(invoiceTaxSummary);
    }

    BigDecimal compute_ttc = dataRetriver.computeWeightedTurnoverTtc();
    System.out.println(compute_ttc);


}



