package br.com.actios.actios_backend.enums;

public enum FormatoEvento {
    ONLINE("online"),
    PRESENCIAL("presencial"),
    HIBRIDO("hibrido");

    private final String dbValue;

    FormatoEvento(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static FormatoEvento fromValue(String value) {
        for (FormatoEvento formato : values()) {
            if (formato.name().equalsIgnoreCase(value) || formato.dbValue.equalsIgnoreCase(value)) {
                return formato;
            }
        }
        throw new IllegalArgumentException("Formato inválido: " + value);
    }

}