package co.edu.uniquindio.alquiler.model;

import co.edu.uniquindio.alquiler.enums.Marca;
import co.edu.uniquindio.alquiler.exceptions.*;
import co.edu.uniquindio.alquiler.utils.ArchivoUtils;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Getter
@Log
public class AlquilaFacil {

    private ArrayList<Vehiculo> vehiculos;

    private ArrayList<Cliente> clientes;

    private ArrayList<Alquiler> alquileres;

    private static AlquilaFacil alquilaFacil;

    private static final String RUTA_CLIENTES = "src/main/resources/persistencia/clientes.txt";
    private static final String RUTA_VEHICULOS = "src/main/resources/persistencia/vehiculos.txt";
    private static final String RUTA_ALQUILERES = "src/main/resources/persistencia/alquiler.ser";

    private AlquilaFacil(){
        inicializarLogger();
        log.info("Se crea una nueva instancia de AlquilaFacil" );

        this.vehiculos = new ArrayList<>();
        leerVehiculos();

        this.clientes = new ArrayList<>();
        leerClientes();

        this.alquileres = new ArrayList<>();
        leerAlquileres();
    }

    private void leerVehiculos() {
        try{

            ArrayList<String> lineas = ArchivoUtils.leerArchivoScanner(RUTA_VEHICULOS);

            for(String linea : lineas){

                String[] valores = linea.split(";");
                this.vehiculos.add( Vehiculo.builder()
                        .placa(valores[0])
                        .referencia(valores[1])
                        .marca(Marca.valueOf(valores[2]))
                        .modelo(Integer.parseInt(valores[3]))
                        .foto(valores[4])
                        .kilometraje(Integer.parseInt(valores[5]))
                        .precioDia(Float.parseFloat(valores[6]))
                        .esAutomatico(Boolean.parseBoolean(valores[7]))
                        .numPuertas(Integer.parseInt(valores[8])).build() );
            }

        }catch (IOException e){
            log.severe(e.getMessage());
        }
    }

    private void leerClientes() {

        try{

            ArrayList<String> lineas = ArchivoUtils.leerArchivoScanner(RUTA_CLIENTES);

            for(String linea : lineas){

                String[] valores = linea.split(";");
                this.clientes.add( Cliente.builder()
                        .cedula(valores[0])
                        .nombreCompleto(valores[1])
                        .email(valores[2])
                        .telefono(valores[3])
                        .ciudad(valores[4])
                        .direccion(valores[5]).build() );
            }

        }catch (IOException e){
            log.severe(e.getMessage());
        }

    }

    private void leerAlquileres(){

        try {
            ArrayList<Alquiler> lista = (ArrayList<Alquiler>) ArchivoUtils.deserializarObjeto(RUTA_ALQUILERES);
            if(lista != null){
                this.alquileres = lista;
            }
            System.out.println(alquileres);
        } catch (IOException | ClassNotFoundException e) {
            log.severe(e.getMessage());
        }

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

    public static AlquilaFacil getInstance(){
        if(alquilaFacil == null){
            alquilaFacil = new AlquilaFacil();
        }

        return alquilaFacil;
    }

    public Cliente registrarCliente(String cedula, String nombreCompleto,String email,String direccion,String ciudad,String telefono) throws AtributoVacioException, InformacionRepetidaException {

        if(cedula == null || cedula.isBlank()){
            log.warning("La cédula es obligatoria para el registro" );
            throw new AtributoVacioException("La cédula es obligatoria");
        }

        if( obtenerCliente(cedula) != null ){
            log.severe("La cédula "+cedula+" ya está registrada" );
            throw new InformacionRepetidaException("La cédula "+cedula+" ya está registrada");
        }

        //Demás validaciones

        Cliente cliente = Cliente.builder()
                .cedula(cedula)
                .nombreCompleto(nombreCompleto)
                .email(email)
                .direccion(direccion)
                .ciudad(ciudad)
                .telefono(telefono)
                .build();

        clientes.add(cliente);

        try {
            String linea = cliente.getCedula()+";"+cliente.getNombreCompleto()+";"+cliente.getEmail()+";"+cliente.getTelefono()+";"+cliente.getCiudad()+";"+cliente.getDireccion();
            ArchivoUtils.escribirArchivoBufferedWriter(RUTA_CLIENTES, List.of(linea), true);
        }catch (IOException e){
            log.severe(e.getMessage());
        }

        log.info("Se ha registrado un nuevo cliente con la cédula: "+cedula);
        return cliente;
    }

    public Vehiculo registrarVehiculo(String placa, String referencia, Marca marca, int modelo, String foto, int kilometraje, float precioDia, boolean esAutomatico, int numPuertas) throws AtributoNegativoException{

        //Validar atributos

        if(kilometraje < 0){
            log.severe("El kilometraje no puede ser negativo" );
            throw new AtributoNegativoException("El kilometraje no puede ser negativo");
        }

        Vehiculo vehiculo = Vehiculo.builder()
                .placa(placa)
                .referencia(referencia)
                .marca(marca)
                .modelo(modelo)
                .foto(foto)
                .kilometraje(kilometraje)
                .precioDia(precioDia)
                .esAutomatico(esAutomatico)
                .numPuertas(numPuertas)
                .build();

        vehiculos.add(vehiculo);

        try {
            String linea = vehiculo.getPlaca()+";"+vehiculo.getReferencia()+";"+vehiculo.getMarca()+";"+vehiculo.getModelo()+";"+vehiculo.getFoto()+";"+vehiculo.getKilometraje()+";"+vehiculo.getPrecioDia()+";"+vehiculo.isEsAutomatico()+";"+vehiculo.getNumPuertas();
            ArchivoUtils.escribirArchivoBufferedWriter(RUTA_VEHICULOS, List.of(linea), true);
        }catch (IOException e){
            log.severe(e.getMessage());
        }

        log.info("Se ha registrado un nuevo vehículo con la placa: "+placa);
        return vehiculo;

    }

    public Alquiler registrarAlquiler(String cedulaCliente,String placaVehiculo,LocalDate fechaInicio,LocalDate fechaFin) throws Exception{

        Vehiculo vehiculo = obtenerVehiculo(placaVehiculo);

        if(vehiculo == null){
            log.severe("No existe un vehículo con la placa "+placaVehiculo);
            throw new ElementoNoEncontradoException("No existe un vehículo con la placa "+placaVehiculo);
        }

        Cliente cliente = obtenerCliente(cedulaCliente);

        if(cliente == null){
            log.severe("No existe un cliente con la cédula "+cedulaCliente);
            throw new ElementoNoEncontradoException("No existe un cliente con la cédula "+cedulaCliente);
        }

        if(fechaInicio.isAfter(fechaFin)){
            log.severe("La fecha de inicio no puede ser después de la fecha final");
            throw new FechaInvalidaException("La fecha de inicio no puede ser después de la fecha final");
        }

        if( !estaDisponible(vehiculo, fechaInicio, fechaFin) ){
            log.severe("El vehículo elegido ("+placaVehiculo+") no está disponible en las fechas seleccionadas");
            throw new FechaInvalidaException("El vehículo elegidono ("+placaVehiculo+") está disponible en las fechas seleccionadas");
        }

        long dias = fechaInicio.until(fechaFin, ChronoUnit.DAYS);
        float costo = vehiculo.getPrecioDia() * dias;

        Alquiler alquiler = Alquiler.builder()
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .vehiculo(vehiculo)
                .cliente(cliente)
                .valorTotal(costo)
                .fechaRegistro(LocalDateTime.now())
                .build();

        alquileres.add(alquiler);

        try{
            ArchivoUtils.serializarObjeto(RUTA_ALQUILERES, alquileres);
        }catch (Exception e){
            log.severe(e.getMessage());
        }

        log.info("Se ha registrado un nuevo préstamo al cliente con la cédula: "+cedulaCliente+", y la placa: "+placaVehiculo);

        return alquiler;
    }

    public Cliente obtenerCliente(String cedula){
        return clientes.stream().filter(c -> c.getCedula().equals(cedula)).findFirst().orElse(null);
    }

    public Vehiculo obtenerVehiculo(String placa){
        return vehiculos.stream().filter(v -> v.getPlaca().equals(placa)).findFirst().orElse(null);
    }

    public boolean estaDisponible(Vehiculo v, LocalDate fechaInicio, LocalDate fechaFin){
        return alquileres.stream()
                .filter(
                        a -> a.getVehiculo().equals(v))
                .filter(
                        a -> fechaInicio.isBefore( a.getFechaFin() ) && a.getFechaInicio().isBefore(fechaFin))
                .findFirst()
                .isEmpty();
    }

    public float calcularTotalGanado(LocalDate fechaInicio, LocalDate fechaFin){
        return (float) alquileres.stream()
                .filter(a -> a.getFechaInicio().isAfter(fechaInicio) && a.getFechaInicio().isBefore(fechaFin))
                .mapToDouble( a -> a.getValorTotal() )
                .sum();
    }

    public List<Vehiculo> listarVehiculosAlquilados(LocalDate fecha){
        return alquileres.stream()
                .filter(a -> !fecha.isBefore(a.getFechaInicio()) && !fecha.isAfter(a.getFechaFin()) )
                .map(a -> a.getVehiculo())
                .distinct()
                .toList();
    }

    public Marca obtenerMarcaMasAlquilada() throws Exception{

        if(!alquileres.isEmpty()) {

            int[] acumulados = new int[Marca.values().length];

            for (Alquiler a : alquileres) {
                Vehiculo v = a.getVehiculo();
                acumulados[v.getMarca().ordinal()] += 1;
            }

            List<Integer> lista = Arrays.stream(acumulados).boxed().toList();
            return Marca.values()[lista.indexOf(Collections.max(lista))];
        }

        log.severe("Aún no hay alquileres registrados");
        throw new Exception("Aún no hay alquileres registrados");
    }


}