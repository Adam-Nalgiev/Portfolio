package com.pets.insplash.entity.entities

interface User {

    val id: String
    val username: String
    val name: String
    val profile_image: ProfileImage? //при использовании принятой нормы наименования переменной происходит ошибка сериализации, возможно, замена converter factory решит эту проблему, но не в этот раз :)

}