<%-- 
    Document   : binmanagementhome
    Created on : Mar 6, 2013, 10:47:38 AM
    Author     : asitha_l
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
        <script>

            function invokeUpdate()
            {

                document.updatecardbinform.action = "${pageContext.request.contextPath}/UpdateCardBinMgtServlet";
                document.updatecardbinform.submit();

            }

            function invokeUpdateview(binNumber)
            {
                window.location = "${pageContext.request.contextPath}/LoadUpdateBinMgtServlet?binNumber=" + binNumber;
            }

            
            function ConfirmDelete(binNumber)
            {
                $("#dialog-confirm").html("<p>Do you really want to delete "+binNumber+" bin number ?</p>");
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
                            window.location = "${pageContext.request.contextPath}/DeleteBinMgtServlet?binNumber=" + binNumber;
                        }
                    }
                });

            }

            function invokeAdd()
            {

                document.addbinmgtform.action = "${pageContext.request.contextPath}/AddBinMgtServlet";
                document.addbinmgtform.submit();

            }

            function invokeResetCardBin()
            {

                window.location = "${pageContext.request.contextPath}/LoadBinMgtServlet";

            }

            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.BIN_MGT%>'
                        },
                function (data) {

                    if (data != '') {
                        $('.center').text(data)
                        var heading = data.split('→');
                        $('.heading').text(heading[1]);

                    }


                });

            }

        </script>  

        <title>EPIC CMS BIN MANAGEMENT</title>
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
                                    <form method="POST" action="" name="addbinmgtform">

                                        <table border="0">

                                            <tbody>

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
                                                            <!--<option value="" >--SELECT--</option>-->
                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyMap}">

                                                                <c:if test="${cardBinBean.currTypeCode==curr.key}">
                                                                    <option value="${curr.key}" selected>${curr.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.currTypeCode!=curr.key}">
                                                                    <c:if test="${curr.key eq '144'}">
                                                                        <option value="${curr.key}" selected="true">${curr.value}</option>
                                                                    </c:if>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                    <td></td>
                                                </tr>                                               

                                                <tr><td style="height: 5px"></td></tr>                                                                                              

                                                <tr>
                                                    <td>Sending Channel </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="sendchanel" style="width: 168px;">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="send" items="${sessionScope.SessionObject.aquireChannelList}">
                                                                <c:if test="${cardBinBean.sendingChannel==send.channelID}">
                                                                    <option value="${send.channelID}" selected>${send.channelName}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.sendingChannel!=send.channelID}">
                                                                    <option value="${send.channelID}" >${send.channelName}</option>
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
                                    <form action="" method="POST" name="viewbinform">

                                        <table border="0">

                                            <tr>
                                                <td style="width: 150px"><b>Bin Number</b></td>
                                                <td style="width: 20px">:</td>
                                                <td>${cardBinBean.binNumber}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>
                                            <tr>
                                                <td style="width: 150px"><b>Description</b></td>
                                                <td style="width: 20px">:</td>
                                                <td>${cardBinBean.description}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td style="width: 150px"><b>On Us Status</b></td>
                                                <td style="width: 20px">:</td>
                                                <td>${cardBinBean.onus}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td style="width: 150px"><b>Card Type</b></td>
                                                <td style="width: 20px">:</td>
                                                <td>${cardBinBean.cardTypeDes}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td style="width: 150px"><b>Card Domain</b></td>
                                                <td style="width: 20px">:</td>
                                                <td>${cardBinBean.binType}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td style="width: 150px"><b>Currency Type</b></td>
                                                <td style="width: 20px">:</td>
                                                <td>${cardBinBean.currTypeDes}</td>
                                            </tr>

                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td style="width: 150px"><b>Sending Channel</b></td>
                                                <td style="width: 20px">:</td>
                                                <td>${cardBinBean.sendingChannelDes}</td>
                                            </tr>
                                            <tr><td style="height: 5px"></td></tr>

                                            <tr>
                                                <td style="width: 150px"><b>Status</b></td>
                                                <td style="width: 20px">:</td>
                                                <td>${cardBinBean.statusDes}</td>
                                            </tr>

                                            <tr> <td style="height:12px;"></td></tr>
                                            <tr>
                                                <td></td>
                                                <td></td>
                                                <td style="width: 300px">
                                                    <input type="button" value="Back" name="Back" class="defbutton" onclick="invokeResetCardBin()"/> 
                                                </td>                                               
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
                                                    <td><select  name="" style="width: 168px;" disabled="true">
                                                            <option value="" >--SELECT--</option>
                                                            <c:forEach var="curr" items="${sessionScope.SessionObject.currencyMap}">

                                                                <c:if test="${cardBinBean.currTypeCode==curr.key}">
                                                                    <option value="${curr.key}" selected>${curr.value}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.currTypeCode!=curr.key}">
                                                                    <option value="${curr.key}">${curr.value}</option>

                                                                </c:if>


                                                            </c:forEach>


                                                        </select>
                                                    </td>
                                                    <td>
                                                        <c:forEach var="curr" items="${sessionScope.SessionObject.currencyMap}">
                                                            <c:if test="${cardBinBean.currTypeCode==curr.key}">
                                                                <input type="hidden" value="${curr.key}" name="currType"/>
                                                            </c:if>
                                                        </c:forEach>
                                                    </td>
                                                </tr>

                                                <tr><td style="height: 5px"></td></tr>                                                                                              

                                                <tr>
                                                    <td>Sending Channel </td>
                                                    <td><font style="color: red;">*</font></td>
                                                    <td>
                                                        <select  name="sendchanel" style="width: 168px;">
                                                            <option value="" >--SELECT--</option>

                                                            <c:forEach var="send" items="${sessionScope.SessionObject.aquireChannelList}">
                                                                <c:if test="${cardBinBean.sendingChannel==send.channelID}">
                                                                    <option value="${send.channelID}" selected>${send.channelName}</option>
                                                                </c:if>
                                                                <c:if test="${cardBinBean.sendingChannel!=send.channelID}">
                                                                    <option value="${send.channelID}" >${send.channelName}</option>
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
                                            </tr>
                                            <tr><td style="height: 10px"></td></tr>
                                        </table>
                                    </form>
                                </c:if>
                                <%-- -------------------------update form end -------------------------------- --%>

                                <table  border="1"  class="display" id="scoreltableview2">
                                    <thead>
                                        <tr>
                                            <th>BIN Number</th>
                                            <th>Description</th>

                                            <th>On Us Status</th>
                                            <th>Card Type</th>

                                            <th>Card Domain</th>
                                            <th>Currency Type</th>

                                            <th>Sending Channel</th>
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

                                                <td >${cardbin.sendingChannelDes}</td>
                                                <td >${cardbin.statusDes}</td>


                                                <td><a href='${pageContext.request.contextPath}/ViewBinMgtServlet?binNumber=<c:out value="${cardbin.binNumber}"></c:out>'>View</a></td>
                                                <td><a href='${pageContext.request.contextPath}/LoadUpdateBinMgtServlet?binNumber=<c:out value="${cardbin.binNumber}"></c:out>'>Update</a></td>
                                                <td><a  href='#' onclick="ConfirmDelete('${cardbin.binNumber}')">Delete</a></td>

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