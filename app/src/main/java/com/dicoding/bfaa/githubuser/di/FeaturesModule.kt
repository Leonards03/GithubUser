package com.dicoding.bfaa.githubuser.di

import com.dicoding.bfaa.githubuser.features.ReminderReceiver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object FeaturesModule {
    @Provides
    fun provideReminderReceiver(): ReminderReceiver = ReminderReceiver()
}