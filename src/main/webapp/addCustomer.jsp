<form action="MainServlet" method="post">

    <input type="hidden" name="operation" value="newRecord">

    Customer Name:
    <input type="text" name="customerName" required><br><br>

    Email:
    <input type="text" name="email" required><br><br>

    Phone:
    <input type="text" name="phone" required><br><br>

    Join Date:
    <input type="date" name="joinDate" required><br><br>

    Status:
    <input type="text" name="status" required><br><br>

    Remarks:
    <input type="text" name="remarks"><br><br>

    <input type="submit" value="Submit">

</form>
