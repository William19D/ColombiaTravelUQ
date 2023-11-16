package co.edu.uniquindio.agencia.model;

import co.edu.uniquindio.agencia.persistencia.ArchivoUtils;
import co.edu.uniquindio.agencia.exceptions.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.*;
import lombok.extern.java.Log;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;


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
    private ArrayList<Cliente> clientes;
    private ArrayList<Destino> destinos;
    private ArrayList<Reserva> reservas;
    private ArrayList<Administrador> administradores;

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
            List<File> listaDeImagenes1 = new ArrayList<>();
            List<File> listaDeImagenes2 = new ArrayList<>();
            List<File> listaDeImagenes3 = new ArrayList<>();
            List<File> listaDeImagenes4 = new ArrayList<>();
            List<File> listaDeImagenes5 = new ArrayList<>();

            // Cargar la imagen de Armenia
            File armeniaFoto = new File("src/main/resources/imagenes/destinos/Armenia.jpeg");
            listaDeImagenes1.add(armeniaFoto);

            // Cargar la imagen de Filandia
            File filandiaFoto = new File("src/main/resources/imagenes/destinos/Filandia.jpeg");
            listaDeImagenes2.add(filandiaFoto);

            // Cargar la imagen de Salento
            File salentoFoto = new File("src/main/resources/imagenes/destinos/Salento.jpeg");
            listaDeImagenes3.add(salentoFoto);
            // Cargar la imagen de BuenaVista
            File buenavistaFoto = new File("src/main/resources/imagenes/destinos/Buenavista.jpeg");
            listaDeImagenes4.add(buenavistaFoto);
            File paseoCaballoFoto = new File("src/main/resources/imagenes/destinos/paseoCaballo.jpg");
            listaDeImagenes5.add(paseoCaballoFoto);

            Destino armenia = registrarDestino("Plaza de Bolivar", "Armenia", "Meh", listaDeImagenes1, Clima.TEMPLADO);
            Destino filandia = registrarDestino("Mirador", "Filandia", "Bonito", listaDeImagenes2, Clima.TEMPLADO);
            Destino salento = registrarDestino("Cocora", "Salento", "Bonito", listaDeImagenes3, Clima.TEMPLADO);
            Destino buenavista = registrarDestino("Pueblo", "Buenavista", "Bonito", listaDeImagenes4, Clima.TEMPLADO);
            Destino caballo = registrarDestino("Paseo a caballo cocora", "Salento", "Bonito", listaDeImagenes5, Clima.FRIO);

            ArrayList<Destino> destinosQuindio = new ArrayList<>();
            ArrayList<Destino> cocoraPaseo = new ArrayList<>();
            cocoraPaseo.add(caballo);
            cocoraPaseo.add(salento);
            destinosQuindio.add(armenia);
            destinosQuindio.add(filandia);
            destinosQuindio.add(salento);
            destinosQuindio.add(buenavista);

            // TODO: HACER LA FUNCION DE VERIFICAR SI YA EXISTE UN PAQUETE QUE RETORNE UN MENSAJE
            registrarPaquetes("Quindio: Corazon del Cafe", destinosQuindio, "Conoce Armenia y Filandia", "Desayuno", 3990000, 30, LocalDate.now().plusDays(1), LocalDate.now().plusWeeks(1), null);
            registrarPaquetes("Salento Oferta", cocoraPaseo, "Conoce cocora y pasea a caballo ", "Desayuno, almuerzo y fotos", 1490000, 20, LocalDate.now().plusDays(5), LocalDate.now().plusWeeks(1), null);
            // FIN DE CODIGO PARA PROBAR PAQUETES.
        }

        // Imprimir destinos
        for (Destino destino : destinos) {
            System.out.println(destino.toString());
        }
        for (PaquetesTuristicos paquete : paquetes) {
            System.out.println(paquete.toString());
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

    public void actualizarCliente(String cedula, String nombre, String correo, String direccion, String telefono, String contrasenia) throws AtributoVacioException, InformacionRepetidaException, ElementoNoEncontradoException {
        if (cedula == null || cedula.isBlank()) {
            throw new AtributoVacioException("La cédula es obligatoria");
        }

        Cliente clienteExistente = obtenerCliente(cedula);
        if (clienteExistente == null) {
            throw new InformacionRepetidaException("No existe un cliente con la cédula " + cedula);
        }

        // Actualiza los datos del cliente
        clienteExistente.setNombre(nombre);
        clienteExistente.setCorreo(correo);
        clienteExistente.setDireccion(direccion);
        clienteExistente.setTelefono(telefono);
        clienteExistente.setContrasenia(contrasenia);

        // Actualiza el archivo de persistencia clientes.txt
        actualizarArchivoClientes();


        log.info("Se ha actualizado el cliente con la cédula: " + cedula);
    }

    private void actualizarArchivoClientes() {
        try {
            // Borra el contenido del archivo
            ArchivoUtils.borrarContenidoArchivo(RUTACLIENTES);

            // Escribe nuevamente todos los clientes en el archivo
            for (Cliente cliente : clientes) {
                escribirCliente(cliente);
            }
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    private void escribirCliente(Cliente cliente){
        try {
            String linea = cliente.getCedula()+";"+cliente.getNombre()+";"+cliente.getCorreo()+";"+cliente.getTelefono()+";"+cliente.getDireccion()+";"+cliente.getContrasenia();
            ArchivoUtils.escribirArchivoBufferedWriter(RUTACLIENTES, List.of(linea), true);
        }catch (IOException e){
            log.severe(e.getMessage());
        }
    }
    public Cliente obtenerCliente(String cedula){
        return clientes.stream().filter(c -> c.getCedula().equals(cedula)).findFirst().orElse(null);
    }
    private void leerClientes() {

        try {

            ArrayList<String> lineas = ArchivoUtils.leerArchivoScanner(RUTACLIENTES);

            for (String linea : lineas) {

                String[] valores = linea.split(";");

                    clientes.add(Cliente.builder()
                            .cedula(valores[0])
                            .nombre(valores[1])
                            .correo(valores[2])
                            .direccion(valores[3])
                            .telefono(valores[4])
                            .contrasenia(valores[5])
                            .build());

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //DESTINOS
    public Destino registrarDestino(String nombre, String ciudad, String descripcion, List<File> imagenes, Clima clima) throws AtributoVacioException, RutaInvalidaException, DestinoRepetidoException {
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
        if(obtenerDestinoPorNombre(nombre) != null){
            if (obtenerDestinoPorNombre(nombre).getCiudad() == ciudad) {
                // Aquí puedes manejar el caso de que ya existe un destino con ese nombre
                // Puedes lanzar una excepción, imprimir un mensaje, etc.
                throw new DestinoRepetidoException("Ya existe un destino con esas caracteristicas: " + "Nombre: " + nombre + " Ciudad: " + ciudad);
            }
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



    public void eliminarDestino(String nombre, String ciudad) throws ElementoNoEncontradoException, RutaInvalidaException {
        Destino destinoAeliminar = obtenerDestino(nombre, ciudad);

        if (destinoAeliminar != null) {
            destinos.remove(destinoAeliminar);
            log.info("Se ha eliminado el destino de nombre " + nombre + " y ciudad " + ciudad);

            // Eliminar la información del destino del archivo de texto
            escribirDestino();
        } else {
            throw new ElementoNoEncontradoException("No se encontró un destino con el nombre y ciudad proporcionados.");
        }
    }

    private Destino obtenerDestino(String nombre, String ciudad) {
        for (Destino destino : destinos) {
            if (destino.getNombre().equals(nombre) && destino.getCiudad().equals(ciudad)) {
                return destino;
            }
        }
        return null;
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

    public void actualizarDestino(String nombre, String ciudad, String descripcion, List<File> imagenes, Clima clima) throws AtributoVacioException, InformacionRepetidaException, RutaInvalidaException{
        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("La identificacion es obligatoria");
        }

        if(descripcion == null || descripcion.isBlank()){
            throw new AtributoVacioException("La descripcion es obligatorio");
        }

        if(ciudad == null || ciudad.isBlank()){
            throw new AtributoVacioException("La descripcion es obligatorio");
        }


        Destino destino = obtenerDestinos(nombre);
        if(destino !=null){
            destino.setNombre(nombre);
            destino.setCiudad(ciudad);
            destino.setClima(clima);
            destino.setDescripcion(descripcion);
            eliminarInfoDestinoArchivo(nombre);
            escribirDestino();
        }else {
            throw new AtributoVacioException("No existe el destino");
        }
    }

    public Destino obtenerDestinos(String nombre){
        return destinos.stream().filter(c -> c.getNombre().equals(nombre)).findFirst().orElse(null);
    }
    private void eliminarInfoDestinoArchivo(String nombre) {
        try {

            List<String> lineas = ArchivoUtils.leerArchivoScanner(RUTADESTINOS);
            lineas = lineas.stream()
                    .filter(linea -> !linea.contains(nombre))
                    .collect(Collectors.toList());


            ArchivoUtils.escribirArchivoBufferedWriter(RUTADESTINOS, lineas, false);
        } catch (IOException e) {
            log.severe("Error al intentar eliminar la información del destino del archivo: " + e.getMessage());
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
//Metodo que actualiza los quias turisticos

    public void actualizarGuias(String nombre, String identificacion, List<Idiomas> idiomas, String exp) throws AtributoVacioException, InformacionRepetidaException, RutaInvalidaException{
        if(identificacion == null || identificacion.isBlank()){
            throw new AtributoVacioException("La identificacion es obligatoria");
        }

        if(nombre == null || nombre.isBlank()){
            throw new AtributoVacioException("El nombre es obligatorio");
        }

        if(exp == null || exp.isBlank()){
            throw new AtributoVacioException("la experiencia es obligatoria");
        }
        GuiaTuristico guiaTuristico = obtenerGuia(identificacion);
        if(guiaTuristico !=null){
            guiaTuristico.setNombre(nombre);
            guiaTuristico.setExp(exp);
            guiaTuristico.setIdiomas(idiomas);
            eliminarInfoGuiaArchivo(identificacion);
            escribirGuias(guiaTuristico);
        }else {
            throw new AtributoVacioException("No existe el usuario");
        }
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
    public void eliminarGuia(String identificacion) throws ElementoNoEncontradoException {
        GuiaTuristico guiaAEliminar = obtenerGuia(identificacion);
        if (guiaAEliminar != null) {
            guias.remove(guiaAEliminar);
            log.info("Se ha eliminado el guía con la identificación " + identificacion);

            // Eliminar la información del guía del archivo de texto
            eliminarInfoGuiaArchivo(identificacion);
        } else {
            throw new ElementoNoEncontradoException("No se encontró un guía con la identificación proporcionada.");
        }
    }

    private void eliminarInfoGuiaArchivo(String identificacion) {
        try {
            // Leer todas las líneas del archivo
            List<String> lineas = ArchivoUtils.leerArchivoScanner(RUTAGUIAS);

            // Filtrar las líneas para excluir la información del guía a eliminar
            lineas = lineas.stream()
                    .filter(linea -> !linea.contains(identificacion))
                    .collect(Collectors.toList());

            // Escribir las líneas actualizadas al archivo
            ArchivoUtils.escribirArchivoBufferedWriter(RUTAGUIAS, lineas, false);
        } catch (IOException e) {
            log.severe("Error al intentar eliminar la información del guía del archivo: " + e.getMessage());
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
    public List<PaquetesTuristicos> getPaquetesPorPresupuesto(double valorPresupuesto) {
        try {

            return paquetes.stream()
                    .filter(paquete -> paquete.getPrecio() <= valorPresupuesto)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {

            return new ArrayList<>(paquetes);
        }
    }
    public List<File> seleccionarImagenes() throws AtributoVacioException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        List<File> imagenesSeleccionadas = fileChooser.showOpenMultipleDialog(new Stage());

        if (imagenesSeleccionadas != null) {
            for (File imagen : imagenesSeleccionadas) {
                log.info("Imagen seleccionada: " + imagen.getAbsolutePath());
            }
        }else{
            throw  new AtributoVacioException("No se selecciono ninguna imagen");
        }
        return imagenesSeleccionadas;
    }
    public List<PaquetesTuristicos> getPaquetesPorCiudad(String ciudad) {
        List<PaquetesTuristicos> paquetesPorCiudad = new ArrayList<>();

        for (PaquetesTuristicos paquete : paquetes) {
            // Verificar si alguno de los destinos del paquete tiene la ciudad especificada
            if (paquete.getDestinos().stream().anyMatch(destino -> destino.getCiudad().equalsIgnoreCase(ciudad))) {
                paquetesPorCiudad.add(paquete);
            }
        }

        return paquetesPorCiudad;
    }
    public List<PaquetesTuristicos> getPaquetesPorClima(Clima clima) {
        return paquetes.stream()
                .filter(paquete -> paquete.getDestinos().stream().anyMatch(destino -> destino.getClima() == clima))
                .toList();
    }
    public int calcularCuposRestantes(int cupoMax, int cupoActual){
        int cupo = cupoMax-cupoActual;
        return cupo;
    }
    public boolean paqueteDisponible(int interesados, PaquetesTuristicos paquete){
        boolean disponible = false;

        if(interesados < calcularCuposRestantes(paquete.getCupoMax(),paquete.getCupoActual())){
            disponible = true;
        }
        return disponible;
    }

    public List<PaquetesTuristicos> getPaquetesDisponiblesPorPersonas(int cantidadPersonas) {
        List<PaquetesTuristicos> paquetesDisponibles = new ArrayList<>();

        List<PaquetesTuristicos> todosLosPaquetes = agenciaViajes.getPaquetes();

        for (PaquetesTuristicos paquete : todosLosPaquetes) {
            if (paqueteDisponible(cantidadPersonas, paquete)) {
                paquetesDisponibles.add(paquete);
            }
        }

        return paquetesDisponibles;
    }


    public boolean verificarClienteAdministrador(String cedula, String contrasena) throws AtributoVacioException{
        if(cedula.isEmpty()){
            throw new AtributoVacioException("Cedula vacia");
        }
        if(contrasena.isEmpty()){
            throw new AtributoVacioException("Contrasena vacia");
        }

        Cliente cliente = obtenerCliente(cedula);
        Administrador administrador = obtenerAdministrador(cedula);

        if (cliente != null && cliente.getContrasenia().equals(contrasena)) {
            return true;
        } else return administrador != null && administrador.getContrasenia().equals(contrasena); // Administrador autenticado
    }

    public Administrador obtenerAdministrador(String cedula) {
        return administradores.stream()
                .filter(admin -> admin.getUsuario().equals(cedula))
                .findFirst()
                .orElse(null);
    }


    public List<PaquetesTuristicos> getPaquetesPorFechas(LocalDate salida, LocalDate llegada) {
        List<PaquetesTuristicos> paquetesEnFechas = new ArrayList<>();

        for (PaquetesTuristicos paquete : paquetes) {
            LocalDate fechaInicioPaquete = paquete.getFechaDisponibleInicio();
            LocalDate fechaFinPaquete = paquete.getFechaDisponibleFin();

            // Verificar si las fechas de salida y llegada están dentro del rango del paquete
            if ((fechaInicioPaquete.isEqual(salida) || fechaInicioPaquete.isAfter(salida))
                    && (fechaFinPaquete.isEqual(llegada) || fechaFinPaquete.isBefore(llegada))) {
                paquetesEnFechas.add(paquete);
            }
        }

        return paquetesEnFechas;
    }


}
