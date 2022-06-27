const bucketId = /\/space\/(\d+)\//.exec(location.pathname).at(1)
var uploader = WebUploader.create({
    pick: '#picker',
    resize: false,
    server: ""
});

console.log(uploader)

$("#upload").click(()=>{
    const key = $("#key").val()

    $.ajax({
        url:"/space/" + bucketId + '/api/token',
        method:"get",
        datatype:"json",
        success(res){
            console.log(uploader.options)
            uploader.options['server'] = res.data.base_url + "/upload/direct?key=" +key
            uploader.options.headers = {
                token:res.data.token
            }
            uploader.upload()
        }
    })
})


uploader.on( 'fileQueued', function( file ) {
    $("#key").val(file.name)
});




uploader.on( 'uploadProgress', function( file, percentage ) {

    $percent = $("#upload_progress")
    $percent.css( 'width', percentage * 100 + '%' );
});


uploader.on( 'uploadSuccess', function( file ) {
    $.success("上传成功")
});