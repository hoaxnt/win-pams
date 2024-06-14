package com.winpams.data.model;

import com.winpams.data.Database;
import com.winpams.data.annotations.*;
import com.winpams.core.exceptions.NoAnnotation;
import org.javatuples.Pair;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseModel {
    @Id
    @Column(name = "id")
    public Integer id;

    @Column(name = "created_at")
    public Timestamp createdAt;

    @Column(name = "updated_at")
    public Timestamp updatedAt;

    public BaseModel() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
        checkQueryField();
    }

    private void checkQueryField() {
        try {
            Field queryField = this.getClass().getField("query");

            if (!QueryBuilder.class.isAssignableFrom(queryField.getType()))
                throw new IllegalStateException(this.getClass().getName() + " must have a static field 'query' of type QueryBuilder<" + this.getClass().getSimpleName() + ">.");
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException(this.getClass().getName() + " must have a static field 'query' of type QueryBuilder<" + this.getClass().getSimpleName() + ">.");
        }
    }

    public Map<String, Object> dump() throws Exception {
        boolean table = this.getClass().isAnnotationPresent(Entity.class);

        if (!table) throw new NoAnnotation("Table annotation is missing");

        Map<String, Object> map = new HashMap<>();

        for (Field field : getFields()) {
            if (!field.isAnnotationPresent(Column.class)) continue;

            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            Object value = field.get(this);
            map.put(column.name(), value);
        }

        return map;
    }

    public void save() throws Exception {
        Database db = Database.instance();

        if (this.id != null) {
            this.updatedAt = new Timestamp(System.currentTimeMillis());
            Pair<String, Object[]> query = db.buildQuery(this, Database.Operation.UPDATE);
            db.execute(query.getValue0(), query.getValue1());
            return;
        }

        Pair<String, Object[]> query = db.buildQuery(this, Database.Operation.INSERT);
        db.execute(query.getValue0(), query.getValue1());
    }

    public void delete() throws Exception {
        Database db = Database.instance();
        Pair<String, Object[]> query = db.buildQuery(this, Database.Operation.DELETE);
        db.execute(query.getValue0(), query.getValue1());
    }

    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();
        return "<" + className + " id=" + this.id + ">";
    }

    List<Field> getFields() {
        List<Field> fields = new ArrayList<>();
        Class<?> currentClass = this.getClass();

        while (currentClass != null) {
            Field[] currentFields = currentClass.getDeclaredFields();
            fields.addAll(Arrays.stream(currentFields)
                    .filter(field -> field.isAnnotationPresent(Column.class))
                    .collect(Collectors.toList())
            );
            currentClass = currentClass.getSuperclass();
        }

        return fields;
    }

    private static <T extends BaseModel> List<T> load(Class<T> modelClass, ResultSet result) throws Exception {
        List<T> models = new ArrayList<>();

        while (result.next()) {
            T model = modelClass.getDeclaredConstructor().newInstance();

            for (Field field : model.getFields()) {


                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                Object value = result.getObject(column.name());
                field.set(model, value);
            }
            models.add(model);
        }

        return models;
    }

    public static class QueryBuilder<T extends BaseModel> {
        private final Class<T> modelClass;
        private final List<String> whereClauses = new ArrayList<>();
        private final List<Object> parameters = new ArrayList<>();
        private final List<String> orderByClauses = new ArrayList<>();

        public QueryBuilder(Class<T> modelClass) {
            this.modelClass = modelClass;
        }

        public QueryBuilder<T> where(String column, Object value) {
            if (whereClauses.isEmpty()) {
                whereClauses.add(column + " = ?");
            } else {
                whereClauses.add("AND " + column + " = ?");
            }
            parameters.add(value);
            return this;
        }

        public QueryBuilder<T> and(String column, Object value) {
            whereClauses.add("AND " + column + " = ?");
            parameters.add(value);
            return this;
        }

        public QueryBuilder<T> or(String column, Object value) {
            whereClauses.add("OR " + column + " = ?");
            parameters.add(value);
            return this;
        }

        public QueryBuilder<T> like(String column, Object value) {
            whereClauses.add(column + " LIKE ?");
            parameters.add(value);
            return this;
        }

        public QueryBuilder<T> greaterThan(String column, Object value) {
            whereClauses.add(column + " > ?");
            parameters.add(value);
            return this;
        }

        public QueryBuilder<T> orderBy(String column) {
            orderByClauses.add(column);
            return this;
        }

        public QueryBuilder<T> orderBy(String column, String order) {
            orderByClauses.add(column + " " + order);
            return this;
        }

        public List<T> execute() throws Exception {
            Database db = Database.instance();
            StringBuilder query = new StringBuilder("SELECT * FROM ");
            query.append(getTableName());

            if (!whereClauses.isEmpty()) {
                query.append(" WHERE ").append(String.join(" ", whereClauses));
            }

            if (!orderByClauses.isEmpty()) {
                query.append(" ORDER BY ").append(String.join(", ", orderByClauses));
            }

            ResultSet result = db.execute(query.toString(), parameters.toArray());
            return load(modelClass, result);
        }

        private String getTableName() throws NoAnnotation {
            Entity entity = modelClass.getAnnotation(Entity.class);
            if (entity == null) {
                throw new NoAnnotation("Table annotation is missing");
            }
            return entity.name();
        }

        public T find(Integer id) throws Exception {
            if (id == null) throw new IllegalArgumentException("id cannot be null");
            where("id", id);
            List<T> models = execute();
            if (models == null || models.isEmpty()) return null;
            return models.get(0);
        }

        public List<T> find(Integer id, String[] cols) throws Exception {
            Database db = Database.instance();
            Pair<String, Object[]> query = db.buildQuery(modelClass.getDeclaredConstructor().newInstance(), Database.Operation.SELECT, cols, id);
            ResultSet result = db.execute(query.getValue0(), query.getValue1());
            return load(modelClass, result);
        }

        public List<T> all() throws Exception {
            return execute();
        }

        public List<T> all(String[] cols) throws Exception {
            return find(null, cols);
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

        public T first(String[] cols) throws Exception {
            return find(null, cols).get(0);
        }

        public T last(String[] cols) throws Exception {
            List<T> models = find(null, cols);
            return models.get(models.size() - 1);
        }
    }
}
