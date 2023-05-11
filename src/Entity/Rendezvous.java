package Entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Rendezvous {
    private Integer id;
    private LocalDateTime date_rdv;
    private LocalTime heure;

    public Rendezvous() {
    }

    public Rendezvous(Integer id, LocalDateTime date_rdv, LocalTime heure) {
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
        return "Rendezvous{" +
                "id=" + id +
                ", date_rdv=" + date_rdv +
                ", heure=" + heure +
                '}';
    }


    @Override
public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }
    Rendezvous rendezvous = (Rendezvous) obj;
    return id.equals(rendezvous.id);
}

@Override
public int hashCode() {
    return Objects.hash(id);
}

    
}
