package com.pets.insplash.di

import com.pets.insplash.data.Repository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideRepository(): Repository {
        return Repository()
    }

}