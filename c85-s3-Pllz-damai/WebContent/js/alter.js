function $(myid) {
    return document.getElementById(myid);
}
function checkForm(){
    var username=$("username").value;
    var password=$("password").value;
    var repassword=$("repassword").value;
    var email=$("email").value;
    var phone=$("phone").value;
    var cname=$("cname").value;


    var unameResult=checkuname(username);
    var pwdResult=checkpwd(password);
    var repwdResult=checkrepwd(repassword);
    var emailResult=checkemail(email);
    var phoneResult=checkphone(phone);
    var cnameResult=checkecname(cname);

    if (unameResult&& pwdResult&& emailResult&&repwdResult&&phoneResult&&cnameResult){
        return false;
    }
    return true;
}
function checkuname(uname) {
    var reg1=/[A-Za-z0-9_\-\u4e00-\u9fa5]+/
    if(!uname){
        $("unameResult").innerHTML="用户名不能为空";
        $("unameResult").style.display="inline";
        $("unameResult").className="errorCss"
        return false;
    }else if (! reg1.test(uname)){
        $("unameResult").innerHTML="用户名由英文，中文和数字组成";
        $("unameResult").style.display="inline";
        $("unameResult").className="errorCss"
        return false;
    }else{
        $("unameResult").innerHTML="√";
        $("unameResult").style.display="inline";
        $("unameResult").className="rightCss"
        return true;
    }
}

function checkemail(email) {
    var reg1=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
    if(!email){
        $("emailResult").innerHTML="邮箱地址不能为空";
        $("emailResult").style.display="inline";
        $("emailResult").className="errorCss"
        return false;
    } else if( ! reg1.test(email)){
        $("emailResult").innerHTML="邮箱地址不正确";
        $("emailResult").style.display="inline";
        $("emailResult").className="errorCss"
        return false;
    }else{
        $("emailResult").innerHTML="√";
        $("emailResult").style.display="inline";
        $("emailResult").className="rightCss"
        return true;
    }
}

function checkpwd(pwd) {
    var reg1=/^[a-zA-Z]\w{5,17}$/
    if (!pwd){
        $("pwdResult").innerHTML="新密码不能为空";
        $("pwdResult").style.display="inline";
        $("pwdResult").className="errorCss"
    }
    else if( ! reg1.test(pwd)){
        $("pwdResult").innerHTML="密码必须是5-17位的数字字母 下划线组成";
        $("pwdResult").style.display="inline";
        $("pwdResult").className="errorCss"
        return false;
    }else{
        $("pwdResult").innerHTML="√";
        $("pwdResult").style.display="inline";
        $("pwdResult").className="rightCss"
        return true;
    }
}
function checkrepwd(repassword) {
    var password=$("password").value;
    if (!repassword){
        $("repwdResult").innerHTML="确认密码不能为空";
        $("repwdResult").style.display="inline";
        $("repwdResult").className="errorCss"
        return false;
    }else if ((password==repassword)==false){
        $("repwdResult").innerHTML="两次输入密码不一致";
        $("repwdResult").style.display="inline";
        $("repwdResult").className="errorCss"
        return false;
    }else{
        $("repwdResult").innerHTML="√";
        $("repwdResult").style.display="inline";
        $("repwdResult").className="rightCss"
        return true;
    }
}
function checkphone(phone) {
    var reg1=/^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9]|17[0|1|2|3|5|6|7|8|9])\d{8}$/
    if(!phone){
        $("phoneResult").innerHTML="手机号不能为空";
        $("phoneResult").style.display="inline";
        $("phoneResult").className="errorCss"
        return false;
    } else if( ! reg1.test(phone)){
        $("phoneResult").innerHTML="手机号码格式不正确";
        $("phoneResult").style.display="inline";
        $("phoneResult").className="errorCss"
        return false;
    }else{
        $("phoneResult").innerHTML="√";
        $("phoneResult").style.display="inline";
        $("phoneResult").className="rightCss"
        return true;
    }
}
function checkcname(cname) {
    var reg1=/^[\u2E80-\u9FFF]+$/
    if(!cname){
        $("cnameResult").innerHTML="姓名不能为空";
        $("cnameResult").style.display="inline";
        $("cnameResult").className="errorCss"
        return false;
    } else if( ! reg1.test(cname)){
        $("cnameResult").innerHTML="不存在该姓氏";
        $("cnameResult").style.display="inline";
        $("cnameResult").className="errorCss"
        return false;
    }else{
        $("cnameResult").innerHTML="√";
        $("cnameResult").style.display="inline";
        $("cnameResult").className="rightCss"
        return true;
    }
}
/*
跳转到修改界面 将控件转为可写
 */
function f() {

}

//校验用户名是否存在
function checkUserName() {
    // ajax 验证用户名是否存在
}