/**
 * Created by rubinder on 10/30/16.
 */
$( document ).ready(function() {
    var containerId = null;

    var showUpload = function () {
        containerId = $(this).attr('id');
        $(".upload .breadcrumb .active").text($(this).find('span').text());
        $(".containers").hide();
        $(".upload").show();
    };
    $(".containerDiv").click(showUpload);

    $(".gotoContainers").click(function () {
        containerId = null;
        $(".containers").show();
        $(".upload").hide();
    });

    $(".form-create-container .btn").click(function () {
        $.ajax({
            url: "/containers",
            type: 'POST',
            data: $(".form-create-container").serialize(),
            success: function(result) {
                if(result.errorVOs) {
                    $.each(result.errorVOs, function (i, errorVO) {
                        $(".form-create-container .errorMessage").append('<div class="alert alert-danger alert-dismissible" role="alert">'
                            + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                            + errorVO.message
                            + '</div>');
                    })
                } else {
                    $(".form-create-container .successMessage").append('<div class="alert alert-success alert-dismissible" role="alert">'
                        + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                        + 'Container Successfully Created.'
                        + '</div>');

                    $('<div class="col-md-3">'
                        +'<div class="containerDiv" id="'+result.id+'">'
                        +'<div class='+result.type+'></div>'
                        +'<span>'+result.name+'</span>'
                        +'</div>'
                        +'</div>').click(showUpload).appendTo(".containers");
                    $(".form-create-container").reset()
                }
            }
        });
    });

    $(".form-share-container .btn").click(function () {
        var data = $(".form-share-container").serialize();
        data = data +"&id="+containerId;
        $.ajax({
            url: "/containers/share",
            type: 'POST',
            data: data,
            success: function(result) {
                if(result.errorVOs) {
                    $.each(result.errorVOs, function (i, errorVO) {
                        $(".form-share-container .errorMessage").append('<div class="alert alert-danger alert-dismissible" role="alert">'
                            + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                            + errorVO.message
                            + '</div>');
                    })
                } else {
                    $(".form-share-container .successMessage").append('<div class="alert alert-success alert-dismissible" role="alert">'
                        + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                        + 'Container Successfully Shared.'
                        + '</div>');
                }
            }
        });
    });
});