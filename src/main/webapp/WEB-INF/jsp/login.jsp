<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>研修ポータルmini ログイン</title>

<style>
body{
    margin:0;
    padding:0;
    font-family:"Yu Gothic", sans-serif;
    background:url("images/cafe.jpg") no-repeat center center;
    background-size:cover;
    height:100vh;
    display:flex;
    justify-content:center;
    align-items:center;
    background: #dff3e3;
}

.login-box{
    width:350px;
    background:rgba(255,255,255,0.95);
    padding:40px;
    border-radius:15px;
    box-shadow:0 5px 20px rgba(0,0,0,0.2);
}

h1{
    text-align:center;
    color:#006241;
    margin-bottom:30px;
}

.input-group{
    margin-bottom:20px;
}

.input-group input{
    width:100%;
    padding:12px;
    border:1px solid #ccc;
    border-radius:5px;
    box-sizing:border-box;
    font-size:16px;
}

.login-btn{
    width:100%;
    padding:12px;
    background:#006241;
    color:white;
    border:none;
    border-radius:30px;
    font-size:16px;
    cursor:pointer;
    transition:0.3s;
}

.login-btn:hover{
    background:#004d33;
}
</style>

</head>
<body>

<div class="login-box">
    <h1>Login</h1>

    <form action="login" method="post">

        <div class="input-group">
            <input type="text"
                   name="loginId"
                   placeholder="ログインID"
                   required>
        </div>

        <div class="input-group">
            <input type="password"
                   name="password"
                   placeholder="パスワード"
                   required>
        </div>

        <input type="submit" class="login-btn">
    
</input>

    </form>
</div>

</body>
</html>