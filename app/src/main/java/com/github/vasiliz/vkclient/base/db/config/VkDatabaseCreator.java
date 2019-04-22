package com.github.vasiliz.vkclient.base.db.config;

import com.github.vasiliz.vkclient.base.utils.ConstantStrings;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VkDatabaseCreator {

    private final String TAG = VkDatabaseCreator.class.getSimpleName();
    private Class[] mClasses;

    public String getDatabaseName(final AnnotatedElement pClass) {

        final Annotation annotation = pClass.getAnnotation(Database.class);
        if (annotation != null) {
            final String nameDB = (((Database) annotation).databaseName());
            mClasses = ((Database) annotation).entity();
            return nameDB;
        } else {
            throw new RuntimeException("@Database annotation missing");
        }
    }

    public List<String> getListQueryForCreateTable() {
        if (mClasses != null) {
            final List<String> listSql = new ArrayList<String>();
            for (final Class<?> aClass : mClasses) {
                final String headOfQuery = getNameTable(aClass);
                final Field[] fields = aClass.getDeclaredFields();
                final List<String> listWithAllTableFields = new ArrayList<String>();
                final StringBuilder builder = new StringBuilder();
                for (final Field field : fields) {
                    for (final Annotation annotation1 : field.getDeclaredAnnotations()) {
                        createTableFields(annotation1, field, listWithAllTableFields);
                    }
                }
                Collections.sort(listWithAllTableFields);
                listSql.add(headOfQuery + createQuery(listWithAllTableFields, builder) + ConstantStrings.DB.END_OF_QUERY);
            }
            return listSql;
        } else {
            throw new IllegalArgumentException("at fist call getDatabase() method for init entities class");
        }
    }

    private void createTableFields(final Annotation pDeclaredAnnotations, final Field pField, final List<String> pListForFields) {

        //noinspection ChainOfInstanceofChecks
        //todo переделать ID на что нибудь другое
        if (pDeclaredAnnotations instanceof Id) {
            pListForFields.add("id" + ConstantStrings.DB.CREATE_ID_COLUMN);
        }
        if (pDeclaredAnnotations instanceof com.github.vasiliz.vkclient.base.db.config.Field) {
            if (pField.getType().equals(String.class)) {
                pListForFields.add( pField.getName()
                        + ConstantStrings.DB.Type.TEXT);
            } else if ((pField.getType().equals(int.class))
                    || (pField.getType().equals(long.class))) {
                pListForFields.add(pField.getName() + ConstantStrings.DB.Type.INTEGER);
            } else {
                pListForFields.add(pField.getName() + ConstantStrings.DB.Type.TEXT);
            }
        }
    }

    private String createQuery(final List<String> pList, final StringBuilder pStringBuilder) {

        for (int i = 0; i < pList.size(); i++) {
            pStringBuilder
                    .append(pList.get(i))
                    .append(ConstantStrings.DB.COMMA);
        }
        pStringBuilder.delete(pStringBuilder.length() - 1, pStringBuilder.length());
        return pStringBuilder.toString();
    }

    private String getNameTable(final Class<?> pClass) {
        return ConstantStrings.DB.CREATE_TABLE
                + pClass.getSimpleName()
                + ConstantStrings.DB.OPEN_BRACKET;
    }

}
