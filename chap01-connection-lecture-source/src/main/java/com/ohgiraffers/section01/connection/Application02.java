package com.ohgiraffers.section01.connection;

import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Application02 {

    public static void main(String[] args) {

        String driver = "com.mysql.ch.jdbc.Driver";
        String url = "jdbc:mysql://localhost/employee";
        String user = "ohgiraffers";
        String password = "ohgiraffers";

        /* index. 1. Connection 객체 선언 */
        Connection con = null;

        /* index. 2. 사용할 드라이버 등록*/
        try {
            Class.forName(driver);

            /* index. 3. 드라이버 매니저로 Connection 인스턴스 생성 */
            con = DriverManager.getConnection(url, user, password);
            System.out.println("con = " + con);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        /* index. 4. 사용한 커넥션 자원 닫기 */
        } finally {
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

}
