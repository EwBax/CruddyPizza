package models;

public class OrderModel {

    private int orderNum;
    private int size;
    private int top1;
    private int top2;
    private int top3;
    private String date;
    private String customerName;


    public OrderModel(int orderNum, int size, int top1, int top2, int top3, String date, String customerName) {
        this.orderNum = orderNum;
        this.size = size;
        this.top1 = top1;
        this.top2 = top2;
        this.top3 = top3;
        this.date = date;
        this.customerName = customerName;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTop1() {
        return top1;
    }

    public void setTop1(int top1) {
        this.top1 = top1;
    }

    public int getTop2() {
        return top2;
    }

    public void setTop2(int top2) {
        this.top2 = top2;
    }

    public int getTop3() {
        return top3;
    }

    public void setTop3(int top3) {
        this.top3 = top3;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
