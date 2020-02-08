<%-- 
    Document   : onlinecardinfobasicveiw
    Created on : Dec 13, 2012, 3:40:23 PM
    Author     : badrika
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script>
            function ViewCard(value){
                document.viewTableForm.action="${pageContext.request.contextPath}/SearchOnlineCardInfoServlet?adview=yes";           
                document.getElementById('id').value=value;    
                document.viewTableForm.submit();
            }
            
            
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.ONLINE_CARD_INFO%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('→');
                        $('.heading').text(heading[1]) ;
                                           
                    }
                                      
                                        
                });
                
            }
                             
        </script> 
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container" >

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main" >
                <jsp:include page="/subheader.jsp"/>



                <div class="content" >

                    <jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1" >
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        </td>
                                    </tr>
                                </table>


                                <form method="POST" action="${pageContext.request.contextPath}/SearchOnlineCardInfoServlet?adview=no">
                                    <table border="0">


                                        <tbody>
                                            <tr>
                                                <td style="width: 100px;">Card Number </td>
                                                <td><font style="color: red;">*</font>&nbsp;</td>
                                                <td><input type="text" maxlength="20" name="cardNum" value="${cardNum}" class="deftext"/></td>
                                            </tr>


                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td></td>

                                            </tr>
                                            <tr style="height: 12px;"></tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td style="width: 300px;"> 
                                                    <input type="submit" value="Search" name="Search" class="defbutton"/>
                                                    <input onclick="" type="reset" value="Reset" class="defbutton"/>
                                                </td>

                                            </tr>

                                        </tbody>
                                    </table>

                                </form>
                                <br/><br/>

                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Card NUmber</th>
                                                <th>Expiry Date</th>
                                                <th>Currency Code</th>
                                                <th>Card Status</th>
                                                <th>Card Domain</th>
                                                <th>Card Type</th>              
                                                <th>View</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:if test="${cardBean.cardNum!= null}">
                                                <tr>
                                                    <td>${cardBean.cardNum}</td>
                                                    <td>${cardBean.expDate}</td>
                                                    <td>${cardBean.currencyCodeDes}</td>
                                                    <td>${cardBean.cardStatusDes}</td>
                                                    <td>${cardBean.cardDomainDes}</td>
                                                    <td>${cardBean.cardTypeDes}</td>
                                                    <td><a  href='#' onclick="ViewCard('${cardBean.cardNum}')">View</a></td>

                                                </tr>
                                            </c:if>
                                        </tbody>
                                        <input type="hidden" id="id"  name="id" maxlength="16" />
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

