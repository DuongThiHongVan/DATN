package com.fastshop.net.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Changed {
    @NotBlank(message = "(*) Vui lòng nhập mật khẩu hiện tại")
    private String current;

    @NotBlank(message = "(*) Mật khẩu mới không được để trống")
    private String password1;

    @NotBlank(message = "(*) Mật khẩu xác nhận không được để trống")
    private String password2;
}
