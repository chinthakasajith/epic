<%-- 
    Document   : controlpanelhome
    Created on : Jan 10, 2012, 5:13:40 PM
    Author     : janaka_h
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
        <script type="text/javascript">
            
            function resetBranchForm(){
                window.location = "${pageContext.request.contextPath}/LoadBankBranchMgtServlet";
            }
            
            
            function viewBankBrach(value,val2){
                window.location = "${pageContext.request.contextPath}/ViewBankBranchMgtServlet?id="+value+"&bankCode="+val2;
            }
            function updateBankBrach(value,val2){
                window.location = "${pageContext.request.contextPath}/UpdateBankBrachMgtLoadServlet?id="+value+"&bankCode="+val2;
            }
            
            function ConfirmDelete(value1,value2)
            {
                $("#dialog-confirm").html("<p>Do you really want to delete "+value1+" Branch?</p>");
                $("#dialog-confirm").dialog({
                    resizable: false,
                    height: 'auto',
                    width: 500,
                    modal: true,
                    buttons: {
                        "No": function () {
                            $(this).dialog("close");
                        },
                        "Yes": function () {
                            window.location="${pageContext.request.contextPath}/DeleteBankBranchMgtServlet?id="+value1+"&bankName="+value2;
                        }
                    }
                });

            }
        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.BANKBRANCH%>'                                  
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
                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/AddBankBranchMgtServlet">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td style="width: 150px;">Branch Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="32" name="branchCode" value="${branchBean.branchName}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="64" name="description" value="${branchBean.description}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Bank </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="bankName" style="width: 168px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="vRule" items="${requestScope.bankList}">

                                                                <c:if test="${vRule.bankCode ==branchBean.bankCode}">
                                                                    <option selected="" value="${vRule.bankCode}">${vRule.bankName}</option>
                                                                </c:if>
                                                                <c:if test="${vRule.bankCode !=branchBean.bankCode}">
                                                                    <option value="${vRule.bankCode}">${vRule.bankName}</option>
                                                                </c:if>



                                                            </c:forEach>
                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Address</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input maxlength="32" type="text" name="address1" value="${branchBean.address1}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input maxlength="32" type="text" name="address2" value="${branchBean.address2}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="text" maxlength="32" name="address3" value="${branchBean.address3}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Contact Person</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="32" name="contactPerson" value="${branchBean.contactPer}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Contact No</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="20" name="contactNo" value="${branchBean.contactNo}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Display Digit</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="4" name="displayDigit" value="${branchBean.displayDigit}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> <input type="submit" value="Add" name="Add" style="width: 100px"/><input onclick="resetBranchForm()" type="reset" value="Reset" style="width: 100px"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.BANKBRANCH%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>


                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>




                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/UpdateBankBranchMgtServlet">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td style="width: 150px;">Branch Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="32" name="branchCode" readonly="" value="${branchBean.branchName}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="64" name="description" value="${branchBean.description}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Bank </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="bankName" disabled="" style="width: 168px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="vRule" items="${requestScope.bankList}">

                                                                <c:if test="${vRule.bankCode ==branchBean.bankCode}">
                                                                    <option selected="" value="${vRule.bankCode}"  >${vRule.bankName}</option>
                                                                </c:if>
                                                                <c:if test="${vRule.bankCode !=branchBean.bankCode}">
                                                                    <option value="${vRule.bankCode}">${vRule.bankName}</option>
                                                                </c:if>



                                                            </c:forEach>
                                                        </select></td>
                                                </tr>
                                                <tr>                                        
                                                    <td><input type="hidden" name="bankName2" value="${branchBean.bankCode}"/></td>                                        
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Address</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="32" name="address1" value="${branchBean.address1}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="address2" maxlength="32" value="${branchBean.address2}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="text" name="address3" maxlength="32" value="${branchBean.address3}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Contact Person</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="contactPerson" maxlength="32" value="${branchBean.contactPer}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Contact No</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="20" name="contactNo" value="${branchBean.contactNo}" /></td>
                                                </tr>
                                                <tr>
                                                    <td><input type="hidden" name="oldvalue" value="${oldval}" /></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Display Digit</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" maxlength="4" name="displayDigit" value="${branchBean.displayDigit}" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                        <table>
                                            <tr>
                                                <td style="width: 168px;"> </td>
                                                <td > <input type="submit" value="Update" name="Update" style="width: 100px"/></td>
                                                <td><input type="button" value="Back" name="back" style="width: 100px" onclick="resetBranchForm()"/></td>
                                                <td><input onclick="updateBankBrach('${branchBean.branchName}','${branchBean.bankCode}')" type="reset" value="Reset" style="width: 100px"/></td>

                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.BANKBRANCH%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                            </tr>                                            

                                        </table>




                                    </form>
                                </c:if>








                                <c:if test="${operationtype=='view'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/LoadBankBranchMgtServlet">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td>Branch Code </td>
                                                    <td>:</td>
                                                    <td>${bankBean.branchName}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td>:</td>
                                                    <td>${bankBean.description}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Bank </td>
                                                    <td>:</td>
                                                    <td>${bankBean.bankName}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Address</td>
                                                    <td>:</td>
                                                    <td>${bankBean.address1}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>${bankBean.address2}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>${bankBean.address3}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Contact Person</td>
                                                    <td>:</td>
                                                    <td>${bankBean.contactPer}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Contact No</td>
                                                    <td>:</td>
                                                    <td>${bankBean.contactNo}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Display Digit</td>
                                                    <td>:</td>
                                                    <td>${bankBean.displayDigit}</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> <input type="submit" value="Back" name="Back" style="width: 100px"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.BANKBRANCH%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>


                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>
                                <c:if test="${operationtype=='reset'}" >
                                    <form method="POST" action="${pageContext.request.contextPath}/AddBankBranchMgtServlet">
                                        <table border="0">


                                            <tbody>
                                                <tr>
                                                    <td style="width: 150px;">Branch Code </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="branchCode" value="" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="description" value="" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Bank </td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td>
                                                        <select name="bankName" style="width: 168px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="vRule" items="${requestScope.bankList}">
                                                                <option value="${vRule.bankCode}">${vRule.bankName}</option>

                                                            </c:forEach>
                                                        </select></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Address</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="address1" value="" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="text" name="address2" value="" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td><input type="text" name="address3" value="" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Contact Person</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="contactPerson" value="" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td>Contact No</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="contactNo" value="" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                
                                                <tr>
                                                    <td>Display Digit</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text" name="displayDigit" value="" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td style="width: 300px;"> <input type="submit" value="Add" name="Add" style="width: 100px"/><input onclick="resetToback()" type="reset" value="Reset" style="width: 100px"/></td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.BANKBRANCH%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>


                                            </tbody>
                                        </table>

                                    </form>
                                </c:if>


                                <table border="1" class="display" id="scoreltableview5">
                                    <thead>
                                        <tr>
                                            <th>Branch Code</th>
                                            <th>Description</th>
                                            <th>Bank</th>
                                            <th>Address 1</th>
                                            <th>Address 2</th>
                                            <th>Address 3</th>
                                            <th>Contact Person</th>
                                            <th>Contact No</th>
                                            <th>Display Digit</th>
                                            <th>View</th>
                                            <th>Update</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach  items="${requestScope.branchAllList}" var="branchAllList">
                                            <tr>
                                                <td>${branchAllList.branchName}</td>
                                                <td>${branchAllList.description}</td>
                                                <td>${branchAllList.bankName}</td>
                                                <td>${branchAllList.address1}</td>
                                                <td>${branchAllList.address2}</td>
                                                <td>${branchAllList.address3}</td>
                                                <td>${branchAllList.contactPer}</td>
                                                <td>${branchAllList.contactNo}</td>
                                                <td>${branchAllList.displayDigit}</td>
                                                <td><a  href='#' onclick="viewBankBrach('${branchAllList.branchName}','${branchAllList.bankCode}')">View</a></td>
                                                <td><a  href='#' onclick="updateBankBrach('${branchAllList.branchName}','${branchAllList.bankCode}')">Update</a></td>
                                                <td><a  href='#' onclick="ConfirmDelete('${branchAllList.branchName}','${branchAllList.bankCode}')">Delete</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!--confirmation dialog -->
                                <div id="dialog-confirm" title="Delete Confirmation"></div>





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
