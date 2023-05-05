/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Model.Response;
import java.util.List;

/**
 *
 * @author hp
 */
public interface ResponseInterface {
     public void addResponse(Response r);
    public void deleteResponse(int id);
    public void updateResponse(Response r);
    public List<Response> displayResponse();
}
