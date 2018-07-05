package ru.aleksandrtrushchinskii.surfproject.model.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ru.aleksandrtrushchinskii.surfproject.common.tools.Converter
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo


@Database(entities = [Todo::class], version  = 1)
@TypeConverters(Converter::class)
abstract class AppCache : RoomDatabase() {

    abstract fun todoDao(): TodoDao

}