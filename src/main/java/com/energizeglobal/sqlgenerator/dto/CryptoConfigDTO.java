package com.energizeglobal.sqlgenerator.dto;

public class CryptoConfigDTO {

  private Long id;

  private String description;

  private String protocolOne;

  private String protocolTwo;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getProtocolOne() {
    return protocolOne;
  }

  public void setProtocolOne(String protocolOne) {
    this.protocolOne = protocolOne;
  }

  public String getProtocolTwo() {
    return protocolTwo;
  }

  public void setProtocolTwo(String protocolTwo) {
    this.protocolTwo = protocolTwo;
  }

  @Override
  public String toString() {
    return "CryptoConfigDTO{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", protocolOne='" + protocolOne + '\'' +
            ", protocolTwo='" + protocolTwo + '\'' +
            '}';
  }
}
