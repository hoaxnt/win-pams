package com.winpams.data.model;

import com.winpams.data.Database;
import com.winpams.data.annotations.*;
import com.winpams.core.exceptions.NoAnnotation;
import org.javatuples.Pair;


import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

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

    private <T extends BaseModel> List<T> load(ResultSet result) throws Exception {

        List<T> models = new ArrayList<>();

        do {
            @SuppressWarnings("unchecked")
            T model = (T) this.getClass().getDeclaredConstructor().newInstance();

            for (Field field : getFields()) {
                if (!field.isAnnotationPresent(Column.class)) continue;

                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                Object value = result.getObject(column.name());
                field.set(model, value);
            }
            models.add(model);
        } while (result.next());

        return models;
    }

    public void save() throws Exception {
        Database db = Database.getInstance();

        if (this.id != null) {
            Pair<String, Object[]> query = db.buildQuery(this, Database.Operation.UPDATE);
            db.execute(query.getValue0(), query.getValue1());

            return;
        }

        Pair<String, Object[]> query = db.buildQuery(this, Database.Operation.INSERT);
        db.execute(query.getValue0(), query.getValue1());
    }

    public void delete() throws Exception {
        Database db = Database.getInstance();
        Pair<String, Object[]> query = db.buildQuery(this, Database.Operation.DELETE);
        db.execute(query.getValue0(), query.getValue1());
    }

    public <T extends BaseModel> List<T> find(Integer id, String[] cols) throws Exception {
        Database db = Database.getInstance();

        Pair<String, Object[]> query = db.buildQuery(this, Database.Operation.SELECT, cols, id);
        ResultSet result = db.execute(query.getValue0(), query.getValue1());

        if (!result.next()) return null;

        return load(result);
    }

    public <T extends BaseModel> T find(Integer id) throws Exception {
        if (id == null) throw new IllegalArgumentException("id cannot be null");

        List<T> models = find(id, null);

        if (models == null) return null;

        return models.isEmpty() ? null : models.get(0);
    }

    public <T extends BaseModel> List<T> all() throws Exception {
        return find(null, null);
    }

    public <T extends BaseModel> List<T> all(String[] cols) throws Exception {
        return find(null, cols);
    }

    @Override
    public String toString() {
        String className = this.getClass().getSimpleName();

        return "<" + className + " id=" + this.id + ">";
    }

    private List<Field> getFields() {
        List<Field> fields = new ArrayList<>();
        Class<?> currentClass = this.getClass();

        while (currentClass != null) {
            Field[] currentFields = currentClass.getDeclaredFields();
            fields.addAll(Arrays.asList(currentFields));
            currentClass = currentClass.getSuperclass();
        }

        return fields;
    }
}
