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
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * @author FateInDestiny on 2017-05-26.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtnInsertData;
    private FIDDBManager mFIDDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnInsertData = (Button) findViewById(R.id.main_btn_insert_data);

        mFIDDBManager = FIDDBManager.getInstance();
        mFIDDBManager.initialize(getApplicationContext());

    }

    @Override
    protected void onStart() {
        super.onStart();
        mBtnInsertData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mBtnInsertData) {
            try {
                TestDBInfo testDBInfo = new TestDBInfo(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/test.db", 2);
                mFIDDBManager.open(testDBInfo);

                ContentValues contentValues = new ContentValues();
                contentValues.put(TestDBInfo.DB_TABLE_0.COLUMN_0, "test text");
                contentValues.put(TestDBInfo.DB_TABLE_0.COLUMN_1, 11);
                mFIDDBManager.insertTest(testDBInfo, TestDBInfo.DB_TABLE_0.NAME, contentValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
} // end of class MainActivity
