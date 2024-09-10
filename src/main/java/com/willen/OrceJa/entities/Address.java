package com.willen.OrceJa.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id")
    private UUID addressId;

    private String city;

    private String Street;

    private String state;

    private int number;

    private boolean isDefault;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Client client;

    public Address() {
    }

    public Address(UUID addressId, String city, String street, String state, int number, boolean isDefault) {
        this.addressId = addressId;
        this.city = city;
        this.Street = street;
        this.state = state;
        this.number = number;
        this.isDefault = isDefault;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
