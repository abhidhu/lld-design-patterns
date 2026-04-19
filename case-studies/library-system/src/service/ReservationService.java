package service;

import java.util.HashMap;
import java.util.Map;

import enums.NotificationChannelType;
import model.Book;
import model.Reservation;
import enums.ReservationStatus;
import strategy.notification.EmailNotificationChannel;
import strategy.notification.SMSNotificationChannel;
import observer.Observer;
import strategy.notification.NotificationChannelStrategy;

public class ReservationService implements Observer {

  private static final ReservationService reservationService = new ReservationService();

  private ReservationService() {
    if (reservationService != null) {
      throw new IllegalCallerException("Can not create more than 1 bean");
    }
  }

  public static ReservationService getInstance() {
    return reservationService;
  }

  private static final Map<NotificationChannelType, NotificationChannelStrategy> notificationChannelMap = new HashMap<>();
  //in real app we will fetch this from yml file or configuration service
  private static final NotificationChannelType NOTIFICATION_CHANNEL_TYPE = NotificationChannelType.SMS;

  static {
    //    notificationChannelMap.put() we will put every new startegy here
    notificationChannelMap.put(NotificationChannelType.SMS, new SMSNotificationChannel());
    notificationChannelMap.put(NotificationChannelType.EMAIL, new EmailNotificationChannel());
  }

  @Override
  public void update(Book book) {
    Reservation reservation = book
      .getReservations()
      .stream()
      .filter(res -> res.getReservationStatus().equals(ReservationStatus.OPEN))
      .findFirst()
      .orElse(null);
    if (reservation != null) {
      notifyMember(reservation);
    }
  }

  private void notifyMember(Reservation reservation) {
    //after new strategy add this blocks needs to be updated, but we can make changes for this, in way that we not need to update this block
    notificationChannelMap.get(getNotificationChannelType(reservation)).sendNotification(reservation);
  }

  private NotificationChannelType getNotificationChannelType(Reservation reservation) {
    if (reservation.getNotificationChannelType() != null) {
      return reservation.getNotificationChannelType();
    }
    else if (reservation.getMember() != null && reservation.getMember().getNotificationChannelType() != null) {
      return reservation.getMember().getNotificationChannelType();
    }
    else {
      return NOTIFICATION_CHANNEL_TYPE;
    }
  }
}
