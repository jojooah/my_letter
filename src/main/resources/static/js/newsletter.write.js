const Write = (function () {
    let formDataArray = [];  // 업로드된 이미지들을 담는 배열
    function start() {

        $('#summernote').summernote({
            placeholder: '뉴스레터를 작성하세요.',
            tabsize: 2,
            height: 600,
            callbacks: {
                onImageUpload: function (files) {
                    for (let i = 0; i < files.length; i++) {
                        imageUploader(files[i], this);
                      //  addToFormDataArray(files[i],this);
                    }
                },
                onMediaDelete : function(files) {
                    let imageSrc = target[0].src;
                    deleteImageFromArray(imageSrc);  // 삭제된 이미지를 formData 배열에서 제거

                }
            }
        });

        $("select[name=freeYn]").on('change', function () {
            let freeYn = $(this).val();
            let costDiv = document.getElementById('costDiv');
            if (freeYn === 'N') {
                costDiv.style.visibility = 'visible';
            } else {
                costDiv.style.visibility = 'hidden';
            }
        });

        $("input[name=cost]").on('keypress', function (e) {
            let charCode = e.which ? e.which : e.keyCode;

            // 정규 표현식: 숫자만 허용 (48-57 => '0'-'9')
            if (!/^[0-9]$/.test(String.fromCharCode(charCode))) {
                e.preventDefault();
            }
        });

        $("button[name=saveNewsLetter]").click(function () {
            let formData = new FormData();

            let objParams = {
                content: $("#summernote").summernote("code") === '<p><br></p>' ? '' : $("#summernote").summernote("code"),
                title: $("input[name=title]").val(),
                newsLetterHeaderSeq: $("input[name=newsLetterHeaderSeq]").val(),
                newsLetterSeq:$("input[name=newsLetterSeq]").val(),
                freeYn: $("select[name=freeYn]").val(),
                cost: $("input[name=cost]").val(),
                description :  $("input[name=description]").val(),
            }

            if (objParams.title === null || objParams.title.trim() === "") {
                alert("뉴스레터 제목을 입력하세요.");
                return;
            }

            if (objParams.content === null || objParams.content.trim() === "") {
                alert("뉴스레터 내용을 입력하세요.");
                return;
            }

            formData.append("newsLetter", new Blob([JSON.stringify(objParams)], { type: "application/json" }));

            let thumbnail = $('input[name=thumbnail]')[0].files[0];
            if (thumbnail) {
                formData.append("thumbnail", thumbnail);
            }

            if (confirm("저장하시겠습니까?")) {
                $.ajax({
                    url: "/saveNewsLetter",
                    type: "POST",
                    processData: false,
                    contentType: false,
                    data: formData,
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

            }
        })
    }

    function imageUploader(file, el) {
        var formData = new FormData();
        formData.append('file', file);

        $.ajax({
            data : formData,
            type : "POST",
            url : '/newsletter/image-upload',
            contentType : false,
            processData : false,
            enctype : 'multipart/form-data',
            success : function(result) {
                $(el).summernote('insertImage', result.data.data.imageUrl, function($image) {
                    $image.css('width', "100%");
                });
            }
        });
    }

    return {
        start: start
    }

})()



