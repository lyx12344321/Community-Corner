package life.mj.community.filter;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import life.mj.community.mapper.UserMapper;
import life.mj.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import java.io.IOException;


@Component //将过滤器标记为Spring 组件，框架会自动发现并注册它
@Order  //来改变过滤器在过滤器链中的位置
public class LoginFilter implements Filter {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 过滤器初始化逻辑
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        // 遍历请求中的所有Cookie
        for (Cookie cookie : req.getCookies()) {
            // 如果Cookie的名称为token
            if (cookie.getName().equals("token")) {
                // 获取Cookie的值
                String token = cookie.getValue();
                // 根据token查询用户
                User user = userMapper.selectUserByToken(token);
                // 如果用户存在
                if (user != null) {
                    // 将用户存入Session中
                    req.getSession().setAttribute("user", user);
                }
                break;
            }
        }
        // 继续执行下一个过滤器或请求处理
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 过滤器销毁逻辑
    }
}