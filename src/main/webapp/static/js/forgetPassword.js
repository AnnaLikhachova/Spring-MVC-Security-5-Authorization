var serverContext = '/';

$(document).ready(function () {
	$('form').submit(function(event) {
		resetPass(event);
    });
});

function resetPass(event){
    event.preventDefault();	
    var email = $("#email").val();
    $.post(serverContext + "user/resetPassword",{email: email} ,function(data){
        window.location.href = serverContext + "information?message="
            + data.message;
    })
    .fail(function(data) {
            window.location.href = serverContext + "information?message=" + data.responseJSON.statusText;
    });
}

