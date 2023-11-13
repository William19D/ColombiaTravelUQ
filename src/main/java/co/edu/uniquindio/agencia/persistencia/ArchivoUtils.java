package co.edu.uniquindio.agencia.persistencia;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

/**
 * Clase de utilidad para la lectura y escritura de archivos
 * @author caflorezvi
 *
 */
public class ArchivoUtils {

    /**
     * Permite leer un archivo desde una ruta específica mediante Scanner
     * @param ruta Ruta a leer
     * @return Lista de String por cada línea del archivo
     * @throws IOException
     */
    public static ArrayList<String> leerArchivoScanner(String ruta) throws IOException{

        ArrayList<String> lista = new ArrayList<>();
        Scanner sc = new Scanner(new File(ruta));

        while(sc.hasNextLine()) {
            lista.add(sc.nextLine());
        }

        sc.close();

        return lista;
    }

    /**
     * Permite leer un archivo desde una ruta específica mediante BufferedReader
     * @param ruta Ruta a leer
     * @return Lista de String por cada línea del archivo
     * @throws IOException
     */
    public static ArrayList<String> leerArchivoBufferedReader(String ruta) throws IOException{

        ArrayList<String> lista = new ArrayList<>();
        FileReader fr = new FileReader(ruta);
        BufferedReader br = new BufferedReader(fr);
        String linea;

        while( ( linea = br.readLine() )!=null ) {
            lista.add(linea);
        }

        br.close();
        fr.close();

        return lista;
    }

    /**
     * Escribe datos en un archivo de texo
     * @param ruta Ruta donde se va a crear el archivo
     * @param lista Datos que se escriben en el archivo
     * @throws IOException
     */
    public static void escribirArchivoFormatter(String ruta, List<String> lista) throws IOException{
        Formatter ft = new Formatter(ruta);
        for(String s : lista){
            ft.format(s+"%n");
        }
        ft.close();
    }

    /**
     * Escribe datos en un archivo de texo
     * @param ruta ruta Ruta donde se va a crear el archivo
     * @param lista Información a guardar en el archivo
     * @param concat True si se concatena los nuevos datos sin sobreescibir todo el archivo
     * @throws IOException
     */
    public static void escribirArchivoBufferedWriter(String ruta, List<String> lista, boolean concat) throws IOException{

        FileWriter fw = new FileWriter(ruta, concat);
        BufferedWriter bw = new BufferedWriter(fw);

        for (String string : lista) {
            bw.write(string);
            bw.newLine();
        }

        bw.close();
        fw.close();
    }

    /**
     * Serializa un objeto en disco
     * @param ruta Ruta del archivo donde se va a serializar el objeto
     * @param objeto Objeto a serializar
     * @throws IOException
     */
    public static void serializarObjeto(String ruta, Object objeto) throws IOException{
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(ruta));
        os.writeObject(objeto);
        os.close();
    }

    /**
     * Deserializa un objeto que está guardado en disco
     * @param ruta Ruta del archivo a deserializar
     * @return Objeto deserializado
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Object deserializarObjeto(String ruta) throws IOException, ClassNotFoundException {
        ObjectInputStream is = new ObjectInputStream(new FileInputStream(ruta));
        Object objeto = is.readObject();
        is.close();

        return objeto;
    }

    /**
     * Serializa un objeto en un archivo en formato XML
     * @param ruta Ruta del archivo donde se va a serializar el objeto
     * @param objeto Objeto a serializar
     * @throws FileNotFoundException
     */
    public static void serializarObjetoXML(String ruta, Object objeto) throws FileNotFoundException {
        XMLEncoder encoder = new XMLEncoder(new FileOutputStream(ruta));
        encoder.writeObject(objeto);
        encoder.close();
    }

    /**
     * Deserializa un objeto desde un archivo XML
     * @param ruta Ruta del archivo a deserializar
     * @return Objeto deserializado
     * @throws IOException
     */
    public static Object deserializarObjetoXML(String ruta) throws IOException{
        XMLDecoder decoder = new XMLDecoder(new FileInputStream(ruta));
        Object objeto = decoder.readObject();
        decoder.close();

        return objeto;
    }

    public static Object cargarRecursoSerializado(String rutaArchivo)throws Exception
    {
        Object aux = null;
//		MarketplaceVendedores marketplace = null;
        ObjectInputStream ois = null;
        try {
            // Se crea un ObjectInputStream
            ois = new ObjectInputStream(new FileInputStream(rutaArchivo));

            aux = ois.readObject();

        } catch (Exception e2) {
            File file = new File("C:\\td\\persistencia/Model.dat");
            file.createNewFile();
        } finally {
            if (ois != null)
                ois.close();
        }
        return aux;
    }
    public static void salvarRecursoSerializado(String rutaArchivo, Object object)	throws Exception {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo));
            oos.writeObject(object);
        } catch (Exception e) {
            throw e;
        } finally {
            if (oos != null)
                oos.close();
        }
    }
    public static Object cargarRecursoSerializadoXML(String rutaArchivo) throws IOException {

        XMLDecoder decodificadorXML;
        Object objetoXML;

        decodificadorXML = new XMLDecoder(new FileInputStream(rutaArchivo));
        objetoXML = decodificadorXML.readObject();
        decodificadorXML.close();
        return objetoXML;
    }

    public static void salvarRecursoSerializadoXML(String rutaArchivo, Object objeto) throws IOException {

        XMLEncoder codificadorXML;

        codificadorXML = new XMLEncoder(new FileOutputStream(rutaArchivo));
        codificadorXML.writeObject(objeto);
        codificadorXML.close();

    }

}