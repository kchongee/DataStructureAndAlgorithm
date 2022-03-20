package entity;

import adtImplementation.ArrayList;

public class InvoiceList {
    ArrayList<Invoice> invoice;

    public InvoiceList() {
        this.invoice = new ArrayList<Invoice>();
    }

    public ArrayList<Invoice> getInvoice() {
        return invoice;
    }

    public void setInvoice(ArrayList<Invoice> invoice) {
        this.invoice = invoice;
    }

}
