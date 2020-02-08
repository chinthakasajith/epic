<%-- 
    Document   : viewfailalerthome
    Created on : Apr 27, 2012, 1:54:33 PM
    Author     : nisansala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <jsp:include page="/content.jsp"/>

        <script language="javascript">  
            function invokeSearch()
            {   
                
                oTable.fnDraw();
                
                if($('#readstatus').val() == '2'){

                    oTable = $('#jsontable').dataTable({
                        "bDestroy" : true,
                        "bServerSide": true,
                        "sAjaxSource": "${pageContext.servletContext.contextPath}/SearchViewFailAlertServlet",
                        "fnServerData": function ( sSource, aoData, fnCallback ) {
                            aoData.push( { "name": "isback", "value":$('#isback').val() } );
                            aoData.push( { "name": "txnid", "value":$('#txnid').val() } );
                            aoData.push( { "name": "fromdate", "value":$('#fromdate').val() } );
                            aoData.push( { "name": "todate", "value":$('#todate').val() } );
                            aoData.push( { "name": "riskCategory", "value":$('#riskCategory').val() } );
                            aoData.push( { "name": "readstatus", "value":$('#readstatus').val() } );                                                        
                                    
                            $.ajax( {
                                "dataType": 'json', 
                                "type": "GET", 
                                "url": "${pageContext.servletContext.contextPath}/SearchViewFailAlertServlet", 
                                "data": aoData, 
                                "success": fnCallback
                            } );
                        },
                        "bProcessing": true,
                        "sPaginationType": "full_numbers",
                        "bJQueryUI": true,
                        "aoColumns": [
                            { "mDataProp": "txnID","bVisible": true },
                            { "mDataProp": "errorInfo","bVisible": true },
                            { "mDataProp": "riskDes","bVisible": true },
                            { "mDataProp": "view","bVisible": false }  
                        ]});
                } 
                else if($('#readstatus').val() == '1'){

                    oTable = $('#jsontable').dataTable({
                        "bDestroy" : true,
                        "bServerSide": true,
                        "sAjaxSource": "${pageContext.servletContext.contextPath}/SearchViewFailAlertServlet",
                        "fnServerData": function ( sSource, aoData, fnCallback ) {
                            aoData.push( { "name": "isback", "value":$('#isback').val() } );
                            aoData.push( { "name": "txnid", "value":$('#txnid').val() } );
                            aoData.push( { "name": "fromdate", "value":$('#fromdate').val() } );
                            aoData.push( { "name": "todate", "value":$('#todate').val() } );
                            aoData.push( { "name": "riskCategory", "value":$('#riskCategory').val() } );
                            aoData.push( { "name": "readstatus", "value":$('#readstatus').val() } );                                                        
                                    
                            $.ajax( {
                                "dataType": 'json', 
                                "type": "GET", 
                                "url": "${pageContext.servletContext.contextPath}/SearchViewFailAlertServlet", 
                                "data": aoData, 
                                "success": fnCallback
                            } );
                        },
                        "bProcessing": true,
                        "sPaginationType": "full_numbers",
                        "bJQueryUI": true,
                        "aoColumns": [
                            { "mDataProp": "txnID","bVisible": true },
                            { "mDataProp": "errorInfo","bVisible": true },
                            { "mDataProp": "riskDes","bVisible": true },
                            { "mDataProp": "view","bVisible": true }                        
                        ]});
                }                 
            } 
            
            var oTable;
            $(document).ready(function() {
                
                if($('#readstatus').val() == '2'){

                    oTable = $('#jsontable').dataTable({
                        "bDestroy" : true,
                        "bServerSide": true,
                        "sAjaxSource": "${pageContext.servletContext.contextPath}/SearchViewFailAlertServlet",
                        "fnServerData": function ( sSource, aoData, fnCallback ) {
                            aoData.push( { "name": "isback", "value":$('#isback').val() } );
                            aoData.push( { "name": "txnid", "value":$('#txnid').val() } );
                            aoData.push( { "name": "fromdate", "value":$('#fromdate').val() } );
                            aoData.push( { "name": "todate", "value":$('#todate').val() } );
                            aoData.push( { "name": "riskCategory", "value":$('#riskCategory').val() } );
                            aoData.push( { "name": "readstatus", "value":$('#readstatus').val() } );                                                        
                                    
                            $.ajax( {
                                "dataType": 'json', 
                                "type": "GET", 
                                "url": "${pageContext.servletContext.contextPath}/SearchViewFailAlertServlet", 
                                "data": aoData, 
                                "success": fnCallback
                            } );
                        },
                        "bProcessing": true,
                        "sPaginationType": "full_numbers",
                        "bJQueryUI": true,
                        "aoColumns": [
                            { "mDataProp": "txnID","bVisible": true },
                            { "mDataProp": "errorInfo","bVisible": true },
                            { "mDataProp": "riskDes","bVisible": true },
                            { "mDataProp": "view","bVisible": false }   
                            
                        ]});
                } 
                else if($('#readstatus').val() == '1'){

                    oTable = $('#jsontable').dataTable({ 
                        "bDestroy" : true,
                        "bServerSide": true,
                        "sAjaxSource": "${pageContext.servletContext.contextPath}/SearchViewFailAlertServlet",
                        "fnServerData": function ( sSource, aoData, fnCallback ) {
                            aoData.push( { "name": "isback", "value":$('#isback').val() } );
                            aoData.push( { "name": "txnid", "value":$('#txnid').val() } );
                            aoData.push( { "name": "fromdate", "value":$('#fromdate').val() } );
                            aoData.push( { "name": "todate", "value":$('#todate').val() } );
                            aoData.push( { "name": "riskCategory", "value":$('#riskCategory').val() } );
                            aoData.push( { "name": "readstatus", "value":$('#readstatus').val() } );                                                        
                                    
                            $.ajax( {
                                "dataType": 'json', 
                                "type": "GET", 
                                "url": "${pageContext.servletContext.contextPath}/SearchViewFailAlertServlet", 
                                "data": aoData, 
                                "success": fnCallback
                            } );
                        },
                        "bProcessing": true,
                        "sPaginationType": "full_numbers",
                        "bJQueryUI": true,
                        "aoColumns": [
                            { "mDataProp": "txnID","bVisible": true },
                            { "mDataProp": "errorInfo","bVisible": true },
                            { "mDataProp": "riskDes","bVisible": true },
                            { "mDataProp": "view","bVisible": true }
                        
                        ]});
                } 
            }); 
            
            
            
              
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadViewFailAlertServlet";

            }
            
            
            function goBack(){
                window.location = "${pageContext.request.contextPath}/SearchViewFailAlertServlet?isBack="+"back";
            }
            
            function invokeView(id,status)
            {                
                document.searchalertform.action="${pageContext.request.contextPath}/ViewViewFailAlertServlet?id="+id+"&status="+status;
                document.searchalertform.submit();

            }
            
            

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.FAILALERT%>'                                  
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



                                <c:if test="${operationtype=='search'}" >
                                    <form method="POST" action="" name="searchalertform">

                                        <table>
                                            <tr>
                                                <td><font color="Red"> ${errorMsg}</font> </td>
                                                <td><font color="green"> ${successMsg}</font> </td>
                                                <td></td>

                                            </tr>
                                        </table>

                                        <table cellpadding="0" cellspacing="10">

                                            <tbody>
                                                <tr>
                                                    <td ></td>
                                                    <td><input type="hidden"  value="${isBack}" name="isback" id ="isback" /></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Transaction ID</td>
                                                    <td><input type="text"  value="${inputBean.txnID}" name="txnid" id ="txnid" maxlength=""/></td>
                                                    <td></td>
                                                </tr>                                          
                                                <tr>
                                                    <td style="width: 200px;">From Date</td>
                                                    <td>
                                                        <input  name="fromdate" maxlength="16" readonly value="${inputBean.fromDate}" key="fromdate" id="fromdate"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#fromdate" ).datepicker({
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
                                                    <td>&nbsp;</td>
                                                    <td></td>                                             
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">To Date</td>
                                                    <td>
                                                        <input  name="todate" maxlength="16" readonly value="${inputBean.toDate}" key="todate" id="todate"  />
                                                        <script type="text/javascript">
                                                            $(function() {
                                                                $( "#todate" ).datepicker({
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
                                                    <td>&nbsp;</td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Risk Category</td>                                                    
                                                    <td>
                                                        <select  name="riskCategory" id="riskCategory">
                                                            <option value="">--SELECT--</option>                                                            
                                                            <c:forEach var="risk" items="${riskCategory}">                                                                
                                                                <c:if test="${risk.key==alertBean.riskCategory}">
                                                                    <option value="${risk.key}" selected="">${risk.value}</option>
                                                                </c:if>
                                                                <c:if test="${risk.key!=alertBean.riskCategory}">
                                                                    <option value="${risk.key}">${risk.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="width: 200px;">Read Status</td>

                                                    <td>
                                                        <select name="readstatus" id="readstatus">
                                                            <option value="" selected="">--SELECT--</option>
                                                            <c:if test="${inputBean.readStatus == 2}">
                                                                <option value="2" selected>Read</option>
                                                            </c:if>
                                                            <c:if test="${inputBean.readStatus != 2}">
                                                                <option value="2" >Read</option>
                                                            </c:if>
                                                            <c:if test="${inputBean.readStatus == 1}">
                                                                <option value="1" selected>Unread</option>
                                                            </c:if>
                                                            <c:if test="${inputBean.readStatus != 1}">
                                                                <option value="1" >Unread</option>
                                                            </c:if>
                                                        </select>
                                                    </td>
                                                </tr>




                                                <tr>
                                                    <td></td>
                                                    <td><input type="button" value="Search" name="search" id="search" style="width: 100px" onclick="invokeSearch()"/>
                                                        <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"/></td>
                                                </tr>
                                            </tbody>
                                        </table>

                                        <table  border="1" class="display" id="jsontable">
                                            <thead>
                                                <tr>
                                                    <th>Transaction ID</th>
                                                    <th>Alert Information</th>
                                                    <th>Risk Category</th>
                                                    <th>Read</th>
                                                </tr>
                                            </thead>
                                            <tbody>


                                            </tbody>
                                        </table>
                                        <!--
                                                                                <table  hidden="" border="1"  class="display" id="jsontable1">
                                                                                    <thead>
                                                                                        <tr>
                                                                                            <th>Transaction ID</th>
                                                                                            <th>Alert Information</th>
                                                                                            <th>Risk Category</th>
                                        
                                                                                        </tr>
                                                                                    </thead>
                                                                                    <tbody>
                                        
                                        
                                                                                    </tbody>
                                                                                </table>-->

                                    </form>

                                </c:if>

                                <%-- -------------------------add form end -------------------------------- --%>


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



