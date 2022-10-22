package com.tsa.orm.services;

import com.tsa.orm.annotation.MyColumn;
import com.tsa.orm.annotation.MyEntity;
import com.tsa.orm.annotation.MyTable;
import com.tsa.orm.interfaces.QueryGenerator;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryGeneratorImpl implements QueryGenerator {
    private static final String SELECT = "SELECT ";
    private static final String DELETE = "DELETE ";
    private static final String FROM = "FROM ";
    private static final String SEMICOLON = ";";
    public QueryGeneratorImpl() {}

    @Override
    public String findAll(Class<?> type, String... columns) {
        nullCheck(type);
        entityCheck(type);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT);
        if (columns.length > 0) {
            StringJoiner stringJoiner = new StringJoiner(", ", "", " ");
            for (String column : columns) {
                for (Field declaredField : type.getDeclaredFields()) {
                    declaredField.setAccessible(true);
                    String retrievedColumnName;
                    if (declaredField.getAnnotation(MyColumn.class) != null &&
                            declaredField.getAnnotation(MyColumn.class).value().length() > 0) {
                        retrievedColumnName = declaredField.getAnnotation(MyColumn.class).value();
                    } else {
                        retrievedColumnName = declaredField.getName();
                    }
                    if (column.equals(retrievedColumnName)) stringJoiner.add(retrievedColumnName);
                }
            }
            stringBuilder.append(stringJoiner);
        } else {
            stringBuilder.append("* ");
        }
        stringBuilder.append(FROM);
        getClassName(type, stringBuilder);
        stringBuilder.append(SEMICOLON);

        return stringBuilder.toString();
    }

    @Override
    public String findById(Class<?> type, Serializable id) {
        nullCheck(type, id);
        entityCheck(type);
        return methodsById(type, id, SELECT);
    }

    @Override
    public String deleteById(Class<?> type, Serializable id) {
        nullCheck(type, id);
        entityCheck(type);
        return methodsById(type, id, DELETE);
    }

    @Override
    public String insert(Object value) throws IllegalAccessException {
        nullCheck(value);
        Class<?> retrievedClazz = value.getClass();
        entityCheck(value);
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> aggregator = new HashMap<>();
        stringBuilder.append("INSERT INTO ");
        getClassName(retrievedClazz, stringBuilder);
        stringBuilder.append(" (");
        StringJoiner stringJoiner = new StringJoiner(", ");
        mapFiller(value, retrievedClazz, aggregator);
        for (String s : aggregator.keySet()) {
            stringJoiner.add(s);
        }
        stringBuilder.append(stringJoiner);
        stringBuilder.append(") VALUE (");
        stringJoiner = new StringJoiner(", ");
        for (String s : aggregator.keySet()) {
            stringJoiner.add(aggregator.get(s).toString());
        }
        stringBuilder.append(stringJoiner);
        stringBuilder.append(");");
        return stringBuilder.toString();
    }

    @Override
    public String update(Object value) throws IllegalAccessException {
        nullCheck(value);
        Class<?> retrievedClazz = value.getClass();
        entityCheck(value);
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> aggregator = new HashMap<>();
        stringBuilder.append("UPDATE ");
        getClassName(retrievedClazz, stringBuilder);
        stringBuilder.append(" SET ");
        mapFiller(value, retrievedClazz, aggregator);
        StringJoiner stringJoiner = new StringJoiner(", ");
        StringBuilder condition = new StringBuilder();
        for (String s : aggregator.keySet()) {
            StringBuilder stringBuilder1 = new StringBuilder();
            if(s.endsWith("id")) {
                condition.append(s); condition.append(" = "); condition.append(aggregator.get(s));
            }
            if(!s.endsWith("id")) {
                stringBuilder1.append(s);
                stringBuilder1.append(" = ");
                stringBuilder1.append(aggregator.get(s));
            }
            if (stringBuilder1.toString().length() > 0)stringJoiner.add(stringBuilder1);
        }
        stringBuilder.append(stringJoiner);
        stringBuilder.append(" WHERE ");
        stringBuilder.append(condition);
        stringBuilder.append(SEMICOLON);
        return stringBuilder.toString();
    }

    private String methodsById (Class<?> type, Serializable id, String action) {
        Long incomeId = null;
        String idClassName = id.getClass().getName();
        if(idClassName.contains("String")) {
            Matcher matcher = Pattern.compile("\\d+").matcher(id.toString());
            while (matcher.find()) {
                String result = matcher.group();
                incomeId = Long.parseLong(result);
                if (!result.isEmpty()) break;
            }
            if (incomeId == null) {
                throw new IllegalArgumentException("Entity id should be String or integer");
            }
        } else if (idClassName.contains("Integer")) {
            Integer integer = (Integer) id;
            incomeId = integer.longValue();
        } else if (idClassName.contains("Long")) {
            incomeId = (Long) id;
        } else {
            throw new IllegalArgumentException("Entity id should be String or integer");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(action);
        stringBuilder.append("* FROM ");
        getClassName(type, stringBuilder);
        stringBuilder.append(" WHERE id = ");
        stringBuilder.append(incomeId);
        stringBuilder.append(SEMICOLON);

        return stringBuilder.toString();
    }
    private void nullCheck(Object value, Object... arg) {
        if (arg.length > 0) {
            if(value == null || arg[0] == null) {
                throw new IllegalArgumentException("The arguments cannot be \"null\"");
            }
        }
        if(value == null) throw new IllegalArgumentException("The arguments cannot be \"null\"");
    }
    private <T> void entityCheck(T type) {
        if (type instanceof Class<?>) {
            if(((Class<?>)type).getAnnotation(MyEntity.class) == null) {
                throw new IllegalStateException("Provided class: "+ ((Class<?>)type).getName() + " not an Entity");
            }
        } else {
            if(type.getClass().getAnnotation(MyEntity.class) == null) {
                throw new IllegalStateException("Provided class: "+ type.getClass().getName() + " not an Entity");
            }
        }
    }
    private void getClassName (Class<?> type, StringBuilder stringBuilder) {
        if (type.getAnnotation(MyTable.class) != null &&
                type.getAnnotation(MyTable.class).value().length() > 0) {
            stringBuilder.append(type.getAnnotation(MyTable.class).value().toLowerCase());
        } else {
            stringBuilder.append(type.getSimpleName().toLowerCase());
        }
    }
    private void mapFiller(Object value, Class<?> retrievedClazz, Map<String, Object> aggregator) throws IllegalAccessException {
        for (Field declaredField : retrievedClazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            if (declaredField.getAnnotation(MyColumn.class) != null &&
                    declaredField.getAnnotation(MyColumn.class).value().length() > 0) {
                if (declaredField.get(value) != null && !declaredField.get(value).toString().isEmpty()) {
                    Object retrievedString = declaredField.get(value);
                    if(retrievedString instanceof String) retrievedString = "\"" + retrievedString + "\"";
                    aggregator.put(declaredField.getAnnotation(MyColumn.class).value(), retrievedString);
                }
            } else {
                if (declaredField.get(value) != null && !declaredField.get(value).toString().isEmpty()) {
                    Object retrievedString = declaredField.get(value);
                    if(retrievedString instanceof String) retrievedString = "\"" + retrievedString + "\"";
                    aggregator.put(declaredField.getName(), retrievedString);
                }
            }
        }
    }
}
