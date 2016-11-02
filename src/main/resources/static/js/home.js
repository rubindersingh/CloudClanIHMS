/**
 * Created by rubinder on 10/30/16.
 */
$( document ).ready(function() {
    var containerId = null;

    var showUpload = function () {
        containerId = $(this).attr('id');
        $(".upload .breadcrumb .active").text($(this).find('span').text());
        $(".upload .successMessage").empty();
        $(".upload .errorMessage").empty();
        $(".containers").hide();
        $(".upload").show();
    };
    $(".containerDiv").click(showUpload);

    $(".gotoContainers").click(function () {
        containerId = null;
        $(".containers").show();
        $(".upload").hide();
        $(".upload .successMessage").empty();
        $(".upload .errorMessage").empty();
    });

    $(".form-create-container .btn").click(function () {
        $(".form-create-container .errorMessage").empty();
        $(".form-create-container .successMessage").empty();
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
                }
            },
            error: function(result) {
                if(result.errorVOs) {
                    $.each(result.errorVOs, function (i, errorVO) {
                        $(".form-create-container .errorMessage").append('<div class="alert alert-danger alert-dismissible" role="alert">'
                            + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                            + errorVO.message
                            + '</div>');
                    })
                } else {
                    $(".form-create-container .errorMessage").append('<div class="alert alert-danger alert-dismissible" role="alert">'
                        + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                        + 'Something went wrong.'
                        + '</div>');
                }
            }
        });
        $('.form-create-container .reset').click();
    });

    $(".form-share-container .btn").click(function () {
        $(".form-share-container .errorMessage").empty();
        $(".form-share-container .successMessage").empty();
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
            },
            error: function(result) {
                if(result.errorVOs) {
                    $.each(result.errorVOs, function (i, errorVO) {
                        $(".form-share-container .errorMessage").append('<div class="alert alert-danger alert-dismissible" role="alert">'
                            + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                            + errorVO.message
                            + '</div>');
                    })
                } else {
                    $(".form-share-container .errorMessage").append('<div class="alert alert-danger alert-dismissible" role="alert">'
                        + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                        + 'Something went wrong.'
                        + '</div>');
                }
            }
        });
        $('.form-share-container .reset').click();
    });

    var uploadFiles = function (e) {
        $(".upload .successMessage").empty();
        $(".upload .errorMessage").empty();
        var data = new FormData();
        data.append('containerId', containerId);
        data.append('keepOriginal', true);
        $.each(e.target.files, function(i, file) {
            data.append('files', file);
        });
        $.ajax({
            url: '/images/',
            data: data,
            cache: false,
            contentType: false,
            processData: false,
            type: 'POST',
            success: function(result){
                if(result.errorVOs) {
                    $.each(result.errorVOs, function (i, errorVO) {
                        $(".upload .errorMessage").append('<div class="alert alert-danger alert-dismissible" role="alert">'
                            + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                            + errorVO.message
                            + '</div>');
                    })
                } else {
                    $(".upload .successMessage").append('<div class="alert alert-success alert-dismissible" role="alert">'
                        + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                        + 'Images Successfully Uploaded.'
                        + '</div>');
                }
            },
            error: function(error){
                if(error.errorVOs) {
                    $.each(result.errorVOs, function (i, errorVO) {
                        $(".upload .errorMessage").append('<div class="alert alert-danger alert-dismissible" role="alert">'
                            + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                            + errorVO.message
                            + '</div>');
                    })
                } else {
                    $(".upload .errorMessage").append('<div class="alert alert-danger alert-dismissible" role="alert">'
                        + '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                        + 'Something went wrong.'
                        + '</div>');
                }
            }
        });
        $('.form-upload .reset').click();
    };
    $('#folderUpload').change(uploadFiles);
    $('#fileUpload').change(uploadFiles);
});