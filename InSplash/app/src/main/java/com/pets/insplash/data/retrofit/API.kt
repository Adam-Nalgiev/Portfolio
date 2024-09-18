package com.pets.insplash.data.retrofit

import com.pets.insplash.entity.constants.Constants
import com.pets.insplash.entity.dto.CollectionDTO
import com.pets.insplash.entity.dto.CurrentUserDTO
import com.pets.insplash.entity.dto.LikeUnlikeDTO
import com.pets.insplash.entity.dto.LikedPhotosDTO
import com.pets.insplash.entity.dto.OnePhotoDTO
import com.pets.insplash.entity.dto.PhotosDTO
import com.pets.insplash.entity.dto.SearchResultDTO
import com.pets.insplash.entity.dto.UrlDTO
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @GET("/users/{username}/likes")
    suspend fun getUsersLikedPhotos(
        @Header("Authorization") request: String?,
        @Path("username") username: String
    ): List<LikedPhotosDTO>

    @POST("/photos/{id}/like")
    suspend fun likePhoto(
        @Header("Authorization") request: String?,
        @Path("id") id: String
    ) : LikeUnlikeDTO

    @DELETE("/photos/{id}/like")
    suspend fun unlikePhoto(
        @Header("Authorization") request: String?,
        @Path("id") id: String
    ) : LikeUnlikeDTO

    @GET("/photos/{id}/download")
    suspend fun downloadPhoto(
        @Header("Authorization") request: String?,
        @Path("id") id: String
    ): UrlDTO

    @GET("/photos/{id}")
    suspend fun getOnePhoto(
        @Header("Authorization") request: String?,
        @Path("id") id: String
    ): OnePhotoDTO

    @GET("/photos")
    suspend fun getHomePhotos(
        @Header("Authorization") request: String?,
        @Query("page") page: Int,
        @Query("order_by") orderBy: String = "latest"
    ): List<PhotosDTO>

    @GET("/collections/{id}/photos")
    suspend fun getCollectionPhotos(
        @Header("Authorization") request: String?,
        @Path("id") id: String
    ): List<PhotosDTO>

    @GET("/collections")
    suspend fun getCollections(
        @Header("Authorization") request: String?
    ): List<CollectionDTO>

    @GET("/collections/{id}")
    suspend fun getOneCollection(
        @Header("Authorization") request: String?,
        @Path("id") id: String
    ): CollectionDTO

    @GET("/me")
    suspend fun getProfile(
        @Header("Authorization") request: String?
    ): CurrentUserDTO

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Header("Authorization") request: String?,
        @Header("client_id") clientID :String = Constants.CLIENT_ID, // проверить возможность удаения этого поля
        @Query("page") page: Int,
        @Query("query") query: String,
    ): SearchResultDTO // проверить одинаковость модели с обычным фотоДТО

}