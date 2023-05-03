/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author ASUS
 */
public class Consultation {
    
    private Integer id;
    private String nom,prenom,description;
    private Integer num_seance;

    public Consultation() {
        //To change body of generated methods, choose Tools | Templates.
    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNum_seance() {
        return num_seance;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNum_seance(Integer num_seance) {
        this.num_seance = num_seance;
    }

    @Override
    public String toString() {
        return "Consultation{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", description=" + description + ", num_seance=" + num_seance + '}';
    }

    public Consultation(Integer id, String nom, String prenom, String description, Integer num_seance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.description = description;
        this.num_seance = num_seance;
    }

    public Consultation(String nom, String prenom, String description, Integer num_seance) {
        this.nom = nom;
        this.prenom = prenom;
        this.description = description;
        this.num_seance = num_seance;
    }
    
    
    
    
    
}
