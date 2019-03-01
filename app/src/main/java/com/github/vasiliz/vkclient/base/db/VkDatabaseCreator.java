package com.github.vasiliz.vkclient.base.db;

import android.util.Log;

import com.github.vasiliz.vkclient.base.utils.ConstantStrings;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.ArrayList;
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
            final StringBuilder stringBuilder = new StringBuilder();
            for (final Class aClass : mClasses) {

                final String createTableSql =
                        ConstantStrings.DB.CREATE_TABLE
                                + aClass.getSimpleName();
                stringBuilder.append(createTableSql);
                final Field[] fields = aClass.getDeclaredFields();
                for (final Field field : fields) {
                    final Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
                    for (final Annotation annotation1 : declaredAnnotations) {

                        if (annotation1 instanceof com.github.vasiliz.vkclient.base.db.Id) {
                            final String idColumn =
                                    ConstantStrings.DB.OPEN_BRACKET
                                            + field.getName()
                                            + ConstantStrings.DB.CREATE_ID_COLUMN;
                            stringBuilder.append(idColumn);
                        } else {
                            if (field.getType().equals(String.class)) {
                                final String createVarcharFields =
                                        ConstantStrings.DB.COMMA
                                                + field.getName()
                                                + ConstantStrings.DB.Type.VARCHAR;
                                stringBuilder.append(createVarcharFields);
                            } else if ((field.getType().equals(int.class))
                                    || (field.getType().equals(long.class))) {
                                final String createIntFields =
                                        ConstantStrings.DB.COMMA
                                                + field.getName()
                                                + ConstantStrings.DB.Type.INTEGER;
                                stringBuilder.append(createIntFields);
                            } else {
                                final String objectField =
                                        ConstantStrings.DB.COMMA
                                                + field.getName()
                                                + ConstantStrings.DB.Type.TEXT;
                                stringBuilder.append(objectField);
                            }
                        }
                    }
                }
                stringBuilder.append(ConstantStrings.DB.END_OF_QUERY);
                Log.d(TAG, "getListQueryForCreateTable: " + stringBuilder);
                listSql.add(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            }
            return listSql;
        } else {
            throw new IllegalArgumentException("at fist call getDatabase() method for init entities class");
        }
    }

}
