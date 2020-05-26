package tw.edu.ntub.imd.databaseconfig.converter;

import tw.edu.ntub.imd.databaseconfig.enumerated.LogRecordDevice;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LogRecordDeviceConverter implements AttributeConverter<LogRecordDevice, String> {
    @Override
    public String convertToDatabaseColumn(LogRecordDevice attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDevice();
    }

    @Override
    public LogRecordDevice convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return LogRecordDevice.getInstance(dbData);
    }
}
