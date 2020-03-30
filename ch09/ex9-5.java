// 예제 9.5  XmlQueryParser를 사용해 검색 요청을 처리한다.

protected void doPost(HttpServletRequest request, HttpServletResponse
       response)
     throws ServletException, IOException {

  // 폼 필드에 입력된 값을 모두 찾아와 Properties 객체에 추가한다.
  Properties completedFromFields = new Properties();
  Enumeration pNames = request.getParameterNames();
  while(pNames.hasMoreElements()) {
    String propName = (String) pNames.nextElement();
    String value = request.getParameter(propName);
    if((value!=null)&&(value.trim().length()>0)) {
      completedFormFields.setProperty(propName, value);
    }
  }

  try {

    // 사용자가 폼 필드에 입력한 조건을 기준으로
    // XML 생성
    org.w3c.dom.Document xmlQuery =
      queryTemplateManager.getQueryAsDOM(completedFormFields);

    // XML을 다시 루씬 질의 객체로 변환한다.
    Query query = xmlParser.getQuery(xmlQuery.getDocumentElement());

    // 루씬 질의로 검색
    TopDocs topDocs = searcher.search(query, 10);

    // 결과를 취합해서 JSP 페이지에 넘겨준다.
    if(topDocs!=null) {
      ScoreDoc[] sd = topDocs.scoreDocs;
      Document[] results = new Document[sd.length];
      for (int i = 0; i < results.length; i++) {
	results[i] = searcher.doc(sd[i].doc);
      }
      request.setAttribute("results", results);
    }
    RequestDispatcher dispatcher =
      getServerContext().getRequestDispatcher("/index.jsp");
    dispatcher.forward(request,response);
  }
  catch(Exception e) {
    throws new ServletExecption("Error processing query",e);
  }
}
