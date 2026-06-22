<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>研修ポータルmini ログイン</title>

<style>
:root{
    --sb-dark-green:#1e3932;
    --sb-light-green:#00704a;
    --sb-accent-green:#d4e9e2;
    --sb-warm-white:#f2f0eb;
    --text-dark:#212121;
    --text-muted:#6b6b6b;
}

*{
    margin:0;
    padding:0;
    box-sizing:border-box;
}

body{
    font-family:'Noto Sans JP',"Yu Gothic",sans-serif;

    background:
        linear-gradient(
            rgba(242,240,235,0.85),
            rgba(242,240,235,0.85)
        ),
        url("${pageContext.request.contextPath}/images/coffee-bg.png");

    background-size:cover;
    background-position:center;
    background-repeat:no-repeat;
    background-attachment:fixed;

    min-height:100vh;
}

.header{
    background-color:var(--sb-dark-green);
    color:white;
    padding:24px 40px;
    box-shadow:0 4px 12px rgba(0,0,0,0.1);
}

.header-title{
    font-size:24px;
    font-weight:700;
    letter-spacing:0.05em;
    display:flex;
    align-items:center;
    gap:12px;
}

.header-icon{
    width:60px;
    height:60px;
    object-fit:contain;
}

.login-container{
    min-height:calc(100vh - 88px);
    display:flex;
    justify-content:center;
    align-items:center;
}

.login-box{
    width:420px;
    background:rgba(255,255,255,0.95);
    padding:45px;
    border-radius:16px;
    box-shadow:0 8px 20px rgba(0,0,0,0.12);
    border:1px solid rgba(0,0,0,0.05);
}

.login-logo{
    text-align:center;
    color:var(--sb-dark-green);
    font-size:24px;
    font-weight:700;
    margin-bottom:25px;
    letter-spacing:0.05em;
}

h1{
    text-align:center;
    color:var(--sb-dark-green);
    margin-bottom:10px;
    font-size:28px;
}


.sub-title{
    text-align:center;
    color:var(--text-muted);
    margin-bottom:30px;
    font-size:14px;
}

.input-group{
    margin-bottom:20px;
}

.input-group input{
    width:100%;
    padding:14px;
    border:1px solid #d0d0d0;
    border-radius:8px;
    font-size:15px;
    transition:.3s;
}

.input-group input:focus{
    outline:none;
    border-color:var(--sb-light-green);
    box-shadow:0 0 0 3px rgba(0,112,74,0.15);
}

.login-btn{
    width:100%;
    padding:14px;
    background:var(--sb-light-green);
    color:white;
    border:none;
    border-radius:30px;
    font-size:16px;
    font-weight:bold;
    cursor:pointer;
    transition:.3s;
}

.login-btn:hover{
    background:var(--sb-dark-green);
    transform:translateY(-2px);
}

.error-message{
    background:#ffe6e6;
    color:#d8000c;
    border:1px solid #d8000c;
    padding:10px;
    margin-bottom:20px;
    border-radius:8px;
    text-align:center;
    font-size:14px;
}
</style>

</head>
<body>

<div class="header">
    <div class="header-title">
        <div class="header-title">
    <img src="${pageContext.request.contextPath}/images/dogicon.png"
         alt="Dog Icon"
         class="header-icon">
    Shoma-Cafe Backroom System
</div>
        
    </div>
</div>

<div class="login-container">

<div class="login-box">

    <h1>Login</h1>
    
   <c:if test="${not empty errorMessage}">
    <div class="error-message">
        ${errorMessage}
    </div>
</c:if>
   
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

        <input type="submit" class="login-btn" value="ログイン">
    
    </form>
</div>
</div>

</body>
</html>