console.log("hhh");
localStorage.setItem("url","http://localhost:8080");
$("#signUp").hide();
$(".forgot").bind("click",function(a){
    a.preventDefault();
    $(".login").hide();
    $(".signUp").show();
})
$("#loginBtn").click(function(a){
    a.preventDefault();
    var t = $(this);
    t.addClass("not-allow");
    var e = $("#emailLogin").val()
        , l = $("#passwordLogin").val(),
        s =$("input[name='optionsRadios']:checked").val();
    $.ajax({
        url: localStorage.getItem("url") + "/user/login",
        type: "POST",
        dataType: "json",
        xhrFields: {
            withCredentials: !0
        },
        crossDomain: !0,
        data: {
            email: e,
            password: l
        },
        success: function(a) {
            a.data!=null  ? (localStorage.setItem("userId",a.data.userId),
                location.href = "html/home.html") : alert("登录失败")
        },
        complete: function() {
            t.removeClass("not-allow")
        }
    })
})