package co.edu.uniquindio.alquiler.model;

import co.edu.uniquindio.alquiler.exceptions.AtributoVacioException;
import co.edu.uniquindio.alquiler.exceptions.InformacionRepetidaException;
import javafx.stage.FileChooser;
import lombok.*;
import lombok.extern.java.Log;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


@Getter
@Log
public class AgenciaViajes {

    private static AgenciaViajes agenciaViajes;
    public static String RUTACLIENTES = null;
    public static String RUTAGUIAS= null;
    public static String RUTADESTINOS= null;
    public static String RUTAPAQUETES= null;

    private static ArrayList<GuiasTuristicos> guias;
    private static ArrayList<PaquetesTuristicos> paquetes;
    private static ArrayList<Cliente> clientes;
    private static ArrayList<Destino> destinos;

    private AgenciaViajes(){
        inicializarLogger();
        log.info("Se crea una nueva instancia de Colombia Travel" );
        try {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
            prop.load(fis);
            RUTACLIENTES = prop.getProperty("rutaClientes");
            RUTAGUIAS = prop.getProperty("rutaGuias");
            RUTADESTINOS = prop.getProperty("rutaDestinos");
            RUTAPAQUETES = prop.getProperty("rutaPaquetes");
        } catch (IOException e) {
            log.severe("Ocurrio un error al momento de cargar las propiedades");
        }
        clientes = new ArrayList<>();
        leerClientes();

        guias = new ArrayList<>();
        leerGuias();

        paquetes= new ArrayList<>();
        leerPaquete();

        destinos = new ArrayList<>();
        leerDestinos();

    }
    private void inicializarLogger(){
        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter( new SimpleFormatter());
            log.addHandler(fh);
        }catch (IOException e){
            log.severe(e.getMessage() );
        }
    }

    public static AgenciaViajes getInstance(){
        if(agenciaViajes == null){
            agenciaViajes = new AgenciaViajes();
        }

        return agenciaViajes;
    }
    public Cliente registrarCliente(String cedula, String nombreCompleto,String email,String direccion,String ciudad,String telefono,String contrasenia) throws AtributoVacioException, InformacionRepetidaException {

        if(cedula == null || cedula.isBlank()){
            throw new AtributoVacioException("La cédula es obligatoria");
        }

        if( obtenerCliente(cedula) != null ){
            throw new InformacionRepetidaException("La cédula "+cedula+" ya está registrada");
        }

        if(nombreCompleto == null || nombreCompleto.isBlank()){
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if(email == null || email.isBlank()){
            throw new AtributoVacioException("El email es obligatorio");
        }

        //Demás validaciones

        Cliente cliente = Cliente.builder()
                .cedula(cedula)
                .nombre(nombreCompleto)
                .correo(email)
                .direccion(direccion)
                .telefono(telefono)
                .contrasenia(contrasenia)
                .build();

        clientes.add(cliente);
        escribirCliente(cliente);

        log.info("Se ha registrado un nuevo cliente con la cédula: "+cedula);
        return cliente;
    }
    private void escribirCliente(Cliente cliente){
        try {
            String linea = cliente.getCedula()+";"+cliente.getNombre()+";"+cliente.getCedula()+";"+cliente.getCorreo()+";"+cliente.getTelefono()+";"+cliente.getDireccion()+";"+cliente.getContrasenia();
            ArchivoUtils.escribirArchivoBufferedWriter(RUTACLIENTES, List.of(linea), true);
        }catch (IOException e){
            log.severe(e.getMessage());
        }
    }
    public Cliente obtenerCliente(String cedula){
        return clientes.stream().filter(c -> c.getCedula().equals(cedula)).findFirst().orElse(null);
    }
    private void leerClientes() {

        try{

            ArrayList<String> lineas = ArchivoUtils.leerArchivoScanner(RUTACLIENTES);

            for(String linea : lineas){

                String[] valores = linea.split(";");
                clientes.add( Cliente.builder()
                        .cedula(valores[0])
                        .nombre(valores[1])
                        .correo(valores[2])
                        .direccion(valores[3])
                        .telefono(valores[4])
                        .contrasenia(valores[5])
                        .build());
            }

        }catch (IOException e){
            log.severe(e.getMessage());
        }

    }
    public static boolean verificarLoginUsuario(String usuario, String contrasenia) {
        boolean existe = false;
        for (Cliente cliente : clientes) {
            if (cliente.getCorreo().equals(usuario) && cliente.getContrasenia().equals(contrasenia)) {
                existe = true; // Veterinario encontrado
                break; // Salir del ciclo una vez que encuentra al cliente
            }
        }
        return existe;
    }
    //DESTINOS
    public Destino registrarDestino(String nombre, String descripcion, FileChooser fileChooser, Clima clima) throws AtributoVacioException {
        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre del destino es obligatorio");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new AtributoVacioException("La descripción es obligatoria");
        }
        if (fileChooser == null || fileChooser.showOpenDialog(null) == null) {
            throw new AtributoVacioException("Debes seleccionar una imagen");
        }
        if (clima == null) {
            throw new AtributoVacioException("El clima es obligatorio");
        }

        //Demás validaciones
        ArrayList<File> imagenes = new ArrayList<>();
        File imagenSeleccionada = fileChooser.showOpenDialog(null);
        imagenes.add(imagenSeleccionada);

        Destino destino = Destino.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .rutasImagenes(imagenes)
                .clima(clima)
                .build();

        destinos.add(destino);
        escribirDestino();

        log.info("Se ha registrado un nuevo destino con el nombre: " + nombre);
        return destino;
    }
    private void escribirDestino(){
        try{
            ArchivoUtils.serializarObjeto(RUTADESTINOS, destinos);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }
    // Metodo que lee los paquetes turisticos
    private void leerDestinos(){

        try {
            ArrayList<Destino> lista = (ArrayList<Destino>) ArchivoUtils.deserializarObjeto(RUTADESTINOS);
            if(lista != null){
                destinos = lista;
            }
        } catch (IOException | ClassNotFoundException e) {
            log.severe(e.getMessage());
        }

    }


// Metodo que reguistra los guias turisticos

    public GuiasTuristicos registrarGuias(String nombre, String identificacion,Idiomas idiomas,String exp) throws AtributoVacioException, InformacionRepetidaException {

        if(identificacion == null || identificacion.isBlank()){
            throw new AtributoVacioException("La identificacion es obligatoria");
        }

        if( obtenerGuia(identificacion) != null ){
            throw new InformacionRepetidaException("La identificacion "+identificacion+" ya está registrada");
        }

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if(exp == null || exp.isBlank()){
            throw new AtributoVacioException("la experiencia es obligatoria");
        }


        //Demás validaciones

        GuiasTuristicos guia = GuiasTuristicos.builder()
                .nombre(nombre)
                .identificacion(identificacion)
                .idiomas(idiomas)
                .exp(exp)
                .build();

        guias.add(guia);
        escribirGuias(guia);

        log.info("Se ha registrado un nuevo guia con la identificacion: "+identificacion);
        return guia;
    }

    public GuiasTuristicos obtenerGuia(String identificacion){
        return guias.stream().filter(c -> c.getIdentificacion().equals(identificacion)).findFirst().orElse(null);
    }

    //Metodo que escribe los Guias Turisticos

    private void escribirGuias(GuiasTuristicos guias){
        try {
            String linea = guias.getNombre()+";"+guias.getIdentificacion()+";"+guias.getIdiomas()+";"+guias.getExp();
            ArchivoUtils.escribirArchivoBufferedWriter(RUTAGUIAS, List.of(linea), true);
        }catch (IOException e){
            log.severe(e.getMessage());
        }
    }

    //Metodo que lee los guias Turisticos
    private void leerGuias() {

        try{

            ArrayList<String> lineas = ArchivoUtils.leerArchivoScanner(RUTAGUIAS);

            for(String linea : lineas){

                String[] valores = linea.split(";");
                guias.add( GuiasTuristicos.builder()

                        .nombre(valores[0])
                        .identificacion(valores[1])
                        .idiomas(Idiomas.valueOf(valores[2]))
                        .exp(valores[3])
                        .build());
            }

        }catch (IOException e){
            log.severe(e.getMessage());
        }

    }

    // Metodo que registra los Paquetes turisticos

    public PaquetesTuristicos registrarPaquetes(String nombre, String direccion, String serviciosAdicionales, float precio, int cupoMax, LocalDate fechaDisponible) throws AtributoVacioException, InformacionRepetidaException {

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre es obligatorip");
        }

        if( obtenerPaquete(nombre) != null ){
            throw new InformacionRepetidaException("El paquete "+nombre+" ya está registrado");
        }

        if(direccion == null || direccion.isBlank()){
            throw new AtributoVacioException("La direccion es obligatoria");
        }

        if(serviciosAdicionales == null || serviciosAdicionales.isBlank()){
            throw new AtributoVacioException("Añade servicios adicionales");
        }
        if(precio == 0 ){
            throw new AtributoVacioException("El precio es obligatorio");
        }
        if(cupoMax == 0){
            throw new AtributoVacioException("El cupo Maximo es obligatorio");
        }
        if(fechaDisponible == null){
            throw new AtributoVacioException("Ingrese la fecha donde el paquete esta disponible");
        }


        //Demás validaciones

        PaquetesTuristicos paquete = PaquetesTuristicos.builder()
                .nombre(nombre)
                .direccion(direccion)
                .serviciosAdicionales(serviciosAdicionales)
                .precio(precio)
                .cupoMax(cupoMax)
                .build();

        paquetes.add(paquete);
        escribirPaquete();

        log.info("Se ha registrado un nuevo paquete con el nombre: "+nombre);
        return paquete;
    }
    public PaquetesTuristicos obtenerPaquete(String nombre){
        return paquetes.stream().filter(c -> c.getNombre().equals(nombre)).findFirst().orElse(null);
    }
// Metodo que escribe los paquetes turisticos
    private void escribirPaquete(){
        try{
            ArchivoUtils.serializarObjeto(RUTAPAQUETES, paquetes);
        }catch (Exception e){
            log.severe(e.getMessage());
        }
    }
// Metodo que lee los paquetes turisticos
    private void leerPaquete(){

        try {
            ArrayList<PaquetesTuristicos> lista = (ArrayList<PaquetesTuristicos>) ArchivoUtils.deserializarObjeto(RUTAPAQUETES);
            if(lista != null){
                paquetes = lista;
            }
        } catch (IOException | ClassNotFoundException e) {
            log.severe(e.getMessage());
        }

    }

}
