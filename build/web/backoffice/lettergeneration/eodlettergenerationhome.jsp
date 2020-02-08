<%-- 
    Document   : eodlettergenerationhome
    Created on : Apr 5, 2013, 2:34:24 PM
    Author     : jeevan
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
        <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jspdf.min.js"></script>

        <script>
            function settitle() {

                $.post("${pageContext.request.contextPath}/SetNavigationTitleServlet",
                        {pagecode: '<%= PageVarList.LETTER_GEN%>'
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


        <script type="text/javascript">

            function searchLetter() {
                document.searchForm.action = "${pageContext.request.contextPath}/SearchLetterGenerationServlet";
                document.searchForm.submit();
            }
            function resetForm() {
                window.location = "${pageContext.request.contextPath}/LoadLetterGenerationServlet";
            }
//            function GenerateLetter(cardNo) {
//                document.searchForm.action = "${pageContext.request.contextPath}/GenerateLetterServlet?cardNo=" + cardNo;
//                document.searchForm.submit();
//            }
//
//            function generateAllLetters() {
//                document.searchForm.action = "${pageContext.request.contextPath}/GenerateAllLettersServlet";
//                document.searchForm.submit();
//            }
            function generate() {
                var e = document.search.elements.length;


                var searchArray = [];

                for (var i = 0; i < e; i++) {
                    if (document.search.elements[i].checked) {
                        searchArray.push(document.search.elements[i].value);
                    }
                }

                if (searchArray.length > 0) {
                    //                    alert(searchArray.length);
                    //window.location = "${pageContext.request.contextPath}/LetterGenerationServlet?searchArray=" + searchArray;
                    //new
                    $.getJSON("${pageContext.servletContext.contextPath}/LetterGenerationServlet",
                            {searchArray: searchArray},
                    function (jsonobject) {

                        $.each(jsonobject.combo1, function (code, description) {
                            genPdf(code, description);
                        });
                    });
                    ///searchLetter();
                } else {
                    alert("Select at least one row to calculate credit score");

                }

            }
            function checkAll()
            {
                // Initialize your table
                var tbl = $('#tableview').dataTable();

                var e = tbl.fnGetData().length;

                for (var i = 0; i < e; i++) {
                    $('.checkBoxChangecontactDetails').attr('checked', true);
                }



            }
            function uncheckAll() {
                 // Initialize your table
                var tbl = $('#tableview').dataTable();

                var e = tbl.fnGetData().length;

                for (var i = 0; i < e; i++) {
                    $('.checkBoxChangecontactDetails').attr('checked', false);
                }
            }



        </script>
        <script type="text/javascript">
//            function genPdf(letterId) {
//                //alert(letterId);
//                var doc = new jsPDF();
//
//                var specialElementHandlers = {
//                    '#refId': function (element, renderer) {
//                        return true;
//                    }
//                };
//
//                doc.fromHTML($('#' + letterId).get(0), 15, 15, {'width': 170, 'elementHandlers': specialElementHandlers});
//                doc.save('Test.pdf');
//
//            }
            //new
            function genPdf(refId, letterText) {
                //alert(refId);
                //alert(letterText);
                var doc = new jsPDF();

//                var specialElementHandlers = {
//                    '#refId': function (element, renderer) {
//                        return true;
//                    }
//                };

                doc.fromHTML(letterText, 15, 15, {'width': 170});
                doc.save('letter_' + refId + '.pdf');
                // alert("ok");

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
                                <!--                                <table class="tit"> <tr> <td   class="center">  TERMINAL MODEL </td> </tr><tr> <td>&nbsp;</td> </tr></table>-->
                                <table class="tit"> <tr> <td   class="heading"> </td> </tr></table>
                                <script> settitle();</script>

                                <table>
                                    <tr>
                                        <td colspan="3"><font color="#FF0000"><b><c:out value="${errorMsg}"></c:out></b></font>
                                            <font color="green"><b><c:out value="${successMsg}"></c:out></b></font>
                                            </td>
                                        </tr>
                                    </table>
                                <c:if test="${operationtype=='search'}">
                                    <form method="POST" action="${pageContext.request.contextPath}/SearchLetterGenerationServlet" name="searchForm">

                                        <table border="0">

                                            <tbody> 
                                                <tr>
                                                    <td>Letter Type</td>
                                                    <td style="width: 33px"></td>
                                                    <td>
                                                        <select name="letterCategory" style="width:163px">
                                                            <option value="">--SELECT--</option>
                                                            <c:forEach var="tmp" items="${letterTmpList}">
                                                                <c:if test="${letterBean.tmpCode==tmp.templateCode}">
                                                                    <option value="${tmp.templateCode}" selected="true">${tmp.description}</option>
                                                                </c:if>
                                                                <c:if test="${letterBean.tmpCode!=tmp.templateCode}">
                                                                    <option value="${tmp.templateCode}">${tmp.description}</option>
                                                                </c:if>
                                                            </c:forEach>
                                                        </select> 
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>From Date</td>
                                                    <td style="width: 33px"></td>
                                                    <td>
                                                        <input  name="fromdate" maxlength="16" readonly class="inputfeild"value="${letterBean.fromDate}" key="fromdate" id="fromdate"  />
                                                        <script type="text/javascript">
                                                            $(function () {
                                                                $("#fromdate").datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd",
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                        </script>

                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>To Date</td>
                                                    <td style="width: 33px"></td>
                                                    <td>
                                                        <input name="todate" maxlength="16"  readonly class="inputfeild"value="${letterBean.toDate}" key="todate" id="todate" />
                                                        <script type="text/javascript">
                                                            $(function () {
                                                                $("#todate").datepicker({
                                                                    showOn: "button",
                                                                    buttonImage: "<%=request.getContextPath()%>/resources/calender/images/calendar.png",
                                                                    changeMonth: true,
                                                                    changeYear: true,
                                                                    buttonImageOnly: true,
                                                                    dateFormat: "yy-mm-dd",
                                                                    showWeek: true,
                                                                    firstDay: 1
                                                                });
                                                            });
                                                        </script>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr>

                                                <tr>
                                                    <td><input type="hidden" name="redirectValue" value="add" id="redirectValue" /></td>
                                                    <td></td>
                                                    <td></td>
                                                </tr> 

                                                <tr>
                                                    <td></td>
                                                    <td></td>
                                                    <td>                                    
                                                        <input type="submit" value="Search" style="width: 100px;" onclick="searchLetter()"/>
                                                        <!--                                                        <input type="button" value="Generate All" style="width:100px;" onclick="generateAllLetters()"/>-->
                                                        <input type="reset" value="Reset" style="width:100px;" onclick="resetForm()"/>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>


                                    </form>
                                </c:if>
                                </br>
                                <form method="POST" name="search" id="search">
                                    <table border="1" class="display" id="tableview">
                                        <thead>
                                            <tr  style="text-align: center">
                                                <th>Select</th>
                                                <th>Ref Num</th>
                                                <th>Letter Type</th>
                                                <th>ID</th>
                                                <th>ID Type</th>
                                                <th>Status</th>
                                                <!--                                            <th>Generate</th>-->
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach   var="lBean" items="${searchList}">
                                                <tr> 
                                                    <td  style="text-align: center"><input type="checkbox" name="chk" id="chk" class="checkBoxChangecontactDetails"value="${lBean.refNum}"</td>
                                                    <td>${lBean.refNum}</td>
                                                    <td>${lBean.tmpDes}</td>
                                                    <td>${lBean.id}</td>
                                                    <td>${lBean.idType}</td>
                                                    <td>${lBean.statusDes}</td>

<!--                                                <td> <input type="button" value="Generate" name="generateletter" onclick="GenerateLetter('${lBean.tmpCode}')"/></td>-->

                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                    <Input type="button" value="CheckAll" onclick="checkAll()" />
                                    <Input type="button" value="UncheckAll" onclick="uncheckAll()"/>
                                    <input type="button" value="Generate" onclick="generate()"/>

                                </form>


                                <c:forEach var="letter" items="${letterList}" varStatus="theCount">
                                    <div id="${theCount.index}" style="display: none">
                                        ${letter}
                                    </div>  
                                    <a href="javascript:genPdf(${theCount.index})">Download Letter ${theCount.index+1}</a><br/>
                                </c:forEach>

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