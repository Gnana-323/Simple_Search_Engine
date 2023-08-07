import com.Accio.DatabaseConnection;
import com.Accio.searchResults;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@WebServlet("/search")
public class search extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
       //getting keyword from frontend
        String keyword = request.getParameter("keyword");
        //setting up connection to database
        Connection connection = DatabaseConnection.getConnection();
        try {
            //store the query of user
            PreparedStatement preparedStatement=connection.prepareStatement("Insert into history values(?,?);");
            preparedStatement.setString(1,keyword);
            preparedStatement.setString(2,"http://localhost:8080/SearchEngineProject/search?keyword="+keyword);
            preparedStatement.executeUpdate();

            //getting results after running the ranking query
            ResultSet resultSet = connection.createStatement().executeQuery("select pageTitle,pageLink,(length(lower(pageText))-length(replace(lower(pageText),'" + keyword.toLowerCase() + "','')))/length('" + keyword.toLowerCase() + "') as countoccurences from pages order by countoccurences desc limit 30;");
            ArrayList<searchResults> results = new ArrayList<searchResults>();
            //transferring values from resultSet yo result arrayList
            while (resultSet.next()) {
                searchResults searchresults = new searchResults();
                searchresults.setTitle(resultSet.getString("pageTitle"));
                searchresults.setLink((resultSet.getString("pageLink")));
                results.add(searchresults);
            }
            //Displaying results in the console
            for(searchResults result:results){
                System.out.println(result.getTitle()+"/n"+result.getLink()+"/n");
            }
            request.setAttribute("results",results);
            request.getRequestDispatcher("search.jsp").forward(request,response);

            // System.out.println(keyword);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            // out.println("<h3>This is the Keyword you have entered "+keyword+"</h3>");
        }
        catch (SQLException | ServletException sqlException){
            sqlException.printStackTrace();
        }
    }
}
