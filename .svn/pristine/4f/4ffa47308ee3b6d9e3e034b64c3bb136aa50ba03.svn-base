<%-- 
    Document   : cardchangeapprovehome
    Created on : Aug 15, 2012, 9:16:03 AM
    Author     : nisansala
--%>

<%@page import="com.epic.cms.system.util.variable.PageVarList"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html >
    <head>

        <style type="text/css">
            form.inset {border-style:inset; width: 510px; color: #0063DC;}
        </style>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>
        <title>EPIC_CMS_HOME</title>


        <script type="text/javascript"> 
            function invokeSearch(){
                
                document.searchrequestapprove.action="${pageContext.request.contextPath}/SearchCardChangeApproveServlet";
                document.searchrequestapprove.submit();
            }
         
            function invokeReset(){

                window.location = "${pageContext.request.contextPath}/LoadRequestApproveServlet";

            }
            
            function invokeCancel(form){
                
                
                if(form == 'CDRP'){
                    document.cardreplace.action="${pageContext.request.contextPath}/LoadRequestApproveServlet";
                    document.cardreplace.submit();
                }
                if(form == 'CDRI'){
                    document.cardreissue.action="${pageContext.request.contextPath}/LoadRequestApproveServlet";
                    document.cardreissue.submit();
                }
                if(form == 'PIRI'){
                    document.pinreissue.action="${pageContext.request.contextPath}/LoadRequestApproveServlet";
                    document.pinreissue.submit();
                }
                
                
            }
            
            function invokeApproveCardChange(operation){
            
                if(operation == 'TYPE'){
                    document.type.action="${pageContext.request.contextPath}/UpdateCardChangeApproveServlet?operation="+operation;
                    document.type.submit();
                }
                
                if(operation == 'PRODUCT'){
                    document.product.action="${pageContext.request.contextPath}/UpdateCardChangeApproveServlet?operation="+operation;
                    document.product.submit();
                }
            }
            
            
            
            function invokeHistory(value){

                $.post("${pageContext.request.contextPath}/ViewHistoryRequestApproveServlet", {id:value},
                function(data) {
                    if(data == "success"){
                   
                        $.colorbox({href:"${pageContext.request.contextPath}/cpmm/requestconfirm/cardhistory.jsp", iframe:true, height:"80%", width:"80%",overlayClose:false});
                    }
                       
                    else if(data == "session"){
                        
                        window.location = "${pageContext.request.contextPath}/administrator/controlpanel/login/login.jsp?message=<%=MessageVarList.SESSION_EXPIRED%>";    
                    }
                    else{
                        alert("error on loading data.") ;
                    }
                });
            }
            //----------------------------------------------------------------------------------  
        </script>
    </head>
    <body>

        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">
            <div class="header">
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
                                <table class="tit"> <tr> <td   class="center">  CARD CHANGE APPROVE </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>

                                    <!--/////////////////////////////////////////////End Default view  ///////////////////////////////////////////////////////////-->          

                                <c:if test="${operationtype=='search'}">

                                    <form method="POST" name="searchrequestapprove" >
                                        <table border="0" cellspacing="10" cellpadding="0">
                                            <tbody >
                                                <tr>
                                                    <td width="200px;">Card Number</td>
                                                    <td>
                                                        <input type="text" value="${cardBean.cardNo}" name="cardno"/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Priority Level</td>
                                                    <td>
                                                        <select style="width: 100px" name="prioritycode">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="priority" items="${priorityLevelList}">
                                                                <c:if test="${cardBean.priorityLevel==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.priorityLevel != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Status</td>
                                                    <td>
                                                        <select name="approvestatus">
                                                            <option value="">--SELECT--</option>
                                                            <c:forEach var="approve" items="${approveStatus}">
                                                                <c:if test="${cardBean.status == null}">
                                                                    <option value="${approve.key}" >${approve.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.status == approve.key}">
                                                                    <option value="${approve.key}" selected>${approve.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.status == approve.key}">
                                                                    <option value="${approve.key}" selected>${approve.value}</option>
                                                                </c:if>


                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Reason Code</td>
                                                    <td>
                                                        <select  name="reasoncode" >                                                           
                                                            <option value="" >--SELECT--</option>
                                                            <c:if test="${cardBean.reasonCode == 'TYPE'}">                                                                
                                                                <option value="TYPE" selected="">Type Change</option>                                                                
                                                                <option value="PRODUCT" >Product Change</option>                                                                
                                                            </c:if>

                                                            <c:if test="${cardBean.reasonCode == 'PRODUCT'}">
                                                                <option value="TYPE" >Type Change</option>                                                                
                                                                <option value="PRODUCT" selected="">Product Change</option>                                                                
                                                            </c:if>
                                                            <c:if test="${cardBean.reasonCode == null}">
                                                                <option value="TYPE" >Type Change</option>                                                                
                                                                <option value="PRODUCT" >Product Change</option>                                                                
                                                            </c:if>  
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>   
                                                <tr>
                                                    <td></td>
                                                    <td>   
                                                        <input type="submit" class="defbutton" name="search" value="Search" onclick="invokeSearch()"/>                                                         
                                                        <input type="submit" class="defbutton" name="reset" value="Reset" onclick="invokeReset()"/> 
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>   
                                </c:if>
                                <c:if test="${operationtype=='update'}" >
                                    <c:if test="${operation=='TYPE'}">

                                        <form method="POST" name="type" >
                                            <table border="0" cellspacing="10" cellpadding="0">

                                                <tbody >
                                                    <tr>
                                                        <td width="200px;">Card Number</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.cardNo}" name="cardno" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Card Status</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.statusDes}" name="status" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Credit Limit</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.creditLimit}" name="creditlimit" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Cash Limit</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.cashLimit}" name="cashlimit" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Expiry Date</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.expiryDate}" name="expitydate" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Current Card Type</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.cdTypeDes}" name="cardtype" readonly=""/>
                                                        </td>
                                                    </tr>   
                                                <td width="200px;">Current Card Product</td>
                                                <td>
                                                    <input style="color: #999999" type="text" value="${cardBean.cdProdDes}" name="cardproduct" readonly=""/>
                                                </td>
                                                </tr>
                                                <tr>
                                                    <td width="200px;">Requested Card Type</td>
                                                    <td>
                                                        <input style="color: #999999" type="text" value="${cardBean.reqCdTypeDes}" name="reqcardtype" readonly=""/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width="200px;">Requested Card Product</td>
                                                    <td>
                                                        <input style="color: #999999" type="text" value="${cardBean.reqCdProdDes}" name="reqcardproduct" readonly=""/>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Priority Level</td>
                                                    <td>
                                                        <select style="width: 100px" name="prioritycode" disabled="">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="priority" items="${priorityLevelList}">
                                                                <c:if test="${cardBean.priorityLevel==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.priorityLevel != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>                                                                                               
                                                <tr>
                                                    <td>Remark</td>
                                                    <td><textarea name="remarks" value="${cardBean.remark}" ></textarea></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td>   
                                                        <input type="submit" class="defbutton" name="approve" value="Approve" onclick="invokeApproveCardChange('TYPE')"/>  
                                                        <input type="submit" class="defbutton" name="reject" value="Reject" onclick="invokeReject()"/>    
                                                        <input type="button" class="defbutton" name="reset" value="Cancel" onclick="invokeCancel('CDRP')"/></td>

                                                    <td><a  href="#"  onclick="invokeHistory('${cardBean.cardNo}')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></td>
                                                </tr>
                                                </tbody>
                                            </table> 
                                        </form>
                                    </c:if>

                                    <c:if test="${operation=='PRODUCT'}">

                                        <form method="POST" name="product" >
                                            <table border="0" cellspacing="10" cellpadding="0">

                                                <tbody >
                                                    <tr>
                                                        <td width="200px;">Card Number</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.cardNo}" name="cardno" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Card Status</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.statusDes}" name="status" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Credit Limit</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.creditLimit}" name="creditlimit" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Cash Limit</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.cashLimit}" name="cashlimit" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Expiry Date</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.expiryDate}" name="expitydate" readonly=""/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="200px;">Current Card Type</td>
                                                        <td>
                                                            <input style="color: #999999" type="text" value="${cardBean.cdTypeDes}" name="cardtype" readonly=""/>
                                                        </td>
                                                    </tr>   
                                                <td width="200px;">Current Card Product</td>
                                                <td>
                                                    <input style="color: #999999" type="text" value="${cardBean.cdProdDes}" name="cardproduct" readonly=""/>
                                                </td>
                                                </tr>
                                                <tr>
                                                    <td width="200px;">Requested Card Type</td>
                                                    <td>
                                                        <input style="color: #999999" type="text" value="${cardBean.reqCdTypeDes}" name="reqcardtype" readonly=""/>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width="200px;">Requested Card Product</td>
                                                    <td>
                                                        <input style="color: #999999" type="text" value="${cardBean.reqCdProdDes}" name="reqcardproduct" readonly=""/>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Priority Level</td>
                                                    <td>
                                                        <select style="width: 100px" name="prioritycode" disabled="">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="priority" items="${priorityLevelList}">
                                                                <c:if test="${cardBean.priorityLevel==priority.key}">
                                                                    <option value="${priority.key}" selected>${priority.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBean.priorityLevel != priority.key}">
                                                                    <option value="${priority.key}" >${priority.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>                                                                                               
                                                <tr>
                                                    <td>Remark</td>
                                                    <td><textarea name="remarks" value="${cardBean.remark}" ></textarea></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td>   
                                                        <input type="submit" class="defbutton" name="approve" value="Approve" onclick="invokeApproveCardChange('PRODUCT')"/>  
                                                        <input type="submit" class="defbutton" name="reject" value="Reject" onclick="invokeReject()"/>    
                                                        <input type="button" class="defbutton" name="reset" value="Cancel" onclick="invokeCancel('CDRI')"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('${cardBean.cardNo}')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></td>
                                                </tr>
                                                </tbody>
                                            </table> 
                                        </form>
                                    </c:if>


                                </c:if>

                                <!-- **************************************************start table view*******************************************************************************-->
                                <br></br>

                                <table  border="1"  class="display" id="tableview">
                                    <thead>
                                        <tr class="gradeB">
                                            <th>Card No</th>
                                            <th>Priority Level</th>
                                            <th>Remark</th>
                                            <th>Request Type</th>                                           

                                            <th>View</th>                                            

                                        </tr>
                                    </thead>
                                    <tbody>

                                        <c:forEach var="requests" items="${searchedList}">
                                            <tr>
                                                <td>${requests.cardNo}</td>
                                                <td>${requests.priorityDes}</td>
                                                <td>${requests.remark}</td>
                                                <td><c:if test="${requests.reasonCode == 'TYPE'}">
                                                        Type Change
                                                    </c:if>
                                                    <c:if test="${requests.reasonCode == 'PRODUCT'}">
                                                        Product Change
                                                    </c:if>
                                                </td>   

                                                <td  ><a href='${pageContext.request.contextPath}/LoadUpdateCardChangeApproveServlet?cardNumber=<c:out value="${requests.cardNo}"></c:out>&reasonCode=<c:out value="${requests.reasonCode}"></c:out>'>View</a></td>

                                                </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>                 

                                <br />


                                <!--   ------------------------- end developer area  --------------------------------                      -->
                                <!--<font style="color: red;">*</font>&nbsp;-->



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
