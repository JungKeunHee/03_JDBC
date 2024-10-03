import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

public class UpdateApplication {

    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;

        Properties prop = new Properties();

        Scanner sc = new Scanner(System.in);
        System.out.print("수정할 메뉴 이름을 입력해주세요 : ");
        String menuName = sc.nextLine();

        System.out.print("수정할 메뉴의 가격을 입력해주세요 : ");
        int menuPrice = sc.nextInt();

        System.out.print("수정할 메뉴의 카테고리 번호를 입력해주세요 : ");
        int categoryCode = sc.nextInt();

        sc.nextLine();
        System.out.print("메뉴의 판매여부를 입력해주세요(Y/N) : ");
        String orderableStatus = sc.nextLine().toUpperCase();

        System.out.print("입력하신 메뉴를 어느 코드에 수정할 건가요? : ");
        int menuCode = sc.nextInt();

        int result = 0;

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/menudb-query.xml"));

            String query = prop.getProperty("updateMenu");

            pstmt = con.prepareStatement(query);

            pstmt.setString(1, menuName);
            pstmt.setInt(2, menuPrice);
            pstmt.setInt(3, categoryCode);
            pstmt.setString(4, orderableStatus);
            pstmt.setInt(5, menuCode);

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
            System.out.println("입력하실 메뉴로 수정되었습니다!!!");
        } else {
            System.out.println("알 수 없는 오류로 수정이 실패되었습니다...");
        }


    }

}
