package com.uhafactory.builder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by lineplus on 2016. 6. 1..
 */
public abstract class AbstractBuilder<T> {

    public T build() {
        try {
            Object targetObject = ((Class)((ParameterizedType)this.getClass().
                    getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
            Field[] fields = this.getClass().getDeclaredFields();

            for (Field field : fields) {
                // TODO list, map, set 처리
                //			if (field.getType().getSuperclass() == null) {
                //				continue;
                //			}

                apply(this, field, targetObject);
            }
            return (T)targetObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void apply(Object source, Field field, Object target) throws Exception {
        for (SupportClassType supportClassType : SupportClassType.values()) {
            if (!supportClassType.isSupportType(field)) {
                continue;
            }
            field.setAccessible(true);
            Object sourceValue = supportClassType.getSourceValue(field, source, target);
            copyField(field, sourceValue, target);
            return;
        }
    }

    public void copyField(Field field, Object source, Object target) throws Exception {
        Field targetField = target.getClass().getDeclaredField(field.getName());
        targetField.setAccessible(true);
        targetField.set(target, source);
    }

    enum SupportClassType {
        ABSTRACT_BUILDER {
            @Override
            boolean isSupportType(Field field) {
                if (field.getType().getSuperclass() == null) {
                    return false;
                }
                return AbstractBuilder.class.isAssignableFrom(field.getType().getSuperclass());
            }

            @Override
            Object getSourceValue(Field field, Object sourceObject, Object target) throws Exception {
                AbstractBuilder buildHelper = (AbstractBuilder)field.get(sourceObject);
                if (buildHelper == null) {
                    return null;
                }
                return buildHelper.build();
            }
        },
        PRIMITIVE_OBJECT {
            @Override
            boolean isSupportType(Field field) {
                if (field.getType().getSuperclass() == null) {
                    return false;
                }
                return Object.class.isAssignableFrom(field.getType().getSuperclass());
            }

            @Override
            Object getSourceValue(Field field, Object sourceObject, Object target) throws Exception {
                return field.get(sourceObject);
            }
        },
        PRIMITIVE {
            @Override
            boolean isSupportType(Field field) {
                if (field.getType().isPrimitive()) {
                    return true;
                }
                return false;
            }

            @Override
            Object getSourceValue(Field field, Object sourceObject, Object target) throws Exception {
                return field.get(sourceObject);
            }
        },

        LIST {
            private List TEST = ImmutableList.of();

            @Override
            boolean isSupportType(Field field) {
                return field.getType().isInstance(TEST);
            }

            @Override
            Object getSourceValue(Field field, Object sourceObject, Object target) throws Exception {
                List list = (List)field.get(sourceObject);

                if (CollectionUtils.isEmpty(list) || !AbstractBuilder.class.isAssignableFrom(list.get(0).getClass().getSuperclass())) {
                    return list;
                }

                List result = Lists.newArrayList();
                for (Object object : list) {
                    result.add(((AbstractBuilder)object).build());
                }

                return result;
            }
        },
        MAP {
            @Override
            boolean isSupportType(Field field) {
                return false;
            }

            @Override
            Object getSourceValue(Field field, Object sourceObject, Object target) throws Exception {
                return false;
            }

        };
        abstract boolean isSupportType(Field field);

        abstract Object getSourceValue(Field field, Object sourceObject, Object target) throws Exception;

    }

}
