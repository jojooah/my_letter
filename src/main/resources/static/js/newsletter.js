const Newsletter = (function () {
    function start() {

        $("input[name=saveNewsLetter]").click(function(){
            let objParams = $('#newsLetterForm').serializeObject();

            $.ajax({
                url: "/saveNewsLetter",
                type: "POST",
                contentType: "application/json",
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

        $("button[data-progress=createNewsLetterHeader]").click(function() {
            $("#newsletterModal").modal('show');
        });

        $("button[data-progress=saveNewsLetterHeader]").click(function() {
            let objParams = $("#newsLetterForm").serializeObject();
            console.log(objParams)
            $.ajax({
                url: "/saveNewsLetterHeader",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(objParams),
                success: function(response) {
                    if (response.data.status === "SUCCESS") {
                        alert(response.data.message);
                        window.location.href = "/author/newsletter/list";
                    } else {
                        alert(response.data.message);
                        window.location.href = "/author/newsletter/list";
                    }
                },
                error: function(xhr, status, error) {
                    alert(error);
                }
            });
        });
    }

    return {
        start: start
    }

})();