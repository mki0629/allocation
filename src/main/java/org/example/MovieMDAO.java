package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieMDAO {
	private static final String URL = "jdbc:mariadb://192.168.100.232/minor";
	private static final String USER = "user1";
	private static final String PASS = "";
	private static final String SQL = "select * from movies;";
	public static int m = 0;

	public List<MovieDTO> findAll() {
		List<MovieDTO> dtoList = new ArrayList<>();

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement ps = conn.prepareStatement(SQL)) {

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					m = m + 1;
					MovieDTO sd = new MovieDTO();
					sd.setUrl(rs.getString("url"));
					sd.setCom(rs.getString("com"));
					sd.setT(rs.getString("t"));
					sd.setG(rs.getString("g"));
					sd.setN(rs.getString("n"));
					dtoList.add(sd);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
		return dtoList;
	}
}