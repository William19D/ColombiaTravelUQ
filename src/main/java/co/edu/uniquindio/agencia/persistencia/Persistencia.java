package co.edu.uniquindio.agencia.persistencia;

import co.edu.uniquindio.agencia.model.AgenciaViajes;

import java.util.Properties;

public class Persistencia {
    public static final String RUTA_ARCHIVO_MODELO_PROCESOS_BINARIO = (new Properties()).getProperty("rutaAgencia");
    public static final String RUTA_ARCHIVO_MODELO_PROCESOS_XML = "C:\\td\\persistencia_estructura/Encript.xml";


    public static AgenciaViajes cargarRecursoXML() {

        AgenciaViajes agenciaViajes = null;

        try {
          agenciaViajes = (AgenciaViajes) ArchivoUtils.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_PROCESOS_XML);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return agenciaViajes;

    }

    public static void guardarRecursoXML(AgenciaViajes agenciaViajes) {

        try {
            ArchivoUtils.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_PROCESOS_XML, agenciaViajes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static AgenciaViajes cargarRecursoBinario() {

        AgenciaViajes agenciaViajes = null;

        try {
            agenciaViajes = (AgenciaViajes) ArchivoUtils.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_PROCESOS_BINARIO);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return agenciaViajes;
    }

    public static void guardarRecursoBinario(AgenciaViajes agenciaViajes) {

        try {
            ArchivoUtils.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_PROCESOS_BINARIO, agenciaViajes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
