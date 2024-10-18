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

    @Value("${github.get.access.token.uri}")
    private String getAccessTokenUri;
    public String getAccessToken(AccessTokenDTO accessTokenDTO){

        MediaType mediaType = MediaType.get("application/json");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url(getAccessTokenUri)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                String resp_string = response.body().string();
                String[] split = resp_string.split("&");
                String[] access_token = split[0].split("=");
                return access_token[1];
            }
        } catch (IOException ignored) {}
        return null;
    }

    @Value("${github.get.user.uri}")
    private String getUserUri;

    public GitHubUser getUser(String accessToken) {
        Request request = new Request.Builder()
                .url(getUserUri)
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("Authorization", "Bearer "+ accessToken)
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
                .build();
        OkHttpClient client = new OkHttpClient();
        try (Response response = client.newCall(request).execute()) {

            if (response.body() != null) {
                JSONArray jsonArray = JSON.parseArray(response.body().string());

                // 获取第一个对象（因为这是一个数组，数组中只有一个元素）
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                // 提取"id"字段
                Long id = Long.valueOf(jsonObject.getInteger("id"));

                // 提取"owner"对象中的"login"字段
                JSONObject ownerObject = jsonObject.getJSONObject("owner");
                String login = ownerObject.getString("login");

                GitHubUser gitHubUser = new GitHubUser();
                gitHubUser.setId(id);
                gitHubUser.setName(login);
                return gitHubUser;
            }

        } catch (IOException ignored) {}
        return null;
    }
}