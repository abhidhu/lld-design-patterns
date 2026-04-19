package strategy.notification;

import model.Reservation;

public class SMSNotificationChannel implements NotificationChannelStrategy {

  @Override
  public void sendNotification(Reservation reservation) {
    //logic to send email
    System.out.println(
      "SMS Notification has been sent to member " + reservation.getMember().getName() + " for reservation id "
        + reservation.getReservationId() + " book name " + reservation.getBook().getTitle());
  }
}
