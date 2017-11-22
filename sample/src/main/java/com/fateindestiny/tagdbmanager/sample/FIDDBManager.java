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
package com.fateindestiny.tagdbmanager.sample;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.fateindestiny.tagdbmanager.TagDBInfo;
import com.fateindestiny.tagdbmanager.TagDBManager;

/**
 * @author FateInDestiny on 2017-05-26.
 */
public class FIDDBManager extends TagDBManager {
    private static FIDDBManager mInstance;

    private FIDDBManager() {
    }

    public static FIDDBManager getInstance() {
        if (FIDDBManager.mInstance == null) {
            synchronized (FIDDBManager.class) {
                if (FIDDBManager.mInstance == null) {
                    FIDDBManager.mInstance = new FIDDBManager();
                }
            }
        }
        return FIDDBManager.mInstance;
    }


    public void insertTest(TagDBInfo dbInfo, String tableName, ContentValues contentValues) {
        try {
            SQLiteDatabase sqLiteDatabase = open(dbInfo);
            sqLiteDatabase.insert(tableName, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


} // end of class FIDDBManager
