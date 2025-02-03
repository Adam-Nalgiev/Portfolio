package com.mountech.onboarding.data.movies

import com.mountech.onboarding.data.models.MovieDto
/**
 * Я хотел сделать сделать список загружаемым из сети (такой функционал есть в API)
 * Но пока буду использовать предзагруженный список
 **/

internal object MoviesList {

    val movies = listOf(
        MovieDto(
            url = "https://image.openmoviedb.com/kinopoisk-images/10853012/f05240b1-7215-4af2-92a5-eb0f2d831d9b/orig",
            description = "Project Silence is a 2023 South Korean disaster thriller film co-written and directed by Kim Tae-gon."
        ),
        MovieDto(
            url = "https://image.openmoviedb.com/kinopoisk-images/10671298/8aef6c50-2a32-4e96-a9de-2efef24b9048/orig",
            description = "En Nueva Delhi, tres hermanos mafiosos inician una guerra entre ellos por el control del negocio familiar." +
                    " Mientras tanto, un traficante de armas conspira contra los hermanos y un policía encubierto lucha por acabar con el legado de la familia."
        ),
        MovieDto(
            url = "https://image.openmoviedb.com/kinopoisk-images/10671298/91259163-0cdd-4b3a-b532-39374046b118/orig",
            description = "Prince Philip is not your run of the mill stiff upper lip royalty by any means, walking two steps behind Her Royal Highness Queen Elizabeth" +
                    " ever since she became Queen in 1952, Philips's loyalty and service are undoubted."
        ),
        MovieDto(
            url = "https://image.openmoviedb.com/kinopoisk-images/10812607/a139fe25-e90d-473e-9ba1-d14fa57867d5/orig",
            description = "The G is a 2023 Canadian dark thriller film written, directed, and produced by Karl R. Hearne, and starring Dale Dickey as a grandmother seeking revenge."
        )
    )
}