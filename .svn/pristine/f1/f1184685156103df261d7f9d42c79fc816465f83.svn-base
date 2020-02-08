<%-- 
    Document   : bulkcardnumbergenerationview
    Created on : Sep 25, 2012, 4:52:39 PM
    Author     : nalin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


        <!--        include content.jsp for all js and css inclusion-->
        <jsp:include page="/content.jsp"/>
        <!--        include content.jsp for all js and css inclusion-->




        <script >
            
            
            function invokeReject()
            {

                window.location="${pageContext.request.contextPath}/LoadBulkCardNumberGenerationServlet";
                

            }
            
            function invokeConfirm(batchID)
            {
                window.location="${pageContext.request.contextPath}/StartBulkCardNumberGenerationServlet?batchID="+batchID;
                
            }
            

        </script>


        <title>CMS BULK CARD NUMBER GENERATION</title>
    </head>
    <body>
        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp"/>
        </c:if>

        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

            </div>


            <div class="main">
                <jsp:include page="/subheader.jsp"/>



                <div class="content" style="height: 500px">

                    <td class="acstable-menubar"><jsp:include page="/leftmenu.jsp"/>

                </div>


                <div id="content1">


                    <div id="content" align="center">
                        <div id="contenttext">
                            <div class="bodytext" style="padding:12px;" align="left">

                                <!--  ----------------------start  developer area  --- -->

                                <table class="tit"> <tr> <td   class="center"><b>  BULK CARD NUMBER GENERATION </b></td> </tr><tr> <td>&nbsp;</td> </tr></table>

                                <table>
                                    <tr>
                                        <td><b><font color="#FF0000"> ${errorMsg}</font></b> </td>
                                        <td><b><font color="Green"> ${successMsg}</font></b> </td>
                                    </tr>
                                </table>

                                <form action="" method="POST" name="viewMerchantForm">
                                    <table border="0">
                                        <tbody>
                                            <tr>
                                                <td><b>Bulk Card Request Details</b><hr /> </td>
                                            </tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>

                                            <tr>
                                                <td>Batch ID</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.batchID}</td>
                                            </tr>
                                            <tr>
                                                <td>Card Domain</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.cdDomainDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Card Type</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.cdTypeDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Card Product</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.cdProductDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Production Mode</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.productModeDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Currency</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.currencyDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Priority Level</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.priorityLvlDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Requested Branch</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.branchName}</td>
                                            </tr>
                                            <tr>
                                                <td>Requested No of Cards</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.reqNumOfCds}</td>
                                            </tr>
                                            <tr>
                                                <td>Requested User</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.reqUser}</td>
                                            </tr>
                                            <tr>
                                                <td>Requested Date</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.ceatedTime}</td>
                                            </tr>

                                            <tr></tr>
                                            <tr></tr> 
                                            <tr></tr>
                                            <tr></tr> 
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr> 
                                            <tr></tr>
                                            <tr></tr>

                                            <tr>
                                                <td><b> Bulk Card Confirmation Details</b><hr /> </td>
                                            </tr>
                                            <tr></tr>
                                            <tr></tr> 
                                            <tr></tr>
                                            <tr></tr>
                                            <tr>
                                                <td>Approved No of Card</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.apprvNumOfCds}</td>
                                            </tr>
                                            <tr>
                                                <td>Card Bin</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.cdBinDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Card Template</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.templateDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Credit Limit</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.creditLimit}</td>
                                            </tr>
                                            <tr>
                                                <td>Approved Branch</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.apprvBranchDes}</td>
                                            </tr>
                                            <tr>
                                                <td>Approved User</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.apprvUser}</td>
                                            </tr>
                                            <tr>
                                                <td>Last Updated User</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.lastUpdatedUser}</td>
                                            </tr>
                                            <tr>
                                                <td>Last Updated Date</td>
                                                <td></td>
                                                <td> : </td>
                                                <td></td>
                                                <td>${numGenViewBean.lastUpdatedTime}</td>
                                            </tr>

                                        </tbody>
                                    </table>

                                    <table>
                                        <tbody>  
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr></tr>
                                            <tr >
                                                <td style="width: 200px;"></td>
                                                <td ><input type="button" name="confirm" value="Confirm" onclick="invokeConfirm('${numGenViewBean.batchID}')" style="width: 95px;"/>
                                                    <input type="button" onclick="invokeReject()" name="next" value="Reject" style="width: 95px;"/></td>
                                            </tr>
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