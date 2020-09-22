function tokenResend() {
    var token = document.getElementById("token").value;
    var serverContext = '/';
    $.get(
        serverContext + 'user/resendRegistrationToken?token=' + token,
        function (data) {
            window.location.href = serverContext + "information?message="
                + data.message;
        }).fail(
        function (data) {
                window.location.href = serverContext + "information?message="
                    + data.responseJSON.statusText;
        });
}

