package com.winpams.core.model;

import com.winpams.core.annotations.*;
import com.winpams.core.exceptions.NoAnnotation;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;


public class BaseModel {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

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

    // TODO: implement crud operations
}
