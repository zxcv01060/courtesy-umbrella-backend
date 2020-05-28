package tw.edu.ntub.imd.courtesyumbrella.annotation;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import tw.edu.ntub.imd.courtesyumbrella.TestApplication;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
public @interface ServiceTest {
}
