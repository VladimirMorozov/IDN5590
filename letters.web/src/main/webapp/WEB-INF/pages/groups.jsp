<%--
  Created by IntelliJ IDEA.
  User: VOVA
  Date: 27.05.2015
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
View
<form action="/groups/result.sf">
    <div>
        <label>
            from word beginning only
            <input name="fromBeginning" type="checkbox">
        </label>
    </div>

    <div>
        <label>
            case sensitive
            <input name="caseSensitive" type="checkbox">
        </label>
    </div>

    <div>
        <label>
            sort
            <input name="sort" type="checkbox">
        </label>
    </div>

    <div>
        <label>
            use custom alphabet
            <input name="useCustomAlphabet" type="checkbox">
        </label>
    </div>

    <div>
        <label>
            custom alphabet
            <input name="customAlphabet" type="text">
        </label>
    </div>

    <div>
        <label>
            paste text here:
            <textarea name="text"></textarea>
        </label>
    </div>

    <div>
        <button type="submit">Submit</button>
    </div>

</form>


</body>
</html>
