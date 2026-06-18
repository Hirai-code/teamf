<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>


<h1>アカウント編集</h1>


<c:if test="${not empty errorMessage}">
<p style="color:red;">
${errorMessage}
</p>
</c:if>



<form action="${pageContext.request.contextPath}/AccountUpdateServlet"
      method="post">


<input type="hidden"
       name="accountId"
       value="${account.accountId}">



ログインID

<input type="text"
       name="loginId"
       value="${account.loginId}"
       minlength="4"
       maxlength="50"
       required>


<br>


スタッフ名

<input type="text"
       name="staffName"
       value="${account.staffName}"
       maxlength="50"
       required>


<br>


パスワード

<input type="password"
       name="password"
       minlength="8"
       required>


<br>


ロール

<select name="role">


<option value="MANAGER"
${account.role == 'MANAGER' ? 'selected':''}>
店長
</option>


<option value="STAFF"
${account.role == 'STAFF' ? 'selected':''}>
売上登録専用
</option>


</select>


<br>


<button type="button"
onclick="history.back()">
戻る
</button>


<button type="submit">
保存
</button>


</form>