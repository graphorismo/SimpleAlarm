package ru.graphorismo.simplealarm.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.graphorismo.simplealarm.data.AlarmDAO
import ru.graphorismo.simplealarm.data.AlarmDataBase
import ru.graphorismo.simplealarm.data.AlarmRepositoryImpl
import ru.graphorismo.simplealarm.data.IAlarmRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesAlarmDataBase(@ApplicationContext context: Context): AlarmDataBase{
        val database: AlarmDataBase = Room
            .databaseBuilder(
                context.applicationContext,
                AlarmDataBase::class.java,
                "AlarmDataBase"
            )
            .build()
        return database
    }

    @Provides
    fun providesAlarmDAO(alarmDataBase: AlarmDataBase) : AlarmDAO{
        return alarmDataBase.alarmDAO()
    }

    @Provides
    @Singleton
    fun providesIAlarmRepository(alarmRepositoryImpl: AlarmRepositoryImpl) : IAlarmRepository{
        return alarmRepositoryImpl
    }

}