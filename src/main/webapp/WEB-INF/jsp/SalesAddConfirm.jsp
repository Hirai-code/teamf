<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ja">

<head>

<meta charset="UTF-8">

<meta name="viewport"
      content="width=device-width, initial-scale=1.0">


<title>
売上登録確認 | Cafe売上管理システム
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

    justify-content:space-between;

    align-items:center;

}




.header-title {

    font-size:22px;

    font-weight:700;

}




.back-link {

    color:white;

    text-decoration:none;

    background:rgba(255,255,255,0.15);

    padding:8px 16px;

    border-radius:20px;

}




.container {

    width:90%;

    max-width:600px;

    margin:50px auto;

}




.form-card {

    background:white;

    padding:40px;

    border-radius:16px;

    box-shadow:
    0 6px 16px rgba(0,0,0,0.05);

}




.form-title {

    font-size:20px;

    font-weight:700;

    color:var(--sb-dark-green);

    border-bottom:
    2px solid var(--sb-accent-green);

    padding-bottom:10px;

    margin-bottom:30px;

}




.confirm-group {

    margin-bottom:20px;

}




.confirm-label {

    font-size:14px;

    font-weight:700;

    color:var(--sb-dark-green);

    margin-bottom:6px;

}




.confirm-value {

    background:#fafafa;

    border:1px solid var(--border-color);

    border-radius:8px;

    padding:12px 16px;

}




.amount-box {

    background:var(--sb-warm-white);

    padding:16px;

    border-radius:8px;

    font-weight:700;

    color:var(--sb-dark-green);

}



.total {

    color:var(--sb-light-green);

    font-size:18px;

}




.btn-container {

    display:flex;

    gap:15px;

    margin-top:35px;

}




.btn {

    flex:1;

    padding:14px;

    border-radius:25px;

    text-align:center;

    text-decoration:none;

    font-weight:700;

    border:none;

    cursor:pointer;

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

    color:#666;

    border:1px solid #ddd;

}




</style>

</head>



<body>



<div class="header">


<div class="header-title">

📝 売上登録確認

</div>


</div>




<div class="container">


<form action="SalesInsertServlet" method="post">



<div class="form-card">



<div class="form-title">

入力内容の確認

</div>




<div class="confirm-group">

<div class="confirm-label">
売上日
</div>

<div class="amount-box">
	${salesDate}
</div>

</div>





<div class="confirm-group">

<div class="confirm-label">
	商品名
</div>

<div class="amount-box">
	${itemName}
</div>

</div>





<div class="confirm-group">

<div class="confirm-label">
	販売時単価
</div>

<div class="amount-box">
<span style="color:#00704a">
	${unitPrice} 
</span>
円
</div>

</div>



<div class="confirm-group">

<div class="confirm-label">
	数量
</div>

<div class="amount-box">
	${quantity}
</div>

</div>



<div class="confirm-group">

<div class="confirm-label">
合計金額
</div>

<div class="amount-box">

<span class="total">
	${totalAmount}
</span>
円
</div>

</div>





<div class="confirm-group">


<div class="confirm-label">

登録者

</div>


<div class="amount-box">
	${loginUser.staffName}
</div>

</div>





<div class="confirm-group">


<div class="confirm-label">

メモ

</div>
<div class="amount-box">
	${memo}
</div>
</div>





<!-- SalesInsertServletへ渡す値 -->

<input type="hidden"
       name="salesDate"
       value="${salesDate}">


<input type="hidden"
       name="itemId"
       value="${itemId}">


<input type="hidden"
       name="quantity"
       value="${quantity}">


<input type="hidden"
       name="memo"
       value="${memo}">





<div class="btn-container">


<a href="SalesAddServlet"
   class="btn btn-secondary">

戻る

</a>



<button type="submit"
        class="btn btn-primary">

登録する

</button>



</div>


</div>


</form>


</div>


</body>


</html>