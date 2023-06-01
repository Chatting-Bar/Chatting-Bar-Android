package com.osh.chatting_bar_android;

import com.osh.chatting_bar_android.data_model.*;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("/auth/sign-up")
    Call<BaseResponse> sign_up(@Body SignUpRequest signUpRequest);

    @POST("/auth/sign-in")
    Call<SignInResponse> sign_in(@Body SignInRequest signInRequest);

    @POST("/auth/sign-out")
    Call<BaseResponse> sign_out(@Body stringRequest refreshToken);

    @POST("/api/chatroom")
    Call<BaseResponse> createRoom(@Body ChatRoomRequest chatRoomRequest);

    @POST("/auth/refresh")
    Call<SignInResponse> refresh(@Body stringRequest refreshToken);

    @GET("/api/users")
    Call<UserResponse> getUserInfo();
}

//왜인지 @Field 안돌아가서 다 @Body로 하기위함....string은 사소해서 여기에 클래스 선언함
class stringRequest {
    private String refreshToken;

    public stringRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
