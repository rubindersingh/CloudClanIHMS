/**
 * Created by rubinder on 10/30/16.
 */
$( document ).ready(function() {
    $(".form-signin .btn").click(function () {
        $.ajax({
            url: "/registration",
            type: 'POST',
            data: $(".form-signin").serialize(),
            success: function(result) {
                if(result.errorVOs) {
                    $.each(result.errorVOs, function (i, errorVO) {
                        $(".errorMessage").append('<div class="alert alert-danger alert-dismissible" role="alert">'
                            + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                            + errorVO.message
                            + '</div>');
                    })
                } else {
                    $(".successMessage").append('<div class="alert alert-success alert-dismissible" role="alert">'
                        + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                        + 'Registration Successfull. Please go to <a href="/home.html" class="alert-link">home page.</a>'
                        + '</div>');
                }
            }
        });
    });
});