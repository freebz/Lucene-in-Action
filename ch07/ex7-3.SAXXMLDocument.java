// 예제 7.3  SAX API를 사용해 주소록 XML 파싱

public class SAXXMLDocument extends DefualtHandler {

  private StringBuilder elementBuffer = new StringBuilder();
  private Map<String,String> attributeMap = new HashMap<String,String>();

  private Document doc;

  public Document getDocument(InputStream is)
       throws DocumentHandlerException {

    SAXParserFactory spf = SAXParserFactory.newInstance();
    try {
      SAXParser parser = spf.newSAXParser();
      parser.parse(is, this);
    } catch (Exception e) {
      throw new DocumentHandlerException(
	  "Cannot parse XML document", e);
    }

    return doc;
  }

  public void startDocument() {
    doc = new Document();
  }

  public void startElement(String uri, String localName,
       String qName, Attribute atts)
       throws SAXException {

    elementBuffer.setLength(0);
    attributeMap.clear();
    int numAtts = atts.getLength();
    if (numAtts > 0) {
      for (int i = 0; i < numAtts; i++) {
	attributeMap.put(atts.getQName(i), atts.getValue(i));
      }
    }
  }

  public void characters(char[] text, int start, int length) {
    elementBuffer.append(text, start, length);
  }

  public void endElement(String uri, String localName,
			 String qName)
      throws SAXException {
    if (qName.equals("address-book")) {
      return;
    }
    else if (qName.equals("contact")) {
      for (Entry<String,String> attribute : attributeMap.entrySet()) {
	String attName = attribute.getKey();
	String attValue = attribute.getValue();
	doc.add(new Field(attName, attValue, Field.Store.YES,
			  Field.Index.NOT_ANALYZED));
      }
    }
    else {
      doc.add(new Field(qName, elementBuffer.toString(), Field.Store.YES,
	  Field.Index.NOT_ANALYZED));
    }
  }

  public static void main(String args[]) throws Exception {
    SAXXMLDocument handler = new SAXXMLDocument()
      Document doc = handler.getDocument(
	  new FileInputStream(new File(args[0])));
    System.out.println(doc);
  }
}
