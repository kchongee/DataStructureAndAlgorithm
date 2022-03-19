package entity;

public class Voucher {
    private double minSpend;
    private double discountPercentage;
    private boolean isReleased;    

    public Voucher(){
        this.isReleased = true;
    }

    public Voucher(double minSpend, double discountPercentage){        
        this();
        this.minSpend = minSpend;
        this.discountPercentage = discountPercentage;        
    }

    public double getMinSpend() {
        return minSpend;
    }
    public void setMinSpend(double minSpend) {
        this.minSpend = minSpend;
    }
    public double getDiscountPercentage() {
        return discountPercentage;
    }
    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public boolean isReleased() {
        return isReleased;
    }

    public void setReleased(boolean isReleased) {
        this.isReleased = isReleased;
    }    
}
