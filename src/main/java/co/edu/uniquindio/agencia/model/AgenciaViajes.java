package co.edu.uniquindio.agencia.model;

import co.edu.uniquindio.agencia.exceptions.*;
import com.sun.javafx.iio.ImageLoader;
import javafx.stage.FileChooser;
import lombok.*;
import lombok.extern.java.Log;

import java.io.*;
import java.net.URL;
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
    public static String RUTARESERVAS= null;

    public final ArrayList<GuiaTuristico> guias;
    private ArrayList<PaquetesTuristicos> paquetes;
    private static ArrayList<Cliente> clientes;
    private ArrayList<Destino> destinos;
    private ArrayList<Reserva> reservas;
    private static ArrayList<Administrador> administradores;

    private AgenciaViajes() throws RutaInvalidaException, AtributoVacioException, InformacionRepetidaException, DestinoRepetidoException {
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
            RUTARESERVAS = prop.getProperty("rutaReservas");

        } catch (IOException e) {
            log.severe("Ocurrio un error al momento de cargar las propiedades");
        }

        administradores = new ArrayList<>();
        administradores.add(new Administrador("Daniel", "123"));
        administradores.add(new Administrador("Camila", "123"));
        administradores.add(new Administrador("Juan Pablo", "123"));
        administradores.add(new Administrador("caflorezvi", "123"));

        clientes = new ArrayList<>();
        leerClientes();

        guias = new ArrayList<>();
        leerGuias();

        paquetes= new ArrayList<>();
        leerPaquete();

        destinos = new ArrayList<>();
        leerDestinos();

        reservas = new ArrayList<>();
        leerReserva();

        //TODO ESTO ES UNA FUNCION DE PRUEBA PARA ANADIR DESTINOS Y PAQUETES
        inicializarDestinosTest();
    }
    private void inicializarLogger() {
        try {
            File logFile = new File("logs.log");
            if (!logFile.exists()) {
                // Si el archivo no existe, se crea el FileHandler
                FileHandler fh = new FileHandler("logs.log", true);
                fh.setFormatter(new SimpleFormatter());
                log.addHandler(fh);
                log.info("INICIO ARCHIVO LOG COLOMBIA TRAVEL.");
            } else {
                // Si el archivo ya existe, se utiliza sin crear uno nuevo
                FileHandler fh = new FileHandler("logs.log", true);
                fh.setFormatter(new SimpleFormatter());
                log.addHandler(fh);
            }
        } catch (IOException e) {
            log.severe("Error al inicializar el logger: " + e.getMessage());
        }
    }

    private void inicializarDestinosTest() throws AtributoVacioException, RutaInvalidaException, InformacionRepetidaException, DestinoRepetidoException {
        if (existeArchivoSerializable(RUTAPAQUETES)  || existeArchivoSerializable(RUTADESTINOS) ) {
            // Si el archivo serializable existe, cargar la información desde el archivo
            log.info("No se han cargado los datos ya que ya se tiene informacion ");
        } else {
            // Si el archivo no existe, realizar la inicialización normal
            ArrayList<File> listaDeImagenes1 = new ArrayList<>();
            ArrayList<File> listaDeImagenes2 = new ArrayList<>();
            ArrayList<File> listaDeImagenes3 = new ArrayList<>();

            // Cargar la imagen de Armenia
            File armeniaFoto = new File("src/main/resources/imagenes/Armenia.jpeg");
            listaDeImagenes1.add(armeniaFoto);

            // Cargar la imagen de Filandia
            File filandiaFoto = new File("src/main/resources/imagenes/Filandia.jpeg");
            listaDeImagenes2.add(filandiaFoto);

            // Cargar la imagen de Salento
            File salentoFoto = new File("src/main/resources/imagenes/Salento.jpeg");
            listaDeImagenes3.add(salentoFoto);

            Destino armenia = registrarDestino("Plaza de Bolivar", "Aremenia", "Meh", listaDeImagenes1, Clima.TEMPLADO);
            Destino filandia = registrarDestino("Mirador", "Filandia", "Bonito", listaDeImagenes2, Clima.TEMPLADO);
            Destino salento = registrarDestino("Cocora", "Salento", "Bonito", listaDeImagenes3, Clima.TEMPLADO);

            ArrayList<Destino> destinosQuindio = new ArrayList<>();
            destinosQuindio.add(armenia);
            destinosQuindio.add(filandia);
            destinosQuindio.add(salento);

            // TODO: HACER LA FUNCION DE VERIFICAR SI YA EXISTE UN PAQUETE QUE RETORNE UN MENSAJE
            PaquetesTuristicos paqueteQuindio = registrarPaquetes("Quindio: Corazon del Cafe", destinosQuindio, "Conoce Armenia y Filandia", "Desayuno", 3990000, 30, LocalDate.now().plusDays(1), LocalDate.now().plusWeeks(1), null);
            System.out.println(paqueteQuindio.toString());
            paquetes.add(paqueteQuindio);
            // FIN DE CODIGO PARA PROBAR PAQUETES.
        }

        // Imprimir destinos
        for (Destino destino : destinos) {
            System.out.println(destino.toString());
        }
    }

    private boolean existeArchivoSerializable(String NOMBRE_ARCHIVO) {
        File archivo = new File(NOMBRE_ARCHIVO);
        return archivo.exists() && archivo.isFile() && archivo.length() > 0;
    }
    public Destino obtenerDestinoPorNombre(String nombre) {
        return destinos.stream()
                .filter(destino -> destino.getNombre().equals(nombre))
                .findFirst()
                .orElse(null);
    }


    public static AgenciaViajes getInstance() throws RutaInvalidaException, AtributoVacioException, InformacionRepetidaException, DestinoRepetidoException {
        if(agenciaViajes == null){
            agenciaViajes = new AgenciaViajes();
        }

        return agenciaViajes;
    }
    public Cliente registrarCliente(String cedula, String nombreCompleto,String email,String direccion,String telefono,String contrasenia) throws AtributoVacioException, InformacionRepetidaException {

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

// Metodo que elimina un cliente
    public void eliminarCliente(String cedula) throws ElementoNoEncontradoException{
        Cliente clienteAEliminar = null;
        clienteAEliminar = obtenerCliente(cedula);
        if (clienteAEliminar != null) {
            clientes.remove(clienteAEliminar);
            log.info("se ha eliminado el cliente con la cedula "+ cedula);
        } else {
            throw new ElementoNoEncontradoException("No se encontró un cliente con la cedula proporcionado.");
        }
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
    public Destino registrarDestino(String nombre, String ciudad, String descripcion, ArrayList<File> imagenes, Clima clima) throws AtributoVacioException, RutaInvalidaException, DestinoRepetidoException {
        if (nombre == null || nombre.isBlank()) {
            throw new AtributoVacioException("El nombre del destino es obligatorio");
        }
        if (ciudad == null || ciudad.isBlank()) {
            throw new AtributoVacioException("La ciudad del destino es obligatoria");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new AtributoVacioException("La descripción es obligatoria");
        }
        if (imagenes == null || imagenes.isEmpty()) {
            throw new AtributoVacioException("Debes seleccionar al menos una imagen");
        }
        if (clima == null) {
            throw new AtributoVacioException("El clima es obligatorio");
        }
        Destino destinoExistente = obtenerDestinoPorNombre(nombre);
        if (destinoExistente != null) {
            // Aquí puedes manejar el caso de que ya existe un destino con ese nombre
            // Puedes lanzar una excepción, imprimir un mensaje, etc.
            throw new DestinoRepetidoException("Ya existe un destino con el nombre: " + nombre);
        }

        // Verificar si el nombre o la ciudad ya existen en destinos

        Destino destino = Destino.builder()
                .nombre(nombre)
                .ciudad(ciudad)
                .descripcion(descripcion)
                .rutasImagenes(imagenes)
                .clima(clima)
                .build();

        destinos.add(destino);
        escribirDestino();

        log.info("Se ha registrado un nuevo destino con el nombre: " + nombre);
        return destino;
    }


// Metodo que elimina un destino
    public void eliminarDestino(String nombre) throws ElementoNoEncontradoException {
        Destino destinoAEliminar = null;
        destinoAEliminar = obtenerDestino(nombre);
        if (destinoAEliminar != null) {
            destinos.remove(destinoAEliminar);
            log.info("se ha eliminado el destino "+ nombre);
        } else {
            throw new ElementoNoEncontradoException("No se encontró el destino con el nombre proporcionado.");
        }
    }

    public Destino obtenerDestino(String nombre){
        return destinos.stream().filter(c -> c.getNombre().equals(nombre)).findFirst().orElse(null);
    }
    private void escribirDestino() throws RutaInvalidaException{
        try{
            ArchivoUtils.serializarObjeto(RUTADESTINOS, destinos);
        }catch (Exception e){
            log.severe(e.getMessage());
            throw new RutaInvalidaException("A ocurrido un error al momento de escribir los destinos");
        }
    }

    private void leerDestinos() {

        try {
            ArrayList<Destino> lista = (ArrayList<Destino>) ArchivoUtils.deserializarObjeto(RUTADESTINOS);
            if(lista != null){
                this.destinos = lista;
            }
        } catch (IOException | ClassNotFoundException e) {
            log.severe(e.getMessage());
        }

    }

    private void escribirReserva() throws RutaInvalidaException{
        try{
            ArchivoUtils.serializarObjeto(RUTARESERVAS, reservas);
        }catch (Exception e){
            log.severe(e.getMessage());
            throw new RutaInvalidaException("A ocurrido un error al momento de escribir las reservas");
        }
    }
    // Metodo que lee los paquetes turisticos
    private void leerReserva() {

        try {
            ArrayList<Reserva> lista = (ArrayList<Reserva>) ArchivoUtils.deserializarObjeto(RUTARESERVAS);
            if(lista != null){
                reservas = lista;
            }
        } catch (ClassNotFoundException | IOException e) {
            log.severe(e.getMessage());
        }

    }
    public static void registrarReserva(ArrayList<Reserva> listaReservas, Reserva reserva) {
        listaReservas.add(reserva);
    }



// Metodo que reguistra los guias turisticos

    public GuiaTuristico registrarGuias(String nombre, String identificacion, List<Idiomas> idiomas, String exp) throws AtributoVacioException, InformacionRepetidaException, RutaInvalidaException {

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

        GuiaTuristico guia = GuiaTuristico.builder()
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
    public void eliminarGuia(String identificacion) throws ElementoNoEncontradoException{
        GuiaTuristico guiaAEliminar = null;
        guiaAEliminar = obtenerGuia(identificacion);
        if (guiaAEliminar != null) {
            clientes.remove(guiaAEliminar);
            log.info("se ha eliminado el guia con la identificacion  "+ identificacion);
        } else {
            throw new ElementoNoEncontradoException("No se encontró un guia con la identificacion proporcionada.");
        }
    }

    public GuiaTuristico obtenerGuia(String identificacion){
        return guias.stream().filter(c -> c.getIdentificacion().equals(identificacion)).findFirst().orElse(null);
    }

    //Metodo que escribe los Guias Turisticos

    private void escribirGuias(GuiaTuristico guias) throws RutaInvalidaException{
        try {
            String linea = guias.getNombre()+";"+guias.getIdentificacion()+";"+guias.getIdiomas()+";"+guias.getExp();
            ArchivoUtils.escribirArchivoBufferedWriter(RUTAGUIAS, List.of(linea), true);
        }catch (IOException e){
            log.severe(e.getMessage());
            throw new RutaInvalidaException("A ocurrido un error al momento de escribir los destinos");
        }
    }

    //Metodo que lee los guias Turisticos
    private void leerGuias() throws RutaInvalidaException{

        try{

            ArrayList<String> lineas = ArchivoUtils.leerArchivoScanner(RUTAGUIAS);

            for(String linea : lineas){

                String[] valores = linea.split(";");
                guias.add( GuiaTuristico.builder()

                        .nombre(valores[0])
                        .identificacion(valores[1])
                        //.idiomas(Idiomas.valueOf(valores[2]))
                        .exp(valores[3])
                        .build());
            }

        }catch (IOException e){
            log.severe(e.getMessage());
            throw new RutaInvalidaException("A ocurrido un error al momento de leer las reservas");
        }

    }

    // Metodo que registra los Paquetes turisticos

    public PaquetesTuristicos registrarPaquetes(String nombre,ArrayList<Destino> destinos, String descripcion, String serviciosAdicionales, double precio, int cupoMax, LocalDate fechaDisponible,LocalDate fechaDisponibleFin,GuiaTuristico guia) throws AtributoVacioException, InformacionRepetidaException, RutaInvalidaException {

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre es obligatorip");
        }

        if( obtenerPaquete(nombre) != null ){
            throw new InformacionRepetidaException("El paquete "+nombre+" ya está registrado");
        }

        if(descripcion == null || descripcion.isBlank()){
            throw new AtributoVacioException("La descripcion es obligatoria");
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
                .destinos(destinos)
                .descripcion(descripcion)
                .serviciosAdicionales(serviciosAdicionales)
                .precio(precio)
                .cupoMax(cupoMax)
                .fechaDisponibleInicio(fechaDisponible)
                .fechaDisponibleFin(fechaDisponibleFin)
                .build();
        if(guia != null){
            paquete.setGuia(guia);
        }
        paquetes.add(paquete);
        escribirPaquete();

        log.info("Se ha registrado un nuevo paquete con el nombre: "+nombre);
        return paquete;
    }

    public void eliminarPaquete(String nombre) throws ElementoNoEncontradoException {
        PaquetesTuristicos paqueteAEliminar = null;
        paqueteAEliminar = obtenerPaquete(nombre);
        if (paqueteAEliminar != null) {
            paquetes.remove(paqueteAEliminar);
            log.info("se ha eliminado el paquete con el nombre  "+ nombre);
        } else {
            throw new ElementoNoEncontradoException("No se encontró un paquete con el nombre proporcionada.");
        }
    }


    public PaquetesTuristicos obtenerPaquete(String nombre){
        return paquetes.stream().filter(c -> c.getNombre().equals(nombre)).findFirst().orElse(null);
    }
// Metodo que escribe los paquetes turisticos
    private void escribirPaquete() throws RutaInvalidaException {
        try{
            ArchivoUtils.serializarObjeto(RUTAPAQUETES, paquetes);
        }catch (Exception e){
            log.severe(e.getMessage());
            throw new RutaInvalidaException("A ocurrido un error al momento de leer los paquetes");

        }
    }
// Metodo que lee los paquetes turisticos
    private void leerPaquete() {

        try {
            ArrayList<PaquetesTuristicos> lista = (ArrayList<PaquetesTuristicos>) ArchivoUtils.deserializarObjeto(RUTAPAQUETES);
            System.out.println(RUTAPAQUETES);
            if(lista != null){
                this.paquetes = lista;
            }
        } catch (IOException | ClassNotFoundException e) {
            log.severe(e.getMessage());
            System.out.println(RUTAPAQUETES);

        }

    }

}
