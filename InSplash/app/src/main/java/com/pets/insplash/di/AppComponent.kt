package com.pets.insplash.di

import dagger.Component

@Component(modules = [
    DataModule::class,
    DomainModule::class,
    PresentationModule::class
])
interface AppComponent {

}