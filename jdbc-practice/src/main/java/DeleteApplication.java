import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

public class DeleteApplication {

    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;

        Properties prop = new Properties();

        Scanner sc = new Scanner(System.in);
        System.out.print("삭제할 메뉴 코드를 입력해주세요 : ");
        int menuCode = sc.nextInt();

        int result = 0;

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/menudb-query.xml"));

            String query = prop.getProperty("deleteMenu");

            pstmt = con.prepareStatement(query);

            pstmt.setInt(1, menuCode);

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
            System.out.println("해당 메뉴가 삭제되었습니다!!!");
        } else {
            System.out.println("알 수 없는 오류로 실패하였습니다...");
        }

    }

}
