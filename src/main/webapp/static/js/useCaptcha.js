
function useCaptcha(event) {

    $(".alert").html("").hide();
    if (typeof grecaptcha !== 'undefined') {
        var resp = grecaptcha.getResponse();
        if (resp.length == 0) {
            event.preventDefault();
            $("#captchaError").show().html("Please verify that you are not a robot.");
            return;
        }
    }
}

var onReCaptchaSuccess = function(response) {
    $("#captchaError").html("").hide();
};

var onReCaptchaExpired = function(response) {
    $("#captchaError").html("reCaptcha has expired.  Please solve a new reCaptcha").show();
    grecaptcha.reset();
};