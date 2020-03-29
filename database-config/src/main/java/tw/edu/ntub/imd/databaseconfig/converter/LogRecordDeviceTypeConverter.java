package tw.edu.ntub.imd.databaseconfig.converter;

import tw.edu.ntub.imd.databaseconfig.enumerated.LogRecordDeviceType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LogRecordDeviceTypeConverter implements AttributeConverter<LogRecordDeviceType, String> {
    @Override
    public String convertToDatabaseColumn(LogRecordDeviceType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDeviceType();
    }

    @Override
    public LogRecordDeviceType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return LogRecordDeviceType.getInstance(dbData);
    }
}
