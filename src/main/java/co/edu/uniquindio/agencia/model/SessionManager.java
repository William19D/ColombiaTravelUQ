package co.edu.uniquindio.agencia.model;

public class SessionManager {
    private static SessionManager instance;
    private String cedulaUsuario;

    private SessionManager() {

        this.cedulaUsuario = "";
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String getCedulaUsuario() {
        return cedulaUsuario;
    }

    public void setCedulaUsuario(String cedulaUsuario) {
        this.cedulaUsuario = cedulaUsuario;
    }
}