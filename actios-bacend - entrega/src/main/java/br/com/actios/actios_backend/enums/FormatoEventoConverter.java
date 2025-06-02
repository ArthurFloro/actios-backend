package br.com.actios.actios_backend.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FormatoEventoConverter implements AttributeConverter<FormatoEvento, String> {

    @Override
    public String convertToDatabaseColumn(FormatoEvento formato) {
        return formato != null ? formato.getDbValue() : null;
    }

    @Override
    public FormatoEvento convertToEntityAttribute(String dbValue) {
        return dbValue != null ? FormatoEvento.fromValue(dbValue) : null;
    }
}
