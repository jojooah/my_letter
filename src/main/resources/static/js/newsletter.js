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


        $("select[data-progress=filter]").on("change", function() {
            let selectedValue = $(this).val();
            let $nextSelect = $(this).closest("div").next("div").find("select[data-progress=filter]");

            // 다음 셀렉트박스의 모든 옵션을 비활성화 및 숨김 처리
            $nextSelect.find("option").hide().prop("disabled", true);

            // upperCode와 일치하는 옵션만 활성화 및 표시
            if (selectedValue !== "") {
                let matchingOptions = $nextSelect.find("option").filter(function() {
                    return $(this).data("progress") === selectedValue;
                }).show().prop("disabled", false);

                // 일치하는 옵션이 1개인 경우 자동으로 선택
                if (matchingOptions.length === 1) {
                    $nextSelect.val(matchingOptions.val());
                    return;
                }
            }


            $nextSelect.val("");
        });

    }

    return {
        start: start
    }

})();