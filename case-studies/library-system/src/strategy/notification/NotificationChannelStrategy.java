package strategy.notification;

import model.Reservation;

public interface NotificationChannelStrategy {
  void sendNotification(Reservation reservation);
}
