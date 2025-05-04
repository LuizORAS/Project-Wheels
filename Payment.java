package Wheels;

import Wheels.User.Plan;

public class Payment {
    // Member variables
    private User user;
    private int paymentId;

    // Static counter for generating unique payment IDs
    private static int paymentCount = 1;

    // Constructor
    public Payment(User user) {
        this.user = user;
        this.paymentId = paymentCount++;
    }

    // Calculate total payment based on the user's subscription plan
    public void calculateMonthlyPayment() {
        Plan userPlan = user.getSubscriptionPlan();
        double monthlyCost = 0;

        switch (userPlan) {
            case FREE:
                System.out.println("Free plan: No payment required.");
                break;
            case BASIC:
                monthlyCost = 20.0; // Monthly cost for Basic plan
                break;
            case GOLD:
                monthlyCost = 50.0; // Monthly cost for Gold plan
                break;
            case DIAMOND:
                monthlyCost = 100.0; // Monthly cost for Diamond plan
                break;
        }

        if (monthlyCost > 0) {
            System.out.println("Monthly payment for user '" + user.getName() + "': $" + monthlyCost);
        }

        issueReceipt(monthlyCost);
    }

    // Private method to issue a receipt
    private void issueReceipt(double monthlyCost) {
        System.out.println("---- Receipt ----");
        System.out.println("User: " + user.getName() + " (Plan: " + user.getSubscriptionPlan() + ")");
        System.out.println("Monthly Cost: $" + monthlyCost);
        System.out.println("-----------------");
    }

    // Getters for Payment ID and User
    public int getPaymentId() {
        return paymentId;
    }

    public User getUser() {
        System.out.println("User: " + user.getName() + " (Plan: " + user.getSubscriptionPlan() + ")");
        return user;
    }
}