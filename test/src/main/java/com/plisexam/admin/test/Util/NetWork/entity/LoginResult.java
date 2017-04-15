package com.plisexam.admin.test.Util.NetWork.entity;

public class LoginResult {
    private Customer Customer;

    public void setCustomer(Customer Customer){
        this.Customer = Customer;
    }
    public Customer getCustomer(){
        return this.Customer;
    }

    @Override
    public String toString() {
        return Customer.toString();
    }
}
