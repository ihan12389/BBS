package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//control+shift+o �� ���� �����ϰ� sql ���� �ܺζ��̺귯���� �Ѳ����� ������ �� �ִ�!

public class UserDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/bbs?serverTimezone=UTC"; //�ֱ� mysql �������� serverTimezone�� �������� ������ ��������� �߻��ϰ� ���־��. UTC�� ���������ø� �ǹ��մϴ�. �츮������ ǥ�ؽô� KST�Դϴ�!
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID= ?";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID); //����ǥ�� �ش��ϴ� ���뿡 userID�� �־��ش� ��� �ǹ̿���
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword))
					return 1; //�α��� ������ �ǹ�
				else
					return 0; //��й�ȣ ����ġ
			}
			return -1; //���̵� ���� ��츦 �ǹ�
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -2; //�����ͺ��̽��� ������ ����
	}
	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?,?,?,?,?)";
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -1; //�����ͺ��̽� ����
	}
}
