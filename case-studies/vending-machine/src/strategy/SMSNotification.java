package strategy;

public class SMSNotification implements NotificationStrategy {
  @Override
  public void sendNotification(String message) {
    System.out.println("Sending SMS notification: " + message);
  }
}
