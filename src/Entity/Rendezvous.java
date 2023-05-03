/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author ASUS
 */
public class Rendezvous {
    private Integer id;
   private String date_rdv; 
   private String etat, message, type_lieu;

    @Override
    public String toString() {
        return "Rendezvous{" + "id=" + id + ", date_rdv=" + date_rdv + ", etat=" + etat + ", message=" + message + ", type_lieu=" + type_lieu + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate_rdv() {
        return date_rdv;
    }

    public void setDate_rdv(String date_rdv) {
        this.date_rdv = date_rdv;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getMessage() {
        return message;
    }

    public Rendezvous() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType_lieu() {
        return type_lieu;
    }

    public void setType_lieu(String type_lieu) {
        this.type_lieu = type_lieu;
    }

    public Rendezvous(Integer id, String date_rdv, String etat, String message, String type_lieu) {
        this.id = id;
        this.date_rdv = date_rdv;
        this.etat = etat;
        this.message = message;
        this.type_lieu = type_lieu;
    }

    public Rendezvous(String date_rdv, String etat, String message, String type_lieu) {
        this.date_rdv = date_rdv;
        this.etat = etat;
        this.message = message;
        this.type_lieu = type_lieu;
    }

}
