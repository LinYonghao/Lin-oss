/**
 * 点击获取验证码
 */

var smsTimeOut = 60
$("#sendSMS").click(()=>{

    const mobile = $("#mobile").val()
    if(!mobile){
        return $.error("请输入手机号码")
    }
    if(smsTimeOut !== 60){
        return $.error(`请等待${smsTimeOut}秒后再试!`)
    }

    $.ajax({
        url:"/user/sms/" + mobile,
        method:"get",
        datatype:"json",
        success(res){
            if (res.code === 200){

                const IntervalFlag = setInterval(()=>{
                    smsTimeOut--
                    if(smsTimeOut <= 0){
                        smsTimeOut = 60;
                        clearInterval(IntervalFlag)
                    }
                },1000)
                return $.success("验证码发送成功")

            }else{
                return $.error("验证码发送失败");
            }
        }
    })


})