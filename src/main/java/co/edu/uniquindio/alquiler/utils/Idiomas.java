package co.edu.uniquindio.alquiler.utils;

import lombok.Getter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Idiomas {

    @Getter
    private ResourceBundle resourceBundle;

    private static Idiomas idiomas;

    @Getter
    private String idiomaActual;

    private Idiomas(){
        idiomaActual = leerIdioma();
        resourceBundle = ResourceBundle.getBundle("textos", new Locale(idiomaActual));
    }

    public static Idiomas getInstance(){
        if (idiomas == null) {
            idiomas = new Idiomas();
        }

        return idiomas;
    }

    public String leerIdioma(){
        try {
            Properties props = new Properties();
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            props.load(fis);
            fis.close();
            return props.getProperty("idioma");

        }catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public void cambiarIdioma(String nuevo){
        try {

            this.idiomaActual = nuevo;
            this.resourceBundle = ResourceBundle.getBundle("textos", new Locale(idiomaActual));

            Properties props = new Properties();
            FileOutputStream fos = new FileOutputStream("src/main/resources/config.properties");
            props.setProperty("idioma", nuevo);
            props.store(fos, "");
            fos.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
