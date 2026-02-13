<%@ page import="java.util.List" %>
<%@ page import="com.wipro.crm.bean.CrmBean" %>

<%
List<CrmBean> list = (List<CrmBean>) request.getAttribute("list");

if(list == null || list.isEmpty()) {
%>
No records available!
<%
} else {
%>
<table border="1">
<tr>
<th>Record ID</th>
<th>Customer Name</th>
<th>Email</th>
<th>Phone</th>
<th>Join Date</th>
<th>Status</th>
<th>Remarks</th>
</tr>

<%
for(CrmBean bean : list) {
%>
<tr>
<td><%= bean.getRecordId() %></td>
<td><%= bean.getCustomerName() %></td>
<td><%= bean.getEmail() %></td>
<td><%= bean.getPhone() %></td>
<td><%= bean.getJoinDate() %></td>
<td><%= bean.getStatus() %></td>
<td><%= bean.getRemarks() %></td>
</tr>
<%
}
%>
</table>
<%
}
%>
