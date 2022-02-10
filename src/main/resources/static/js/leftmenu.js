$('#mypage').click(getMymenu);
$('#messages').click(getMessages);
$('#config').click(getConfig);

function getMymenu(e){
    e.preventDefault();
    getApi('mypage');
}

function getMessages(e){
    e.preventDefault();
    getApi('messages');
}

function getConfig(e){
    e.preventDefault();
    getApi('config');
}


function getApi(location) {
    var csrfHeader = $('meta[name="_csrf_header"]').attr('content')
    var csrfToken = $('meta[name="_csrf"]').attr('content')

    $.ajax({
        type: "get",
        url: "/api/"+location,
        dataType: "json",
        beforeSend: function (xhr) {
            if(csrfHeader&& csrfToken){
                console.log("csrf set");
                xhr.setRequestHeader(csrfHeader, csrfToken);
            }
            xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            xhr.setRequestHeader("Content-type", "application/json");
        },
        success: function(data) {
            console.log("data" +data);
            window.location = '/'+location;
        },
        error: function (xhr, status, error) {
            console.log("status :" + status);
            console.log("error :" + error);
            console.log("xhr: "+ JSON.stringify(xhr));
            errorType = xhr.responseJSON.error;
            if(xhr.status == '401') {
                $.redirect('/login?error=true&exception=' + errorType,
                    {
                        exceptionMessage: xhr.responseJSON.message
                    });
            }
            else if(xhr.status == '403') {
                $.redirect('/denied?exception=' +errorType,
                {
                    username : 'username',
                    exceptionMessage: xhr.responseJSON.message
                });

            }
        }
    });
}