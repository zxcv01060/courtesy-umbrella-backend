package tw.edu.ntub.imd.databaseconfig.dao;

import org.springframework.stereotype.Repository;
import tw.edu.ntub.imd.databaseconfig.entity.LogRecord;

@Repository
public interface LogRecordDAO extends BaseDAO<LogRecord, Integer> {

}
