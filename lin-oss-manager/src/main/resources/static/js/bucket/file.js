const bucketId = /\/space\/(\d+)\//.exec(location.pathname).at(1)
var uploader = WebUploader.create({
    pick: '#picker',
    resize: false,
    server: "",
    fileSingleSizeLimit:1024 * 1024 * 1024
});

console.log(uploader)

$("#upload").click(() => {
    const key = $("#key").val()

    $.ajax({
        url: "/space/" + bucketId + '/api/token',
        method: "get",
        datatype: "json",
        success(res) {
            console.log(uploader.options)
            uploader.options['server'] = res.data.base_url + "/upload/direct?key=" + key
            uploader.options.headers = {
                token: res.data.token
            }
            uploader.upload()
        }
    })
})


uploader.on('fileQueued', function (file) {
    $("#key").val(file.name)
});


uploader.on('uploadProgress', function (file, percentage) {

    $percent = $("#upload_progress")
    $percent.css('width', percentage * 100 + '%');
});


uploader.on('uploadSuccess', function (file) {
    $.success("上传成功")
    refreshWindow()
});

// 文件详情显示
$(".file-item").click(function() {
    console.log(this)
    const that = this
    // 获取token
    $.ajax({
        url: "/space/" + bucketId + '/api/token',
        method: "get",
        datatype: "json",
        success(res) {
            const mine = $(that).find(".mine").text()
            const size = $(that).find(".size").text()
            const key = $(that).find(".key").text()

            $("#detailModal .key").text("Key:" +  key)
            $("#detailModal .size").text("文件大小" + size)

            const keys = key.split("/")
            const filename = keys[keys.length - 1]
            $("#detailModal .download-link").attr('href',`${res.data.base_url}/download/${key}?Token=${res.data.token}&attachment=${filename}`)
            $("#detailModal #delete").attr('data-key',`${key}`)

        }
    })
})

// 删除文件

$("#delete").click(function() {
    console.log(this)
    const that = this
    // 获取token
    $.ajax({
        url: "/space/" + bucketId + '/api/token',
        method: "get",
        datatype: "json",
        success(res) {
            const key = $(that).attr("data-key")
            console.log(res.data.base_url + '/delete/' + key + '?Token=' + res.data.token)
            $.ajax({
                url:res.data.base_url + '/delete/' + key + '?Token=' + res.data.token,
                method:'delete',
                datatype:'json',
                success(result){
                    if(result.code === 200){
                        refreshWindow()
                    }
                }
            })
        }
    })
})
// 解决点击webuploader按钮没有效果 F12或缩放浏览器再按正常的情况 原因：模态框没有渲染 不知道宽高
$(`#uploadModal`).on(`shown.bs.modal`,function(e){
    uploader.refresh()
});


function refreshWindow(){
    window.location.reload();
}