package br.com.actios.actios_backend.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Conversor JPA para o enum {@link FormatoEvento}.
 * <p>
 * Realiza a conversão automática entre valores do enum FormatoEvento e suas representações
 * no banco de dados. Aplicado automaticamente a todos os atributos do tipo FormatoEvento.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 * @see FormatoEvento
 */
@Converter(autoApply = true)
public class FormatoEventoConverter implements AttributeConverter<FormatoEvento, String> {

    /**
     * Converte um valor do enum FormatoEvento para sua representação no banco de dados.
     *
     * @param formato Instância do enum a ser convertida (pode ser null)
     * @return String correspondente ao valor no banco de dados ou null se o parâmetro for null
     */
    @Override
    public String convertToDatabaseColumn(FormatoEvento formato) {
        return formato != null ? formato.getDbValue() : null;
    }

    /**
     * Converte um valor do banco de dados para o enum FormatoEvento correspondente.
     * <p>
     * Utiliza o método {@link FormatoEvento#fromValue(String)} para realizar a conversão.
     *
     * @param dbValue Valor do banco de dados a ser convertido (pode ser null)
     * @return Instância do enum correspondente ou null se o parâmetro for null
     * @throws IllegalArgumentException Se o valor do banco não corresponder a nenhum formato válido
     */
    @Override
    public FormatoEvento convertToEntityAttribute(String dbValue) {
        return dbValue != null ? FormatoEvento.fromValue(dbValue) : null;
    }
}