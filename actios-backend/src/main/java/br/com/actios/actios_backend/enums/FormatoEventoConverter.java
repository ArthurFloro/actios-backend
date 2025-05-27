package br.com.actios.actios_backend.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FormatoEventoConverter implements AttributeConverter<FormatoEvento, String> {
    @Override
    public String convertToDatabaseColumn(FormatoEvento formato) {
        return formato != null ? formato.getValue() : null;
    }

    @Override
    public FormatoEvento convertToEntityAttribute(String valor) {
        return valor != null ? FormatoEvento.fromValue(valor) : null;
    }
}

