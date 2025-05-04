//ARQUIVO: Customer.java
package Wheels;

public class Customer {
    //set up member variables
    private String name = null;
    private String postcode = null;
    private int telephone = 0;
    private int customerID = 0;

    private static int customerCount = 001;

    public Customer(String cName, String pcode, int tel){
        //set the number variables
        name = cName;
        postcode = pcode;
        telephone = customerCount++;
    }

    public int getCustomerNumber(){
        return customerID;
    }

    public String getName(){
        return name;
    }

    public String getPostcode(){
        return postcode;
    }
}
