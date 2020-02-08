<%-- 
    Document   : cardbinmgthome
    Created on : Mar 30, 2012, 11:43:02 AM
    Author     : chanuka
--%>

<%-- 
    Document   : cardproductmgthome
    Created on : Mar 22, 2012, 12:16:34 PM
    Author     : chanuka
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
            
            function invokeUpdateview(binNumber)
            {
                window.location = "${pageContext.request.contextPath}/UpdateCardBinFormServlet?cardBinNumber="+binNumber;                
            }
            
            function invokeUpdate()
            {

                document.updatecardbinform.action="${pageContext.request.contextPath}/UpdateCardBinServlet";
                document.updatecardbinform.submit();

            }
            function invokeAdd()
            {

                document.addcardbinform.action="${pageContext.request.contextPath}/AddCardBinServlet";
                document.addcardbinform.submit();

            }
            
            function invokeResetCardBin()
            {

                window.location = "${pageContext.request.contextPath}/LoadCardBinServlet";

            }

            
            function ConfirmDelete(binNumber)
            {
                answer = confirm("Do you really want to delete this BIN Number?")
                if (answer !=0)
                {
                    window.location ="${pageContext.request.contextPath}/DeleteCardBinServlet?cardBinNumber="+binNumber;
                }
                else
                {
                    window.location ="${pageContext.request.contextPath}/LoadCardBinServlet";
                }
                
            }
            
    

        </script>
        <script>
            function settitle(){
                
                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                { pagecode:'<%= PageVarList.CARDPBIN%>'                                  
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



        <title>EPIC CMS CARD BIN MANAGEMENT</title>
    </head>
    <body>


        <c:if test="${sessionScope.SessionObject==null}">
            <c:redirect url="/administrator/controlpanel/login/login.jsp">
                <c:param name="message" value="<%=MessageVarList.SESSION_EXPIRED%>"/>
            </c:redirect>
        </c:if>
        <div class="container">

            <div class="header">
                <!--                <font size="20" color="FFFFFF" style="font: bold">  Card Management System </font>-->

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

                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <%-- -------------------------add form start -------------------------------- --%>

                                <table>
                                    <tr>
                                        <td><b><font color="Red"> ${errorMsg}</font> </b></td>
                                        <td><b><font color="green"> ${successMsg}</font> </b></td>
                                        <td></td>

                                    </tr>
                                </table>

                                <br/>

                                <c:if test="${operationtype=='add'}" >
                                    <form method="POST" action="" name="addcardbinform">

                                        <table border="0">

                                            <tbody>
                                                <tr> <td style="height:10px;"></td></tr>
                                                <tr>
                                                    <td style="width: 150px;">BIN Number</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text"  value="${cardBinBean.binNumber}" name="binnumber" maxlength="6"></td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text"  name="binnumberdes" value="${cardBinBean.description}" maxlength="64"></td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <c:if test="${cardBinBean.onus=='YES'}">
                                                            <input type="radio" name="onus" value="YES" checked="true"/> On Us
                                                        </c:if>

                                                        <c:if test="${cardBinBean.onus!='YES'}">
                                                            <input type="radio" name="onus" value="YES" /> On Us
                                                        </c:if>

                                                        <c:if test="${cardBinBean.onus=='NO'}">
                                                            <input type="radio" name="onus" value="NO" checked="true"/> Off Us
                                                        </c:if>

                                                        <c:if test="${cardBinBean.onus!='NO'}">
                                                            <input type="radio" name="onus" value="NO" /> Off Us
                                                        </c:if>

                                                    </td>
                                                    <td></td>
                                                </tr>



                                                <tr><td style="height: 5px"></td></tr>                                                

                                                <tr>
                                                    <td>Card Type </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="cardtype" style="width: 168px;">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="cardType" items="${cardTypeList}">
                                                                <c:if test="${cardBinBean.cardType==cardType.key}">
                                                                    <option value="${cardType.key}" selected>${cardType.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.cardType!=cardType.key}">
                                                                    <option value="${cardType.key}" >${cardType.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Card Domain</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select  name="bintype" style="width: 168px;" >
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="bin" items="${sessionScope.SessionObject.cardDomainsList}">

                                                                <c:if test="${cardBinBean.binType==bin.domainID}">
                                                                    <option value="${bin.domainID}" selected>${bin.descrip}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.binType!=bin.domainID}">
                                                                    <option value="${bin.domainID}">${bin.descrip}</option>

                                                                </c:if>


                                                            </c:forEach>


                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Currency Type</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select  name="currType" style="width: 168px;" >
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyDetailList}">

                                                                <c:if test="${cardBinBean.currTypeCode==curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}" selected>${curr.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.currTypeCode!=curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}">${curr.currencyDes}</option>

                                                                </c:if>


                                                            </c:forEach>


                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Production Mode</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select  name="promode" style="width: 168px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="pro" items="${sessionScope.SessionObject.productionModeList}">

                                                                <c:if test="${cardBinBean.productionMode==pro.productionModeCode}">
                                                                    <option value="${pro.productionModeCode}" selected>${pro.description}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.productionMode!=pro.productionModeCode}">
                                                                    <option value="${pro.productionModeCode}">${pro.description}</option>

                                                                </c:if>


                                                            </c:forEach>


                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select  name="status"  class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${cardBinBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

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
                                                        <input type="submit" value="Add" name="add" class="defbutton" onclick="invokeAdd()">
                                                        <input type="button" value="Reset" name="reset" class="defbutton" onclick="invokeResetCardBin()">
                                                    </td>
                                                    <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CARDPBIN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 10px"></td></tr>

                                            </tbody>
                                        </table>
                                    </form>

                                </c:if>

                                <%-- -------------------------add form end -------------------------------- --%>



                                <%-- -------------------------view form start -------------------------------- --%>
                                <c:if test="${operationtype=='view'}" >
                                    <form action="" method="POST" name="viewcardbinform">

                                        <table border="0">

                                            <tr>
                                                <td>Card Product Code</td>
                                                <td>:</td>
                                                <td>${cardBinBean.binNumber}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>
                                            <tr>
                                                <td>Description</td>
                                                <td>:</td>
                                                <td>${cardBinBean.description}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td>On Us Status</td>
                                                <td>:</td>
                                                <td>${cardBinBean.onus}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td>Card Type </td>
                                                <td>:</td>
                                                <td>${cardBinBean.cardTypeDes}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td>Card Domain</td>
                                                <td>:</td>
                                                <td>${cardBinBean.binType}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td>Currency Type</td>
                                                <td>:</td>
                                                <td>${cardBinBean.currTypeDes}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>                                          

                                            <tr>
                                                <td>Production Mode </td>
                                                <td>:</td>
                                                <td>${cardBinBean.productionModeDes}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td>Status </td>
                                                <td>:</td>
                                                <td>${cardBinBean.statusDes}</td>
                                            </tr>

                                            <tr> <td style="height:12px;"></td></tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td style="width: 300px">
                                                    <input type="button" value="Back" name="Back" class="defbutton" onclick="invokeResetCardBin()"/> </td>
                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CARDPBIN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>


                                            </tr>
                                            <tr><td style="height: 10px"></td></tr>
                                        </table>
                                    </form>

                                </c:if>
                                <%-- -------------------------view form end -------------------------------- --%>

                                <%-- -------------------------update form start -------------------------------- --%>
                                <c:if test="${operationtype=='update'}" >
                                    <form method="POST" action="" name="updatecardbinform">

                                        <table border="0">

                                            <tbody>
                                                <tr>
                                                    <td style="width: 150px;">BIN Number</td>
                                                    <td><font style="color: red;">*</font>&nbsp;</td>
                                                    <td><input type="text"  readonly="true" value="${cardBinBean.binNumber}" name="binnumber" maxlength="6"></td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Description</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><input type="text"  name="binnumberdes" value="${cardBinBean.description}" maxlength="64"></td>
                                                    <td></td>
                                                </tr>
                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td></td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <c:if test="${cardBinBean.onus=='YES'}">
                                                            <input type="radio" name="onus" value="YES" checked="true"/> On Us
                                                        </c:if>

                                                        <c:if test="${cardBinBean.onus!='YES'}">
                                                            <input type="radio" name="onus" value="YES" /> On Us
                                                        </c:if>

                                                        <c:if test="${cardBinBean.onus=='NO'}">
                                                            <input type="radio" name="onus" value="NO" checked="true"/> Off Us
                                                        </c:if>

                                                        <c:if test="${cardBinBean.onus!='NO'}">
                                                            <input type="radio" name="onus" value="NO" /> Off Us
                                                        </c:if>

                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Card Type </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="cardtype" style="width: 168px;">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="cardType" items="${cardTypeList}">
                                                                <c:if test="${cardBinBean.cardType==cardType.key}">
                                                                    <option value="${cardType.key}" selected>${cardType.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.cardType!=cardType.key}">
                                                                    <option value="${cardType.key}" >${cardType.value}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Card Domain</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select  name="bintype" style="width: 168px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="bin" items="${sessionScope.SessionObject.cardDomainsList}">

                                                                <c:if test="${cardBinBean.binType==bin.domainID}">
                                                                    <option value="${bin.domainID}" selected>${bin.descrip}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.binType!=bin.domainID}">
                                                                    <option value="${bin.domainID}">${bin.descrip}</option>

                                                                </c:if>


                                                            </c:forEach>


                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Currency Type</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select  name="currType" style="width: 168px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyDetailList}">

                                                                <c:if test="${cardBinBean.currTypeCode==curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}" selected>${curr.currencyDes}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.currTypeCode!=curr.currencyCode}">
                                                                    <option value="${curr.currencyCode}">${curr.currencyDes}</option>

                                                                </c:if>


                                                            </c:forEach>


                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Production Mode</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select  name="promode" style="width: 168px;">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="pro" items="${sessionScope.SessionObject.productionModeList}">

                                                                <c:if test="${cardBinBean.productionMode==pro.productionModeCode}">
                                                                    <option value="${pro.productionModeCode}" selected>${pro.description}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.productionMode!=pro.productionModeCode}">
                                                                    <option value="${pro.productionModeCode}">${pro.description}</option>

                                                                </c:if>


                                                            </c:forEach>


                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>

                                                <tr>
                                                    <td>Status</td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td><select  name="status"  class="inputfield-mandatory" style="width: 168px;">
                                                            <option value="" selected>--SELECT--</option>
                                                            <c:forEach var="status" items="${sessionScope.SessionObject.statusList}">

                                                                <c:if test="${cardBinBean.status==status.statusCode}">
                                                                    <option value="${status.statusCode}" selected>${status.description}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.status!=status.statusCode}">
                                                                    <option value="${status.statusCode}">${status.description}</option>

                                                                </c:if>


                                                            </c:forEach>
                                                        </select>


                                                    </td>
                                                    <td></td>
                                                </tr>

                                            </tbody>
                                        </table>

                                        <table>
                                            <tr> <td style="height:12px;"><input type="hidden" name="oldvalue" value="${oldval}" /></td></tr>
                                            <tr>
                                                <td style="width: 163px;"></td>
                                                <td><input type="submit" value="Update" name="update" onclick="invokeUpdate()" class="defbutton"/></td>
                                                <td> <input type="button" value="Reset" name="reset" onclick="invokeUpdateview('${cardBinBean.binNumber}')" class="defbutton"/></td>
                                                <td> <input type="button" value="Back" name="back" onclick="invokeResetCardBin()" class="defbutton"/></td>

                                                <td><a  href="#"  onclick="invokeHistory('<%=PageVarList.CARDPBIN%>')"><img src="<%=request.getContextPath()%>/resources/images/history-icon.gif" width="40" height="40" /></a></td>                                            
                                            </tr>
                                            <tr><td style="height: 10px"></td></tr>
                                        </table>
                                    </form>
                                </c:if>
                                <%-- -------------------------update form end -------------------------------- --%>





                                <table  border="1"  class="display" id="scoreltableview4">
                                    <thead>
                                        <tr>
                                            <th>BIN Number</th>
                                            <th>Description</th>

                                            <th>On Us Status</th>
                                            <th>Card Type</th>

                                            <th>Card Domain</th>
                                            <th>Currency Type</th>

<!--                                            <th>Production Mode</th>-->
                                            <th>Status</th>

                                            <th>View</th>
                                            <th>Update</th>
                                            <th >Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody >
                                        <c:forEach var="cardbin" items="${cardBinBeanLst}">
                                            <tr>
                                                <td >${cardbin.binNumber}</td>
                                                <td >${cardbin.description}</td>

                                                <td >${cardbin.onus}</td>
                                                <td >${cardbin.cardTypeDes}</td>

                                                <td >${cardbin.binType}</td>
                                                <td >${cardbin.currTypeDes}</td>

<!--                                                <td >${cardbin.productionModeDes}</td>-->
                                                <td >${cardbin.statusDes}</td>


                                                <td><a href='${pageContext.request.contextPath}/ViewCardBinServlet?cardBinNumber=<c:out value="${cardbin.binNumber}"></c:out>'>View</a></td>
                                                <td><a href='${pageContext.request.contextPath}/UpdateCardBinFormServlet?cardBinNumber=<c:out value="${cardbin.binNumber}"></c:out>'>Update</a></td>
                                                <td><a  href='#' onclick="ConfirmDelete('${cardbin.binNumber}')">Delete</a></td>

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


