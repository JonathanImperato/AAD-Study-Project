package com.macoev.aadstudyproject.data.dao

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import java.lang.reflect.ParameterizedType

@Suppress("MemberVisibilityCanBePrivate")
@RequiresApi(Build.VERSION_CODES.P)
@Dao
abstract class BaseDao<E>  {

    @RawQuery//(observedEntities = [tableClass::class])
    abstract fun all(query: SupportSQLiteQuery): List<E>

    fun all() = all(SimpleSQLiteQuery("SELECT * FROM $tableName}"))

    @Delete
    abstract fun delete(vararg entity: E)

    @Update
    abstract fun update(vararg entity: E)

    @Insert(onConflict = OnConflictStrategy.REPLACE) //AUTO GENERATED IDS WILL CHANGE WHEN ROWS GET REPLACED
    abstract fun insertOrUpdate(vararg entities: E)

    @Insert
    abstract fun insert(vararg entities: E)

    @RawQuery
    abstract fun deleteAll(query: SupportSQLiteQuery)

    fun deleteAll() = deleteAll(SimpleSQLiteQuery("DELETE FROM $tableName"))

    @RawQuery
    abstract fun findBy(query: SupportSQLiteQuery): List<E>

    fun findBy(key: String, value: String) = findBy(SimpleSQLiteQuery("SELECT * FROM $tableName WHERE $key IN ($value)"))
    fun findBy(key: String, value: IntArray) = findBy(SimpleSQLiteQuery("SELECT * FROM $tableName WHERE $key IN ($value)"))
    fun findBy(key: String, value: LongArray) = findBy(SimpleSQLiteQuery("SELECT * FROM $tableName WHERE $key IN ($value)"))
    fun findBy(key: String, value: ShortArray) = findBy(SimpleSQLiteQuery("SELECT * FROM $tableName WHERE $key IN ($value)"))
    fun findBy(key: String, value: DoubleArray) = findBy(SimpleSQLiteQuery("SELECT * FROM $tableName WHERE $key IN ($value)"))
    fun findBy(key: String, vararg value: String) = findBy(SimpleSQLiteQuery("SELECT * FROM $tableName WHERE $key IN ($value)"))

    fun isEmpty() = all().isNullOrEmpty()

    private val tableName : String
        get() = className.apply { replaceRange(0,lastIndexOf(".") + 1, "") }

    private val className : String
        get() = (javaClass.superclass.genericSuperclass as ParameterizedType).actualTypeArguments[0].typeName

    private val tableClass : Class<*> get() = Class.forName(className)

}