
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ManufacturersServlet
 */
@SuppressWarnings("unused")
@WebServlet("/ManufacturersServlet")
public class ManufacturersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManufacturersServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		//文字セット
		request.setCharacterEncoding("utf-8");

		String sql1;
		String sql2;
		RequestDispatcher disp;

		//html
		String strmanufacturers_name = request.getParameter("manufacturers_name");
		String strmanufacturers_address = request.getParameter("address");
		String strmanufacturers_tel = request.getParameter("tel");

		//データベース処理

		String mes = "";
		String page = "/List.jsp";

		//URLセット

		String url = "jdbc:mysql://localhost:3306/test?characterEncording=utf8";
		Connection con = null;
		Statement st = null;

		//Class.forName

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("err1");

			mes = "classfornameでエラー";
			request.setAttribute("mes", mes);

			disp = request.getRequestDispatcher("/err.jsp");
			disp.forward(request, response);
			return;
		}

		try {
			con = DriverManager.getConnection(url, "root", "");
			st = con.createStatement();


		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("err2");

			mes = "DriverManager,stでエラー";
			request.setAttribute("mes", mes);
			disp = request.getRequestDispatcher("/err.jsp");
			disp.forward(request, response);
			return;

		}

		//SQL文作成

		sql1 = "INSERT INTO manufacturers(manufacturers_name,manufacturers_address,tel) VALUES('" + strmanufacturers_name
				+ "','" + strmanufacturers_address + "','" + strmanufacturers_tel + "');";

		try {

			con.prepareStatement(sql1);
			System.out.println(sql1);
			System.out.println("sql1 OK");

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("sql1 err");
		}



		sql2 = "SELECT * FROM manufacturers;";
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();

		try {
			st.executeQuery(sql2);
			System.out.println(sql2);
			System.out.println("sql2 OK");

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("sql2 err");
		}



		ResultSet rs;
		try {
			rs = st.executeQuery(sql2);

			while (rs.next()) {
				ArrayList<String> rec = new ArrayList<String>();


				rec.add(rs.getString("manufacturers_name"));
				rec.add(rs.getString("manufacturers_address"));
				rec.add(rs.getString("tel"));
				table.add(rec);


			}
			System.out.println("resultset OK");


		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			System.out.println("resultset err");
		}

		request.setAttribute("TABLE", table);
		disp = request.getRequestDispatcher(page);
		disp.forward(request, response);



		//SQL実行








	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
