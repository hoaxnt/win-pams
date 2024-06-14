package com.winpams.data.model;

import com.winpams.data.Database;
import com.winpams.data.QueryBuilder;
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

    public List<Field> getFields() {
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
}
