<%-- 
    Document   : merchantsearch
    Created on : Jul 17, 2012, 10:10:14 AM
    Author     : janaka_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Merchant Search</title>

        <script type="text/javascript">
            function reset(){
             
                $("#merchant").val("");
                $("#mid").val("");
                $("#tid").val("");                
                $("#passport").val("");
                $("#licence").val("");
                
                
                //                window.location = "${pageContext.request.contextPath}/LoadCallCenterMgtServlet";
                //                alert("hhgfhgh");
            }
        </script>
    </head>
    <body>
        <form name="form1" method="post" action="${pageContext.request.contextPath}/LoadAcquireQuestionVerifyPageServlet">
            <!--<table > <tr> <td   class="center">  CALL CENTER SEARCH </td> </tr><tr> <td>&nbsp;</td> </tr></table>-->
            <table>
                <tr>
                    <td style="color: #FFFFFF">Customer ID</td>
                    <td style="color: #FFFFFF"><input type="text" name="customerid" class="textField" id="customerid" value="" maxlength="30"/></td>
                </tr>
                <tr>
                    <td style="color: #FFFFFF">Account No</td>
                    <td style="color: #FFFFFF"><input type="text" name="account" class="textField" id="cusAcc" value="" maxlength="30"/></td>
                </tr>
                <tr>
                    <td style="color: #FFFFFF">MID</td>
                    <td style="color: #FFFFFF"><input type="text" name="mid" class="textField" id="mid" value="" maxlength="20"/></td>
                </tr>
                <tr>
                    <td style="color: #FFFFFF">TID</td>
                    <td style="color: #FFFFFF"><input type="text" name="tid" class="textField" id="tid" value="" maxlength="20"/></td>
                </tr>


                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <!--                        <td colspan="2"><center><input type="submit" name="" style="width:100px;" value="Search"></center></td>-->
                    <td><center><input type="submit" name="" style="width:100px;" value="Search" /></center></td>
                <td><center><input type="button" name="" style="width:100px;" value="Clear" onclick="reset()"/></center></td>

                </tr>
            </table>

        </form>
    </body>
</html>
