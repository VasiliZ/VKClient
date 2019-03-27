package com.github.vasiliz.vkclient.base.db.config;

import android.util.Log;

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
        try {

            final Annotation annotation = pClass.getAnnotation(Database.class);
            final String nameDB = (((Database) annotation).databaseName());
            mClasses = ((Database) annotation).entity();
            return nameDB;
        } catch (final Exception pE) {
            throw new IllegalArgumentException("no annotated class");
        }
    }

    public List<String> getListQueryForCreateTable() {
        if (mClasses != null) {
            final List<String> listSql = new ArrayList<String>();
            for (final Class<?> aClass : mClasses) {
                String headOfQuery = ConstantStrings.DB.CREATE_TABLE
                        + aClass.getSimpleName()
                        + ConstantStrings.DB.OPEN_BRACKET;
                final Field[] fields = aClass.getDeclaredFields();
                final List<String> tableFields = new ArrayList();
                for (final Field field : fields) {
                    final Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
                    for (final Annotation annotation1 : declaredAnnotations) {

                        if (annotation1 instanceof com.github.vasiliz.vkclient.base.db.config.Field) {
                            if (field.getType().equals(String.class)) {
                                final String textFiels = field.getName()
                                        + ConstantStrings.DB.Type.TEXT;
                                tableFields.add(textFiels);
                            } else if ((field.getType().equals(int.class))
                                    || (field.getType().equals(long.class))) {
                                final String intField = field.getName() + ConstantStrings.DB.Type.INTEGER;
                                tableFields.add(intField);
                            } else {
                                final String otherFields = field.getName() + ConstantStrings.DB.Type.TEXT;
                                tableFields.add(otherFields);
                            }
                        }
                    }
                }
                Collections.sort(tableFields);
                listSql.add(headOfQuery + createCreateQuery(tableFields) + ConstantStrings.DB.END_OF_QUERY);
            }
            return listSql;
        } else {
            throw new IllegalArgumentException("at fist call getDatabase() method for init entities class");
        }
    }

    private String createCreateQuery(List<String> pList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < pList.size(); i++) {
            stringBuilder
                    .append(pList.get(i))
                    .append(ConstantStrings.DB.COMMA);
        }
        stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
        return stringBuilder.toString();
    }

}
