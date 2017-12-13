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
package com.fateindestiny.tagdbfactory;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

/**
 * This class is database information class.
 * Basically extended this class.
 * This class has database file name, version and query event interface that use in {@link TagDBFactory}.
 *
 * @author FateInDestiny on 2017-05-26.
 */
public abstract class TagDBInfo {
    private final String mDBFileName;
    private final int mDBVersion;
    private WorkQueryFunction mWorkQueryFunction;

    /**
     * This interface call when table upgrade and downgrade in {@link TagDBFactory.DBOpenHelper}.
     */
    public interface WorkQueryFunction {
        /**
         * Called when table upgrade.
         * This parameters receive from {@link TagDBFactory.DBOpenHelper#onUpgrade(SQLiteDatabase, int, int)}.
         *
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

        /**
         * Called when table downgrade.
         * This parameters receive from {@link TagDBFactory.DBOpenHelper#onDowngrade(SQLiteDatabase, int, int)}.
         *
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion);
    }

    public TagDBInfo(@NonNull String DBFileName, int DBVersion) {
        mDBFileName = DBFileName;
        mDBVersion = DBVersion;
    }

    public String getDBFileName() {
        return mDBFileName;
    }

    public int getDBVersion() {
        return mDBVersion;
    }

    public WorkQueryFunction getWorkQueryFunction() {
        return mWorkQueryFunction;
    }

    public void setWorkQueryFunction(WorkQueryFunction workQueryFunction) {
        mWorkQueryFunction = workQueryFunction;
    }

    /**
     * This method checked if the {@link TagDBInfo} is the same as {@link TagDBInfo#getDBFileName()}.
     *
     * @param obj This is target object. Needs type of {@link TagDBInfo} or {@link String}
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if (obj instanceof TagDBInfo) {
            result = this.mDBFileName.equals(((TagDBInfo) obj).getDBFileName());
        } else if (obj instanceof String) {
            result = this.mDBFileName.equals(obj);
        }

        return result;
    }
} // end of class TagDBInfo
