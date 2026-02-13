<%@ page import="com.wipro.crm.bean.CrmBean" %>

<html>
<body>

<%
CrmBean bean = (CrmBean) request.getAttribute("record");  // ✅ FIXED

if(bean == null){
%>

No matching records exists! Please try again!

<%
}else{
%>

Record ID: <%= bean.getRecordId() %><br>
Customer Name: <%= bean.getCustomerName() %><br>
Email: <%= bean.getEmail() %><br>
Phone: <%= bean.getPhone() %><br>
Join Date: <%= bean.getJoinDate() %><br>
Status: <%= bean.getStatus() %><br>
Remarks: <%= bean.getRemarks() %><br>

<%
}
%>

</body>
</html>
