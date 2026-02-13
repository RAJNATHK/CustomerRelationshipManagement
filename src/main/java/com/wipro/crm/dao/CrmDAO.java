package com.wipro.crm.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.wipro.crm.bean.CrmBean;
import com.wipro.crm.util.DBUtil;
import java.sql.Date;

public class CrmDAO {


    public String createRecord(CrmBean bean) {

        try {
            Connection con = DBUtil.getDBConnection();


            String sql = "INSERT INTO CRM_TB VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, bean.getRecordId());
            ps.setString(2, bean.getCustomerName());
            ps.setString(3, bean.getEmail());
            ps.setString(4, bean.getPhone());
            ps.setDate(5, new java.sql.Date(bean.getJoinDate().getTime()));
            ps.setString(6, bean.getStatus());
            ps.setString(7, bean.getRemarks());

            int rows = ps.executeUpdate();

            ps.close();
            con.close();

            if (rows > 0)
                return bean.getRecordId();
            else
                return "FAIL";

        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
    }

    public CrmBean fetchRecord(String name, java.sql.Date date) {

        CrmBean bean = null;

        try {
            Connection con = DBUtil.getDBConnection();

            String sql =
            "SELECT * FROM CRM_TB WHERE TRIM(CUSTOMERNAME)=? " +
            "AND TO_CHAR(JOIN_DATE,'YYYY-MM-DD')=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name.trim());
            ps.setString(2, date.toString());   // "2026-02-09"

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bean = new CrmBean();
                bean.setRecordId(rs.getString("RECORDID"));
                bean.setCustomerName(rs.getString("CUSTOMERNAME"));
                bean.setEmail(rs.getString("EMAIL"));
                bean.setPhone(rs.getString("PHONE"));
                bean.setJoinDate(rs.getDate("JOIN_DATE"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setRemarks(rs.getString("REMARKS"));
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bean;
    }



    public boolean recordExists(String name, java.sql.Date date) {
        return fetchRecord(name, date) != null;
    }

    public List<CrmBean> fetchAllRecords() {

        List<CrmBean> list = new ArrayList<>();

        try {
            Connection con = DBUtil.getDBConnection();

            System.out.println("===== FETCH ALL EXECUTED =====");
            System.out.println("Connected User: " + con.getMetaData().getUserName());

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM CRM_TB");

            while (rs.next()) {
                CrmBean bean = new CrmBean();
                bean.setRecordId(rs.getString("RECORDID"));
                bean.setCustomerName(rs.getString("CUSTOMERNAME"));
                bean.setEmail(rs.getString("EMAIL"));
                bean.setPhone(rs.getString("PHONE"));
                bean.setJoinDate(rs.getDate("JOIN_DATE"));
                bean.setStatus(rs.getString("STATUS"));
                bean.setRemarks(rs.getString("REMARKS"));
                list.add(bean);
            }

            System.out.println("Records Fetched: " + list.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }



    public String generateRecordID(String name, java.util.Date date) {

        try {
            Connection con = DBUtil.getDBConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT CRM_SEQ.NEXTVAL FROM DUAL");

            rs.next();
            int seq = rs.getInt(1);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String datePart = sdf.format(date);

            String namePart = name.substring(0, 2).toUpperCase();

            rs.close();
            st.close();
            con.close();

            return datePart + namePart + seq;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
