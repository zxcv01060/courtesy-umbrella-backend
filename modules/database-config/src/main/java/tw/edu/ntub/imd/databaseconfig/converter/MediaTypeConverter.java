package tw.edu.ntub.imd.databaseconfig.converter;

import tw.edu.ntub.imd.databaseconfig.enumerated.MediaType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MediaTypeConverter implements AttributeConverter<MediaType, String> {
    @Override
    public String convertToDatabaseColumn(MediaType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getType();
    }

    @Override
    public MediaType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return MediaType.getInstance(dbData);
    }
}
