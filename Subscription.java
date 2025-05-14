package Wheels;
import java.util.Date;

public class Subscription {
    private Date startDate;
    private User user;
    private int subscriptionId;
    private static int subscriptionCount = 1;

    public Subscription(Date startDate, User user) {
        this.startDate = startDate;
        this.user = user;
        this.subscriptionId = subscriptionCount++;
    }

    // Getters
    public Date getStartDate() {
        return startDate;
    }

    public User getUser() {
        return user;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    // ToString override for better object representation
    @Override
    public String toString() {
        return "Subscription{" +
                "startDate=" + startDate +
                ", user=" + user.getName() +
                ", plan=" + user.getSubscriptionPlan() +
                ", subscriptionId=" + subscriptionId +
                '}';
    }
}