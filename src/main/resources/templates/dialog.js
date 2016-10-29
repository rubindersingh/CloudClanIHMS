/**
 * Created by Utkarsh on 10/25/2016.
 */

$(document).ready(function() {
    //display module
    var element1 = document.getElementById('display_module');
    //containers module
    var element2 = document.getElementById('container_module');
    //back to container
    var element3 = document.getElementById("back_to_container");
    //Container names
    var element4 = document.getElementById("ulObj");
    //Current Container
    var element5 = document.getElementById("current_container");


    element3.addEventListener('click', function() {
        console.log('click handler fired');
        element1.style.display = "none";
        element2.style.display = "block";
    });

    element4.addEventListener('click', function () {
        Populate();
        element1.style.display = "block";
        element2.style.display = "none";
        $("#current_container").append(this.textContent);
    });
    $(function() {

        $("#dialog").dialog({
            autoOpen: false
        });
        $("#create_folder").on("click", function() {
            $("#dialog").dialog("open");
        });
    });

    $.getJSON('Module.json', function(data) {
        // Set json data from file to variable 'persons'
        var buttons = data.Buttons;
        // For each item of variable person append to ul list
        $.each(buttons, function(key, val)
        {
            $('<li style=" background-color: black; float: left; border: 3px solid deepskyblue; padding: 10px; margin-left: 10px;"><a style="display: block; color: white; text-align: center; padding: 14px 16px; text-decoration: none; ">' + val.TText + '</a></li>').appendTo('#ulObj');
        });
    });

    $(function() {
        function callme(e) {
            var data = new FormData();
            data.append('name', 'myfiles');
            $.each(e.target.files, function(i, file) {
                data.append('file', file);
            });
            $.ajax({
                url: '/uploadFiles',
                data: data,
                cache: false,
                contentType: false,
                processData: false,
                type: 'POST',
                success: function(data){
                    alert('Done');
                }
            });
            /* fileList = e.target.files;
             for (var i = 0, file; file = fileList[i]; ++i) {
             alert(file.webkitRelativePath);
             } */
        }
        $('#file_input1').change(callme);
        $('#file_input2').change(callme);
    });
});


function Populate() {
    $("#aaa").empty();
    /*$.getJSON('Module1.json', function (data) {
        // Set json data from file to variable 'persons'
        var buttons = data.Buttons;
        var icondir = data.IconsDirectory;
        // For each item of variable person append to ul list
        $.each(buttons, function (key, val) {
            $('<div><div style="width: 50px; height: 50px; background-color: #8A2BE2"' + val.JavaScriptAction + '"> ' + val.TText + '</div></div>').appendTo('#aaa');
        });
    });*/
    $.getJSON('Module1.json', function (json)
    {
        var imgList= "";
        var products = json.products;
        $.each(products, function () {
            imgList += '<div><img style="height: 50px; width: 50px;" src= "' + this.imgPath + '"></div>';
        });

        $('#aaa').append(imgList);
    });
}
