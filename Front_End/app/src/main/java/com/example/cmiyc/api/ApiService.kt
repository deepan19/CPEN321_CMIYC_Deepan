package com.example.cmiyc.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import com.example.cmiyc.api.dto.*

interface ApiService: UserApiService, FriendApiService{
    @PUT("/fcm/{userID}")
    suspend fun setFCMToken(
        @Path("userID") userId: String,
        @Body fcmToken: FCMTokenRequestDTO,
    ): Response<Unit>
}

interface UserApiService {
    // User Management
    @POST("user")
    suspend fun registerUser(
        @Body user: UserRegistrationRequestDTO
    ): Response<UserRegistrationResponseDTO>

    @PUT("location/{userID}")
    suspend fun updateUserLocation(
        @Path("userID") userId: String,
        @Body location: LocationUpdateRequestDTO
    ): Response<Unit>

    @POST("user/ban/{userID}")
    suspend fun banUser(
        @Path("userID") userId: String,
        @Body banRequestAdminID: BanUserRequestDTO
    ): Response<Unit>

    @GET("user/{userID}")
    suspend fun getAllUsers(
        @Path("userID") userId: String
    ): Response<List<UserDTO>>

    // Notifications & Device Management
    @GET("notifications/{userID}")
    suspend fun getLogs(
        @Path("userID") userId: String
    ): Response<List<LogDTO>>

    @POST("send-event/{userID}")
    suspend fun broadcastMessage(
        @Path("userID") userId: String,
        @Body eventName: BroadcastMessageRequestDTO,
    ): Response<Unit>
}

interface FriendApiService {
    // Friends Management
    @GET("friends/{userID}")
    suspend fun getFriends(
        @Path("userID") userID: String,
    ): Response<List<FriendDTO>>

    @PUT("friends/{userID}/deleteFriend/{friendID}")
    suspend fun removeFriend(
        @Path("userID") userId: String,
        @Path("friendID") friendID: String
    ): Response<Unit>

    @GET("friends/{userID}/friendRequests}")
    suspend fun getFriendRequests(
        @Path("userID") userId: String,
    ): Response<List<FriendDTO>>

    @POST("friends/{userID}/sendRequest/{friendEmail}")
    suspend fun sendFriendRequest(
        @Path("userID") userId: String,
        @Path("friendEmail") friendEmail: String
    ): Response<Unit>

    @POST("friends/{userID}/acceptRequest/{friendID}")
    suspend fun acceptFriendRequest(
        @Path("userID") userId: String,
        @Path("friendID") friendID: String
    ): Response<Unit>

    @POST("friends/{userID}/declineRequest/{friendID}")
    suspend fun declineFriendRequest(
        @Path("userID") userId: String,
        @Path("friendID") friendID: String
    ): Response<Unit>
}