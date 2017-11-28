/****************************************************************************************************
 *  Copyright 2017 FateInDestiny(Ki-man, Kim)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 ****************************************************************************************************/
package com.fateindestiny.tagdbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * This class is SQLite database manager class.
 * Basically use extended this class.
 * Managing according to extended {@link TagDBInfo} object.
 *
 * @author FateInDestiny on 2017-05-26.
 */
public abstract class TagDBManager {
    public enum COLUMN_TYPE {
        TEXT,
        INTEGER,
        BLOB,
        REAL,
        NUMERIC,
    }

    private Context mContext;
    private ArrayList<DBManagerHelper> mDBManagerHelpers;

    /*****************************************************************************************************
     * Method
     *****************************************************************************************************/
    /**
     * This is initializing method. Needs first execute before use.
     *
     * @param context
     */
    public void initialize(@NonNull Context context) {
        mContext = context;
        mDBManagerHelpers = new ArrayList<DBManagerHelper>();
    }

    /**
     * Database open according to parameter {@link TagDBInfo}.
     *
     * @param tagDbInfo This parameter is target {@link TagDBInfo} for open database.
     * @return Return writable database obejct by parameter {@link TagDBInfo}.
     * @throws Exception Throws if not call {@link TagDBManager#initialize(Context)}.
     */
    public SQLiteDatabase open(TagDBInfo tagDbInfo) throws Exception {
        if (mContext == null) {
            throw new NullPointerException("Context is null. Not initialized.");
        }

        return getDBManagerHelper(tagDbInfo).getWritableDatabase();
    }

    /**
     * This is working find {@link DBManagerHelper} according to {@link TagDBInfo}.
     *
     * @param tagDbInfo This parameter is condition for find {@link DBManagerHelper}.
     * @return Return finded {@link DBManagerHelper}.
     */
    private DBManagerHelper getDBManagerHelper(TagDBInfo tagDbInfo) {
        DBManagerHelper result;
        int index = mDBManagerHelpers.indexOf(tagDbInfo);

        if (index > -1) {
            result = mDBManagerHelpers.get(index);
        } else {
            result = new DBManagerHelper(tagDbInfo, null);
            mDBManagerHelpers.add(result);
        }
        return result;
    }

    /**
     * This method checked if database is open according to {@link TagDBInfo}.
     *
     * @param tagDbInfo This parameter is condition for check open database.
     * @return Return true if database open.
     */
    public boolean isOpen(TagDBInfo tagDbInfo) {
        int index = mDBManagerHelpers.indexOf(tagDbInfo);
        if (index > -1) {
            return mDBManagerHelpers.get(index).getWritableDatabase().isOpen();
        } else {
            return false;
        }
    }

    /**
     * Database close according to parameter {@link TagDBInfo}.
     *
     * @param tagDbInfo This parameter is target {@link TagDBInfo} for close database.
     * @return Return true if call close method.
     * @throws Exception Throws if not call {@link TagDBManager#initialize(Context)}.
     */
    public boolean close(TagDBInfo tagDbInfo) throws Exception {
        if (mContext == null) {
            throw new NullPointerException("Context is null. Not initialized.");
        }

        int index = mDBManagerHelpers.indexOf(tagDbInfo);
        if (index > -1) {
            mDBManagerHelpers.get(index).close();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generate table query by {@link TagDBInfo} extended class.
     * It find annotation and change to create query if inner classes named by {@link TagTable}.
     *
     * @param tagDbInfo A class that has table creation information by {@link TagDBInfo} extended class.
     * @return Table creation query list.
     */
    private ArrayList<String> generateCreateQuerys(TagDBInfo tagDbInfo) {
        Class<? extends TagDBInfo> clazz = tagDbInfo.getClass();
        ArrayList<String> createQuerys = new ArrayList<String>();

        StringBuilder queryBuilder = new StringBuilder();

        String tableNm;
        ArrayList<String> columnList = new ArrayList<String>();
        StringBuilder columnBuilder = new StringBuilder();
        int columnSize;
        int columnIndex;

        try {
            for (Class<?> tClass : clazz.getDeclaredClasses()) {
                if (tClass.isAnnotationPresent(TagTable.class)) {
                    tableNm = null;
                    columnList.clear();
                    queryBuilder.setLength(0);
                    if (BaseColumns.class.isAssignableFrom(tClass)) {
                        columnBuilder.append(BaseColumns._ID);
                        columnBuilder.append(" INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE");
                        columnList.add(columnBuilder.toString());
                        columnBuilder.setLength(0);
                        columnBuilder.append(BaseColumns._COUNT);
                        columnBuilder.append(" INTEGER");
                        columnList.add(columnBuilder.toString());
                    }

                    for (Field field : tClass.getDeclaredFields()) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(TagTableName.class) && tableNm == null) {
                            // Table
                            tableNm = (String) field.get(tagDbInfo);
                        } else if (field.isAnnotationPresent(TagColumn.class)) {
                            // Column
                            TagColumn annotation = field.getAnnotation(TagColumn.class);

                            columnBuilder.setLength(0);
                            columnBuilder.append(field.get(tagDbInfo));
                            columnBuilder.append(" ");
                            columnBuilder.append(annotation.type());

                            if (annotation.type() == TagDBManager.COLUMN_TYPE.TEXT && annotation.hasNotNull()) {
                                columnBuilder.append(" NOT NULL");
                            }

                            if (annotation.isPrimaryKey()) {
                                columnBuilder.append(" PRIMARY KEY");
                            }

                            if (annotation.isAutoIncrement()) {
                                columnBuilder.append(" AUTOINCREMENT");
                            }

                            if (annotation.isUnique()) {
                                columnBuilder.append(" UNIQUE");
                            }

                            if (!TextUtils.isEmpty(annotation.defaultValue())) {
                                columnBuilder.append(" DEFAULT ");
                                columnBuilder.append(annotation.defaultValue());
                            }

                            columnList.add(columnBuilder.toString());
                        }
                    }

                    if(TextUtils.isEmpty(tableNm)) {
                        throw new Exception("Table name is empty.");
                    }

                    queryBuilder.append("CREATE TABLE ");
                    queryBuilder.append(tableNm);
                    queryBuilder.append("(");
                    columnIndex = 0;
                    columnSize = columnList.size();

                    for (String columns : columnList) {
                        queryBuilder.append(columns);
                        columnIndex++;
                        if (columnIndex < columnSize) {
                            queryBuilder.append(",");
                        }
                    }
                    queryBuilder.append(")");
                    createQuerys.add(queryBuilder.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return createQuerys;
    }

    /*****************************************************************************************************
     * Inner Class
     *****************************************************************************************************/
    /**
     * This is DB Helper class extended {@link SQLiteOpenHelper}.
     */
    protected class DBManagerHelper extends SQLiteOpenHelper {
        private TagDBInfo mTagDBInfo;

        public DBManagerHelper(TagDBInfo tagDbInfo, SQLiteDatabase.CursorFactory factory) {
            super(mContext, tagDbInfo.getDBFileName(), factory, tagDbInfo.getDBVersion());
            this.mTagDBInfo = tagDbInfo;
        }

        /**
         * Create table by querys maked by {@link TagDBManager#generateCreateQuerys(TagDBInfo)}.
         *
         * @param db
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            ArrayList<String> createQuerys = generateCreateQuerys(this.mTagDBInfo);
            for (String createQuery : createQuerys) {
                db.execSQL(createQuery);
            }
        }

        /**
         * This method is Upgrade database method.<br>
         * Calling event {@link TagDBInfo.WorkQueryFunction#onUpgrade(SQLiteDatabase, int, int)} in {@link TagDBInfo}.
         *
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            TagDBInfo.WorkQueryFunction workQueryFunction = this.mTagDBInfo.getWorkQueryFunction();
            if (workQueryFunction != null) {
                workQueryFunction.onUpgrade(db, oldVersion, newVersion);
            }
        }

        /**
         * This method is downgrade database method.<br>
         * Calling event {@link TagDBInfo.WorkQueryFunction#onDowngrade(SQLiteDatabase, int, int)} in {@link TagDBInfo}.
         *
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            TagDBInfo.WorkQueryFunction workQueryFunction = this.mTagDBInfo.getWorkQueryFunction();
            if (workQueryFunction != null) {
                workQueryFunction.onDowngrade(db, oldVersion, newVersion);
            }
        }

        /**
         * This methd checked if the {@link DBManagerHelper} is the same as {@link TagDBInfo}.
         *
         * @param obj This parameter is target object. Needs extended {@link DBManagerHelper} or {@link TagDBInfo}.
         * @return
         */
        @Override
        public boolean equals(Object obj) {
            boolean result = false;

            if (obj instanceof DBManagerHelper) {
                result = this.mTagDBInfo.equals(((DBManagerHelper) obj).getTagDBInfo());
            } else if (obj instanceof TagDBInfo) {
                result = this.mTagDBInfo.equals(obj);
            }

            return result;
        }

        /**
         * Return {@link TagDBInfo} object in this class.
         *
         * @return {@link TagDBInfo}
         */
        public TagDBInfo getTagDBInfo() {
            return mTagDBInfo;
        }

    } // end of class DBManagerHelper
} // end of class TagDBManager
