package com.erostech.kiki.modules

import android.app.Application
import android.content.Context
import com.erostech.kiki.KikiApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by erosgarciaponte on 19.06.17.
 */
@Module
class AppModule(val app: KikiApplication) {
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication(): Application = app
}