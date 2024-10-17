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

        //뉴스레터 저장
        $("button[data-progress=saveNewsLetterHeader]").click(function() {
            let objParams = $("#newsLetterForm").serializeObject();
            if(!validationCheck()) return;

            if (confirm("저장하시겠습니까?")) {
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
            }
        });

        //뉴스레터 삭제
        $("button[data-progress=delete]").click(function() {
            let newsLetterSeq = $("#contentNewsLetterSeq").val();
            let newsLetterHeaderSeq = $("#contentNewsLetterHeaderSeq").val();

            if (confirm("삭제하시겠습니까?")) {
                $.ajax({
                    url: "/deleteNewsLetter",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(newsLetterSeq),
                    success: function(response) {
                        if (response.data.status === "SUCCESS") {
                            alert(response.data.message);
                            window.location.href = "/newsletter/list?seq="+newsLetterHeaderSeq;
                        } else {
                            alert(response.data.message);
                            location.reload();
                        }
                    },
                    error: function(xhr, status, error) {
                        alert(error);
                    }
                });
            }
        });

        //뉴스레터 수정
        $("button[data-progress=update]").click(function() {
            let objParams = {
                newsLetterHeaderSeq: $("#contentNewsLetterHeaderSeq").val(),
                newsLetterSeq:$("#contentNewsLetterSeq").val(),
                title:$("#title").text(),
                content:$("#content").next("p").html(),
                freeYn:$("#freeYn").val(),
                cost:$("#cost").val(),
                description:$("#description").text(),
            }

           window.location.href = "/write?headerSeq="+objParams.newsLetterHeaderSeq+"&seq="+objParams.newsLetterSeq;

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
                }else{}

            }
            $nextSelect.prepend('<option value="">전체</option>');
            $nextSelect.val("");

        });

    }

    function validationCheck(){
        let isValid = true;
        $("[data-validation]").each(function() {
            let validationType = $(this).data("validation");
            let value = $(this).val().trim();

            if (validationType === "required") {
                if (value === "") {
                   alert($(this).data("name")+"은(는) 필수값입니다.");
                   isValid = false;
                   return false;
                }
            }
        });
        return isValid;
    }

    return {
        start: start
    }

})();

const Reply = (function () {
    const SaveStatus = {
        INSERT: { code: 'I', label: '입력' },
        DELETE: { code: 'D', label: '삭제' },
        UPDATE: { code: 'U', label: '수정' }
    };
    function start() {
        $("button[data-progress=saveReply], button[data-progress=deleteReply]").click(function() {
            let objParams = {
                newsLetterSeq: $("#newsLetterSeq").val(),
                replyContent:$("#replyContent").val(),
                saveStatus:$(this).data('code'),
                replySeq:$(this).data('seq'),
            }

            saveComment(objParams);
        });
    }

    // 수정 버튼 클릭 이벤트
    $("button[data-progress=updateReply]").click(function() {
        let replyContent = $(this).closest('div').prev('p').text();
        $("#replyContent").val(replyContent);
        $("button[data-progress=saveReply]").data('code', 'U');
        $("button[data-progress=saveReply]").data('seq', $(this).data('seq'));

        // 스크롤 이동을 위한 요소의 위치 계산
        let elementOffset = $('#replyContent').offset().top;
        let windowHeight = $(window).height();
        let scrollToPosition = elementOffset - (windowHeight / 2);

        $('html, body').animate({
            scrollTop: scrollToPosition
        }, 200);

    });

    function saveComment(objParams) {
        if ((objParams.saveStatus === SaveStatus.INSERT.code || objParams.saveStatus === SaveStatus.UPDATE.code) && objParams.replyContent === "") {
            alert("댓글 내용을 입력해주세요.");
            return;
        }
        let status = Object.values(SaveStatus).find(status => status.code === objParams.saveStatus)?.label;
        let confirmMessage = "댓글을" + status + "하시겠습니까?"

        if (confirm(confirmMessage)) {
            $.ajax({
                url: "/saveReply",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(objParams),
                success: function (response) {
                    if (response.data.status === "SUCCESS") {
                        alert(response.data.message);
                        location.reload();
                    } else {
                        alert(response.data.message);
                    }
                },
                error: function (xhr, status, error) {
                    alert(error);
                }
            });
        }
    }

    return {
        start:start
    }
})()