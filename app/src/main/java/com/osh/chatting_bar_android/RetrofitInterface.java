package com.osh.chatting_bar_android;

import com.osh.chatting_bar_android.data_model.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @POST("/auth/sign-up")
    Call<BaseResponse> sign_up(@Body SignUpRequest signUpRequest);

    @POST("/auth/sign-in")
    Call<SignInResponse> sign_in(@Body SignInRequest signInRequest);

//    @FormUrlEncoded
//    @POST("/auth/sign-out")
//    Call<BaseResponse> sign_out(@Field("refreshToken") string token);
    @POST("/auth/sign-out")
    Call<BaseResponse> sign_out(@Body stringRequest refreshToken);

    @POST("/api/chatroom")
    Call<BaseResponse> createRoom(@Body ChatRoomRequest chatRoomRequest);

    //    @FormUrlEncoded
//    @POST("/auth/refresh")
//    Call<BaseResponse> refresh(@Field("refreshToken") string token);
    @POST("/auth/refresh")
    Call<SignInResponse> refresh(@Body stringRequest refreshToken);

    @GET("/api/users")
    Call<UserResponse> getUserInfo();

    @FormUrlEncoded
    @POST("/api/chatroom/enter")
    Call<BaseResponse> roomEnter(@Field("id") long id);

    @FormUrlEncoded
    @DELETE("/api/chatroom/{roomId}")
    Call<BaseResponse> roomExit(@Path("roomId") int roomId);

    @FormUrlEncoded
    @DELETE("/api/chatroom?roomId=&userId=")
    Call<BaseResponse> roomKick(@Query("roomId") int roomId, @Query("userId") int userId);

    @FormUrlEncoded
    @PATCH("/api/chatroom?roomId=&userId=")
    Call<BaseResponse> roomIce(@Query("roomId") int roomId, @Query("userId") int userId);

    @FormUrlEncoded
    @GET("/api/chatroom")
    Call<ChatRoomResponse> getAllRoom();

    @FormUrlEncoded
    @GET("/api/chatroom/latest")
    Call<ChatRoomResponse> getLatestRoom();

    @FormUrlEncoded
    @GET("/api/chatroom/recommend")
    Call<ChatRoomResponse> getRecommendRoom();

    @FormUrlEncoded
    @GET("/api/chatroom/{search}")
    Call<SearchResponse> getSearchRoom(@Path("search") int search);
}

//왜인지 @Field 안돌아가서 다 @Body로 하기위함....string은 사소해서 여기에 클래스 선언함
class stringRequest {
    private String refreshToken;

    public stringRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
