var serverContext = '/';

$(document).ready(function () {
    $('form').submit(function (event) {
        savePassword(event);
    });

    $(":password").keyup(function () {
        if ($("#password").val() != $("#matchPassword").val()) {
            $("#error-dialog-error-label").show().html("Passwords do not match.");
        } else {
            $("#error-dialog-error-label").html("").hide();
        }
    });

    options = {
        common: {minChar: 8},
        ui: {
            showVerdictsInsideProgressBar: true,
            showErrors: true,
            errorMessages: {}
        }
    };
    $('#password').pwstrength(options);
});

function savePassword(event) {
    event.preventDefault();
    $(".alert").html("").hide();
    if ($("#password").val() != $("#matchPassword").val()) {
        $("#error-dialog-error-label").show().html("Passwords do not match.");
        return;
    }
    var formData = $('form').serialize();
    var queryString = window.location.search;
    var urlParams = new URLSearchParams(queryString);
    var token = urlParams.get('token');
    $.post(serverContext + "user/updatePassword?token=" + token, formData, function (data) {
        window.location.href = serverContext + "information" + "?message=" + data.message;
    })
        .fail(function (data) {
            $("#error-info").show().append(data.responseJSON.statusText);
        });
}