package tw.edu.ntub.imd.databaseconfig.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import tw.edu.ntub.imd.databaseconfig.entity.Savable;

import java.util.Iterator;

@Aspect
@Component
public class SaveAspect {
    @Pointcut("execution(public * tw.edu.ntub.imd.databaseconfig.dao.BaseDAO.save(*)) || " +
            "execution(public * tw.edu.ntub.imd.databaseconfig.dao.BaseDAO.saveAndFlush(*))")
    public void savePointcut() {

    }

    @Before("savePointcut()")
    public void beforeSave(JoinPoint joinPoint) {
        setEntityNew(joinPoint.getArgs()[0]);
    }

    @SuppressWarnings("rawtypes")
    private void setEntityNew(Object entity) {
        if (entity instanceof Savable) {
            ((Savable) entity).isNewEntity();
        } else {
            throw new IllegalStateException("Entity應實作(implements)Savable");
        }
    }

    @Pointcut("execution(public * tw.edu.ntub.imd.databaseconfig.dao.BaseDAO.saveAll(*))")
    public void saveAllPointcut() {

    }

    @Before("saveAllPointcut()")
    public void beforeSaveAll(JoinPoint joinPoint) {
        Iterator<?> iterator = (Iterator<?>) joinPoint.getArgs()[0];
        while (iterator.hasNext()) {
            setEntityNew(iterator.next());
        }
    }
}
