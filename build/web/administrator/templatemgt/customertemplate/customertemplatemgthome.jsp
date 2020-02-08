<%-- 
    Document   : customertemplatemgthome
    Created on : Jan 25, 2012, 3:32:23 PM
    Author     : janaka_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  
         <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
       <!--        include content.jsp for all js and css inclusion-->




        <title>CUSTOMER TEMPLATE PAGE</title>
    </head>
    <body>



        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="center">  CUSTOMER TEMPLATE MANAGEMENT </td> </tr></table>

                               
                                            <table>
                                                <tr>
                                                    <td><font color="Red"> ${errorMsg}</font> </td>
                                                    <td><font color="green"> ${successMsg}</font> </td>
                                                    <td></td>

                                                </tr>
                                            </table>
                                            <form method="POST" action="<%=request.getContextPath()%>/SearchCustomerTempalteServlet" name="searchform">        
                                            <table cellspacing="10">

                                                <tbody>
                                                    <tr> <td style="height:20px;"></td></tr>
                                                    <tr>
                                                        <td  style="width: 200px;">Template Category </td>
                                                        <td><input type="text"  readonly="true" value="Customer Template" maxlength="64">
                                                           
                                                        </td>
                                                        <td></td>
                                                    </tr>
                                                    <tr>
                                                        <td style="width: 200px;">Template Name </td>
                                                        <td><input type="text"  name="templateName" value="" maxlength="64"></td>
                                                        <td></td>
                                                    </tr>
                                                    <tr>
                                                        <td style="width: 200px;">Status </td>
                                                        <td>
                                                             <select name="status">
                                                        <option value="">--Select Status-- </option>
                                                        <c:if test="${customerTemplateBean.status != null}">
                                                            <c:forEach var="statusLst" items="${requestScope.statusLst}">
                                                                <c:if test="${customerTemplateBean.status == statusLst.statusCode}">
                                                                    <option value="${statusLst.statusCode}" selected="true">${statusLst.description}</option>
                                                                </c:if>
                                                            </c:forEach>

                                                        </c:if> 
                                                        <c:forEach var="statusLst" items="${requestScope.statusLst}">
                                                            <c:if test="${customerTemplateBean.status != statusLst.statusCode}">
                                                                <option value="${statusLst.statusCode}">${statusLst.description}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>
                                                        </td>
                                                        <td></td>
                                                    </tr>

                                                    <tr> <td style="height:12px;"></td></tr>
                                                    <tr>
                                                        <td style="width: 200px;"></td>
                                                        <td><input type="submit" value="Search" name="search" style="width: 100px" >
                                                            <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"></td>
                                                        <td></td>
                                                    </tr>


                                                </tbody>
                                            </table>
                                        </form>
                                            <br />                        
                                         <table  border="1"  class="display" id="tableview2">
                                            <thead>
                                                <tr>
                                                    <th>Template Category</th>
                                                    <th>Template Code</th>
                                                    <th>Template Name</th>
                                                    <th>Status</th>
                                                    <th>Edit</th>
                                                    <th>Delete</th>
                                                </tr>
                                            </thead>
                                            <tbody>


                                            </tbody>
                                        </table>                           
                                                                    
                                   

                               


                            </div>

                        </div>
                    </div>



                </div>
                <div class="clearer"><span></span></div>
            </div>

        </div>
        <div class="footer"><jsp:include page="/footer.jsp"/></div>
    </body>
</html>

