package com.fastshop.net.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FakerUtils {
    String[] ho = {"Nguyễn", "Huỳnh", "Phạm", "Phan", "Trần", "Lê", "Mai", "Trương", "Trịnh", "Phùng", "Lại", "Hà", "Lương", "Lý", "Hà", "Thôi", "Triệu", "Tào", "Đoàn", "Nhâm", "Khương", "Viên", "Tăng", "Đồng", "Diệp", "Tô", "Thái", "Từ", "Đàm", "Quách", "Đào", "Đinh", "Tạ", "Ông", "Cao"};
    String[] lot = {"Thị", "", "Văn", "Minh", "Tấn", "Lộc", "Kim", "Đình", "Ngọc", "Bá", "Bích", "Thu", "Thanh", "Quang", "Tú", "Lan", "Khởi", "Trùng", "Vân", "Tiên", "Bích", "Thanh", "Thiên", "Trọng", "Mỹ"};
    String[] ten =      {"Nhi", "Khang", "Tâm", "Sương", "Thảo", "Linh", "Tài", "Khải", "Hương", "Lan", "Nam", "Bình", "Lam", "Cường", "Phú", "Tân", "Lĩnh", "Trinh", "Ngân", "Thành", "Thanh", "Trọng", "Tuấn", "Sơn", "Đức", "An", "Mỹ", "Vân", "My"};
    String[] nameMaNV = {"nhi", "khang", "tam", "suong", "thao", "linh", "tai", "khai", "huong", "lan", "nam", "binh", "lam", "cuong", "phu", "tan", "minhlinh", "trinh", "ngan", "thanh", "trong", "tuan", "son", "duc", "an", "my", "van"};
    String[] manv = {"NC", "AP", "123", "IT", "HA", "CI", "EXO", "KA", "HM", "542", "875", "GU", "QG", "HV", "XX", "TX", "789", "521", "963"};
    String[] street = {"Tôn Đức Thắng", "Phạm Như Xương", "Đống Đa", "Lý Thái Tông", "Phạm Thị Minh Khai", "Trần Quốc Toản", "Nguyễn Thị Minh Khai", "Đa Bác", "Hòa Mỹ", "Đặng Dung", "Quang Nam", "Huỳnh Thúc Kháng", "Ngã Ba Huế", "Tô Hiệu", "Tôn Đản", "Võ Nguyên Giáp", "Gia Bắc", "Hồ Chí Minh", "Số 7", "Số 5", "Số 3", "Số 10", "Hà Huy Tập", "Võ Thị Sáu"};
    String[] city = {"Đà Nẵng", "TP.HCM", "Cần Thơ", "Nha Trang", "Hà Nội", "Quãng Nam", "Quãng Ngãi", "Bình Thuận", "Quy Nhơn", "Cà Mau", "Ninh Thuận", "Nghệ An", "Huế", "Quãng Trị", "Hà Nam", "Đồng Nai"};
    String[] role = {"STAFF", "USER"};
    int[] nam_nhanvien = {1988, 1989, 1990, 1991, 1992, 1993, 1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003};
    int[] nam_banve = {2015, 2016, 2017, 2018, 2019, 2020, 2021};
    int[] thang = {1,2,3,4,5,6,7,8,9,10,11,12};
    int[] ngay = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
    int[] gioitinh = {0, 1};
    int[] vaitro = {0,1};
    int[] digit = {0,1,2,3,4,5,6,7,8,9};
    String[] headerphone = {"031", "032", "033", "035", "037", "039", "071", "072", "073", "075", "077", "090", "096", "095", "091", "099", "062", "063", "064", "067"};
    String[] detailemail = {"@yahoo.com", "@gmail.com", "@fpt.edu.vn", "@io.com", "@outlook.com", "@zoho.com", "@edu.com.vn"};
    final String user = "sa";
    final String password = "0907718993";
    final String url = "jdbc:sqlserver://localhost:1433;databaseName=fastshop";
    
    // -----------------------------------------------------------------------------
    static int number = 500;

    public String getPhoneRandom(String headphone) {
        for (int j = 0; j < 7; j++) {
            headphone += "" + digit[  (int) Math.floor(Math.random()*digit.length + 0)  ];
        }
        return headphone;
    }


    public void create_employee() {
        for (int i = 0; i < number; i++) {
            /** random for full name  */
            int randomHo = (int) Math.floor(Math.random()*ho.length + 0);
            int randomLot = (int) Math.floor(Math.random()*lot.length + 0);
            int randomTen = (int) Math.floor(Math.random()*ten.length + 0);


            /** random for email */
            int randomNameMaNV = (int) Math.floor(Math.random()*nameMaNV.length + 0);
            int randomheaderphone = (int) Math.floor(Math.random() * headerphone.length + 0);
            int randomMaNV = (int) Math.floor(Math.random() * manv.length + 0);
            int randomDetailMail = (int) Math.floor(Math.random() * detailemail.length + 0);


            /** random for role */
            int randomRole = (int) Math.floor(Math.random() * role.length + 0);


            // --------------------------------------------------------------------------------------------------------------------
            String id = nameMaNV[randomNameMaNV] + manv[randomMaNV] + headerphone[randomheaderphone];
            String email = nameMaNV[randomNameMaNV] + manv[randomMaNV] + headerphone[randomheaderphone] + detailemail[randomDetailMail];
            String hoten = ho[randomHo] + " " + lot[randomLot] + " " + ten[randomTen];
            String pw = "123456789";
            String avartar = "abc.jpg";
            String phone = getPhoneRandom(headerphone[randomheaderphone]);
            String vaitro = role[randomRole];
            

            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = conn.prepareStatement("INSERT INTO accounts VALUES (?,?,?,?,?,?)");
                ps.setString(1, id);
                ps.setString(2, email);
                ps.setString(3, hoten);
                ps.setString(4, pw);
                ps.setString(5, avartar);
                ps.setString(6, phone);
                ps.execute();

                PreparedStatement ps1 = conn.prepareStatement("INSERT INTO authorities VALUES (?,?)");
                ps1.setString(1, id);
                ps1.setString(2, vaitro);
                ps1.execute();


                ps.close();
                ps1.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        }
    }


    // public static void main(String[] args) {
    //     new FakerUtils().create_employee();
    // }
}
