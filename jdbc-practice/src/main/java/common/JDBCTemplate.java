package common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCTemplate {

    // Connection 을 이용하여 데이터베이스와 어플리케이션 간의 연결을 생성하는 메서드
    public static Connection getConnection(){

        // Connection 객체를 null로 초기화하여 오류 방지
        Connection con = null;

        // 데이터베이스 연결 설정을 위한 Properties 객체 생성
        Properties prop = new Properties();

        try {
            // 설정값 파일을 불러오기 위해 FileReader를 사용하여 경로를 설정하고 load 메서드로 파일 내용을 읽어옴
            prop.load(new FileReader("src/main/java/config/config-query.properties"));

            // properties 파일에서 'driver' 키에 해당하는 값(드라이버 클래스 이름)을 가져옴
            String driver = prop.getProperty("driver");

            // 동일하게 'url' 키에 해당하는 값(데이터베이스 URL)을 가져옴
            String url = prop.getProperty("url");

            // 드라이버 정보를 메모리에 로드하기 위해 Class.forName() 사용
            Class.forName(driver);

            // driver 와 url이 설정되어 있다면 DriverManager를 통해 데이터베이스 연결다리를 생성
            con = DriverManager.getConnection(url, prop);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 만들어진 연결다리를 return
        return con;
    }

    // 메모리 자원 낭비하는 것을 방지하기 위해 닫아주기 위한 메소드
    // (Connection, Statement, ResultSet)
    public static void close (Connection con){
        try {
            // con 의 값이 null 값이더라도 con.isClosed 를 통해 통로 닫아주기
            // & 하나는 앞이 거짓이라도 뒤에 조건을 실행시켜주겠다는 의미
            if (con != null & con.isClosed()){
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void close (Statement stmt){
        try {
            if (stmt != null & stmt.isClosed()){
                stmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close (ResultSet rset){
        try {
            if(rset != null & rset.isClosed()){
                rset.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
