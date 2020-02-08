<%-- 
    Document   : bulkcardrequestconfirm
    Created on : Sep 13, 2012, 9:01:38 AM
    Author     : badrika
--%>

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

        <script language="javascript" >
            
            function resetConfirmation(){
                              
                $("#apprvNumofCards").val("");
                $("#binProfile").val("");
                $("#cardtemplate").val("");                
                $("#crditLimit").val("");              
            }
            
            function resetReject(){
              
                $("#remark").val("");
              
            }
            
            function Reject(batchid)
            {
                answer = confirm("Are you sure you want to Reject this Bulk Card?")
                if (answer !=0)
                {
                    document.confirmForm.action="${pageContext.request.contextPath}/RejectBulkCardServlet?batchID="+batchid;
                }
                else
                {
                    document.confirmForm.action="${pageContext.request.contextPath}/LoadBulkCardDetailsServlet?batchID="+batchid;
                }
               
            }
            
            function Confirm(batchid)
            {
                answer = confirm("Are you sure you want to confirm this Bulk Card?")
                if (answer !=0)
                {
                    document.confirmForm.action="${pageContext.request.contextPath}/ConfirmBulkCardServlet?batchID="+batchid;
                }
                else
                {
                    document.confirmForm.action="${pageContext.request.contextPath}/LoadBulkCardDetailsServlet?batchID="+batchid;
                }               
            }
            function back(){

                window.location = "${pageContext.request.contextPath}/SearchBulkCardServlet?back=yes";
              
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

                                <table class="tit"> <tr> <td   class="center">  BULK CARD CONFIRMATION </td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                        </td>
                                    </tr>
                                </table>

                                <table cellpadding="0" cellspacing="10" style="height: 200px;" >

                                    <tr>
                                        <td style="width: 200px;">Batch ID</td>
                                        <td><b><font color="#000000"> ${viewBean.batchID}</font></b></td>

                                        <td style="width: 100px;"></td>

                                        <td style="width: 200px;"> Card Domain</td>
                                        <td><b><font color="#000000"> ${viewBean.cdDomainDes}</font></b></td>                                        
                                    </tr>

                                    <tr>
                                        <td style="width: 200px;">Card Type</td>
                                        <td><b><font color="#000000"> ${viewBean.cdTypeDes}</font></b></td>

                                        <td style="width: 100px;"></td>

                                        <td style="width: 200px;"> Card Product</td>
                                        <td><b><font color="#000000"> ${viewBean.cdProductDes}</font></b></td>

                                    </tr>

                                    <tr>
                                        <td style="width: 200px;">Currency </td>    
                                        <td><b><font color="#000000"> ${viewBean.currencyDes}</font></b></td>

                                        <td style="width: 100px;"></td>

                                        <td style="width: 200px;">Branch </td>
                                        <td><b><font color="#000000"> ${viewBean.branchName}</font></b></td>


                                    </tr>

                                    <tr>
                                        <td style="width: 200px;">Requested Number of Cards </td>
                                        <td><b><font color="#000000"> ${viewBean.reqNumOfCds}</font></b></td>

                                        <td style="width: 100px;"></td>

                                        <td style="width: 200px;">priority Level </td>    
                                        <td><b><font color="#000000"> ${viewBean.priorityLvlDes}</font></b></td>


                                    </tr>

                                    <tr>
                                        <td style="width: 200px;">Production Mode</td>
                                        <td><b><font color="#000000"> ${viewBean.productModeDes}</font></b></td>

                                        <td style="width: 100px;"></td>

                                        <td style="width: 200px;">Status </td>    
                                        <td><b><font color="#000000"> ${viewBean.statusDes}</font></b></td>


                                    </tr>

                                </table>

                                <br/>

                                <form name="confirmForm" action="" method="POST">
                                    <div class="outset" style="border-style:outset; background-color: #B8B8B8 ;  border-color: #999; width: 100%">

                                        <table cellpadding="0" cellspacing="10"  >      
                                            <tr>
                                                <td colspan="6" ><font style="color: #999"> <br/>Confirm</font></td>
                                            </tr>
                                            <!--                                    <form name="acceptForm" action="" method="POST" >-->


                                            <tr>
                                                <td style="width: 200px;">Approved Number Of Cards</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td><input type="text" value="${confirmBean.apprvNumOfCds}" name="apprvNumofCards" id="apprvNumofCards" maxlength="4" />
                                                    <input type="hidden" value="${viewBean.reqNumOfCds}" name="reqNumofCards" id="reqNumofCards" />
                                                </td> 
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Bin Profile</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select id="binProfile" name="binProfile"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" style="width: 100px;" selected>--SELECT--</option>
                                                        <c:forEach var="bin" items="${binlist}">

                                                            <c:if test="${confirmBean.cdBin==bin.binNumber}">
                                                                <option value="${bin.binNumber}" selected>${bin.description}</option> 
                                                            </c:if>
                                                            <c:if test="${confirmBean.cdBin!=bin.binNumber}">
                                                                <option value="${bin.binNumber}" >${bin.description}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>   

                                                </td> 
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Card Template</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td>
                                                    <select id="cardtemplate" name="cardtemplate"  class="inputfield-mandatory" style="width: 168px;">
                                                        <option value="" style="width: 100px;" selected>--SELECT--</option>
                                                        <c:forEach var="templt" items="${templtlist}">

                                                            <c:if test="${confirmBean.templateCode==templt.templateCode}">
                                                                <option value="${templt.templateCode}" selected>${templt.templateName}</option> 
                                                            </c:if>
                                                            <c:if test="${confirmBean.templateCode!=templt.templateCode}">
                                                                <option value="${templt.templateCode}" >${templt.templateName}</option>

                                                            </c:if>


                                                        </c:forEach>
                                                    </select>

                                                </td> 
                                            </tr>

                                            <tr><c:if test="${viewBean.cdDomainDes=='Loyalty' || viewBean.cdDomainDes=='Prepaid'}">
                                                    <td style="width: 200px;">Credit Limit</td>
                                                    <td></td>
                                                    <td><input type="text" value="${confirmBean.creditLimit}" name="crditLimit" id="crditLimit" maxlength="11" /></td> 
                                                    </c:if>
                                            </tr>

                                        </table>

                                        <table>

                                            <td><input type="submit" value="Confirm"  onclick="Confirm('${viewBean.batchID}')" style="width: 100px"></input></td>
                                            <td><input type="button" value="Reset" style="width: 100px" onclick="resetConfirmation()"></input></td>
                                            <td><input type="button" value="Back" style="width: 100px" onclick="back()"></input></td>

                                        </table>

                                    </div>

                                    <br/>
                                    <div class="outset"   width: 100%">


                                         <table cellpadding="0" cellspacing="10"  >     
                                            <tr>
                                                <td colspan="6" ><font style="color: #999"> <br/>Reject</font></td>
                                            </tr>

                                            <tr>
                                                <td style="width: 200px;">Remarks</td>
                                                <td><font style="color: red;">*</font></td>
                                                <td> <TEXTAREA id="remark" name="remark" ROWS="3" style="width: 350px;" ></TEXTAREA></td>
                                            </tr>


                                            </tr>
                                            <tr>

                                                <td>
                                                    <table>

                                                        <td><input type="submit" value="Reject" name="next" style="width: 100px" onclick="Reject('${viewBean.batchID}')"></input></td>
                                                        <td><input type="button" value="Reset" name="next" style="width: 100px" onclick="resetReject()"></input></td>
                                                        <td><input type="button" value="Back" style="width: 100px" onclick="back()"></input></td>

                                                    </table>
                                                </td>
                                            </tr>
                                        </table>

                                    </div>

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








