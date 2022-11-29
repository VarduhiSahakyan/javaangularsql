package com.energizeglobal.sqlgenerator.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CryptoConfig")
public class CryptoConfigurationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "protocolOne")
    private String protocolOne;

    @Column(name = "protocolTwo")
    private String protocolTwo;

    @Column(name = "description")
    private String description;

//    @OneToMany(mappedBy = "cryptoConfigurationEntity")
//    private List<BinRangeEntity> linkedBinRanges;

    @OneToMany(mappedBy = "cryptoConfigEntity", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<SubIssuer> subIssuers;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cryptoConfigEntity")
//    @JsonIgnore
//    private List<SubIssuer> subIssuers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<BinRangeEntity> getLinkedBinRanges() {
//        return linkedBinRanges;
//    }
//
//    public void setLinkedBinRanges(List<BinRangeEntity> linkedBinRanges) {
//        this.linkedBinRanges = linkedBinRanges;
//    }
//
    public List<SubIssuer> getSubIssuers() {
        return subIssuers;
    }

    public void setSubIssuers(List<SubIssuer> subIssuers) {
        this.subIssuers = subIssuers;
    }

}
