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
package com.fateindestiny.tagdbfactory.sample;

import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.fateindestiny.tagdbfactory.TagColumn;
import com.fateindestiny.tagdbfactory.TagDBInfo;
import com.fateindestiny.tagdbfactory.TagDBFactory;
import com.fateindestiny.tagdbfactory.TagTable;
import com.fateindestiny.tagdbfactory.TagTableName;

/**
 * @author FateInDestiny on 2017-05-26.
 */
public class TestDBInfo extends TagDBInfo {
    public TestDBInfo(@NonNull String DBFileName, int DBVersion) {
        super(DBFileName, DBVersion);
    }

    @TagTable
    public class DB_TABLE_0 implements BaseColumns {
        @TagTableName
        public static final String NAME = "tb_test_0";


        @TagColumn(type = TagDBFactory.COLUMN_TYPE.TEXT, hasNotNull = true)
        public static final String COLUMN_0 = "column_0";
        @TagColumn(type = TagDBFactory.COLUMN_TYPE.INTEGER, defaultValue = "1")
        public static final String COLUMN_1 = "column_1";
        @TagColumn
        public static final String COLUMN_2 = "column_2";
    }
} // end of class TestDBInfo
