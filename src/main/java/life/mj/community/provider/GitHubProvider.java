package life.mj.community.provider;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import life.mj.community.dto.AccessTokenDTO;
import life.mj.community.dto.GitHubUser;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubProvider {

    // 注入GitHub获取access token的URI
    @Value("${github.get.access.token.uri}")
    private String getAccessTokenUri;
    // 获取access token
    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        // 设置请求体类型为JSON
        MediaType mediaType = MediaType.get("application/json");

        // 创建OkHttpClient实例
        OkHttpClient client = new OkHttpClient();

        // 将accessTokenDTO对象转换为JSON字符串，并创建请求体
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        // 创建请求
        Request request = new Request.Builder()
                .url(getAccessTokenUri)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            // 如果响应体不为空
            if (response.body() != null) {
                // 将响应体转换为字符串
                String resp_string = response.body().string();
                // 以"&"分割字符串
                String[] split = resp_string.split("&");
                // 以"="分割第一个字符串
                String[] access_token = split[0].split("=");
                // 返回access token
                return access_token[1];
            }
        } catch (IOException ignored) {}
        return null;
    }

    // 注入GitHub获取用户信息的URI
    @Value("${github.get.user.uri}")
    private String getUserUri;

    // 获取用户信息
    public GitHubUser getUser(String accessToken) {
        // 创建请求
        Request request = new Request.Builder()
                .url(getUserUri)
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("Authorization", "Bearer "+ accessToken)
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
                .build();
        // 创建OkHttpClient实例
        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {

            // 如果响应体不为空
            if (response.body() != null) {
                // 将响应体转换为JSONArray
                JSONArray jsonArray = JSON.parseArray(response.body().string());

                // 获取第一个对象（因为这是一个数组，数组中只有一个元素）
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                // 提取"id"字段
                Long id = Long.valueOf(jsonObject.getInteger("id"));

                // 提取"owner"对象中的"login"字段
                JSONObject ownerObject = jsonObject.getJSONObject("owner");
                String login = ownerObject.getString("login");

                // 创建GitHubUser对象，并设置id和name
                GitHubUser gitHubUser = new GitHubUser();
                gitHubUser.setId(id);
                gitHubUser.setName(login);
                return gitHubUser;
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}