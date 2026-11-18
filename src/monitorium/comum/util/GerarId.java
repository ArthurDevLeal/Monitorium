/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitorium.comum.util;

import java.util.UUID;

/**
 *
 * @author arthu
 */
public final class GerarId {
    public static String gerarId() {
        return UUID.randomUUID().toString();
    }
}
