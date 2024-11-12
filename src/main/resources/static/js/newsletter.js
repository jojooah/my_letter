const Newsletter = (function () {
    function start() {

        $("input[name=saveNewsLetter]").click(function () {
            let objParams = $('#newsLetterForm').serializeObject();

            $.ajax({
                url: "/saveNewsLetter",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(objParams),
                success: function (response) {
                    if (response.data.status === "SUCCESS") {
                        alert(response.data.message);
                        window.location.href = "/write";
                    } else {
                        alert(response.data.message);
                        window.location.href = "/write";
                    }
                },
                error: function (xhr, status, error) {
                    alert(error);
                }
            });
        })

        //뉴스레터 헤더 팝업창
        $("button[data-progress=createNewsLetterHeader]").click(function () {
            $("#newsLetterForm")[0].reset();
            $("#newsLetterForm select").prop('selectedIndex', 0);
            $("#newsletterModal").modal('show');
        });

        //뉴스레터헤더 저장
        $("button[data-progress=saveNewsLetterHeader]").click(function () {
            let formData = new FormData();
            let objParams = $("#newsLetterForm").serializeObject();
            if (!validationCheck()) return;
            let thumbnail = $('input[name=thumbnail]')[0].files[0];

            formData.append("newsLetterHeader", new Blob([JSON.stringify(objParams)], {type: "application/json"}));
            formData.append("thumbnail", thumbnail);

            if (confirm("저장하시겠습니까?")) {
                $.ajax({
                    url: "/saveNewsLetterHeader",
                    type: "POST",
                    processData: false,
                    contentType: false,
                    data: formData,
                    success: function (response) {
                        if (response.data.status === "SUCCESS") {
                            alert(response.data.message);
                        } else {
                            alert(response.data.message);
                        }
                    },
                    error: function (xhr, status, error) {
                        if (xhr.responseJSON && xhr.responseJSON.message) {
                            alert("오류 발생: " + xhr.responseJSON.message);
                        } else {
                            alert("예상치 못한 오류가 발생했습니다.");
                        }
                    }
                });
            }
        });

        //뉴스레터 삭제
        $("button[data-progress=delete]").click(function () {
            let newsLetterSeq = $("#contentNewsLetterSeq").val();
            let newsLetterHeaderSeq = $("#contentNewsLetterHeaderSeq").val();

            if (confirm("삭제하시겠습니까?")) {
                $.ajax({
                    url: "/deleteNewsLetter",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(newsLetterSeq),
                    success: function (response) {
                        if (response.data.status === "SUCCESS") {
                            alert(response.data.message);
                            window.location.href = "/newsletter/list?seq=" + newsLetterHeaderSeq;
                        } else {
                            alert(response.data.message);
                            location.reload();
                        }
                    },
                    error: function (xhr, status, error) {
                        alert(error);
                    }
                });
            }
        });

        //뉴스레터 수정
        $("button[data-progress=update]").click(function () {
            let objParams = {
                newsLetterHeaderSeq: $("#contentNewsLetterHeaderSeq").val(),
                newsLetterSeq: $("#contentNewsLetterSeq").val(),
                title: $("#title").text(),
                content: $("#content").next("p").html(),
                freeYn: $("#freeYn").val(),
                cost: $("#cost").val(),
                description: $("#description").text(),
            }

            window.location.href = "/write?headerSeq=" + objParams.newsLetterHeaderSeq + "&seq=" + objParams.newsLetterSeq;

        });

        $("select[data-progress=filter]").on("change", function () {
            let selectedValue = $(this).val();
            let $nextSelect = $(this).closest("div").next("div").find("select[data-progress=filter]");

            // 다음 셀렉트박스의 모든 옵션을 비활성화 및 숨김 처리
            $nextSelect.find("option").hide().prop("disabled", true);
            // upperCode와 일치하는 옵션만 활성화 및 표시
            if (selectedValue !== "") {
                let matchingOptions = $nextSelect.find("option").filter(function () {
                    return $(this).data("progress") === selectedValue;
                }).show().prop("disabled", false);

                // 일치하는 옵션이 1개인 경우 자동으로 선택
                if (matchingOptions.length === 1) {
                    $nextSelect.val(matchingOptions.val());
                    return;
                } else {
                }

            }
            $nextSelect.prepend('<option value="">전체</option>');
            $nextSelect.val("");

        });

    }

    function validationCheck() {
        let isValid = true;
        $("[data-validation]").each(function () {
            let validationType = $(this).data("validation");
            let value = $(this).val().trim();

            if (validationType === "required") {
                if (value === "") {
                    alert($(this).data("name") + "은(는) 필수값입니다.");
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
        INSERT: {code: 'I', label: '입력'},
        DELETE: {code: 'D', label: '삭제'},
        UPDATE: {code: 'U', label: '수정'}
    };

    function start() {
        $("button[data-progress=saveReply], button[data-progress=deleteReply]").click(function () {
            let objParams = {
                newsLetterSeq: $("#newsLetterSeq").val(),
                replyContent: $("#replyContent").val(),
                saveStatus: $(this).data('code'),
                replySeq: $(this).data('seq'),
            }

            saveComment(objParams);
        });
    }

    // 수정 버튼 클릭 이벤트
    $("button[data-progress=updateReply]").click(function () {
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
        start: start
    }
})()

const Index = (function () {
    function start() {
        $("span[data-name=weekDay]").click(function () {
            let code = $(this).data("progress");

            $.ajax({
                url: "/newsLetterHeader/week",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({code: code}),
                success: function (response) {
                    if (response.data.status === "SUCCESS") {
                        const html = generateNewsletterHTML(response.data.data);
                        $("#newsLetterContainer").html(html);
                    } else {
                        alert(response.data.message);
                    }
                },
                error: function (xhr, status, error) {
                    alert(error);
                }
            });
        });

    }

    function generateNewsletterHTML(newsLetterHeaderList) {
        let html = "";
        newsLetterHeaderList.forEach(item => {
            html += `
                <div class="col-md-6 col-lg-4 col-xl-3">
                    <a href="/newsletter/list?seq=${item.newsLetterHeaderSeq}">
                        <div class="rounded position-relative fruite-item">
                            <div class="fruite-img">
                                <img src="${item.imagePath && item.imagePath.imageUrl ? item.imagePath.imageUrl : '/img/single-item.jpg'}" class="img-fluid w-100 rounded-top">
                            </div>
                            <div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px;">${item.regDate}</div>
                            <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                <h4>${item.headerTitle}</h4>
                                <p>${item.description}</p>
                                <div class="d-flex justify-content-between flex-lg-wrap">
                                    <p class="text-dark fs-5 fw-bold mb-0">${item.cat1Name} > ${item.cat2Name} > ${item.cat3Name}</p>
                                    <a href="/author/${item.authorId}" class="btn border border-secondary rounded-pill px-3 text-primary">
                                        <i class="fa fa-shopping-bag me-2 text-primary"></i>${item.authorName}
                                    </a>
                                </div>
                            </div>
                        </div>
                    </a>
                </div>
            `;
        });
        return html;
    }

    return {
        start: start
    }
})()

const User = (function () {
    Reply.start();

    function start() {
        // 스크랩 or 구독버튼 클릭
        $("button[data-progress=SCRAP], button[data-progress=SUBSCRIBE]").click(function () {
            let objParams = {
                newsLetterSeq: $("#newsLetterSeq").val(),
                newsLetterHeaderSeq: $("#newsLetterHeaderSeq").val(),
                scrapType: $(this).data("type")
            }

            $.ajax({
                url: "/saveSubscriptionOrScrap",
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
        });

        // 스크랩 or 구독취소버튼 클릭
        $("button[data-progress=CANCEL]").click(function () {
            let objParams = {
                newsLetterSeq: $("#newsLetterSeq").val(),
                newsLetterHeaderSeq: $("#newsLetterHeaderSeq").val(),
                scrapType: $(this).data("type")
            }

            $.ajax({
                url: "/cancelScrapOrDescription",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(objParams),
                success: function (response) {
                    if (response.data.status === "SUCCESS") {
                        alert(response.data.message);
                        location.reload();
                    } else {
                        alert(response.data.message);
                        location.reload();
                    }
                },
                error: function (xhr, status, error) {
                    alert(error);
                }
            });
        });
    }

    return {
        start: start
    }
})()