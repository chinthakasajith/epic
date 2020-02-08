<%-- 
    Document   : terminalallocationdeallocation
    Created on : May 9, 2012, 9:30:54 PM
    Author     : mahesh_m
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
            

            function invokeAssignSearch()
            {

                document.searchterminalform.action="${pageContext.request.contextPath}/SearchAllocationAndDeallocationServlet";
                document.searchterminalform.submit();

            }
            
            function invokeReset()
            {

                window.location = "${pageContext.request.contextPath}/LoadAllocationAndDeallocation";

            }
            function invokeCreate(){
                document.searchterminalform.action="${pageContext.request.contextPath}/LoadCreateTerminaDataServlet";
                document.searchterminalform.submit();
            }
            
            function deallocate(terminalId){
             
                    answer = confirm("Do you really want to Deallocate "+terminalId+" Terminal?")
                   
                    if (answer !=0)
                    {
                        window.location="${pageContext.request.contextPath}/DeAllocateTerminalServlet?terminalId="+terminalId;
                    }
                    else
                    {
//                        window.location = "${pageContext.request.contextPath}/LoadAllocationAndDeallocation";
                    }

                }


    

        </script>
       <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.TRMINAL_ALLOCATION_DEALLOCATION%>'                                  
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
                                        <td colspan="3">
                                            <font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <table cellpadding="0" cellspacing="10">

                                        <tbody>
                                            <tr> <td style="height:20px;"></td></tr>
                                            <tr>
                                                <td style="width: 200px;">Terminal ID(TID)</td>
                                                <td><input type="text"  value="${terminalBean.terminalID}" name="terminalid" maxlength="9"/></td>
                                                <td></td>
                                            </tr>                                          
                                            <tr>
                                                <td style="width: 200px;">Merchant ID</td>
                                                <td><input type="text"  value="${terminalBean.merchantID}" name="merchantid" maxlength="15"/></td>
                                                 <td></td>                                             
                                            </tr>
                                            <tr>
                                                <td style="width: 200px;">Name</td>
                                                <td><input type="text"  value="${terminalBean.name}" name="name" maxlength="15"/></td>
                                                <td></td>
                                            </tr>




                                            <tr>
                                                <td style="width: 200px;">Serial Number</td>
                                                <td><input type="text"  value="${terminalBean.serialNo}" name="serialnumber" maxlength="16"/></td>
                                                <td></td>

                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Model</td>
                                                <td>
                                                    <select name="model">
                                                        <option value="" selected="">--SELECT--</option>
                                                        <c:forEach var="model" items="${modelList}">
                                                            <c:if test="${terminalBean.model==model.key}">
                                                                <option value="${model.key}" selected>${model.value}</option>
                                                            </c:if>
                                                            <c:if test="${terminalBean.model != model.key}">
                                                                <option value="${model.key}" >${model.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                        
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px">Allocation Status</td>
                                                <td>
                                                    <select name="allocatestatus">
                                                        <option value="" selected="">--SELECT--</option>
                                                        <c:forEach var="allocate" items="${allocateList}">
                                                            <c:if test="${terminalBean.allocationStatus==allocate.key}">
                                                                <option value="${allocate.key}" selected>${allocate.value}</option>
                                                            </c:if>
                                                            <c:if test="${terminalBean.allocationStatus != allocate.key}">
                                                                <option value="${allocate.key}" >${allocate.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                        
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width: 200px">Terminal Status</td>
                                                <td>
                                                    <select name="terminalstatus">
                                                        <option value="" selected="">--SELECT--</option>
                                                        <c:forEach var="terminal" items="${terminalStatusList}">
                                                            <c:if test="${terminalBean.terminalStatus==terminal.key}">
                                                                <option value="${terminal.key}" selected>${terminal.value}</option>
                                                            </c:if>
                                                            <c:if test="${terminalBean.terminalStatus != terminal.key}">
                                                                <option value="${terminal.key}" >${terminal.value}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                        
                                                    </select>
                                                </td>
                                            </tr>
                                        

                                    
                                        <tr>
                                            <td></td>
                                            <td colspan="2">
                                            <input type="submit" value="Search" name="search" style="width: 100px" onclick="invokeAssignSearch()"/>
                                            <input type="button" value="Reset" name="reset" style="width: 100px" onclick="invokeReset()"/></td>
                                        </tr>
                                        </tbody>
                                    </table>




                                </form>



                                <%-- -------------------------add form end -------------------------------- --%>



                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr>
                                            <th>Terminal ID(TID)</th>
                                            <th>Merchant ID</th>
                                            <th>Name</th>
                                            <th>Serial Number</th>
                                            <th>Model</th>
                                            <th>Allocation Status</th>
                                            <th>Terminal Status</th>
                                            <th>Allocate</th>
                                            <th>De-Allocate</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="searchedTerminal" items="${searchList}">
                                            <tr>
                                                <td >${searchedTerminal.terminalID}</td>
                                                <td >${searchedTerminal.merchantDes}</td>
                                                <td >${searchedTerminal.name}</td>
                                                <td >${searchedTerminal.serialNo}</td>
                                                <td >${searchedTerminal.model}</td>
                                                <td >${searchedTerminal.alloStatus}</td>
                                                <td >${searchedTerminal.terminalStatusDes}</td>
                                                <td ><a href='${pageContext.request.contextPath}/LoadmerchantSearchServlet?id=<c:out value="${searchedTerminal.terminalID}"></c:out>'/>Allocate</td>
                                                <td ><a href='#' onclick="deallocate('${searchedTerminal.terminalID}')">De Allocate</td>
                                                
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!--   ------------------------- end developer area  --------------------------------    -->
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



