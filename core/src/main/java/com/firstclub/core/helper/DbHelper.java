package com.firstclub.core.helper;

import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class DbHelper
{

    public static <T> Update createUpdateObjectWithFieldsToUpdate(Object sourceObject, Class<T> tClass) {
        var update = new Update();
        var fields = getAllDeclaredFields(tClass);

        for (var field: fields) {
            var descriptor = BeanUtils.getPropertyDescriptor(tClass, field.getName());

            if ( Objects.isNull(descriptor)) {
                continue;
            }
            try {
                Object value = descriptor.getReadMethod().invoke(sourceObject);
                if (Objects.nonNull(value)) {
                    update.set(field.getName(), value);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return update;
    }

    public static List<Field> getAllDeclaredFields(Class<?> tClass) {
        var fields = new ArrayList<Field>();
        while (tClass != null && !tClass.equals(Object.class)) {
            fields.addAll( Arrays.asList(tClass.getDeclaredFields()));
            tClass = tClass.getSuperclass();
        }

        return fields;
    }
}
