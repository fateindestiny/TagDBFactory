package com.fateindestiny.tagdbmanager.sample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.fateindestiny.tagdbmanager.*;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DBInfoTest {
    @Test
    public void emptyInfoTest() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        TestDBManager dbManager = new TestDBManager();
        dbManager.initialize(context);
        EmptyDBInfo dbInfo = new EmptyDBInfo();
        dbManager.open(dbInfo);
    }

    @Test
    public void emptyTableNameTest() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        TestDBManager dbManager = new TestDBManager();
        dbManager.initialize(context);
        EmptyTableNameTest dbInfo = new EmptyTableNameTest();
        dbManager.open(dbInfo);
    }

    @Test
    public void emptyColumnInfoTest() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        TestDBManager dbManager = new TestDBManager();
        dbManager.initialize(context);
        EmptyColumnInfoTest dbInfo = new EmptyColumnInfoTest();
        dbManager.open(dbInfo);
    }

    @Test
    public void insertTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        TestDBManager dbManager = new TestDBManager();
        dbManager.initialize(appContext);
        TestDBInfo dbInfo = new TestDBInfo(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/test01.db", 1);
        ContentValues values = new ContentValues();
        values.put("column0", "test 11");
        dbManager.insertTest(dbInfo, values);
    }

    class TestDBManager extends TagDBManager {
        void insertTest(TestDBInfo dbInfo, ContentValues values) {
            try {
                SQLiteDatabase database = open(dbInfo);
                database.insert("tb_test", null, values);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class EmptyDBInfo extends TagDBInfo {
        public EmptyDBInfo() {
            super(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/test01.db", 1);
        }
    }

    class EmptyTableNameTest extends TagDBInfo {
        public EmptyTableNameTest() {
            super(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/test01.db", 1);
        }

        @TagTable
        class Table {

        }
    } // end of class EmptyTableNameTest

    class EmptyColumnInfoTest extends TagDBInfo {
        public EmptyColumnInfoTest() {
            super(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/test01.db", 1);
        }

        @TagTable
        class Table {
            @TagTableName
            String TABLE_NAME = "tb_empty";
        }
    } // end of class EmptyTableNameTest

    static class TestDBInfo extends TagDBInfo {
        TestDBInfo(@NonNull String DBFileName, int DBVersion) {
            super(DBFileName, DBVersion);
        }

        @TagTable
        class Table {
            @TagTableName
            String name = "tb_test";

            @TagColumn
            String column0 = "column0";
        }
    } // end of class TestDBInfo
} // end of class DBInfoTest
