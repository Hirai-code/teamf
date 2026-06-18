<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>売上登録 | Cafe売上管理システム</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+JP:wght@400;500;700&display=swap" rel="stylesheet">


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


*{
    box-sizing:border-box;
    margin:0;
    padding:0;
}


body{
    font-family:'Noto Sans JP',"Yu Gothic",sans-serif;
    background:var(--sb-warm-white);
    color:var(--text-dark);
}



.header{

    background:var(--sb-dark-green);
    color:white;

    padding:20px 40px;

    display:flex;
    justify-content:space-between;
    align-items:center;
}


.header-title{

    font-size:22px;
    font-weight:700;

}



.back-link{

    color:white;
    text-decoration:none;

    background:rgba(255,255,255,0.15);

    padding:8px 16px;

    border-radius:20px;

}



.container{

    width:90%;
    max-width:600px;

    margin:50px auto;

}



.form-card{

    background:white;

    padding:40px;

    border-radius:16px;

    box-shadow:0 6px 16px rgba(0,0,0,0.05);

}

.form-title{

    font-size:20px;

    font-weight:700;

    color:var(--sb-dark-green);

    margin-bottom:30px;

}


.form-group{

    display:flex;

    flex-direction:column;

    gap:8px;

    margin-bottom:24px;

}



label{

    font-weight:700;

    color:var(--sb-dark-green);

}



input,
select,
textarea{

    padding:12px 16px;

    border:1px solid var(--border-color);

    border-radius:8px;

    font-size:15px;

}

input:disabled{

    background:#eee;

}

.badge{

    font-size:11px;

    color:white;

    background:#e05e5e;

    padding:2px 6px;

    border-radius:4px;

    margin-left:5px;

}



.optional{

    background:#999;

}



.system{

    background:var(--sb-light-green);

}




.calc-preview{

    background:var(--sb-warm-white);

    padding:15px;

    border-radius:8px;

    font-weight:bold;

}



.btn-container{

    display:flex;

    gap:15px;

    margin-top:35px;

}



.btn{

    flex:1;

    padding:14px;

    border-radius:25px;

    text-align:center;

    text-decoration:none;

    font-weight:bold;

}



.btn-primary{

    background:var(--sb-light-green);

    color:white;

    border:none;

}



.btn-secondary{

    background:white;

    border:1px solid #ddd;

    color:#666;

}


</style>


</head>


<body>


<div class="header">

<div class="header-title">
📝 売上登録
</div>


<a href="SalesListServlet" class="back-link">
← 売上一覧へ戻る
</a>


</div>


<div class="container">


<form action="SalesAddServlet" method="post">



<div class="form-card">


<div class="form-title">
新規売上情報の入力
</div>

<div class="form-group">

<label>
売上日
<span class="badge">必須</span>
</label>


<input type="date"
       name="salesDate"
       value="${salesDate}"
       required>

</div>


<div class="form-group">


<label>
商品名
<span class="badge">必須</span>
</label>


<select name="itemId" required>


<option value="">
選択してください
</option>



<c:forEach var="item" items="${itemList}">


<option value="${item.itemId}"

<c:if test="${itemId == item.itemId}">
selected
</c:if>

>

${item.itemName}

</option>


</c:forEach>


</select>


</div>

<div class="form-group">


<label>
数量
<span class="badge">必須</span>
</label>


<input type="number"
       name="quantity"
       min="1"
       value="${quantity}"
       required>


</div>

<div class="form-group">


<label>
金額計算
</label>


<div class="calc-preview">


販売時単価：
${unitPrice} 円


<br>


合計金額：
<span style="color:#00704a">

${totalAmount}

</span>

円

</div>


</div>

<div class="form-group">


<label>

登録者スタッフ名

<span class="badge system">
ログイン自動設定
</span>


</label>


<input type="text"
       value="${sessionScope.loginUser.staffName}"
       disabled>

</div>


<div class="form-group">


<label>

メモ

<span class="badge optional">
任意
</span>
</label>


<textarea name="memo"
          rows="3">${memo}</textarea>
</div>
<div class="btn-container">
<a href="SalesListServlet"
   class="btn btn-secondary">
キャンセル
</a>
<button type="submit"
        class="btn btn-primary">

確認する

</button>
</div>
</div>
</form>
</div>
</body>

</html>