package com.ohgiraffers.section01.insert;

import com.ohgiraffers.model.dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static com.ohgiraffers.common.JDBCTemplate.close;
import static com.ohgiraffers.common.JDBCTemplate.getConnection;

public class Application2 {

    public static void main(String[] args) {

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

        // 서로 다른 자료형 값을 클래스로 뭉쳐서 관리
        MenuDTO newMenu = new MenuDTO();
        newMenu.setMenuName(menuName);
        newMenu.setMenuPrice(menuPrice);
        newMenu.setCategoryCode(menuCategory);
        newMenu.setOrderableStatus(orderableStatus);

        Connection con = getConnection();

        PreparedStatement pstmt = null;

        // 변화를 받은 행의 갯수
        int result = 0;

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/com/ohgiraffers/mapper/menu-query.xml"));

            String query = prop.getProperty("insertMenu");

            pstmt = con.prepareStatement(query);

            pstmt.setString(1, newMenu.getMenuName());
            pstmt.setInt(2, newMenu.getMenuPrice());
            pstmt.setInt(3, newMenu.getCategoryCode());
            pstmt.setString(4, newMenu.getOrderableStatus());

            result = pstmt.executeUpdate();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(con);
            close(pstmt);
        }

        if (result > 0){
            System.out.println("메뉴 등록 성공!!!");
        } else {
            System.out.println("알 수 없는 오류로 인해 등록 실패...");
        }

    }
}
