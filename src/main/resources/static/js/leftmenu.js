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
            if(xhr.status == '401') {
                console.log("401");
                window.location = '/login?error=true&exception=' + xhr.responseJSON.error;
            }
            else if(xhr.status == '403') {
                window.location = '/denied?exception=' + xhr.responseJSON.error;
            }
        }
    });
}