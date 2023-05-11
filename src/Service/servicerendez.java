package Service;


import Entity.Rendezvous;
import util.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;



public class servicerendez implements Irendezvous<Rendezvous> {
    // ...


    private Integer id;
    private LocalDateTime date_rdv;
    private LocalTime heure;

    public servicerendez() {
    }

    public servicerendez(Integer id, LocalDateTime date_rdv, LocalTime heure) {
        this.id = id;
        this.date_rdv = date_rdv;
        this.heure = heure;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate_rdv() {
        return date_rdv;
    }

    public void setDate_rdv(LocalDateTime date_rdv) {
        this.date_rdv = date_rdv;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    @Override
    public String toString() {
        return "ServiceRendez{" +
                "id=" + id +
                ", date_rdv=" + date_rdv +
                ", heure=" + heure +
                '}';
    }




    
    
    
     public void Ajouterrendezvous(Rendezvous rendezvous) {
        String query = "INSERT INTO rendez_vous ( date_rdv, heure) VALUES (?, ?)";
        try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            //statement.setInt(1, rendezvous.getId());
            statement.setTimestamp(1, java.sql.Timestamp.valueOf(rendezvous.getDate_rdv()));
            statement.setTime(2, java.sql.Time.valueOf(rendezvous.getHeure()));
            statement.executeUpdate();
            System.out.println("Rendezvous ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du rendezvous : " + e.getMessage());
        }
    }

    public void modifierrendezvous(Rendezvous rendezvous, int newId, LocalDateTime newDateTime) {
        String query = "UPDATE rendez_vous SET date_rdv = ? WHERE id = ?";
        try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            //statement.setInt(1, newId);
            statement.setTimestamp(1, java.sql.Timestamp.valueOf(newDateTime));
            statement.setInt(2, rendezvous.getId());
            statement.executeUpdate();
            System.out.println("Rendezvous modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification du rendezvous : " + e.getMessage());
        }
    }

    public void supprimerrendez(Rendezvous rendezvous) {
        String query = "DELETE FROM rendez_vous WHERE id = ?";
        try (PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            statement.setInt(1, rendezvous.getId());
            statement.executeUpdate();
            System.out.println("Rendezvous supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du rendezvous : " + e.getMessage());
        }
    }

    

    public List<Rendezvous> Recuperer() {
        List<Rendezvous> rendezvousList = new ArrayList<>();

        // Code pour récupérer les données de la source de données
        try {
            String requete = "SELECT id, date_rdv, heure FROM rendez_vous";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDateTime date_rdv = rs.getTimestamp("date_rdv").toLocalDateTime();
                LocalTime heure = rs.getTime("heure").toLocalTime();
                rendezvousList.add(new Rendezvous(id, date_rdv, heure));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return rendezvousList;
    }

    // ...

public void modifierrendezvous(Rendezvous l, int id, LocalDateTime date_rdv, LocalTime heure) {
    // Logique de modification du rendezvous
    // Utilisez les paramètres 'id', 'date_rdv' et 'heure' pour mettre à jour le rendezvous 'l'
    // ...

    // Exemple de code :
    l.setId(id);
    l.setDate_rdv(date_rdv);
    l.setHeure(heure);

    // Appelez la méthode de mise à jour appropriée
    modifierrendezvous(l, id, date_rdv,heure); // Appelle la méthode existante qui accepte le rendezvous et les nouvelles valeurs

    System.out.println("Rendezvous modifié avec succès !");
}

    @Override
    public void modifierrendezvous(Rendezvous l, int id, String etat, String message, String type_lieu, String date) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    }










