/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitorium.comum.util;

import monitorium.comum.excecao.RegraNegocioException;

public final class Exigir {
    
    private Exigir(){
        
    }
    
    public static String exigir(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new RegraNegocioException("O campo " + campo + " é obrigatório.");
        }
        return valor.trim();
    }


    public static <T> T exigir(T valor, String campo) {
        if (valor == null) {
            throw new RegraNegocioException("O campo " + campo + " é obrigatório.");
        }
        return valor;
    }
}
