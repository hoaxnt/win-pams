package com.winpams.data;

import com.winpams.core.exceptions.NoAnnotation;
import com.winpams.data.annotations.Column;
import com.winpams.data.annotations.Entity;
import com.winpams.data.model.BaseModel;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QueryBuilder<T extends BaseModel> {
    private final Class<T> modelClass;
    private final List<String> whereClauses = new ArrayList<>();
    private final List<Object> parameters = new ArrayList<>();
    private final List<String> orderByClauses = new ArrayList<>();
    private String lastLogicalOperator = "";

    public QueryBuilder(Class<T> modelClass) {
        this.modelClass = modelClass;
    }

    public QueryBuilder<T> where(String column, Object value) {
        if (!whereClauses.isEmpty() && lastLogicalOperator.isEmpty())
            throw new IllegalArgumentException("Logical operator (AND, OR) must be used between conditions");

        whereClauses.add(column + " = ?");
        parameters.add(value);
        lastLogicalOperator = "";
        return this;
    }

    public QueryBuilder<T> and(String column, Object value) {
        if (whereClauses.isEmpty())
            throw new IllegalArgumentException("AND cannot be used as the first condition");

        whereClauses.add("AND " + column + " = ?");
        parameters.add(value);
        lastLogicalOperator = "AND";
        return this;
    }

    public QueryBuilder<T> or(String column, Object value) {
        if (whereClauses.isEmpty())
            throw new IllegalArgumentException("OR cannot be used as the first condition");

        whereClauses.add("OR " + column + " = ?");
        parameters.add(value);
        lastLogicalOperator = "OR";
        return this;
    }

    public QueryBuilder<T> like(String column, Object value) {
        whereClauses.add(column + " LIKE ?");
        parameters.add(value);
        lastLogicalOperator = "";
        return this;
    }

    public QueryBuilder<T> greaterThan(String column, Object value) {
        whereClauses.add(column + " > ?");
        parameters.add(value);
        lastLogicalOperator = "";
        return this;
    }

    public QueryBuilder<T> lessThan(String column, Object value) {
        whereClauses.add(column + " < ?");
        parameters.add(value);
        lastLogicalOperator = "";
        return this;
    }

    public QueryBuilder<T> between(String column, Object value1, Object value2) {
        whereClauses.add(column + " BETWEEN ? AND ?");
        parameters.add(value1);
        parameters.add(value2);
        lastLogicalOperator = "";
        return this;
    }

    public QueryBuilder<T> notEqual(String column, Object value) {
        whereClauses.add(column + " != ?");
        parameters.add(value);
        lastLogicalOperator = "";
        return this;
    }

    public QueryBuilder<T> isNull(String column) {
        whereClauses.add(column + " IS NULL");
        lastLogicalOperator = "";
        return this;
    }

    public QueryBuilder<T> isNotNull(String column) {
        whereClauses.add(column + " IS NOT NULL");
        lastLogicalOperator = "";
        return this;
    }

    public QueryBuilder<T> notBetween(String column, Object value1, Object value2) {
        whereClauses.add(column + " NOT BETWEEN ? AND ?");
        parameters.add(value1);
        parameters.add(value2);
        lastLogicalOperator = "";
        return this;
    }

    public QueryBuilder<T> in(String column, Object... values) {
        StringBuilder inClause = new StringBuilder(column + " IN (");
        return buildInQuery(inClause, values);
    }

    public QueryBuilder<T> notIn(String column, Object... values) {
        StringBuilder inClause = new StringBuilder(column + " NOT IN (");
        return buildInQuery(inClause, values);
    }

    private QueryBuilder<T> buildInQuery(StringBuilder inClause, Object[] values) {
        for (int i = 0; i < values.length; i++) {
            inClause.append("?");
            if (i < values.length - 1)
                inClause.append(", ");

            parameters.add(values[i]);
        }
        inClause.append(")");
        whereClauses.add(inClause.toString());
        lastLogicalOperator = "";
        return this;
    }

    public QueryBuilder<T> asc(String column) {
        orderByClauses.add(column + " ASC");
        return this;
    }

    public QueryBuilder<T> asc() {
        orderByClauses.add("id ASC");
        return this;
    }

    public QueryBuilder<T> desc() {
        orderByClauses.add("id DESC");
        return this;
    }

    public QueryBuilder<T> desc(String column) {
        orderByClauses.add(column + " DESC");
        return this;
    }

    public List<T> execute() throws Exception {
        Database db = Database.instance();
        StringBuilder query = new StringBuilder("SELECT * FROM ");
        query.append(getTableName());

        if (!whereClauses.isEmpty())
            query.append(" WHERE ").append(String.join(" ", whereClauses));

        if (!orderByClauses.isEmpty())
            query.append(" ORDER BY ").append(String.join(", ", orderByClauses));

        ResultSet result = db.execute(query.toString(), parameters.toArray());

        whereClauses.clear();
        parameters.clear();
        orderByClauses.clear();
        lastLogicalOperator = "";

        return load(modelClass, result);
    }

    private String getTableName() throws NoAnnotation {
        Entity entity = modelClass.getAnnotation(Entity.class);

        if (entity == null) throw new NoAnnotation("Table annotation is missing");

        return entity.name();
    }

    public T find(Integer id) throws Exception {
        where("id", id);
        List<T> models = execute();
        if (models == null || models.isEmpty()) return null;
        return models.get(0);
    }

    public List<T> all() throws Exception {
        return execute();
    }

    public T first() throws Exception {
        List<T> models = execute();
        if (models == null || models.isEmpty()) return null;
        return models.get(0);
    }

    public T last() throws Exception {
        List<T> models = execute();
        if (models == null || models.isEmpty()) return null;
        return models.get(models.size() - 1);
    }

    private List<T> load(Class<T> modelClass, ResultSet result) throws Exception {
        List<T> models = new ArrayList<>();

        while (result.next()) {
            T model = modelClass.getDeclaredConstructor().newInstance();

            for (Field field : modelClass.getDeclaredFields()) {
                field.setAccessible(true);

                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    Object value = result.getObject(column.name());
                    field.set(model, value);
                }
            }
            models.add(model);
        }

        return models;
    }
}
