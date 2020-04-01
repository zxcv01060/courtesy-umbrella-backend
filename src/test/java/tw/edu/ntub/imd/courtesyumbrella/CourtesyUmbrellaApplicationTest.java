package tw.edu.ntub.imd.courtesyumbrella;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:data.sql")
@SpringBootTest
class CourtesyUmbrellaApplicationTest {
    @Test
    void contextLoad() {

    }
}