package com.plisexam.admin.test.Util.NetWork.entity;

import com.plisexam.admin.test.Entry.Customer;

public class LoginResult {
    private com.plisexam.admin.test.Entry.Customer Customer;

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
