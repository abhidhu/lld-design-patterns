package model;

import java.time.LocalDate;
import java.util.Random;

public class Librarian extends Person {
  private Long employeeId;
  private String department;

  public Librarian(String name, LocalDate dateOfBirth, String email, String department) {
    super(name, dateOfBirth, email);
    this.employeeId = new Random().nextLong();
    this.department = department;
  }

  public Long getEmployeeId() {
    return employeeId;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }
}
