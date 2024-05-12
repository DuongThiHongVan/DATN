package com.fastshop.net.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class _CookieService {
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    /**
    * Đọc cookie từ request
    * @param name tên cookie cần đọc
    * @return đối tượng cookie đọc được hoặc null nếu không tồn tại
    */
    public Cookie get(String name) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    cookie = c;
                    break;
                }
            }
        }
        return cookie;
    }

    /**
    * Đọc giá trị của cookie từ request
    * @param name tên cookie cần đọc
    * @return chuỗi giá trị đọc được hoặc rỗng nếu không tồn tại
    */
    public String getValue(String name) {
        Cookie[] cookies = request.getCookies();
        Cookie c = null;
        if (cookies == null) {
            return null;
        }
        else {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    c = cookie;
                }
            }
        }
        return (c == null) ? null : c.getValue();
    }


    /**
    * Tạo và gửi cookie về client
    * @param name tên cookie
    * @param value giá trị cookie
    * @param hours thời hạn (giờ)
    * @return đối tượng cookie đã tạo
    */
    public Cookie add(String name, String value, int hours) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(hours*60*60);
        cookie.setPath("/");
        response.addCookie(cookie);
        return cookie;
    }


    /**
    * Xóa cookie khỏi client
    * @param name tên cookie cần xóa
    */
    public void remove(String name) {
        Cookie cookie = get(name);
        if (cookie != null) {
            cookie.setPath("/");
            cookie.setMaxAge(0);
            cookie.setValue(null);
            response.addCookie(cookie);
        }
    }
}
