package com.mountech.screens

import kotlinx.serialization.Serializable
/**
 * Правильнее было бы если бы здесь была абстракция, а реализация в каждом модуле
 * Неудобный подход для масштабируемости
 * **/

sealed class Screen {

    @Serializable
    data object Movie

    @Serializable
    data object Collections

    @Serializable
    data object Favorites

    @Serializable
    data object Onboarding
}