import dto.MenuDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

public class Application {

    public static void main(String[] args) {

        // JDBCTemplate 에서 만들어둔 데이터베이스 연결다리 메소드 호출
        Connection con = getConnection();

        // 결과값 오류 방지를 위해 null 값으로 초기화
        // 어플리케이션에서 데이터베이스 쪽에 쿼리 실행을 하기 위한 PreparedStatement(동적) 사용
        PreparedStatement pstmt = null;

        // 데이터베이스에서 명령문 처리된 후 결과값을 어플리케이션쪽에서 확인하려면 변환작업이 필요하기 때문에
        // ResultSet 인터페이스 사용(데이터베이스 결과값을 테이블 형태로 보여줌)
        ResultSet rset = null;

        // 데이터베이스에 명령할 쿼리문 파일을 가져오기 위해 Properties 객체 생성
        Properties prop = new Properties();

        // 가지고 온 데이터 값을 MenuDTO 자료형 타입으로
        // 변환해서 정렬해줄 거기 때문에 List 인터페이스 사용
        List<MenuDTO> menuList = null;

        try {
            // xml 파일을 불러오기 위해선 load가 아닌 loadFromXML 메소드를 실행
            // 해당 파일 자체를 가져와야 하기 때문에 FileReader 가 아닌 InputStream으로 불러와야함
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/menudb-query.xml"));

            // 해당 파일을 불러왔으면 getPropeerty를 통해
            // 쿼리문을 적어둔 키값을 불러오고 그 키 값을 String 변수에 담아줌
            String query = prop.getProperty("selectMenuAll");

            // 주어진 쿼리문을 사용하여 PreparedStatement 객체를 생성한 상태
            // (연결 다리에 올려두어 실해할 준비가 된 상태)
            pstmt = con.prepareStatement(query);

            // 쿼리문을 연결다리에 올려둔 뒤 그 쿼리문을 실행
            // 이 후 결과값을 확인하기 위해 rset 변수에 담아둠
            rset = pstmt.executeQuery();

            // 결과값을 ArrayList 형태로 받아서 여러가지 자료형 타입을 배열 형태로 정렬하기 위해 객체 생성
            menuList = new ArrayList<>();

            // while 반복문을 이용해서 정렬
            // 조건은 rset 의 값이 없을 때까지 반복
            while(rset.next()){
                // 하나씩 결과값이 반복될 때마다 MenuDTO 자료형 타입으로 객체 생성
                MenuDTO menuDTO = new MenuDTO();

                // 가지고 온 테이블 데이터 값을 MenuDTO 필드값에 설정
                // 설정하기 위한 set필드명(rset 이 가지고 있는 데이터중 get자료형(컬럼명))
                menuDTO.setMenuCode(rset.getInt("MENU_CODE"));
                menuDTO.setMenuName(rset.getString("MENU_NAME"));
                menuDTO.setMenuPrice(rset.getInt("MENU_PRICE"));
                menuDTO.setCategoryCode(rset.getInt("CATEGORY_CODE"));
                menuDTO.setOrderableStatus(rset.getString("ORDERABLE_STATUS"));

                // setter 를 이용해 하나의 MenuDTO 자료형 타입으로 정렬된 값을
                // ArrayList 에 추가(add)를 하여 누적으로 저장하면서 순서대로 정렬
                menuList.add(menuDTO);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        // 자원 낭비 방지를 위해 사용을 한 뒤 닫아줌
        } finally {
            close(con);
            close(pstmt);
            close(rset);
        }
        // 향상된 for 문을 사용해서 menuList 에 저장된 객체들을 한 칸씩 내리면서 출력
        for (MenuDTO menuSelect : menuList){
            System.out.println(menuSelect);
        }

    }
}
