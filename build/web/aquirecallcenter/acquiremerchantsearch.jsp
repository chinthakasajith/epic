<%-- 
    Document   : acquiremerchantsearch
    Created on : Dec 11, 2012, 1:29:40 PM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">
            

            function invokeAssignSearch(id)
            {

                document.searchterminalform.action="${pageContext.request.contextPath}/SearchCallCenterMerchantSearchServlet?id="+id;
                document.searchterminalform.submit();

            }
            
            function invokeBack(section,terminalId)
            {
                window.location = "${pageContext.request.contextPath}/ViewMerchantMgtServlet?section="+section+"&id="+terminalId;
            
            }
            
            function invokeReset(id)
            {

                window.location = "${pageContext.request.contextPath}/LoadCallCenterMerchantSearchServlet?id="+id;

            }
            //            function Allocate(merchantId,terminalId){
            //                document.searchterminalform.action="${pageContext.request.contextPath}/AllocateTerminalsServlet?merchantId="+merchantId+"&terminalId="+terminalId;
            //                document.searchterminalform.submit();
            //            }
            
            function Assign(merchantId,terminalId)
            {
                answer = confirm("Are you sure to allocate this terminal ?")
                if (answer !=0)
                {
                    document.searchterminalform.action="${pageContext.request.contextPath}/AddDataToCallcenterAllocateTerminalServlet?merchantId="+merchantId+"&terminalId="+terminalId;
                    document.searchterminalform.submit();
                }
                else
                {
                    window.location = "${pageContext.request.contextPath}/LoadmerchantSearchServlet?id="+terminalId;
                }
               
            }
            
          

    

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CALLCENTER_TERMINAL_ALLOCATION%>'                                  
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
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>

        <div class="container">

            <div class="header">             

            </div>


            <div class="main">

                <jsp:include page="/subheader.jsp"/>
                <div class="content" style="height: 500px">
                    <jsp:include page="/leftmenu.jsp"/>
                </div>
                <div id="content1">
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>




                                <form method="POST" action="" name="searchterminalform">

                                    <table>
                                        <tr>
                                            <td><font color="Red"> ${errorMsg}</font> </td>
                                            <td><font color="green"> ${successMsg}</font> </td>
                                            <td></td>

                                        </tr>
                                    </table>

                                    <table cellpadding="0" cellspacing="10">

                                        <tbody>
                                            <tr> <td style="height:20px;"></td></tr>
                                            <tr>
                                                <td style="width: 200px;">Terminal ID(TID)</td>
                                                <td> <b><font color="#000000"> ${terminalId}</font></b></td>
                                                <td></td>
                                            </tr>                                          
                                            <tr>
                                                <td style="width: 200px;">Merchant Name</td>
                                                <td><input type="text"  value="${searchBean.merchantname}" name="merchantName" maxlength="15"/></td>
                                                <td></td>                                             
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Merchant ID</td>
                                                <td><input type="text"  value="${searchBean.merchantId}" name="merchantId" maxlength="15"/></td>
                                                <td></td>
                                            </tr>




                                            <tr>
                                                <td style="width: 200px;">Merchant Location</td>
                                                <td><input type="text"  value="${searchBean.merchantLocation}" name="merchantLocation" maxlength="16"/></td>
                                                <td></td>

                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Merchant Status</td>
                                                <td>
                                                    <select name="merchantStatus">
                                                        <option value="" selected="">--SELECT--</option>
                                                        <c:forEach var="terminal" items="${terminalStatusList}">
                                                            <c:if test="${searchBean.merchantStatus==terminal.key}">
                                                                <option value="${terminal.key}" selected>${terminal.value}</option>
                                                            </c:if>
                                                            <c:if test="${searchBean.merchantStatus != terminal.key}">
                                                                <option value="${terminal.key}" >${terminal.value}</option>
                                                            </c:if>
                                                        </c:forEach>

                                                    </select>
                                                </td>
                                            </tr>


                                            <tr>
                                                <td></td>
                                                <td colspan="2">
                                                    <input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeAssignSearch('${terminalId}')"/>
                                                    <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset('${terminalId}')"/>
                                                    <input type="button" value="Back" name="back" style="width: 100px" onclick="invokeBack('${'ACCTER'}','${terminalId}')"/></td>
                                            </tr>
                                        </tbody>
                                    </table>




                                </form>



                                <%-- -------------------------add form end -------------------------------- --%>



                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Merchant ID</th>
                                            <th>Merchant Name</th>
                                            <th>Merchant Location</th>
                                            <th>Merchant Status</th>

                                            <th>Assign</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="searchedMerchant" items="${searchList}">
                                            <tr>
                                                <td >${searchedMerchant.merchantId}</td>
                                                <td >${searchedMerchant.merchantname}</td>
                                                <td >${searchedMerchant.merchantLocation}</td>
                                                <td >${searchedMerchant.merchantStatusDes}</td>

                                                <td>
                                                    <input type="button" name="pinGenarate" value="Assign" onclick="Assign('${searchedMerchant.merchantId}','${terminalId}')"/>
                                                </td>

                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
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
