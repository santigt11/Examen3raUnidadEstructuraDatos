/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.Utilidades;

import Controlador.PozoDao.PozoControl1;
import Controlador.grafos.exception.VerticeException;
import Modelo.PozosLuz;
import controlador.TDA.grafos.GrafosEtiquetadosDirigidos;
import controlador.TDA.listas.DynamicList;
import controlador.Utiles.UtilesFoto;
import java.io.FileWriter;
import java.util.Random;
import javax.swing.JComboBox;

/**
 *
 * @author Alejandro
 */
public class UtilVistaActividad {
    public static void crearMapaPoste(GrafosEtiquetadosDirigidos<PozosLuz> ge) throws VerticeException, Exception {
         String maps
                = "var osmUrl = 'https://tile.openstreetmap.org/{z}/{x}/{y}.png',\n"
                + "                    osmAttrib = '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors',\n"
                + "                    osm = L.tileLayer(osmUrl, {maxZoom: 15, attribution: osmAttrib});\n"
                + "\n"
                + "            var map = L.map('map').setView([-4.036, -79.201], 15);\n"
                + "\n"
                + "            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {\n"
                + "                attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'\n"
                + "            }).addTo(map);" + "\n";
        for (int i = 1; i <= ge.num_vertice(); i++) {
            PozosLuz ec = ge.getLabelE(i);
            maps += "L.marker([" + ec.getCoordenada().getLatitud() + ", " + ec.getCoordenada().getLongitud() + "]).addTo(map)" + "\n";
            maps += ".bindPopup(\"" + ec.toString() + "\")" + "\n";
            maps += ".openPopup();" + "\n";
        }
        FileWriter file = new FileWriter("mapas/mapa.js");
        file.write(maps);
        file.close();

    }
    

    public static void cargarComboPoste(JComboBox cbx) throws Exception {
        cbx.removeAllItems();
        DynamicList<PozosLuz> list = new PozoControl1().getList();
        for (int i = 0; i < list.getLength(); i++) {
            cbx.addItem(list.getInfo(i));
        }
    }
    public static Double CalcularDistanciaPoste(PozosLuz o,PozosLuz d){
        Double dist=UtilesFoto.coordGpsToKm(o.getCoordenada().getLatitud(),
                    o.getCoordenada().getLongitud(),
                    d.getCoordenada().getLatitud(),
                    d.getCoordenada().getLongitud());
        return dist;
    }
    public static Double CalcularDistanciaVariada(PozosLuz o, PozosLuz d) {
        Random random = new Random();
        
        // Generar una distancia base
        double distanciaBase = UtilesFoto.coordGpsToKm(
                o.getCoordenada().getLatitud(),
                o.getCoordenada().getLongitud(),
                d.getCoordenada().getLatitud(),
                d.getCoordenada().getLongitud()
        );
        
        // Introducir variabilidad aleatoria
        double variabilidad = random.nextDouble() * 2 - 1; // Valor aleatorio entre -1 y 1
        double distanciaVariada = distanciaBase * (1 + variabilidad * 0.2); // Ajusta la variabilidad según tu preferencia
        
        return distanciaVariada;
    }

}
