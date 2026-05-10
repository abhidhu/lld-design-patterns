package order;

import java.time.LocalDateTime;
import java.util.UUID;

import enums.OrderStatus;
import product.Product;

public class Order {
  private String orderId;
  private double amountPaid;
  private double amountDue;
  private double change;
  private OrderStatus orderStatus;
  private LocalDateTime timestamp;
  private String mobileNo;
  private String email;
  private Product product;

  public Order(
    Product product,
    double amountDue,
    String mobileNo,
    String email
  ) {
    this.orderId = UUID.randomUUID().toString();
    this.amountDue = amountDue;
    this.product = product;
    this.orderStatus = OrderStatus.CREATED;
    this.timestamp = LocalDateTime.now();
    this.mobileNo = mobileNo;
    this.email = email;
  }

  public void setAmountPaid(double amountPaid) {
    this.amountPaid = amountPaid;
  }

  public void setAmountDue(double amountDue) {
    this.amountDue = amountDue;
  }

  public void setChange(double change) {
    this.change = change;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public String getOrderId() {
    return orderId;
  }

  public double getAmountPaid() {
    return amountPaid;
  }

  public double getChange() {
    return change;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public String getMobileNo() {
    return mobileNo;
  }

  public String getEmail() {
    return email;
  }

  public double getAmountDue() {
    return amountDue;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
