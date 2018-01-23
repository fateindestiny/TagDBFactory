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

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author FateInDestiny on 2017-05-26.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mETColumn0;
    private EditText mETColumn1;
    private EditText mETColumn2;
    private Button mBtnInsertData;

    private DBFactory mDBFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mETColumn0 = (EditText) findViewById(R.id.et_column_0);
        mETColumn1 = (EditText) findViewById(R.id.et_column_1);
        mETColumn2 = (EditText) findViewById(R.id.et_column_2);
        mBtnInsertData = (Button) findViewById(R.id.main_btn_insert_data);

        mDBFactory = DBFactory.getInstance();
        mDBFactory.initialize(getApplicationContext());

    }

    @Override
    protected void onStart() {
        super.onStart();
        mBtnInsertData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnInsertData) {
            try {
                TestDBInfo testDBInfo = new TestDBInfo(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/test.db", 2);
                mDBFactory.open(testDBInfo);

                ContentValues contentValues = new ContentValues();
                if (!TextUtils.isEmpty(mETColumn0.getText())) {
                    contentValues.put(TestDBInfo.DB_TABLE_0.COLUMN_0, mETColumn0.getText().toString());
                }

                if (!TextUtils.isEmpty(mETColumn1.getText())) {
                    contentValues.put(TestDBInfo.DB_TABLE_0.COLUMN_1, mETColumn1.getText().toString());
                }

                if (!TextUtils.isEmpty(mETColumn2.getText())) {
                    contentValues.put(TestDBInfo.DB_TABLE_0.COLUMN_2, mETColumn2.getText().toString());
                }
                mDBFactory.insertTest(testDBInfo, TestDBInfo.DB_TABLE_0.NAME, contentValues);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
} // end of class MainActivity
