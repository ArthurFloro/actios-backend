package br.com.actios.actios_backend.enums;

/**
 * Enumeração que representa os formatos possíveis para eventos no sistema.
 * <p>
 * Define os tipos de formato de evento suportados pela aplicação, com mapeamento
 * para valores de banco de dados e métodos de conversão.
 *
 * @author Equipe Actios
 * @version 1.1
 * @since 2023-08-30
 */
public enum FormatoEvento {
    /**
     * Evento realizado totalmente online
     */
    ONLINE("online"),

    /**
     * Evento realizado presencialmente
     */
    PRESENCIAL("presencial"),

    /**
     * Evento realizado no formato híbrido (online e presencial)
     */
    HIBRIDO("hibrido");

    private final String dbValue;

    /**
     * Construtor do enum.
     *
     * @param dbValue Valor correspondente no banco de dados
     */
    FormatoEvento(String dbValue) {
        this.dbValue = dbValue;
    }

    /**
     * Obtém o valor utilizado para persistência no banco de dados.
     *
     * @return String com o valor para armazenamento no banco
     */
    public String getDbValue() {
        return dbValue;
    }

    /**
     * Retorna todos os valores válidos para o formato de evento.
     *
     * @return Array de strings com os valores válidos (nomes do enum e valores do banco)
     */
    public static String[] getValidValues() {
        return new String[]{
                ONLINE.name(),
                PRESENCIAL.name(),
                HIBRIDO.name(),
                ONLINE.getDbValue(),
                PRESENCIAL.getDbValue(),
                HIBRIDO.getDbValue()
        };
    }

    /**
     * Converte uma String para o enum correspondente.
     * <p>
     * A conversão é case-insensitive e pode ser feita tanto pelo nome do enum
     * quanto pelo valor de banco de dados correspondente.
     *
     * @param value Valor a ser convertido (nome do enum ou valor do banco)
     * @return Instância de FormatoEvento correspondente
     * @throws IllegalArgumentException Se o valor não corresponder a nenhum formato válido
     */
    public static FormatoEvento fromValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Valor não pode ser nulo ou vazio");
        }

        for (FormatoEvento formato : values()) {
            if (formato.name().equalsIgnoreCase(value) || formato.dbValue.equalsIgnoreCase(value)) {
                return formato;
            }
        }
        throw new IllegalArgumentException("Formato inválido: " + value);
    }
}