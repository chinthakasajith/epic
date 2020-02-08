<%-- 
    Document   : chequereturnandrealize
    Created on : Jun 7, 2013, 10:54:31 AM
    Author     : badrika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>



<!DOCTYPE html>


<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.BO_PAY_CHEQUE_REALISE%>'                                  
                },
                function(data) {
                    
                    if(data!=''){
                        $('.center').text(data)              
                        var heading = data.split('→');
                        $('.heading').text(heading[1]) ;
                                           
                    }
                                      
                                        
                });
                
            }
            
            function resetcheq(){
                
                $("#cheqNum").val("");
                $("#cheqBank").val("");                
                $("#frmDate").val("");                
                $("#toDate").val("");                
                $("#curType").val("");
                $("#amount").val("");
                $("#status").val("");
                
               // window.location = "${pageContext.request.contextPath}/LoadChequeRealizeServlet";
            }
            
            function processcheq(cheqnum){
                window.location="${pageContext.request.contextPath}/LoadChequeRealizeServlet?cheqnum="+cheqnum+"&param=process";
                
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

                                <br/>
                               
                                <form name="checkform" method="POST" action="${pageContext.request.contextPath}/LoadChequeRealizeServlet?param=search" >
                                    
                                    <table border="0" cellpadding="0" cellspacing="10" >

                                        <tr>
                                            <td style="width: 200px;">Cheque Number </td>

                                            <td ><input type="text" name="cheqNum" id="cheqNum" value="${chBean.cheqNum}" style="width: 168px;" maxlength="32"/> </td>
                                        </tr>
                                        
                                        <tr>
                                            <td style="width: 100px;">Cheque Bank</td>

                                            <td>
                                                <select name="cheqBank" id="cheqBank" class="field" >
                                                    <option value="" selected>--SELECT--</option>

                                                    <c:forEach var="bank" items="${banklist}">                                                    

                                                        <c:if test="${chBean.cheqBank == bank.key}">
                                                            <option value="${bank.key}" selected>${bank.value}</option>
                                                        </c:if>
                                                        <c:if test="${chBean.cheqBank != bank.key}">
                                                            <option value="${bank.key}" >${bank.value}</option>
                                                        </c:if>
                                                    </c:forEach>



                                                </select>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 100px;">Payment Date From</td>
                                                
                                            <td>              
                                                <input  name="frmDate" maxlength="32" readonly value="${chBean.payDateFrom}" key="frmDate" id="frmDate" style="width: 168px;"/>
                                                            
                                                <script type="text/javascript">
                                                    $(function() {
                                                        $( "#frmDate" ).datepicker({
                                                            showOn: "button",
                                                            buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                            changeMonth: true,
                                                            changeYear: true,
                                                            buttonImageOnly: true,
                                                            dateFormat: "yy-mm-dd" ,
                                                            showWeek: true,
                                                            firstDay: 1
                                                        });
                                                    });
                                                </script>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 100px;">Payment Date To</td>
                                                
                                            <td>              
                                                <input  name="toDate" maxlength="32" readonly value="${chBean.payDateTo}" key="toDate" id="toDate" style="width: 168px;"/>
                                                    
                                                <script type="text/javascript">
                                                    $(function() {
                                                        $( "#toDate" ).datepicker({
                                                            showOn: "button",
                                                            buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                            changeMonth: true,
                                                            changeYear: true,
                                                            buttonImageOnly: true,
                                                            dateFormat: "yy-mm-dd" ,
                                                            showWeek: true,
                                                            firstDay: 1
                                                        });
                                                    });
                                                </script>
                                            </td>
                                        </tr>
                                        
                                        <tr>
                                            <td style="width: 100px;">Currency Type</td>
                                            <td>
                                                <select name="curType" id="curType" class="inputfield-mandatory" >
                                                    <option value="" selected>--SELECT--</option>

                                                    <c:forEach var="currencyList" items="${currencyList}">                                                    

                                                        <c:if test="${chBean.curType == currencyList.currencyCode}">
                                                            <option value="${currencyList.currencyCode}" selected>${currencyList.currencyDes}</option>
                                                        </c:if>
                                                        <c:if test="${chBean.curType != currencyList.currencyCode}">
                                                            <option value="${currencyList.currencyCode}" >${currencyList.currencyDes}</option>
                                                        </c:if>
                                                    </c:forEach>



                                                </select>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td style="width: 100px;">Amount</td>
                                            <td><input type="text" name="amount" id="amount" value="${chBean.amount}" style="width: 168px;" maxlength="22" /></td>
                                        </tr>
                                        
                                        <tr>
                                            <td style="width: 100px;">Status</td>
                                            <td>
                                                <select name="status" id="status" class="inputfield-mandatory" >
                                                    <option value="" selected>--SELECT--</option>

                                                    <c:forEach var="status" items="${statusList}">                                                    

                                                        <c:if test="${chBean.status == status.key}">
                                                            <option value="${status.key}" selected>${status.value}</option>
                                                        </c:if>
                                                        <c:if test="${chBean.status != status.key}">
                                                            <option value="${status.key}" >${status.value}</option>
                                                        </c:if>
                                                    </c:forEach>



                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="width: 200px;"> </td>

                                            <td ><input type="submit" name="submit" value="Search" class="defbutton"/>
                                                <input type="button" name="reset" onclick="resetcheq()" value="Reset" class="defbutton"/> </td>
                                        </tr>
                                        

                                    </table>

                                </form>

                                
                                <br/>

                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>Cheque Number</th>
                                                <th>Cheque Bank</th>
                                                <th>Payment Date</th>                                
                                                <th>Currency Type</th>
                                                <th>Amount</th>
                                                <th>Status</th>
                                                
                                                <th>Process</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${sessionScope.SessionObject.chequeList}" var="che">
                                                <tr>
                                                    <td>${che.cheqNum}</td>
                                                    <td>${che.cheqBankName}</td>                                                    
                                                    <td>${che.payDate}</td>
                                                    <td>${che.curTypeDes}</td>
                                                    <td>${che.amount}</td>
                                                    <td>${che.statusDes}</td>
                                                    <c:if test="${che.status == 'CQIN'}">
                                                        <td><a  href='#' onclick="processcheq('${che.cheqNum}')">Process</a></td>
                                                    </c:if>
                                                    <c:if test="${che.status != 'CQIN'}">
                                                        <td>-</td>
                                                    </c:if>
                                                        
                                                </tr>
                                            </c:forEach>
                                            

                                        </tbody>

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
