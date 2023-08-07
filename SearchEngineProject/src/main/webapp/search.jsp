<%@page import="java.util.ArrayList"%>
<%@page import="com.Accio.searchResults"%>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
        <h1>Simple Search Engine</h1>
        <form  action="search">
           <input type="text" name="keyword"></input>
           <button type="submit">search</button>
        </form>
        <form  action="History">
           <button type="submit">History</button>
        </form>
        <table border=2 class="resultTable">
            <tr>
                <th>Title</th>
                <th>Link</th>
            </tr>
            <%
            ArrayList<searchResults> results=(ArrayList<searchResults>)request.getAttribute("results");
            for(searchResults result:results){
            %>
            <tr>
                <td><%out.println(result.getTitle());%></td>
                <td><a href="<%out.println(result.getLink());%>"><%out.println(result.getLink());%></a></td>
            </tr>
            <%
                }
            %>
         </table>
    </body>
</html>