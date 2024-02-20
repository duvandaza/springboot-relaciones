package com.duvan.curso.springboot.jpa.springbootjpareationship.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients_details")
public class ClientDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean premium;
    private Integer point;

    @OneToOne
    @JoinColumn(name = "id_cliente")
    private Client client;

    public ClientDetails() {}

    public ClientDetails(boolean premium, Integer point) {
        this.premium = premium;
        this.point = point;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean isPremium() {
        return premium;
    }
    public void setPremium(boolean premium) {
        this.premium = premium;
    }
    public Integer getPoint() {
        return point;
    }
    public void setPoint(Integer point) {
        this.point = point;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", premium=" + premium + ", point=" + point + "}";
    }
    
}
