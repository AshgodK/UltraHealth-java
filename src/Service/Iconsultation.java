/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;
import java.util.List;

/**
 *
 * @author ASUS
 * @param <C>
 */
public interface Iconsultation <C> {
     public void Ajouterconsultation(C c);
      public void modifierconsultation(C l, String nom,String prenom,int num_seance,String description );
      public void supprimerconsultation (C c);
      public List<C>Recupererconsultation();
    
}
