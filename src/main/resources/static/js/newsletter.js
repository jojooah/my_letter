const Newsletter = (function () {
    function start() {

        $("input[name=saveNewsLetter]").click(function(){
            let objParams = $('#newsLetterForm').serializeObject();

            $.ajax({
                url: '/write',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(objParams),
                success: function(response) {

                    if (response.data.status === "SUCCESS") {
                        alert(response.data.message);
                        window.location.href = "/write";
                    } else {
                        alert(response.data.message);
                        window.location.href = "/write";
                    }
                },
                error: function(xhr, status, error) {
                    alert(error);
                }
            });
        })

    }

    return {
        start: start
    }

})();