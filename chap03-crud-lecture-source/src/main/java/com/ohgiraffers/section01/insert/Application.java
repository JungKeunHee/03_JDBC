package com.ohgiraffers.section01.insert;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application {
    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;
        //ResultSet 은 조회, 즉 Select 에서 필요하다
        int result = 0;

        Properties prop = new Properties();


        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));

            String query = prop.getProperty("insertMenu");
            System.out.println("query = " + query);

            Scanner sc = new Scanner(System.in);

            System.out.print("신규 메뉴의 이름을 입력해주세요 : ");
            String menuName = sc.nextLine();

            System.out.print("신규 메뉴의 가격을 입력해주세요 : ");
            int menuPrice = sc.nextInt();

            System.out.print("신규 메뉴의 카테고리 코드를 입력해주세요 : ");
            int menuCategory = sc.nextInt();


            System.out.print("판매여부를 지정해주세요(Y/N) : ");
            sc.nextLine();
            String orderableStatus = sc.nextLine().toUpperCase();

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, menuName);
            pstmt.setInt(2, menuPrice);
            pstmt.setInt(3, menuCategory);
            pstmt.setString(4, orderableStatus);

            /* comment.
            *   executeUpdate()
            *   - 데이터베이스 테이블에 변화가 생기는 만큼
            *   - 정수를 return 해준다
            *  */
            result = pstmt.executeUpdate();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
            close(pstmt);
        }

        System.out.println("result = " + result);
        
        if (result > 0){
            System.out.println("메뉴 등록 성공!!!");
        } else {
            System.out.println("알 수 없는 이유로 메뉴 등록 실패");
        }
        
    }
}
