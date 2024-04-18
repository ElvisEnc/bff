package bg.com.bo.bff.commons.enums;

public enum PersonRol {
    ADMINISTRADOR(1, "Administrador"),
    OPERADOR(2, "Operador"),
    SUPERVISOR(3, "Supervisor"),
    FIRMANTE(4, "Firmante"),
    PERSONA(5, "Persona"),
    COBRADOR(6, "Cobrador");

    private final int id;
    private final String description;

    PersonRol(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
