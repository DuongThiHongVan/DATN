var host_ = "http://localhost:8080/rest";
var account_ = document.getElementById("username_");  // _account.html

var id = account_.innerHTML;
var app = angular.module("bill-app", []);
app.controller("bill-ctrl", ($scope,) => {
    $scope.payment = JSON.parse(localStorage.getItem("payment"));
    $scope.cart = {
        items: [],

        // Tính thành tiền của 1 sản phẩm
        amt_of(item) {
            return item.price * item.qty;
        },

        // Tính tổng số lượng các mặt hàng trong giỏ
        get count() {
            return this.items
                       .map(item => item.qty)
                       .reduce((total, qty) => total += qty, 0);
        },

        // Tổng thành tiền các mặt hàng trong giỏ
        get amount() {
            return this.items
                       .map(item => item.qty*item.price)
                       .reduce((total, qty) => total += qty,0);
        },

        // Lưu giỏ hàng vào local storage
        saveToLocalStorage() {
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem(id, json);
        },

        // Đọc giỏ hàng từ local storage
        loadFromLocalStorage() {
            this.items= JSON.parse(localStorage.getItem(id));
        }
    }
    $scope.cart.loadFromLocalStorage();
});
