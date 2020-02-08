<%-- 
    Document   : assigncommissionprofiletxn
    Created on : Nov 1, 2012, 2:37:00 PM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib  prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@page  import="com.epic.cms.system.util.variable.MessageVarList" %>


<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/content.jsp"/>

        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/default.css" media="screen"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/tablejs/jquery.min.js"></script>


        <title>EPIC_CMS_HOME</title>

        <script  type="text/javascript" charset="utf-8">
            
            function invokeUpdateTxn()
            {

                document.editTxn.action="${pageContext.request.contextPath}/EditCommissionProfileTxnServlet";
                document.editTxn.submit();

            }
            function invokeReset()
            {

                editTxn.elements["crOrdr"].value = ${sessionScope.SessionObject.commissionBean.crOrdr};
                editTxn.elements["flatAmount"].value = ${sessionScope.SessionObject.commissionBean.flatAmount};
                editTxn.elements["percentage"].value = ${sessionScope.SessionObject.commissionBean.percentage};
                editTxn.elements["combination"].value = ${sessionScope.SessionObject.commissionBean.combination};
               

            }
            function invokeCancel(profileCode,opType)
            {
                if(opType=='UPDATE'){
                    window.location="${pageContext.request.contextPath}/LoadUpdateCommissionProfileServlet?profileCode="+profileCode+"&opType="+opType;
                }
                if(opType == 'ADD'){
                    
                    window.location="${pageContext.request.contextPath}/LoadCommisionProfileServlet";
                    
                }
            }
            
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

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>
                <div id="content1">

                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  -----------------------------------                           -->

                                <table class="tit"> <tr> <td   class="center">  Commission Profile Transaction  </td> </tr> <td>&nbsp;</td></table>
                                <br/>
                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        </td>
                                    </tr>
                                </table>

                                <c:if test="${operationtype=='EDIT'}" >
                                    <form method="POST" name="editTxn" action="">

                                        <table cellpadding="0" cellspacing="10">
                                            <tbody>

                                                <tr>
                                                    <td>Commission Profile</td>
                                                    <td><font style="color: white;">*</font>&nbsp;<input disabled="disabled" type="text" name="description" class="inputfield-mandatory" maxlength="50" value='${comisssionBean.description}'>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Currency Type</td>
                                                    <td><font style="color: white;">*</font>&nbsp;<select style="width: 160px;" name="currencyCode"  disabled="disabled" class="inputfield-mandatory">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyDetailList}">
                                                                <c:if test="${comisssionBean.currencyCode==curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}" selected="true" >${curr.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${comisssionBean.currencyCode!=curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}">${curr.currencyDes}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select></td>
                                                </tr>

                                                <tr>
                                                    <td>Transaction Type</td>
                                                    <td><font style="color: white;">*</font>&nbsp;<input type="text" name="txnDes" disabled="disabled" class="inputfield-mandatory" maxlength="50" value='${comisBean.txnDes}'>
                                                        <input type="hidden" hidden="hidden" name="txnDes" class="inputfield-mandatory" maxlength="50" value='${comisBean.txnDes}'>
                                                        <input  type="hidden" hidden="hidden" name="txnType" class="inputfield-mandatory" maxlength="50" value='${comisBean.txnType}'>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td width ="200px;"></td>
                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <c:if test="${comisBean.crOrdr == 'CR'}">
                                                            <input type="radio" name="crOrdr" checked="true" value="CR" /> Credit 
                                                        </c:if>
                                                        <c:if test="${comisBean.crOrdr != 'CR'}">
                                                            <input type="radio" name="crOrdr" checked="true"  value="CR" /> Credit
                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${comisBean.crOrdr == 'DR'}">
                                                            <input type="radio" name="crOrdr" checked="true"  value="DR" /> Debit
                                                        </c:if>
                                                        <c:if test="${comisBean.crOrdr != 'DR'}">
                                                            <input type="radio" name="crOrdr"   value="DR" /> Debit
                                                        </c:if>
                                                    </td> 
                                                </tr>

                                                <tr>
                                                    <td>Flat Amount</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="flatAmount" class="inputfield-mandatory" maxlength="11" value='${comisBean.flatAmount}'></td>
                                                </tr>

                                                <tr>
                                                    <td>Percentage</td>
                                                    <td><font style="color: red;">*</font>&nbsp;<input type="text" name="percentage" class="inputfield-mandatory" maxlength="5" value='${comisBean.percentage}'> %</td>
                                                </tr>

                                                <tr>
                                                    <td width ="200px;"></td>

                                                    <td><font style="color: red;">*</font>&nbsp;
                                                        <c:if test="${comisBean.combination == 'MIN'}">
                                                            <input type="radio" name="combination" checked="true" value="MIN" /> Minimum
                                                        </c:if>
                                                        <c:if test="${comisBean.combination != 'MIN'}">
                                                            <input type="radio" name="combination" checked="true"  value="MIN" /> Minimum 
                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${comisBean.combination == 'MAX'}">
                                                            <input type="radio" name="combination"  checked="true" value="MAX" /> Maximum 
                                                        </c:if>
                                                        <c:if test="${comisBean.combination != 'MAX'}">
                                                            <input type="radio" name="combination"   value="MAX" /> Maximum 
                                                        </c:if>

                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                                        <c:if test="${comisBean.combination == 'ADD'}">
                                                            <input type="radio" name="combination" checked="true"  value="ADD" /> ADD
                                                        </c:if>
                                                        <c:if test="${comisBean.combination != 'ADD'}">
                                                            <input type="radio" name="combination"  value="ADD" /> ADD
                                                        </c:if>
                                                    </td> 
                                                </tr>


                                                <tr></tr>
                                                <tr></tr>
                                                <tr></tr>

                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" style="width: 100px" name="update" value="Update" onclick="invokeUpdateTxn()" />
                                                        <input type="reset" style="width: 100px" name="reset" value="Reset" onclick="invokeReset()" />
                                                        <input type="button" style="width: 100px" name="cancel" value="Back" onclick="invokeCancel('${comisssionBean.comProfileCode}','${sessionScope.SessionObject.operationtype}')" /></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </c:if>
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