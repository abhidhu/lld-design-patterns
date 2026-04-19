package strategy.notification;

import model.Reservation;

public class EmailNotificationChannel implements NotificationChannelStrategy {

  @Override
  public void sendNotification(Reservation reservation) {
    //logic to send sms
    System.out.println(
      "Email Notification has been sent to member " + reservation.getMember().getName() + " for reservation id "
        + reservation.getReservationId() + " book name " + reservation.getBook().getTitle());
  }
}
