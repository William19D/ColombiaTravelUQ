package co.edu.uniquindio.alquiler.model;

import co.edu.uniquindio.alquiler.enums.Marca;
import co.edu.uniquindio.alquiler.exceptions.*;
import lombok.Getter;
import lombok.extern.java.Log;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Getter
@Log
public class AlquilaFacil {

    private final ArrayList<Vehiculo> vehiculos;

    private final ArrayList<Cliente> clientes;

    private final ArrayList<Alquiler> alquileres;

    private static AlquilaFacil alquilaFacil;

    private AlquilaFacil(){

        try {
            FileHandler fh = new FileHandler("logs.log", true);
            fh.setFormatter( new SimpleFormatter());
            log.addHandler(fh);
        }catch (IOException e){
            log.log( Level.SEVERE, e.getMessage() );
        }

        log.log( Level.INFO, "Se crea una nueva instancia de AlquilaFacil" );
        this.vehiculos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.alquileres = new ArrayList<>();
    }

    public static AlquilaFacil getInstance(){
        if(alquilaFacil == null){
            alquilaFacil = new AlquilaFacil();
        }

        return alquilaFacil;
    }

    public Cliente registrarCliente(String cedula, String nombreCompleto,String email,String direccion,String ciudad,String telefono) throws AtributoVacioException, InformacionRepetidaException{

        if(cedula == null || cedula.isBlank()){
            log.log( Level.WARNING, "La cédula es obligatoria para el registro" );
            throw new AtributoVacioException("La cédula es obligatoria");
        }

        if( obtenerCliente(cedula) != null ){
            log.log( Level.SEVERE, "La cédula "+cedula+" es obligatoria para el registro del cliente" );
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

        log.log(Level.INFO, "Se ha registrado un nuevo cliente con la cédula: "+cedula);

        return cliente;
    }

    public Vehiculo registrarVehiculo(String placa, String referencia, Marca marca, int modelo, String foto, int kilometraje, float precioDia, boolean esAutomatico, int numPuertas) throws AtributoNegativoException{

        //Validar atributos

        if(kilometraje < 0){
            log.log( Level.SEVERE, "El kilometraje no puede ser negativo" );
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

        log.log(Level.INFO, "Se ha registrado un nuevo vehículo con la placa: "+placa);

        return vehiculo;

    }

    public Alquiler registrarAlquiler(String cedulaCliente,String placaVehiculo,LocalDate fechaInicio,LocalDate fechaFin) throws Exception{

        Vehiculo vehiculo = obtenerVehiculo(placaVehiculo);

        if(vehiculo == null){
            log.log( Level.SEVERE, "No existe un vehículo con la placa "+placaVehiculo);
            throw new ElementoNoEncontradoException("No existe un vehículo con la placa "+placaVehiculo);
        }

        Cliente cliente = obtenerCliente(cedulaCliente);

        if(cliente == null){
            log.log( Level.SEVERE, "No existe un cliente con la cédula "+cedulaCliente);
            throw new ElementoNoEncontradoException("No existe un cliente con la cédula "+cedulaCliente);
        }

        if(fechaInicio.isAfter(fechaFin)){
            log.log( Level.SEVERE, "La fecha de inicio no puede ser después de la fecha final");
            throw new FechaInvalidaException("La fecha de inicio no puede ser después de la fecha final");
        }

        if( !estaDisponible(vehiculo, fechaInicio, fechaFin) ){
            log.log( Level.SEVERE, "El vehículo elegido ("+placaVehiculo+") no está disponible en las fechas seleccionadas");
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

        log.log(Level.INFO, "Se ha registrado un nuevo préstamo al cliente con la cédula: "+cedulaCliente+", y la placa: "+placaVehiculo);

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

        throw new Exception("Aún no hay alquileres registrados");
    }

}