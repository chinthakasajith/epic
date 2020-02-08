<%-- 
    Document   : aquirebinmgt
    Created on : May 31, 2012, 12:25:29 PM
    Author     : badrika
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page  import="com.epic.cms.system.util.variable.PageVarList" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->

        <script type="text/javascript">
            
            function updateview(binNumber)
            {
                window.location ="${pageContext.request.contextPath}/UpdateAquireBinManagementFormServlet?aquireBinNumber="+binNumber;
            }
            
            function invokeUpdate()
            {
                document.updateaquirebinform.action="${pageContext.request.contextPath}/UpdateAquireBinManagementServlet";
                document.updateaquirebinform.submit();
            }
            function invokeAdd()
            {
                document.addaquirebinform.action="${pageContext.request.contextPath}/AddAquireBinManagementServlet";
                document.addaquirebinform.submit();
            }           
                        
            function invokeReset()
            {
                window.location = "${pageContext.request.contextPath}/LoadAquireBinManagement";
            }
            
            function ConfirmDelete(binNumber)
            {
                answer = confirm("Do you really want to delete " + binNumber +" BIN Number?")
                if (answer !=0)
                {
                    window.location ="${pageContext.request.contextPath}/DeleteAquireBinManagementServlet?aquireBinNumber="+binNumber;
                }
                else
                {
                    window.location ="${pageContext.request.contextPath}/LoadAquireBinManagement";
                }
                
            }
               
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.AQUIREBIN%>'                                  
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

                    <td class="menubar"><jsp:include page="/leftmenu.jsp"/></td>

                </div>


                <div id="content1" >
                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">
                                <%--  --------------   Start developer area    -----------------  --%>

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        </td>
                                    </tr>
                                </table>

                                <%--  --------------   Start add form    ------------------------  --%>

                                <c:if test="${operationtype=='add'}" >

                                    <form action="" method="POST" name="addaquirebinform">
                                        <table border="0">

                                            <tbody>
                                                <tr> <td style="height:10px;"></td></tr>
                                                <tr>
                                                    <td>BIN Number</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text"  value="${aqbean.binNumber}" name="binnumber" maxlength="6"/></td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <c:if test="${aqbean.ownership=='1'}">
                                                            <input type="radio" name="ownership" value="1" checked="true"/> On Us
                                                        </c:if>

                                                        <c:if test="${aqbean.ownership!='1'}">
                                                            <input type="radio" name="ownership" value="1" /> On Us
                                                        </c:if>

                                                        <c:if test="${aqbean.ownership=='2'}">
                                                            <input type="radio" name="ownership" value="2" checked="true"/> Off Us
                                                        </c:if>

                                                        <c:if test="${aqbean.ownership!='2'}">
                                                            <input type="radio" name="ownership" value="2" /> Off Us
                                                        </c:if>

                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

<!--                                                <tr>
                                                    <td>Pay Type</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <c:if test="${aqbean.payType=='1'}">
                                                            <input type="radio" name="paytype" value="1" checked="true"/> CREDIT 
                                                        </c:if>

                                                        <c:if test="${aqbean.payType!='1'}">
                                                            <input type="radio" name="paytype" value="1" /> CREDIT 
                                                        </c:if>

                                                        <c:if test="${aqbean.payType=='2'}">
                                                            <input type="radio" name="paytype" value="2" checked="true"/> DEBIT
                                                        </c:if>

                                                        <c:if test="${aqbean.payType!='2'}">
                                                            <input type="radio" name="paytype" value="2" /> DEBIT
                                                        </c:if>

                                                    </td>
                                                    <td></td>
                                                </tr>-->

                                                <tr><td style="height: 5px"></td></tr>                                                

                                                <tr>
                                                    <td>Sending Channel </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="sendchanel">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="send" items="${sessionScope.SessionObject.aquireChannelList}">
                                                                <c:if test="${aqbean.sendChanel==send.channelID}">
                                                                    <option value="${send.channelID}" selected>${send.channelName}</option>
                                                                </c:if>
                                                                <c:if test="${aqbean.sendChanel!=send.channelID}">
                                                                    <option value="${send.channelID}" >${send.channelName}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

<!--                                                <tr>
                                                    <td>Receiving Channel </td>
<td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="receivechanel">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="receive" items="${sessionScope.SessionObject.aquireChannelList}">
                                                                <c:if test="${aqbean.receiveChanel==receive.channelID}">
                                                                    <option value="${receive.channelID}" selected>${receive.channelName}</option>
                                                                </c:if>
                                                                <c:if test="${aqbean.receiveChanel!=receive.channelID}">
                                                                    <option value="${receive.channelID}" >${receive.channelName}</option>
                                                                </c:if>
                                                            </c:forEach>


                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>-->


                                                <tr>
                                                    <td>Card Key </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="cardKey">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="cType" items="${sessionScope.SessionObject.cardKeyList}">
                                                                <c:if test="${aqbean.cardKey==cType.key}">
                                                                    <option value="${cType.key}" selected>${cType.value}</option>
                                                                </c:if>

                                                                <c:if test="${aqbean.cardKey!=cType.key}">
                                                                    <option value="${cType.key}" >${cType.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

<!--                                                <tr>
                                                    <td>Entry Mode</td>
<td><font style="color: red;">*</font></td>
                                                    <td><select  name="entrymode" >
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="entry" items="${sessionScope.SessionObject.aquireEntryModeList}">
                                                                <c:if test="${aqbean.entryMode==entry.entryMode}">
                                                                    <option value="${entry.entryMode}" selected>${entry.entryModeDes}</option>
                                                                </c:if>

                                                                <c:if test="${aqbean.entryMode!=entry.entryMode}">
                                                                    <option value="${entry.entryMode}" >${entry.entryModeDes}</option>
                                                                </c:if>
                                                            </c:forEach>



                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>-->

<!--                                                <tr><td style="height: 5px"></td></tr>-->

                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select  name="status"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.aquireStatusList}">

                                                                <c:if test="${aqbean.status==status.status}">
                                                                    <option value="${status.status}" selected>${status.statusDes}</option>
                                                                </c:if>
                                                                <c:if test="${aqbean.status!=status.status}">
                                                                    <option value="${status.status}">${status.statusDes}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>


                                                    </td>
                                                    <td></td>
                                                </tr>




                                                <tr> <td style="height:12px;"></td></tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px">
                                                        <input type="submit" value="Add" name="add" onclick="invokeAdd()" class="defbutton">
                                                        <input type="button" value="Reset" name="reset" onclick="invokeReset()" class="defbutton">
                                                    </td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.AQUIREBIN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                    
                                                </tr>
                                                <tr><td style="height: 10px"></td></tr>

                                            </tbody>
                                        </table>
                                    </form>  
                                </c:if>

                                <%--  --------------   End add form    ------------------------    --%>

                                <%--  --------------   Start update form    ------------------------  --%>

                                <c:if test="${operationtype=='update'}" >

                                    <form action="" method="POST" name="updateaquirebinform">
                                        <table border="0">

                                            <tbody>
                                                <tr> <td style="height:10px;"></td></tr>
                                                <tr>
                                                    <td>BIN Number</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text"  readonly="true" value="${aqbean.binNumber}" name="binnumber" maxlength="6"/></td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <c:if test="${aqbean.ownership=='1'}">
                                                            <input type="radio" name="ownership" value="1" checked="true"/> On Us
                                                        </c:if>

                                                        <c:if test="${aqbean.ownership!='1'}">
                                                            <input type="radio" name="ownership" value="1" /> On Us
                                                        </c:if>

                                                        <c:if test="${aqbean.ownership=='2'}">
                                                            <input type="radio" name="ownership" value="2" checked="true"/> Off Us
                                                        </c:if>

                                                        <c:if test="${aqbean.ownership!='2'}">
                                                            <input type="radio" name="ownership" value="2" /> Off Us
                                                        </c:if>

                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

<!--                                                <tr>
                                                    <td>Pay Type</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <c:if test="${aqbean.payType=='1'}">
                                                            <input type="radio" name="paytype" value="1" checked="true"/> CREDIT 
                                                        </c:if>

                                                        <c:if test="${aqbean.payType!='1'}">
                                                            <input type="radio" name="paytype" value="1" /> CREDIT 
                                                        </c:if>

                                                        <c:if test="${aqbean.payType=='2'}">
                                                            <input type="radio" name="paytype" value="2" checked="true"/> DEBIT
                                                        </c:if>

                                                        <c:if test="${aqbean.payType!='2'}">
                                                            <input type="radio" name="paytype" value="2" /> DEBIT
                                                        </c:if>

                                                    </td>
                                                    <td></td>
                                                </tr>-->

                                                <tr><td style="height: 5px"></td></tr>                                                

                                                <tr>
                                                    <td>Sending Channel </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="sendchanel">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="send" items="${sessionScope.SessionObject.aquireChannelList}">
                                                                <c:if test="${aqbean.sendChanel==send.channelID}">
                                                                    <option value="${send.channelID}" selected>${send.channelName}</option>
                                                                </c:if>
                                                                <c:if test="${aqbean.sendChanel!=send.channelID}">
                                                                    <option value="${send.channelID}" >${send.channelName}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

<!--                                                <tr>
                                                    <td>Receiving Channel </td>
<td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="receivechanel">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="receive" items="${sessionScope.SessionObject.aquireChannelList}">
                                                                <c:if test="${aqbean.receiveChanel==receive.channelID}">
                                                                    <option value="${receive.channelID}" selected>${receive.channelName}</option>
                                                                </c:if>
                                                                <c:if test="${aqbean.receiveChanel!=receive.channelID}">
                                                                    <option value="${receive.channelID}" >${receive.channelName}</option>
                                                                </c:if>
                                                            </c:forEach>


                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>-->


                                                <tr>
                                                    <td>Card Key </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="cardKey">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="cType" items="${sessionScope.SessionObject.cardKeyList}">
                                                                <c:if test="${aqbean.cardKey==cType.key}">
                                                                    <option value="${cType.key}" selected>${cType.value}</option>
                                                                </c:if>

                                                                <c:if test="${aqbean.cardKey!=cType.key}">
                                                                    <option value="${cType.key}" >${cType.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

<!--                                                <tr>
                                                    <td>Entry Mode</td>
<td><font style="color: red;">*</font></td>
                                                    <td><select  name="entrymode" >
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="entry" items="${sessionScope.SessionObject.aquireEntryModeList}">
                                                                <c:if test="${aqbean.entryMode==entry.entryMode}">
                                                                    <option value="${entry.entryMode}" selected>${entry.entryModeDes}</option>
                                                                </c:if>

                                                                <c:if test="${aqbean.entryMode!=entry.entryMode}">
                                                                    <option value="${entry.entryMode}" >${entry.entryModeDes}</option>
                                                                </c:if>
                                                            </c:forEach>



                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>-->

<!--                                                <tr><td style="height: 5px"></td></tr>-->

                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select  name="status"  class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.aquireStatusList}">

                                                                <c:if test="${aqbean.status==status.status}">
                                                                    <option value="${status.status}" selected>${status.statusDes}</option>
                                                                </c:if>
                                                                <c:if test="${aqbean.status!=status.status}">
                                                                    <option value="${status.status}">${status.statusDes}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>


                                                    </td>
                                                    <td></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                        <table>
                                            <tr> <td style="height:12px;"></td></tr>
                                            <tr>
                                                <td style="width: 120px;"></td>
                                                <td><input type="submit" value="Update" name="update" class="defbutton" onclick="invokeUpdate()"/></td>
                                                <td><input type="button" value="Reset" name="reset" class="defbutton" onclick="updateview('${aqbean.binNumber}')"/></td>
                                                <td><input type="button" value="Back" name="back" class="defbutton" onclick="invokeReset()"/></td>
                                                
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.AQUIREBIN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                
                                            </tr>
                                            <tr><td style="height: 10px"></td></tr>
                                        </table>

                                    </form>  
                                </c:if>

                                <%--  --------------   End update form    ------------------------  --%>

                                <%-- -------------------------view form start -------------------------------- --%>
                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewaquirebinform">


                                        <table border="0">

                                            <tr>
                                                <td>BIN number</td>
                                                <td>:</td>
                                                <td>${aqbean.binNumber}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>


                                            <tr>
                                                <td>Ownership</td>
                                                <td>:</td>
                                                <td>${aqbean.ownershipDes}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

<!--                                            <tr>
                                                <td>Pay Type</td>
                                                <td>:</td>
                                                <td>${aqbean.payTypeDes}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>-->

                                            <tr>
                                                <td>Sending Channel</td>
                                                <td>:</td>
                                                <td>${aqbean.sendChanelDes}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

<!--                                            <tr>
                                                <td>Receiving Channel</td>
                                                <td>:</td>
                                                <td>${aqbean.receiveChanelDes}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>-->



                                            <tr>
                                                <td>Card Key </td>
                                                <td>:</td>
                                                <td>${aqbean.cardKeyDes}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

<!--                                            <tr>
                                                <td>Entry Mode </td>
                                                <td>:</td>
                                                <td>${aqbean.entryModeDes}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>-->

                                            <tr>
                                                <td>Status </td>
                                                <td>:</td>
                                                <td>${aqbean.statusDes}</td> 
                                            </tr>

                                            <tr> <td style="height:12px;"></td></tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td style="width: 300px">
                                                    <input type="button" value="Back" name="reset" class="defbutton" onclick="invokeReset()"/> </td>
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.AQUIREBIN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>


                                            </tr>
                                            <tr><td style="height: 10px"></td></tr>
                                        </table>
                                    </form>

                                </c:if>
                                <%-- -------------------------view form end -------------------------------- --%>

                                <%-- -------------------------show table data -------------------------------- --%>  
                                <br/>       
                                <form name="viewTableForm" id="viewTableForm" method="post">
                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr>
                                                <th>BIN Number</th>
                                                <th>Ownership</th>
<!--                                                <th>Pay Type</th>-->
                                                <th>Sending Channel</th>
<!--                                                <th>Receiving Channel</th>-->
                                                <th>Card Key</th>
<!--                                                <th>Entry Mode</th>-->
                                                <th>Status</th>

                                                <th>View</th>
                                                <th>Update</th>              
                                                <th>Delete</th>

                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach  items="${aquireBinBeanList}" var="binBean">
                                                <tr>
                                                    <td>${binBean.binNumber}</td>
                                                    <td>${binBean.ownershipDes}</td>
<!--                                                    <td>${binBean.payTypeDes}</td>-->
                                                    <td>${binBean.sendChanelDes}</td>
<!--                                                    <td>${binBean.receiveChanelDes}</td>-->
                                                    <td>${binBean.cardKeyDes}</td>
<!--                                                    <td>${binBean.entryModeDes}</td>-->
                                                    <td>${binBean.statusDes}</td>

                                                    <td><a href='${pageContext.request.contextPath}/VeiwAquireBinManagementServlet?aquireBinNumber=<c:out value="${binBean.binNumber}"></c:out>'>View</a></td>
                                                    <td><a href='${pageContext.request.contextPath}/UpdateAquireBinManagementFormServlet?aquireBinNumber=<c:out value="${binBean.binNumber}"></c:out>'>Update</a></td>

                                                    <td><a  href='#' onclick="ConfirmDelete('${binBean.binNumber}')">Delete</a></td>

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                        <input type="hidden" id="id"  name="id" maxlength="16" />
                                    </table>

                                </form>





                                <%--  ---------------------- end developer are ------------------  --%>

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
