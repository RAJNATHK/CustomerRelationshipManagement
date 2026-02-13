package com.wipro.crm.service;

import java.sql.Date;
import java.util.List;

import com.wipro.crm.bean.CrmBean;
import com.wipro.crm.dao.CrmDAO;
import com.wipro.crm.util.InvalidInputException;

public class Administrator {

    CrmDAO dao = new CrmDAO();

    public String addRecord(CrmBean bean) {

        try {

            if (bean == null || bean.getCustomerName() == null || bean.getJoinDate() == null)
                throw new InvalidInputException();

            if (bean.getCustomerName().length() < 2)
                return "INVALID CUSTOMER NAME";

            if (!bean.getEmail().contains("@"))
                return "INVALID EMAIL";

            if (bean.getPhone().length() < 10)
                return "INVALID PHONE";

            // Convert util.Date to sql.Date
            Date sqlDate = new Date(bean.getJoinDate().getTime());

            if (dao.recordExists(bean.getCustomerName(), sqlDate))
                return "ALREADY EXISTS";

            String id = dao.generateRecordID(bean.getCustomerName(), bean.getJoinDate());
            bean.setRecordId(id);

            return dao.createRecord(bean);

        } catch (InvalidInputException e) {
            return "INVALID INPUT";
        }
    }

    public CrmBean viewRecord(String name, java.sql.Date date) {
        return dao.fetchRecord(name, date);
    }


    public List<CrmBean> viewAllRecords() {
        return dao.fetchAllRecords();
    }
}
