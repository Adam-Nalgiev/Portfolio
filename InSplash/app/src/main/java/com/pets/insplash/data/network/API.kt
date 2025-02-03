package com.pets.insplash.data.network

import com.pets.insplash.data.network.dto.CollectionDTO
import com.pets.insplash.data.network.dto.CurrentUserDTO
import com.pets.insplash.data.network.dto.LikeUnlikeDTO
import com.pets.insplash.data.network.dto.LikedPhotosDTO
import com.pets.insplash.data.network.dto.OnePhotoDTO
import com.pets.insplash.data.network.dto.PhotosDTO
import com.pets.insplash.data.network.dto.SearchResultDTO
import com.pets.insplash.data.network.dto.UrlDTO
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("/users/{username}/likes")
    suspend fun getLikedPhotos(
        @Header("Authorization") request: String? = "Client-ID $CLIENT_ID",
        @Path("username") username: String,
        @Query("page") page: Int
    ): List<LikedPhotosDTO>?

    @POST("/photos/{id}/like")
    suspend fun likePhoto(
        @Header("Authorization") request: String,
        @Path("id") id: String
    ) : LikeUnlikeDTO?

    @DELETE("/photos/{id}/like")
    suspend fun unlikePhoto(
        @Header("Authorization") request: String,
        @Path("id") id: String
    ) : LikeUnlikeDTO?

    @GET("/photos/{id}/download")
    suspend fun downloadPhoto(
        @Header("Authorization") request: String? = "Client-ID $CLIENT_ID",
        @Path("id") id: String
    ): UrlDTO?

    @GET("/photos/{id}")
    suspend fun getOnePhoto(
        @Header("Authorization") request: String? = "Client-ID $CLIENT_ID",
        @Path("id") id: String
    ): OnePhotoDTO?

    @GET("/photos")
    suspend fun getHomePhotos(
        @Header("Authorization") request: String? = "Client-ID $CLIENT_ID",
        @Query("page") page: Int,
        @Query("order_by") orderBy: String = "latest"
    ): List<PhotosDTO>?

    @GET("/collections/{id}/photos")
    suspend fun getCollectionPhotos(
        @Header("Authorization") request: String? = "Client-ID $CLIENT_ID",
        @Path("id") id: String,
        @Query("page") page: Int
    ): List<PhotosDTO>?

    @GET("/collections")
    suspend fun getCollections(
        @Header("Authorization") request: String? = "Client-ID $CLIENT_ID",
        @Query("page") page: Int
    ): List<CollectionDTO>?

    @GET("/me")
    suspend fun getProfile(
        @Header("Authorization") request: String
    ): CurrentUserDTO?

    @GET("/photos/random")
    suspend fun getRandomPhoto(
        @Header("Authorization") request: String? = "Client-ID $CLIENT_ID"
    ): OnePhotoDTO?

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Header("Authorization") request: String? = "Client-ID $CLIENT_ID",
        @Query("page") page: Int,
        @Query("query") query: String,
    ): SearchResultDTO?

    companion object {
        const val CLIENT_ID = "1uqN7imwX1vT0e-R2ALszCV2s0GA2gZT5vz2232tRFw"
    }
}