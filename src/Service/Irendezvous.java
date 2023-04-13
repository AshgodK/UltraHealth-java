/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Service;

import java.util.List;

/**
 *
 * @author ASUS
 * @param <R>
 */
public interface Irendezvous <R> {
     public void Ajouterrendezvous(R r);
      public void modifierrendezvous(R l,int id, String etat,String message,String type_lieu  );
      public void supprimerrendez (R r);
      public List<R>Recuperer();
    
}
