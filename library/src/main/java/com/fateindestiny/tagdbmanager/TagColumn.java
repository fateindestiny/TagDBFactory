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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can tag when column define.
 *
 * @author FateInDestiny on 2017-05-26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TagColumn {
    TagDBManager.COLUMN_TYPE type() default TagDBManager.COLUMN_TYPE.TEXT;
    boolean isPrimaryKey() default false;
    boolean isAutoIncrement() default false;
    boolean isUnique() default false;
    boolean hasNotNull() default false;
    String defaultValue() default "";
}// end of interface TagColumn
