package tw.edu.ntub.imd.databaseconfig.dao.criteria.order;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;

public class RepairOrderSupplier implements OrderSupplier<String> {
    private final String maintenancePersonUserId;
    private final boolean ascending;

    public RepairOrderSupplier(String maintenancePersonUserId, boolean ascending) {
        this.maintenancePersonUserId = maintenancePersonUserId;
        this.ascending = ascending;
    }

    public static RepairOrderSupplier asc(String maintenancePersonUserId) {
        return new RepairOrderSupplier(maintenancePersonUserId, true);
    }

    public static RepairOrderSupplier desc(String maintenancePersonUserId) {
        return new RepairOrderSupplier(maintenancePersonUserId, false);
    }

    @Override
    public Order get(CriteriaBuilder builder, Expression<String> expression) {
        Expression<String> orderExpression = builder.selectCase(expression)
                .when(maintenancePersonUserId, builder.literal(1))
                .getExpression();
        return ascending ? builder.asc(orderExpression) : builder.desc(orderExpression);
    }
}
