package com.ohgiraffers.section02.template;

import java.sql.Connection;

import static com.ohgiraffers.section02.template.JDBCTemplate.close;
import static com.ohgiraffers.section02.template.JDBCTemplate.getConnection;

public class Application01 {

    public static void main(String[] args) {

        Connection con = getConnection();
        System.out.println("con = " + con);

        Connection con2 = getConnection();
        System.out.println("con2 = " + con2);

        close(con);
    }

}
