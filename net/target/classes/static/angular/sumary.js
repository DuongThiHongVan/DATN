var host_ = "http://localhost:8080/rest";
var number = document.getElementById("number");       // _mode_cart.html
var username = document.getElementById("username");   
var account_ = document.getElementById("username_");  // _account.html

var id = account_.innerHTML;
var app = angular.module("app", []);
number.innerHTML = (localStorage.getItem(id) == undefined) ? 0 : JSON.parse(localStorage.getItem(id)).length;
if (localStorage.getItem("rate") != undefined) {
    localStorage.removeItem("rate");
};

function getDateNow(days) {
    var date = new Date();
    var year = date.getFullYear().toString();
    var month = date.getMonth() + 1;
    var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    var strDate = year + "-" + month + "-" + day;
    if (days == null || days == 0) {
        return strDate;   
    }
    else {
        date.setDate(date.getDate() + days);
        var year1 = date.getFullYear().toString();
        var month1 = date.getMonth() + 1;
        var day1 = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var strDate1 = year1 + "-" + month1 + "-" + day1;
        return strDate1;   
    }
};

function convert_vi_to_en(str) {
    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
    str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
    str = str.replace(/đ/g, "d");
    str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
    str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
    str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
    str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
    str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
    str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
    str = str.replace(/Đ/g, "D");
    str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g, " ");
    str = str.replace(/  +/g, ' ');
    return str;
};

function changeFree() {
    var v = document.getElementById("codeFree").value;
    var url_discount_free = `${host_}/discounts/${v}`;
    $("#success_find").css("opacity", "0");
    $("#error_find").css("opacity", "0");
    $("#find-free").css("display", "block");
    $("#btn-free").attr("disabled", "disabled");

    setTimeout(() => {
        $("#find-free").css("display", "none");
        if (!v) {
            $("#error_find").css("opacity", "1");
            $("#error_find").text("(*) Bạn chưa nhập mã giảm giá.");
            $("#success_find").css("opacity", "0");
        }
        else {
            $("#error_find").css("opacity", "0");
            $("#success_find").css({"opacity": "1", "color": "#495579"});
            $("#success_find").text("(*) Đã nhập mã giảm giá. Tiếp tục kiểm tra voucher");
            $("#btn-free").removeAttr("disabled");
        }
    }, 1000);
    $("#getPathFree").text(url_discount_free);
}

function copyFree(index) {
    let code = document.getElementById("voucher-" + index);
    console.log(code.value);
    code.select();
    code.setSelectionRange(0, 99999);
    navigator.clipboard.writeText(code.value);
    if (document.execCommand("copy")) {
        $("#link-voucher-" + index).css({"color":"green"});
        $("#link-voucher-" + index).text("Copied");
    }
}

app.controller("admin-employee", ($scope, $http) => {
    
});

app.controller("history-ctrl", ($scope, $http) => {
    $scope.histories = [];
    $scope.selectId = [];

    $scope.load_histories = () => {
        var url = `${host_}/history/all`;
        $http.get(url).then((resp) => {
            $scope.histories = resp.data;
        }).catch((err) => {
            console.log("Error load histories", err);
        });
    };

    $scope.add = (id) => {
        var index = $scope.selectId.findIndex(item => item == id);
        if (index == -1) {
            $scope.selectId.push(id);
        }
        else {
            $scope.selectId.splice(index, 1);
        }
    };

    $scope.remove = (id) => {
        var url = `${host_}/history/${id}`;
        $http.delete(url).then((resp) => {
            var index = $scope.histories.findIndex(item => item.id == id);
            $scope.histories.splice(index, 1);
            console.log("Xoa thanh cong " + id, resp);
        });
    };

    $scope.delete = () => {
        if ($scope.selectId.length > 0) {
            $scope.selectId.forEach(id => {
                var url = `${host_}/history/${id}`;
                $http.delete(url).then((resp) => {
                    var index = $scope.histories.findIndex(item => item.id == id);
                    $scope.histories.splice(index, 1);
                    $scope.all = false;
                });
            });
        }
        else {
            alert("Choose some history to delete");
        }
    };

    $scope.choose_all = () => {
        if ($scope.all) {
            $scope.histories.forEach(h => {
                $scope.selectId.push(h.id);
            });
        }
        else {
            $scope.selectId = [];
        }
        console.log($scope.selectId.length);
    };

    $scope.load_histories();
});

app.controller('chart-ctrl', ($scope, $http) => {
	$scope.titles = [];
	$scope.load_title = () => {
		var url = `${host_}/orderdetails/category/report`;
		$http
			.get(url)
			.then((resp) => {
				$scope.titles = resp.data;
				var ctx = document.getElementById('myChart');
                // console.log($scope.titles);
				var myChart = new Chart(ctx, {
					type: 'line',
					data: {
						labels: $scope.titles.map(item => item.group.name),
						datasets: [
							{
								label: 'Phân loại hàng',
								data: $scope.titles.map(item => item.count),
								backgroundColor: [
									'rgba(255, 99, 132, 0.2)',
									'rgba(54, 162, 235, 0.2)',
									'rgba(255, 206, 86, 0.2)',
									'rgba(75, 192, 192, 0.2)',
								],
								borderColor: [
									'rgba(255, 99, 132, 1)',
									'rgba(54, 162, 235, 1)',
									'rgba(255, 206, 86, 1)',
									'rgba(75, 192, 192, 1)',
								],
								borderWidth: 1,
							},
						],
					},
				});
			})
			.catch((err) => {
				console.log('Error load items', err);
			});
	};

	$scope.load_title();
});

app.controller('chart2-ctrl', ($scope, $http) => {
	$scope.titles = [];
    $scope.revenue = {
        item: [],
        get total() {
            return this.item
                        .map(item => item)
                        .reduce((total, r) => total+=r, 0);
        }
    };
    // console.log($scope.revenue);
    
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    for (let index = 0; index < 5; index++) {
        var year = new Date().getFullYear() - index;
        var urlRevenue = `${host_}/orders/year/${year}`;
        $http.get(urlRevenue).then((resp) => {
            $scope.revenue.item[index] = resp.data;
        }).catch((err) => {
        });
    }

    function drawChart() {
        var data = google.visualization.arrayToDataTable([
            ['Contry', 'Mhl'],
            ['Doanh thu năm ' + (new Date().getFullYear() - 4), $scope.revenue.item[4] * 100 / $scope.revenue.total],
            ['Doanh thu năm ' + (new Date().getFullYear() - 3), $scope.revenue.item[3] * 100 / $scope.revenue.total],
            ['Doanh thu năm ' + (new Date().getFullYear() - 2), $scope.revenue.item[2] * 100 / $scope.revenue.total],
            ['Doanh thu năm ' + (new Date().getFullYear() - 1), $scope.revenue.item[1] * 100 / $scope.revenue.total],
            ['Doanh thu năm ' + (new Date().getFullYear() - 0), $scope.revenue.item[0] * 100 / $scope.revenue.total]
        ]);
        var chart = new google.visualization.PieChart(document.getElementById('myChart23'));
        chart.draw(data);
    }
});

app.controller("category-ctrl", ($scope, $http) => {
    $scope.categories = [];
    $scope.index = 0;
    $("#prev").hide();

    $scope.load_categories = () => {
        var url = `${host_}/categories`;
        $http.get(url).then((resp) => {
            $scope.categories = resp.data;
        }).catch((err) => {
            console.log("Error load items", err);
        });
    };
    
    $scope.prev = () => {
        $scope.index = 0;
        $("#next").show();
        $("#prev").hide();
    };

    $scope.next = () => {
        $scope.index = 1;
        $("#next").hide();
        $("#prev").show();
    };


    $scope.load_categories();
});

app.controller("atm-ctrl", ($scope, $http) => {
    $scope.atm = {
        "name":"",
        "company": "",
        "number": "",
        "account":{},
        "valid":false
    };

    $http.get(`${host_}/accounts/${id}`).then((resp) => {
        $scope.atm.account = resp.data;
        $scope.atm.name = convert_vi_to_en(String($scope.atm.account.fullname).toUpperCase());
    }).catch((err) => {
        console.log("ATM cannot account", err);
    });

    $scope.them = () => {
        if ($scope.atm.number == "" || $scope.atm.number == undefined) {
            $scope.errorNumber = "(*) Vui lòng nhập số tài khoản";
        }
        else if ($scope.atm.company == "" || $scope.atm.company == undefined) {
            $scope.errorNumber = "";
            $scope.errorSelect = "(*) Vui lòng chọn ngân hàng";
        } 
        else {
            $scope.errorNumber = "";
            $scope.errorSelect = "";
            var url_c = `${host_}/atm`;
            $("#load-page").css("display", "block");


            setInterval(() => {
                $http.post(url_c, $scope.atm).then((resp) => {
                    $("#load-page").css("display", "none");
                    window.location.href = "http://localhost:8080/user/wallet";
                }).catch((err) => {});
            }, 3000);
        }
    };

});

// cart là cái chỉ để thêm sản phẩm vào giỏ hàng
app.controller("cart-ctrl", ($scope, $http) => {

    // luôn lấy dữ liệu từ trong localStorage để khi bấm F5 ko bị mất
    var id = account_.innerHTML;
    number.innerHTML = (localStorage.getItem(id) == undefined) ? 0 : JSON.parse(localStorage.getItem(id)).length;

    $scope.qty = 1;

    $scope.number_orgin = Number(document.getElementById("numberProduct").innerHTML);

    $scope.minus = () => {
        $scope.qty = ($scope.qty < 2) ? 1 : $scope.qty-1;
    };

    $scope.plus = () => {
        $scope.qty++;
        if ($scope.qty > $scope.number_orgin) {
            $scope.qty = $scope.number_orgin;
            $('.toast-warning').toast('show');
            setTimeout(() => {
                $('.toast-warning').toast('hide');
            }, 1100);
        }
    };

    $scope.cart = {
        item: [],

        add(id_product) {
            var item = this.items.find(item => item.id == id_product);
            if (item) {
                item.qty += $scope.qty;
                this.saveToLocalStorage();
            }
            else {
                $http.get(`${host_}/products/${id_product}`).then(resp => {
                    resp.data.qty = $scope.qty;
                    this.items.push(resp.data);
                    this.saveToLocalStorage();
                    number.innerHTML = Number(number.innerHTML) + 1;   // để mặc định number sẽ là $scope.count (+1) để hiện 1 ngay - nó sẽ là localStorage đang giữ bao nhiêu item, nên ko nhất định là 1;
                })
            }
        },

        //Lưu giỏ hàng vào local storage
        saveToLocalStorage(){
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem(id, json);
        },

        //Đọc giỏ hàng từ local storage
        loadFromLocalStorage(){
            var json = localStorage.getItem(id);
            this.items = json ? JSON.parse(json) : [];
        }
    }

    $scope.show_discount = (id) => {
        document.getElementById("voucher-" + id).style.display = "block";
        document.getElementById("btn-voucher-" + id).style.display = "none";
        document.getElementById("link-voucher-" + id).style.display = "block";
    }

    $scope.cart.loadFromLocalStorage();
});

// cái này là để hiển thị danh sách sản phẩm và thanh toán luôn
app.controller("checkout-ctrl", ($scope, $http) => {
    var date = new Date().toLocaleDateString("en-CA").toString();

    $scope.qty = 1;

    $scope.discount_add_voucher = {};

    $scope.cart = {
        items: [],

        minus(id_product) {
            var item = this.items.find(item => item.id == id_product);
            item.qty = (item.qty < 2) ? 1 : item.qty - 1;
            this.saveToLocalStorage();
        },
    
        plus(id_product) {
            var item = this.items.find(item => item.id == id_product);
            item.qty++;
            if (item.qty > item.number) {
                item.qty = item.number;
            }
            this.saveToLocalStorage();
        },


        // Thêm sản phẩm vào giỏ hàng
        add(id_product){
            var item = this.items.find(item => item.id == id_product);
            if(item) {
                item.qty++;
                this.saveToLocalStorage();
            }
            else {
                $http.get(`${host_}/products/${id_product}`).then(resp => {
                    resp.data.qty = 1;
                    this.items.push(resp.data);
                    this.saveToLocalStorage();
                })
            }
        },

        // Xóa sản phẩm khỏi giỏ hàng
        remove(id_product){
            var index = this.items.findIndex(item => item.id == id_product);
            this.items.splice(index, 1);
            number.innerHTML -= 1;
            this.saveToLocalStorage();
        },

        // Xóa sạch các mặt hàng trong giỏ
        clear(){
            this.items = [];
            number.innerHTML = 0;
            this.saveToLocalStorage();
        },

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

        get amountDeliver() {
            var tongtien = Number(this.amount + 1.2);
            if ($scope.discount_add_voucher.free == undefined || $scope.discount_add_voucher.free == null || $scope.discount_add_voucher.free < 10) {
                return tongtien;
            } else {
                return tongtien - tongtien * $scope.discount_add_voucher.free / 100;
            }
        },

        // Lưu giỏ hàng vào local storage
        saveToLocalStorage() {
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem(id, json);
        },

        // Đọc giỏ hàng từ local storage
        loadFromLocalStorage() {
            var json = localStorage.getItem(id);
            this.items= json ? JSON.parse(json):[];
        }
    }
    $scope.cart.loadFromLocalStorage();

    $scope.confirmFree = () => {
        $("#success_find").css("opacity", "0");
        $("#error_find").css("opacity", "0");
        $("#find-free").css("display", "block");

        var path = $("#getPathFree").text();
        
        if ($('#btn-free').prop('disabled', false)) {
            setTimeout(() => {
                $http.get(path).then((resp) => {
                    $("#find-free").css("display", "none");
                    $scope.discount_add_voucher = resp.data;
                    if (!$scope.discount_add_voucher) {
                        $("#error_find").css("opacity", "1");
                        $("#error_find").text("(*) Mã giảm giá không tồn tại.");
                        $("#success_find").css("opacity", "0");
                        $("#btn-free").attr("disabled", "disabled");
                    }
                    else if ($scope.discount_add_voucher && (new Date($scope.discount_add_voucher.dateEnd) < new Date()) ) {
                        $("#error_find").css("opacity", "1");
                        $("#error_find").text("(*) Mã giảm giá đã hết hạn sử dụng.");
                        $("#success_find").css("opacity", "0");
                        $("#btn-free").attr("disabled", "disabled");
                    }
                    else {
                        $("#error_find").css("opacity", "0");
                        $("#success_find").css({"opacity": "1", "color": "#379237"});
                        $("#success_find").text("(*) Đã nhập thành công voucher.");
                        $("#btn-free").css("display", "none");
                        $("#codeFree").attr("disabled", "disabled");
                        $("#btn-enter-free").text($scope.discount_add_voucher.id);
                        $scope.form.total = Math.round($scope.cart.amountDeliver * 100) / 100;
                    }
                }).catch((err) => { 
                    console.log(err);
                });
            }, 3000);
        }
    }

    // ---------------------------------------------------------
    $scope.form = {
        "address": document.getElementById("order-address-buy").innerHTML,
        "account": {},
        "createDate": date,
        "dateConfirm":null,
        "total" : Math.round($scope.cart.amountDeliver * 100) / 100, 
        "status": {}
    };

    $http.get(`${host_}/accounts/${id}`).then((resp) => {
        $scope.form.account = resp.data;
    }).catch((err) => {
        console.log("Order cannot account", err);
    });

    $http.get(`${host_}/status/0`).then((resp) => {
        $scope.form.status = resp.data;
    }).catch((err) => {
        console.log("Order cannot account", err);
    });


    $scope.orderNow = {};

    $scope.order = {
        thread1(){
            var url = `${host_}/orders`;
            $("#load-page").css("display", "block");
            setTimeout(() => {
                $http.post(url, $scope.form).then((resp) => {
                    $scope.orderNow = resp.data;
                    console.log($scope.orderNow);
                }).catch((err) => {
                    console.log("Order failed", err);
                });
            }, 2000);

            setTimeout(() => {
                this.thread2();
            }, 4000);

            setTimeout(() => {
                this.thread3();
            }, 5000);
        },
        thread2(){
            for (var item of $scope.cart.items) {
                $scope.detail = {
                    "product": {
                        "id": item.id,
                        "name": item.name,
                        "image": item.image,
                        "price": item.price,
                        "createDate": item.createDate,
                        "available": item.available,
                        "number": item.number,
                        "category": item.category,
                        "describe": item.describe,
                        "discount": item.discount
                    },
                    "order": $scope.orderNow,
                    "quantity": item.qty
                };

                var url_orderdetails = `${host_}/orderdetails`;
                var record = angular.copy($scope.detail);
                $http.post(url_orderdetails, record).then((resp) => {
                    console.log("- "+ item.id + " ok", resp);
                }).catch((err) => {
                    console.log(item.id + " failed", err);
                });
            };

            localStorage.removeItem(id);
        },
        thread3() {
            $scope.notify = {
                "title"    : "Bạn đã đặt hàng #" + $scope.orderNow.id + " vào " + $scope.orderNow.createDate + " thành công. Hãy kiểm tra tin nhắn trong gmail.",
                "fileName" : "- Tin nhắn thông báo -",
                "sentDate" : "2022-12-10",
                "status"   : true,
                "account"  : $scope.orderNow.account
            };
            var url_notify_1 = `${host_}/notifications`;
            $http.post(url_notify_1, $scope.notify).then((resp) => {
                console.log("notify ok", resp);
            }).catch((err) => {
                console.log("notify failed", err);
            });
            window.location.href = "http://localhost:8080/fastshop.com";
        }
    }
});

app.controller("report-staff-ctrl", ($scope, $http) => {
    $scope.items = [];
    $scope.stock = [];
    $scope.categories = [];
    $scope.authorities = [];
    $scope.discount = [];
    $scope.indexes_1 = [];
    $scope.indexes_2 = [];
    $scope.indexes_3 = [];
    $scope.indexes_4 = [];

    var url_product = `${host_}/products`;
    $http.get(url_product).then((resp) => {
        $scope.items = resp.data;
        $scope.stocks = resp.data;
    }).catch((err) => {});

    $scope.add_1 = (product) => {
        if ($("#pro-name-" + product.id).is(":checked")) {
            $scope.indexes_1.push(product);
        }
        else {
            var index = $scope.indexes_1.findIndex(item => item == product);
            $scope.indexes_1.splice(index, 1);
        }
    };

    // ------------------------------------
    var url_category = `${host_}/categories`;
    $http.get(url_category).then((resp) => {
        $scope.categories = resp.data;
    }).catch((err) => {});

    $scope.add_2 = (category) => {
        if ($("#cat-name-" + category.id).is(":checked")) {
            $scope.indexes_2.push(category);
        }
        else {
            var index = $scope.indexes_2.findIndex(item => item == category);
            $scope.indexes_2.splice(index, 1);
        }
    };

    // --------------------------------------
    var url_employee = `${host_}/authorities/staff/active`;
    $http.get(url_employee).then((resp) => {
        $scope.authorities = resp.data;
    }).catch((err) => {});
    
    $scope.add_3 = (auth) => {
        if ($("#emp-name-" + auth.account.username).is(":checked")) {
            $scope.indexes_3.push(auth);
        }
        else {
            var index = $scope.indexes_3.findIndex(item => item == auth);
            $scope.indexes_3.splice(index, 1);
        }
    };  

    // --------------------------------------
    $scope.add_4 = (product) => {
        if ($("#stock-name-" + product.id).is(":checked")) {
            $scope.indexes_4.push(product);
        }
        else {
            var index = $scope.indexes_4.findIndex(item => item == product);
            $scope.indexes_4.splice(index, 1);
        }
    }
});

app.controller("report-admin-ctrl", ($scope, $http) => {
    // chỗ này để sau này chèn thêm đồ thị
    // trong home admin html
});

app.controller("product-ctrl", ($scope, $http) => {
    $scope.form = {};
    $scope.items = [];
    $scope.now = getDateNow(null);

    var url1 = `${host_}/products`;
    $http.get(url1).then((resp) => {
        $scope.items = resp.data;
    }).catch((err) => {
        console.log("Error load items", err);
    });

    // ----------------------------------------------
    $scope.edit = (id) => {
        var url = `${host_}/products/${id}`;
        $http.get(url).then((resp) => {
            $scope.form = resp.data;
            console.log("edit ok", resp);
        }).catch((err) => {
            console.log("edit fail", err);
        });
    };

    $scope.update = () => {
        var item = angular.copy($scope.form);
        console.log(item);
        var url_p_u = `${host_}/products/${$scope.form.id}`;
        $http.put(url_p_u, item).then((resp) => {
            console.log("Update ok", resp);
        }).catch((err) => {
            console.log("Error", err);
        });
        window.location.href = "http://localhost:8080/staff/products";
    };

    $scope.delete = (id) => {
        var item = angular.copy($scope.form);
        item.available = false;
        item.number = 0;
        var url = `${host_}/products/${id}`;
        $http.put(url, item).then((resp) => {
            console.log("delete success", resp);
        }).catch((err) => {
            console.log("error", err);
        });
        window.location.href = "http://localhost:8080/staff/products";
    };
});

app.controller("order-ctrl", ($scope, $http) => {
    $scope.orders = [];
    $scope.orderdetails = [];
    $scope.bill = {};
    $scope.sum = 0;

    // load all of order
    $scope.load_orders = () => {
        var url_order = `${host_}/orders`;
        $http.get(url_order).then((resp) => {
            $scope.orders = resp.data;
            console.log("load order", resp);
        }).catch((err) => {
            console.log("error order", err);
        });
    };

    // load all of detail
    $scope.load_orderdetails = () => {
        var url_orderdetails = `${host_}/orderdetails`;
        $http.get(url_orderdetails).then((resp) => {
            $scope.orderdetails = resp.data;
            console.log("load order detail", resp);
        }).catch((err) => {
            console.log("error order detail", err);
        });
    };

    // edit order to show product and qua
    $scope.edit = (id) => {
        var url_orderdetails = `${host_}/details/order/${id}`;
        var s = 0;
        $http.get(url_orderdetails).then((resp) => {
            $scope.bill = resp.data;
            for (let index = 0; index < $scope.bill.length; index++) {
                s += Number($scope.bill[index].quantity) * Number($scope.bill[index].product.price);
            }
            $scope.sum = s;
            console.log("load form detail " + $scope.sum, resp);
        }).catch((err) => {
            console.log("error order detail", err);
        });
    };


    $scope.load_orders();
    $scope.load_orderdetails();
});

app.controller("keyword-ctrl", ($scope, $http) => {
    $scope.keysearch = "";
    $scope.keywords = [];

    var keywords_load = localStorage.getItem("keywords");
    if (keywords_load == undefined) {
        localStorage.setItem("keywords", JSON.stringify($scope.keywords));
    }
    else {
        $scope.keywords = JSON.parse(keywords_load);
    }


    // dang bi loi cho nay khi add va delete
    $scope.add = function () {
        var search_input = document.getElementById("search-gallery");
        search_input.addEventListener("keypress", function(event) {
            if (event.key === "Enter" && $scope.keysearch != "") {
                $scope.keywords.push($scope.keysearch);
                // this is create new set to access not duplicate data
                var set = new Set($scope.keywords);

                // this is convert Array from to Set before
                $scope.keywords = Array.from(set);

                // Save a array keywords after converted
                localStorage.setItem("keywords", JSON.stringify($scope.keywords));

                // go head a any page with URL + kw
                window.location.href = "http://localhost:8080/user/search?keyword=" +$scope.keysearch; 
            }
       });
    };

    $scope.delete = (kw) => {
        var index = $scope.keywords.findIndex(item => item == kw);
        $scope.keywords.splice(index, 1);
        localStorage.setItem("keywords", JSON.stringify($scope.keywords));
    };


    // -----------------------------------------------------------------------------------------------
    // do không thể dùng id của thymeleaf cho ng-click nên đổi category thành angular để lấy id
    $scope.categories = [];
    $scope.load_categories = () => {
        var url = `${host_}/categories`;
        $http.get(url).then((resp) => {
            $scope.categories = resp.data;
        }).catch((err) => {
            console.log("Error load items", err);
        });
    };
    $scope.load_categories();


    $scope.priceFrom = 0;
    $scope.priceTo   = 1000000;
    $scope.map_filter = {"rate": 0, "cateId": "", "priceFrom": $scope.priceFrom, "priceTo": $scope.priceTo};

    $scope.add_item_filter = (id) => {
        var item = String(id);
        var index = item.indexOf("-");
        var fStr = item.substring(0, index);
        var lstr = item.substring(index+1, item.length);
        $scope.map_filter[fStr] = lstr;
        console.log($scope.map_filter);
    };

    $scope.add_price_filter = () => {
        if ($scope.priceFrom < $scope.priceTo) {
            $scope.map_filter["priceFrom"] = $scope.priceFrom;
            $scope.map_filter["priceTo"] = $scope.priceTo;
        }
        console.log($scope.map_filter);
    };

    $scope.filter_submit = () => {
        var rate = Number($scope.map_filter["rate"]);
        var cateId = String($scope.map_filter["cateId"]);
        var priceFrom = Number($scope.map_filter["priceFrom"]);
        var priceTo = Number($scope.map_filter["priceTo"]);
        var url = `http://localhost:8080/user/filter?rate=${rate}&cateId=${cateId}&priceFrom=${priceFrom}&priceTo=${priceTo}`;
        window.location.href = url;
    };

});

app.controller("changed-ctrl", ($scope, $http) => {
    $scope.pass1 = "";
    $scope.pass2 = "";
    $scope.pass3 = "";
    $scope.error1 = "";
    $scope.error2 = "";
    $scope.error3 = "";

    $scope.change = () => {
        var id = account_.innerHTML;
        var url = `${host_}/accounts/${id}`;
        $http.get(url).then((resp) => {
            $scope.account = resp.data;

            $scope.error1 = ($scope.pass1 == "" || $scope.pass1 == NaN) ? "(*) Password hiện tại không được để trống." :
                            (!angular.equals($scope.pass1, $scope.account.password)) ? "(*) Password hiện tại không chính xác": "" ;
            
            $scope.error2 = ($scope.pass2 == "" || $scope.pass2 == NaN) ? "(*) Password mới không được để trống." : "" ;
            $scope.error3 = ($scope.pass3 == "" || $scope.pass3 == NaN) ? "(*) Xác nhận password không được để trống." : 
                            ($scope.pass2       != $scope.pass3) ? "(*) Mật khẩu xác nhận không trùng nhau."                 : "" ;
            

            if ($scope.error1 == "" && $scope.error2 == "" && $scope.error3 == "") {
                $scope.account.password = $scope.pass3;
                var item = angular.copy($scope.account);
                $http.put(url, item).then((resp) => {
                    alert("Đổi mật khẩu thành công", resp);
                    window.location.href = window.location.href;
                }).catch((err) => {
                    
                });
            }
        });
    };
});

app.controller("comment-ctrl", ($scope, $http) => {
	let productid;
	let username;
    localStorage.setItem("rate", 1);
	$scope.form = {
		product: {},
		account: {},
		datePost: getDateNow(),
		feedback: '',
		rate: Number(localStorage.getItem("rate"))
	};

	setTimeout(() => {
		productid = document.getElementById('productId').value;
		username = document.getElementById('username').value;
	}, 2000);

	setTimeout(() => {
		$http
			.get(`${host_}/products/${productid}`)
			.then((resp) => {
				$scope.form.product = resp.data;
			})
			.catch((err) => {
				console.log('Product cannot account', err);
			});

		$http
			.get(`${host_}/accounts/${username}`)
			.then((resp) => {
				$scope.form.account = resp.data;
			})
			.catch((err) => {
				console.log('Account cannot account', err);
			});
	}, 2000);

	$scope.create = () => {
        $("#load-page").css("display", "block");
        setTimeout(() => {
            var url = `${host_}/comments`;
            $scope.form.rate = Number(localStorage.getItem("rate"));
            $scope.form.feedback = document.getElementById('content-evaluate').value;
            var item = angular.copy($scope.form);

            $http.post(url, item).then((resp) => {
                    localStorage.removeItem("rate");
                    window.location.href = 'http://localhost:8080/user/detail/' + productid;
                })
                .catch((err) => {
                    result = false;
                    console.log('Comment failed !', err);
                });
        }, 3000);
	};
});

app.controller("detail-staff", ($scope, $http) => {

    var productId = $("#id_product_staff").val();
    $scope.productdetails = [];


    // false là mặc định chưa có gì trong info
    $scope.confirm = false;
    $http.get(`${host_}/product/detail/exist/${productId}`).then((resp) => {
        $scope.confirm = resp.data;
    }).catch((err) => {
        $scope.confirm = false;
    });

    // add product detail
    var url = `${host_}/product/detail/${productId}`;
    $http.get(url).then((resp) => {
        $scope.productdetails = resp.data;
    }).catch((err) => {
    });

    $scope.add = () => {
        $scope.productdetails.forEach(item => {
            var productDetail = {};
            productDetail.categoryDetailId = item.categoryDetailId;
            productDetail.info = item.info;
            productDetail.productId = item.productId;
            productDetail = angular.copy(productDetail);

            if ($scope.confirm == false) {
                var path = `${host_}/product/detail`;
                $http.post(path, productDetail).then((resp) => {
                    console.log("Add thanh cong", resp);
                }).catch((err) => {
                    console.log("Add failed", err);
                });
            }
        });
        window.location.href = "http://localhost:8080/staff/detail/" + productId; 
    };


    // đây là hàm update từng productdetail
    $scope.update = () => {
        $scope.productdetails.forEach(item => {
            var productDetail = {};
            productDetail.id = item.id;
            productDetail.categoryDetailId = item.categoryDetailId;
            productDetail.info = item.info;
            productDetail.productId = item.productId;
            productDetail = angular.copy(productDetail);

            if ($scope.confirm == true) {
                var path = `${host_}/product/detail/update/${item.id}`;
                $http.put(path, productDetail).then((resp) => {
                    console.log("Update thanh cong", resp);
                }).catch((err) => {
                    console.log("Update failed", err);
                });
            }
        });
        window.location.href = "http://localhost:8080/staff/detail/" + productId; 
    };
});

app.controller("discount-ctrl", ($scope, $http) => {
    $scope.now = getDateNow(null);
    $scope.discounts = [];
    $scope.error = "";
    $scope.numberDay = 0;
    $scope.code_ = "...";

    
    var all = `${host_}/discounts/all`;
    $http.get(all).then((resp) => {
        $scope.discounts = resp.data;
    }).catch((err) => {});

    $scope.code = {
        "id": "",
        "dateFrom": new Date(getDateNow(null)),
        "dateEnd": new Date(getDateNow(Number($scope.numberDay))),
        "free": "",
        "dolar": null,
        "number": 0
    };

    $scope.randomCode = () => {
        var length = 19;
        $scope.code_ = "";
        for (var i = 1; i <= length; i++) {
            var random = Math.floor((Math.random() * 9) + 1);
            $scope.code_ += (i%5==0) ? "-" : String(random);
        }
        $scope.code.id = $scope.code_;
    };

    // this is function to update days when change number discount
    $scope.changeDate = () => {
        $scope.code.dateEnd = new Date(getDateNow(Number($scope.numberDay)));
    };

    $scope.check = () => {
        $scope.error = "";
        if ($scope.code_ == "") {
            $scope.error = "(*) Vui lòng tạo mã giảm giá ngay.";
        }
        else if ($scope.code.number <= 0 || $scope.code.number == null) {
            $scope.error = "(*) Số lượng phải lớn hơn 0.";
        }
        else if ($scope.numberDay <= 0 || $scope.numberDay == null) {
            $scope.error = "(*) Số ngày không thể nhập số âm.";
        }
        else if (($scope.code.free == null || $scope.code.free == "") && ($scope.code.dolar <= 0 || $scope.code.dolar == null)) {
            $scope.error = "(*) Chưa có giá trị giảm của discount.";
        }
        else if ($scope.code.free == "money" && ($scope.code.dolar <= 0 || $scope.code.dolar == null)) {
            $scope.error = "(*) Chưa có giá trị giảm tiền mặt.";
        }
        else {
            $scope.error = "";
            $scope.code.free = ($scope.code.free == "money") ? "" : $scope.code.free;
            var url = `${host_}/discounts`;
            var item = angular.copy($scope.code);
            $http.post(url, item).then((resp) => {
                console.log("Discount ok", resp);
            }).catch((err) => {
                console.log("Fail dscount", err);
            });
            window.location.href = "http://localhost:8080/staff/discount";
        }
    };
    
});

app.controller("staffhome-ctrl", ($scope, $http) => {
    $scope.orders = [];
    var url = `${host_}/orders`;
    $http.get(url).then((resp) => {
        $scope.orders = resp.data;
    }).catch((err) => {});

    $scope.length = 0;
});

app.controller("mode-cart-ctrl", ($scope, $http) => {
    $scope.count_notify = [];
    $scope.count_true = [];
    $scope.count_false = [];
    var url_all  = `${host_}/notifications/${id}`; // id of account
    var url_true = `${host_}/notifications/${id}/true`; // id of account
    var url_false = `${host_}/notifications/${id}/false`; // id of account

    $http.get(url_all).then((resp) => { $scope.count_notify = resp.data; }).catch((err) => {});
    $http.get(url_true).then((resp) => { $scope.count_true = resp.data; }).catch((err) => {});
    $http.get(url_false).then((resp) => { $scope.count_false = resp.data; }).catch((err) => {});

    // id of nofity
    $scope.update_status = (id) => {
        var url_id = `${host_}/notifications/notify/${id}`;

        $scope.notify = {};

        setTimeout(() => {
            $http.get(url_id).then((resp) => {
                $scope.notify = resp.data;
            }).catch((err) => {});    
        }, 200);
        

        setTimeout(() => {
            $http.put(url_id).then(() => {
                if (window.location.href.includes("admin")) {
                    window.location.href = "http://localhost:8080/admin/history";
                }
                else if (window.location.href.includes("staff")) {
                    window.location.href = window.location.href;
                }
            }).catch((err) => {});
        }, 300);
    };
});