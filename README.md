# TagDBManager
The TagDBManager help for make database manager module by the SQLite base. This library can make database table by the Object class.

## Download


## Usage
### Make the database infomation class
Infomation class extended by the `TagDBInfo` class must make for use this library.
```java
public class TestDBInfo extends TagDBInfo {
	public TestDBInfo(String DBFileName, int DBVersion) {
        super(DBFileName, DBVersion);
    }
}
```

### Make the table information class
The making database table need the table information class. This class make in the database information. And to do next list.

1. Add the `@TagTable` annotation in the table class.
2. Add the `@TagTableName` in the table name string field.

```java
@TagTable
public class DB_TABLE_0 {
    @TagTableName
    public static final String NAME = "tb_test_0";
}
```

And if table class implement `android.provider.BaseColumn` in the table class, first `_id` and `_count` column added when table creation.

```java
@TagTable
public class DB_TABLE_0 implements BaseColumns {
    @TagTableName
    public static final String NAME = "tb_test_0";
}
```

### Make the column field
Column infomation field make in the table information class. And add the `@TagColumn` annotation on column information field. `@TagColumn` can add properties. The properties is next list.

- **type**
This property is this column's data type. This can set in TEXT, INTEGER, BLOB, REAL and NUMERIC. Default value is TEXT.
- **isPrimaryKey**
`PRRMARY KEY` added in the column if this property set `true`.
Default value is `false`.
- **isAutoIncrement**
`AUTOINCREMENT` added in the column if this property set `true`.
Default value is `false`.
- **isUnique**
`UNIQUE` added in the column if this property set `true`.
Default value is `false`.
- **hasNotNull**
`NOT NULL` added in the column if this property set `true`.
Default value is `false`.
- **defaultValue**
This property value is column's default value.

```java
// The TEXT type column. Nothing property.
@TagColumn
public static final String COLUMN_0 = "column_0";

// This column is the INTEGER type and the primary key.
@TagColumn(type = TagDBManager.COLUMN_TYPE.INTEGER, isPrimaryKey = true)
public static final String COLUMN_1 = "column_1";

// This column is the INTEGER type. And this column value increment auto.
@TagColumn(type = TagDBManager.COLUMN_TYPE.INTEGER, isAutoIncrement = true)
public static final String COLUMN_2 = "column_2";

// This column is the TEXT type. And this column value is unique. And this column can't set null value.
@TagColumn(isUnique = true, hasNotNull = true)
public static final String COLUMN_3 = "column_3";

// This column is the TEXT type. And this column default value is 'none' string.
@TagColumn(defaultValue = "none")
public static final String COLUMN_4 = "column_4";

// This column is the INTEGER type. And this column default value is '1' integer.
@TagColumn(type = TagDBManager.COLUMN_TYPE.INTEGER, defaultValue = "1")
public static final String COLUMN_5 = "column_5";
```

### Database information class sample source
Next is database information class sample source code.
```java
public class TestDBInfo extends TagDBInfo {
    public TestDBInfo(String DBFileName, int DBVersion) {
        super(DBFileName, DBVersion);
    }

    @TagTable
    public class DB_TABLE_0 {
        @TagTableName
        public static final String NAME = "tb_test_0";

        @TagColumn(type = TagDBManager.COLUMN_TYPE.TEXT, hasNotNull = true)
        public static final String COLUMN_0 = "column_0";
        @TagColumn(type = TagDBManager.COLUMN_TYPE.INTEGER, defaultValue = "1")
        public static final String COLUMN_1 = "column_1";
        @TagColumn
        public static final String COLUMN_2 = "column_2";
    }
}
```
- - -

### Make the database manager class
The database manager class must make extended the `TagDBManager` class. And must call `initialize(Context)` method for use.
Next is the database manager class's sample code source.

```java
class FIDDBManager extends TagDBManager {
    FIDDBManager() {
    }

	/**
    * insert test sample method
    */
    public void insertTest(TagDBInfo dbInfo, String tableName, ContentValues values) {
        try {
        	// Open database by TagDBInfo class.
            SQLiteDatabase sqLiteDatabase = open(dbInfo);
            // Data insert.
            sqLiteDatabase.insert(tableName, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## LICENSE
    Copyright 2017 FateInDestiny(Ki-man, Kim)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
