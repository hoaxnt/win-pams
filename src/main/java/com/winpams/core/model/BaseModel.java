package com.winpams.core.model;

import com.winpams.core.Database;
import com.winpams.core.DatabaseOperation;
import com.winpams.core.annotations.*;
import com.winpams.core.exceptions.NoAnnotation;
import org.javatuples.Pair;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseModel {
    @Id
    @Column(name = "id")
    public Long id;

    @Column(name = "created_at")
    public Long createdAt;

    @Column(name = "updated_at")
    public Long updatedAt;

    public BaseModel() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    public Map<String, Object> dump() throws Exception {
        boolean table = this.getClass().isAnnotationPresent(Table.class);

        if (!table) throw new NoAnnotation("Table annotation is missing");

        Map<String, Object> map = new HashMap<>();
        Class<?> currentClass = this.getClass();

        while (currentClass != null) {
            Field[] fields = currentClass.getDeclaredFields();

            for (Field field : fields) {
                boolean column = field.isAnnotationPresent(Column.class);
                if (!column) continue;

                Column columnAnnotation = field.getAnnotation(Column.class);

                field.setAccessible(true);
                String columnName = columnAnnotation.name();
                Object value = field.get(this);

                map.put(columnName, value);
            }

            currentClass = currentClass.getSuperclass();
        }

        return map;
    }

    private <T extends BaseModel> List<T> load(ResultSet result) throws Exception {

        @SuppressWarnings("unchecked") Class<T> modelClass = (Class<T>) this.getClass();
        List<T> models = new ArrayList<>();

        do {
            T model = modelClass.getDeclaredConstructor().newInstance();
            for (Field field : modelClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    field.setAccessible(true);
                    Column column = field.getAnnotation(Column.class);
                    Object value = result.getObject(column.name());
                    field.set(model, value);
                }
            }
            models.add(model);
        } while (result.next());

        return models;
    }

    public void save() throws Exception {
        Database db = Database.getInstance();

        if (this.id != null) {
            Pair<String, Object[]> query = db.buildQuery(this, DatabaseOperation.UPDATE);
            db.execute(query.getValue0(), query.getValue1());

            return;
        }

        Pair<String, Object[]> query = db.buildQuery(this, DatabaseOperation.INSERT);
        db.execute(query.getValue0(), query.getValue1());
    }

    public void delete() throws Exception {
        Database db = Database.getInstance();
        Pair<String, Object[]> query = db.buildQuery(this, DatabaseOperation.DELETE);
        db.execute(query.getValue0(), query.getValue1());
    }

    public <T extends BaseModel> List<T> find(Long id, String[] cols) throws Exception {
        Database db = Database.getInstance();
        @SuppressWarnings("unchecked")

        Pair<String, Object[]> query = db.buildQuery(this, DatabaseOperation.SELECT, cols, id);

        System.out.println(query.getValue0());

        return null;
//        ResultSet result = db.execute(query.getValue0(), query.getValue1());
//
//        if (!result.next()) return null;
//
//        return load(result);
    }

    public <T extends BaseModel> T find(Long id) throws Exception {
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
}
