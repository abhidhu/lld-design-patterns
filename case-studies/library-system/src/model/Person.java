package model;

import java.time.LocalDate;

public abstract class Person {
  private String name;
  private LocalDate dateOfBirth;
  private String email;
  //No field is allowed to modified latter, so we will not provide setter method for any field
  public Person(String name, LocalDate dateOfBirth, String email) {
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public String getEmail() {
    return email;
  }

}
