<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">

<head>

<meta charset="UTF-8">

<meta name="viewport"
      content="width=device-width, initial-scale=1.0">


<title>
登録完了 | Cafe売上管理システム
</title>


<link rel="preconnect" href="https://fonts.googleapis.com">

<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@400;500;700&display=swap"
      rel="stylesheet">



<style>


:root {

    --sb-dark-green:#1e3932;

    --sb-light-green:#00704a;

    --sb-accent-green:#d4e9e2;

    --sb-warm-white:#f2f0eb;

    --text-dark:#212121;

    --text-muted:#6b6b6b;

    --border-color:#ddd;

}



* {

    box-sizing:border-box;

    margin:0;

    padding:0;

}



body {

    font-family:'Noto Sans JP',
                 "Yu Gothic",
                 sans-serif;

    background:var(--sb-warm-white);

    color:var(--text-dark);

}




.header {

    background:var(--sb-dark-green);

    color:white;

    padding:20px 40px;

    display:flex;

    align-items:center;

    justify-content:space-between;

    box-shadow:
    0 4px 12px rgba(0,0,0,0.1);

}



.header-title {

    font-size:22px;

    font-weight:700;

    letter-spacing:0.05em;

}




.container {

    width:90%;

    max-width:500px;

    margin:80px auto;

}




.success-card {

    background:white;

    padding:40px;

    border-radius:16px;

    text-align:center;

    box-shadow:
    0 6px 16px rgba(0,0,0,0.05);

    border:
    1px solid rgba(0,0,0,0.04);

}




.success-icon {

    font-size:48px;

    margin-bottom:20px;

}




.success-title {

    font-size:22px;

    font-weight:700;

    color:var(--sb-dark-green);

    margin-bottom:15px;

}




.success-message {

    color:var(--text-muted);

    font-size:15px;

    line-height:1.8;

    margin-bottom:35px;

}




.btn-container {

    display:flex;

    flex-direction:column;

    gap:14px;

}




.btn {

    padding:14px;

    border-radius:25px;

    text-decoration:none;

    font-size:15px;

    font-weight:700;

    display:block;

    transition:0.2s;

}




.btn-primary {

    background:var(--sb-light-green);

    color:white;

}



.btn-primary:hover {

    background:var(--sb-dark-green);

}




.btn-secondary {

    background:white;

    color:var(--sb-dark-green);

    border:
    1px solid var(--sb-light-green);

}



.btn-secondary:hover {

    background:var(--sb-accent-green);

}



</style>


</head>



<body>



<div class="header">

    <div class="header-title">
        📝 売上登録
    </div>


</div>





<div class="container">


<div class="success-card">


<div class="success-icon">
🎉
</div>



<div class="success-title">

売上登録が完了しました

</div>




<p class="success-message">

売上データが正常に登録されました。<br>

続けて売上を登録する場合は<br>
「続けて登録する」を押してください。

</p>





<div class="btn-container">


<a href="SalesListServlet"
   class="btn btn-primary">

売上一覧画面へ戻る

</a>



<a href="SalesAddServlet"
   class="btn btn-secondary">

続けて登録する

</a>



</div>


</div>


</div>




</body>


</html>