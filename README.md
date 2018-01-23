# TagDBFactory for Android

[![license](https://img.shields.io/badge/license-Apache2.0-blue.svg?style=flat)](LICENSE.md)
![library](https://img.shields.io/badge/library-Android-blue.svg?style=flat)


`TagDBFactory` 는 안드로이드에서 SQLite의 테이블을 생성하는데 도움을 주는 라이브러리입니다. 기능은 다음과 같습니다.

## 기능
----
* DB 정보 클래스로 테이블을 자동으로 생성
* 테이블 정보를 Object 클래스로 모델링
* DB 정보 클래스를 통한 DB 버전 Helper 이벤트 수신


## 다운로드
* **[TagDBFactory-0.1.1.jar](https://github.com/fateindestiny/TagDBFactory/raw/master/download/TagDBFactory-0.1.1.jar)**

## 사용법
`TagDBFactory`를 사용하기 위해 필요한 것들이 있습니다.

* `TagDBFactory`를 상속 받은 클래스
* 데이터 베이스 정보 클래스


## `TagDBFactory`를 상속 받은 클래스
----
`TagDBFactory`는 다음과 같은 용도의 함수와 클래스가 있습니다.

* 데이터 베이스 Open Helper 클래스를 관리하는 함수
* 데이터 베이스 Open, Close 기능
* `DBOpenHelper` 클래스

따라서, 데이터 베이스의 관리를 위한 용도로 클래스를 사용할 때 `TagDBFacotry`를 상속 받아 사용하면 됩니다.

**단, 사용전 `TagDBFactory` 클래스의 initialize(Context) 함수를 호출해주어야 합니다.**
```java
class LocalSQLiteDBHelper extends TagDBFacotry {
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

    // 그외 필요한 기능을 추가하여 사용
    ...
}
```


## 데이터 베이스 정보 클래스
----
데이터 베이스 정보의 설정방법은 다음과 같습니다.

1. `TagDBInfo`를 상속 받은 클래스 생성
2. 필요한 테이블 만큼 테이블 정보 클래스를 내부에 생성
3. 테이블 정보 클래스에 테이블 명과 칼럼 정보 변수를 생성

```java
/**
* 데이터 베이스 정보 클래스의 예제
*/
public class TestDBInfo extends TagDBInfo {
    public TestDBInfo(String DBFileName, int DBVersion) {
        super(DBFileName, DBVersion);
    }

    @TagTable
    public class DB_TABLE_0 {
        @TagTableName
        public static final String NAME = "tb_test_0";

        @TagColumn(type = TagDBFactory.COLUMN_TYPE.TEXT, hasNotNull = true)
        public static final String COLUMN_0 = "column_0";
        @TagColumn(type = TagDBFactory.COLUMN_TYPE.INTEGER, defaultValue = "1")
        public static final String COLUMN_1 = "column_1";
        @TagColumn
        public static final String COLUMN_2 = "column_2";
    }
}
```
## 테이블 정보 클래스
----
`TagDBInfo`를 상속 받은 클래스에 내부 클래스를 만들어 `@TagTable` 어노테이션을 추가하면 테이블 생성시 해당 클래스를 테이블 정보를 가지고 있는 클래스로 인식합니다.
그리고 테이블 이름은 해당 클래스 내부에 이름을 설정할 문자열 변수에 `@TagTableName` 어노테이션을 추가하면 됩니다.

**주의) 테이블 변수는 반드시 static 변수로 설정해야 합니다.**
```java
/**
* 테이블 정보 예제
*/
@TagTable
public class DB_TABLE_0 {
    @TagTableName
    public static final String NAME = "tb_test_0";
}
```

## 칼럼 정보 변수
테이블 정보 클래스에 칼럼명을 설정할 문자열 변수를 만들어 `@TagColumn`을 추가하면 됩니다.

**주의) 칼럼 정보 변수는 반드시 static 변수로 설정해야 합니다.**

`@TagColumn`은 다음과 같은 속성을 설정할 수 있습니다.
* **type**
  * 칼럼의 데이터 타입
  * TEXT, INTEGER, BLOB, REAL, NUMERIC을 설정이 가능
  * 기본값은 TEXT
* **isPrimaryKey**
  * 해당 속성이 `true`라면, `PRRMARY KEY` 속성을 칼럼에 추가
  * 기본값은 `false`
* **isAutoIncrement**
  * 해당 속성이 `true`라면, `AUTOINCREMENT` 속성을 칼럼에 추가
  * 기본값은 `false`
* **isUnique**
  * 해당 속성이 `true`라면, `UNIQUE` 속성을 칼럼에 추가
  * 기본값은 `false`
* **hasNotNull**
  * 해당 속성이 `true`라면, `NOT NULL` 속성을 칼럼에 추가
  * 기본값은 `false`.
* **defaultValue**
  * 칼럼의 기본 값을 설정
  * 정수형도 문자열로 지정

```java
// 칼럼 속성 설정 예제

// column_0이라는 이름의 TEXT형 칼럼. 부가 속성은 없음.
@TagColumn
public static final String COLUMN_0 = "column_0";

// column_1 라는 이름의 INTEGER형 칼럼. PRIMARY KEY 속성이 있음.
@TagColumn(type = TagDBFactory.COLUMN_TYPE.INTEGER, isPrimaryKey = true)
public static final String COLUMN_1 = "column_1";

// column_2 라는 이름의 INTEGER형 칼럼. 자동 값이 증가하는 AUTO INCREMENT 속성이 있음.
@TagColumn(type = TagDBFactory.COLUMN_TYPE.INTEGER, isAutoIncrement = true)
public static final String COLUMN_2 = "column_2";

// column_3 라는 이름의 TEXT형 칼럼. UNIQUE와 NOT NULL 속성이 있음.
@TagColumn(isUnique = true, hasNotNull = true)
public static final String COLUMN_3 = "column_3";

// column_4 라는 이름의 TEXT형 칼럼. 기본값 'none' 데이터가 저장됨.
@TagColumn(defaultValue = "none")
public static final String COLUMN_4 = "column_4";

// column_5 라는 이름의 INTEGER형 칼럼. 기본값 1 이 저장됨.
@TagColumn(type = TagDBFactory.COLUMN_TYPE.INTEGER, defaultValue = "1")
public static final String COLUMN_5 = "column_5";
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
