package com.example.demo;

import okhttp3.*;

import java.io.IOException;

/**
 * @author: lyz
 * @date: 2021/9/15 16:33
 */
public class TCPDemo {

    private static final String REST_USERS = "http://aiorsoft.cn:8080/rest/users/?api-key=laosha";
    private static final String REST_USERS_DOMAIN = "http://aiorsoft.cn:8080/rest/users/aiorsoft.cn?api-key=laosha";
    private static final String REST_ADHOC = "http://aiorsoft.cn:8080/rest/adhoc/aiorsoft.cn?api-key=laosha";
    private static final String REST_AVATAR = "http://aiorsoft.cn:8080/rest/avatar/%s/aiorsoft.cn?api-key=laosha";
    private static final String REST_STATS = "http://aiorsoft.cn:8080/rest/stats/?api-key=laosha";
    private static final String REST_USER = "http://aiorsoft.cn:8080/rest/user/?api-key=laosha";
    private static final String REST_USER_JID = "http://aiorsoft.cn:8080/rest/user/%s?api-key=laosha";
    private static final String REST_USER_TOKEN = "http://aiorsoft.cn:8080/rest/user/  /sess-man?api-key=laosha";

    public static void main(String[] args) throws Exception {
        String response = get(REST_USERS_DOMAIN);
        System.err.println(response);

    }

    public static String get(String address) throws IOException {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new BasicAuthInterceptor("admin@aiorsoft.cn","laosha123"))
                .build();
        Request request = new Request.Builder()
                .get()
                .header("Content-Type", "application/json")
                .url(address)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static class BasicAuthInterceptor implements Interceptor {

        private String credentials;


        public BasicAuthInterceptor(String user, String password) {
            this.credentials = Credentials.basic(user, password);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request authenticatedRequest = request.newBuilder()
                    .header("Authorization", credentials).build();
            return chain.proceed(authenticatedRequest);
        }
    }

}
