package com.fastshop.net.controller;

import java.nio.file.Paths;

public class test {
    public static void main(String[] args) {
        String uploadDir = Paths.get("src", "main", "resources", "static", "dist", "img", "products").toAbsolutePath().toString().replace("\\", "/");
        System.out.println(uploadDir);
    }
}
