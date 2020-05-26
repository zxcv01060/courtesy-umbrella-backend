package tw.edu.ntub.imd.databaseconfig.converter;

import tw.edu.ntub.imd.databaseconfig.enumerated.RentalRecordStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RentalRecordStatusConverter implements AttributeConverter<RentalRecordStatus, String> {
    @Override
    public String convertToDatabaseColumn(RentalRecordStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getStatus();
    }

    @Override
    public RentalRecordStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return RentalRecordStatus.getInstance(dbData);
    }
}
