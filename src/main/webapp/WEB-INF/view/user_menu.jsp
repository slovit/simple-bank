<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<body>

<h2>All transactions</h2><br />

<c:forEach var="transaction" items="${requestScope.transactions}">
    <ul>
        <li>From account :<c:out value="${transaction.fromAccount}"/></li>

        <li>amount :<c:out value="${transaction.amount}"/></li>

        <li>To account : <c:out value="${transaction.toAccountId}"/></li>

        <li>Time: <c:out value="${transaction.transactionDate}"/></li>

    </ul>
    <hr />

</c:forEach>

<h2>Make transaction</h2><br />

<form method="post" action="<c:url value='/transaction'/>">

    <label><input type="number" name="fromAccount"></label>From account<br>

    <label><input type="number" name="amount"></label>amount<br>

    <label><input type="number" name="toAccount"></label>To account<br>

    <input type="submit" value="Ok" name="Ok"><br>
</form>

</body>
</html>