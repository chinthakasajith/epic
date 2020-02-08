<%-- 
    Document   : numbergenarationhistory
    Created on : Apr 30, 2012, 4:36:39 PM
    Author     : janaka_h
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->
        <script language = "javascript">
            
            
             function invokeStartNumberGena(){
                 
             
              document.confirmform.action="${pageContext.request.contextPath}/StartNumberGenarationServlet";
              document.confirmform.submit();
                
            }
            
            
             function invorkeCancel(){
                 
             window.location = "${pageContext.request.contextPath}/LoadCardNumberGenarationServlet";
                
            }
           
        </script>
    </head>
    <body >
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container" >

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1" >
                     <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                    <!--  ----------------------start  developer area  -----------------------------------                           -->

                    <table class="tit"> <tr> <td   class="center">  NUMBER GENERATION HISTORY </td> </tr><tr> <td>&nbsp;</td> </tr></table>
                    <table>
                        <tr>
                            <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                            <td><b><font color="Green"> ${successMsg}</font></b> </td>
                        </tr>
                    </table>
                     <form method="POST" action="#" name="confirmform" >   
                    <table cellpadding="0" cellspacing="10">
                        <tr>
                            <td> Number of Application ID :
                            </td>
                            <td><b>${numberofIDs}</b>

                            </td>
                        </tr>
                        <tr>
                            <td> Card Type &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                  :
                            </td>
                            <td>
                                <b> ${cardType} </b>
                            </td>
                        </tr>
                        <tr>
                            <td> 
                            </td>
                            <td>

                            </td>
                        </tr>
                        <tr>
                            <td> Select Number Format
                            </td>
                            <td><select name="numberFormate" >
                                    <c:forEach var="formate" items="${numberFormateLst}">

                                        <option value="${formate.formatCode}" >${formate.description}</option>

                                    </c:forEach>

                                </select>

                            </td>
                        </tr>
                        <tr>
                            <td> Select BIN Number
                            </td>
                            <td><select name="binNumber" >
                                    <c:forEach var="binList" items="${binList}">

                                        <option value="${binList.binCode}" >${binList.description}</option>

                                    </c:forEach>

                                </select>

                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="button" value="Confirm" name="confirm" style="width: 100px" onclick="invokeStartNumberGena()">
                                <input type="button" value="Cancel" name="cancel" style="width: 100px" onclick="invorkeCancel()"></td>
                            <td></td>
                        </tr>

                    </table>
                     </form>

                    <!--   ------------------------- end developer area  --------------------------------                      -->

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
