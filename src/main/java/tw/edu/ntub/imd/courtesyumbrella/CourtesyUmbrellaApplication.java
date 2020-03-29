package tw.edu.ntub.imd.courtesyumbrella;

import org.aopalliance.aop.Advice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"tw.edu.ntub.imd.config", "tw.edu.ntub.imd.databaseconfig"})
public class CourtesyUmbrellaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourtesyUmbrellaApplication.class, args);
    }

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(@Qualifier("transactionInterceptor") Advice advice) {
        // AOP advisor：AOP 切入點，在 service method 增加交易
        AspectJExpressionPointcut expression = new AspectJExpressionPointcut();
        expression.setExpression("execution(* " + getClass().getPackageName() + ".service.*.*(..))");
        return new DefaultPointcutAdvisor(expression, advice);
    }
}
