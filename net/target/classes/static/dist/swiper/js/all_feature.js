var hours_food  = 23;
var minute_food = 59;
var second_food = 60;
var sum_second_food = 48 * 60 * 60;
var curent_second_food = 0;
var percent_food = 0;

var hours_100  = 2;
var minute_100 = 28;
var second_100 = 36;
var sum_second_100 = 9 * 60 * 60;
var curent_second_100 = 0;
var percent_100 = 0;

// this is toast which show when an user click an item in product detail
function add(status) {
    if (status == true) {
        $(document).ready(function(){
            $('.toast-add').toast('show');
        });   
    }
};


// this is function which to change value checkbox category
function onCbChangeCategory(id) {
    var b = document.getElementById(id).checked;
    document.getElementById("CT-" + id).click();
    document.getElementById(id).checked = !b;
};


// this is function which to focus in search bar
function checkFocus() {
    var hasFocus = $('#search-gallery').is(':focus');
    var hasHover = $('.menu-search').is(':hover');
    var w = $('.menu-search').width();
    if (hasFocus && w > 700) {
        $(".menu-search").show();
    }
    else {
        if (hasHover) {
            $(".menu-search").show();
        }
        else {
            $(".menu-search").hide();
        }
    };
};


$("#report-id-1").show();
$("#report-id-2").hide();
$("#report-id-3").hide();
$("#report-id-4").hide();
function reportNumber() {
    var number = $("#select-report").val();
    $("#report-id-1").hide();
    $("#report-id-2").hide();
    $("#report-id-3").hide();
    $("#report-id-4").hide();
    $("#report-id-" + number).show();
}


// this is form payment which to pay when user accept buy all product in their card
$("#payment-2").hide();
$("#bill-2").hide();
function payment(number) {
    if (number == 1) {
        $("#payment-1").show();
        $("#payment-2").hide();
        $("#bill-1").show();
        $("#bill-2").hide();
        $("#btn-order-show").show();
        localStorage.setItem("payment", JSON.stringify("bill-1"));
    }
    else {
        $("#payment-2").show();
        $("#payment-1").hide();
        $("#bill-2").show();
        $("#bill-1").hide();
        localStorage.setItem("payment", JSON.stringify("bill-2"));
        if ($("#yes-no-show-atm").val() == "yes") {
            $("#btn-order-show").show();
        }
        else {
            $("#btn-order-show").hide();
        }
    }
    return number;
}


$("#order-form-account").hide();
$("#order-form-calendar").hide();
   
$("#order-search-account").click(function(){
    $("#order-form-calendar").hide();
    $("#order-form-account").slideToggle();
});


$("#order-search-calendar").click(function(){
    $("#order-form-account").hide();
    $("#order-form-calendar").slideToggle();
});


if (document.getElementById("discount_food") != undefined && document.getElementById("discount_100") != undefined) {
    setInterval(function () {
        if (hours_food == 0 && minute_food == 0 && second_food == 1) {
            document.getElementById("discount_food").innerHTML = "00 : 00 : 00";
            document.getElementById("discount_100").innerHTML = "00 : 00 : 00";
        } else {
            second_food--;
            second_100--;
            if (second_food == 0) {
                minute_food--;
                second_food = 60;
    
                if (minute_food == 0) {
                    hours_food--;
                    minute_food = 59;
    
                    if (hours_food == 0) {
                        hours_food = hours_food;
                    }
                }
            }
    
            if (second_100 == 0) {
                minute_100--;
                second_100 = 60;
    
                if (minute_100 == 0) {
                    hours_100--;
                    minute_100 = 59;
    
                    if (hours_100 == 0) {
                        hours_100 = hours_100;
                    }
                }
            }
    
            if (hours_food.toString().length == 1) {
                hours_food = "0" + hours_food;
            }
    
            if (minute_food.toString().length == 1) {
                minute_food = "0" + minute_food;
            }
    
            if (second_food.toString().length == 1) {
                second_food = "0" + second_food;
            }
    
            if (hours_food==0 && minute_food == 0 && second_food == 0) {
                clearInterval(this);
            }
    
            curent_second_food += 1;
            percent_food = Math.round(curent_second_food * 100 / sum_second_food);
            document.getElementById("discount_food").innerHTML = hours_food + " : " + minute_food + " : " + second_food;
            document.getElementById("progress-bar-food").style.width = 50 - percent_food + "%";
    
    
            curent_second_100 += 1;
            percent_100 = Math.round(curent_second_100 * 100 / sum_second_100);
            document.getElementById("discount_100").innerHTML = hours_100 + " : " + minute_100 + " : " + second_100;
            document.getElementById("progress-bar-100").style.width =25 - percent_100 + "%";
        };
    }, 1000);
}
