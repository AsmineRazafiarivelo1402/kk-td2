import java.util.List;

public static void main(String[] args) {

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
}

