package br.com.actios.actios_backend.enums;


public enum FormatoEvento {
    ONLINE("online"),
    PRESENCIAL("presencial"),
    HIBRIDO("hibrido");

    private final String value;

    FormatoEvento(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FormatoEvento fromValue(String value) {
        for (FormatoEvento formato : FormatoEvento.values()) {
            if (formato.getValue().equals(value)) {
                return formato;
            }
        }
        throw new IllegalArgumentException("Formato de evento inválido: " + value);
    }
}
