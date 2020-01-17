package com.fateindestiny.tagdbfactory

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


/**
 * TagDBFactory
 * Created by Ki-man(Ethan), Kim on 2020-01-16.
 */
abstract class TagDBFactory {
    private var context: Context? = null
    private val helperCache: HashMap<TagDBInfo, DBOpenHelper> = hashMapOf()
    /**
     * This is initializing method. Needs first execute before use.
     *
     * @param context
     */
    protected fun initialize(context: Context) {
        this.context = context
    }

    /**
     * Database open according to parameter [TagDBInfo].
     *
     * @param dbInfo This parameter is target [TagDBInfo] for open database.
     * @return Return writable database object by parameter [TagDBInfo].
     * @throws NullPointerException Throws if not call [TagDBFactory.initialize].
     */
    fun open(dbInfo: TagDBInfo): SQLiteDatabase {
        if (this.context == null) throw NullPointerException("Context is null, Not initialized.")
        return getDBHelper(dbInfo).writableDatabase
    }

    /**
     * This is working find [DBOpenHelper] according to [TagDBInfo].
     *
     * @param dbInfo This parameter is condition for find [DBOpenHelper].
     * @return Return find [DBOpenHelper].
     */
    private fun getDBHelper(dbInfo: TagDBInfo): DBOpenHelper {
        val context = this.context
                ?: throw NullPointerException("Context is null, Not initialized.")

        return helperCache[dbInfo]
                ?: DBOpenHelper(context, dbInfo, null).apply { helperCache[dbInfo] = this }
    }

    private class DBOpenHelper(context: Context, val dbInfo: TagDBInfo, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, dbInfo.dbFileName, factory, dbInfo.dbVersion) {
        override fun onCreate(db: SQLiteDatabase?) {
            if (db == null) return

            generateCreateQuerys(dbInfo).forEach {
                db.execSQL(it)
            }
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            dbInfo.workQueryFunction?.onUpgrade(db, oldVersion, newVersion)
        }

        override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            dbInfo.workQueryFunction?.onDowngrade(db, oldVersion, newVersion)
        }

        fun generateCreateQuerys(dbInfo: TagDBInfo): ArrayList<String> {

            return arrayListOf()
        }
    } // end of class DBOpenHelper
} // end of class TagDBFactory