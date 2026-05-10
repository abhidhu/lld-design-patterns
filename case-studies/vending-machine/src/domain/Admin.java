package domain;

import enums.NotificationStrategyEnum;
import observer.Observer;
import strategy.EmailNotification;
import strategy.NotificationStrategy;
import strategy.SMSNotification;

public class Admin implements Observer {
  private String name;
  private String id;
  private NotificationStrategy notificationStrategy;

  public Admin(String name, String id, NotificationStrategyEnum strategyEnum) {
    this.name = name;
    this.id = id;
    this.notificationStrategy = switch(strategyEnum) {
      case EMAIL -> new EmailNotification();
      case SMS -> new SMSNotification();
    };
  }

  @Override
  public void update(String message) {
    notificationStrategy.sendNotification(message);
  }
}
