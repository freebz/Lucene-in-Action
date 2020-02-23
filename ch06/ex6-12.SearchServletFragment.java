// 예제 6.12  웹 애플리케이션에서 클라이언트 브라우저의 로케일을 확인하고 QueryParser에 적용

public class SearchServletFragment extends HttpServlet {

  private IndexSearcher searcher;

  protected void doGet(HttpServletRequest request,
		       HttpServletResponse response)
         throws ServletException, IOException {

    QueryParser parser = new NumericDateRangeQueryParser(
			      Version.LUCENE_30,
			      "contents",
			      new StandardAnalyzer(Version.LUCENE_30));

    parser.setLocale(request.getLocale());
    parser.setDateResolution(DateTools.Resolution.DAY);

    Query query = null;
    try {
      query = parser.parse(request.getParameter("q"));
    } catch (ParseException e) {
      e.printStackTrace(System.err);
    }

    TopDocs docs = searcher.search(query, 10);
  }
}
