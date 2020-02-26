// 예제 7.4  아파치 커먼스 다이제스터 프로젝트로 XML 분석

public class DigesterXMLDocument {

  private Digester dig;
  private static Document doc;

  public DigesterXMLDocument() {

    dig = new Digester();
    dig.setValidating(false);

    dig.addObjectCreate("address-book", DigesterXMLDocument.class);
    dig.addObjectCreate("address-book/contact", Contact.class);

    dig.addSetProperties("address-book/contact", "type", "type");

    dig.addCallMethod("address-book/contact/name",
		      "setName", 0);
    dig.addCallMethod("address-book/contact/address",
		      "setAddress", 0);
    dig.addCallMethod("address-book/contact/city",
		      "setCity", 0);
    dig.addCallMethod("address-book/contact/province",
		      "setProvince", 0);
    dig.addCallMethod("address-book/contact/postalcode",
		      "setPostalcode", 0);
    dig.addCallMethod("address-book/contact/country",
		      "setCountry", 0);
    dig.addCallMethod("address-book/contact/telephone",
		      "setTelephone", 0);

    dig.addSetNext("address-book/contact", "populateDocument");
  }

  public synchronized Document getDocument(InputStream is)
      throws DocumentHandlerException {

    try {
      dig.parse(is);
    }
    catch (IOException e) {
      throw new DocumentHandlerException(
	  "Cannot parse XML document", e);
    }
    catch (SAXException e) {
      throw new DocumentHandlerException(
	  "Cannot parse XML document", e);
    }

    return doc;
  }

  public void populateDocument(Contact contact) {

    doc = new Document();

    doc.add(new Field("type", contact.getType(), Field.Store.YES,
		      Field.Index.NOT_ANALYZED));
    doc.add(new Field("name", contact.getName(), Field.Store.YES,
		      Field.Index.NOT_ANALYZED));
    doc.add(new Field("address", contact.getAddress(), Field.Store.YES,
		      Field.Index.NOT_ANALYZED));
    doc.add(new Field("city", contact.getCity(), Field.Store.YES,
		      Field.Index.NOT_ANALYZED));
    doc.add(new Field("province", contact.getProvince(), Field.Store.YES,
		      Field.Index.NOT_ANALYZED));
    doc.add(new Field("postalcode", contact.getPostalcode(),
		      Field.Store.YES,
		      Field.Index.NOT_ANALYZED));
    doc.add(new Field("country", contact.getCountry(), Field.Store.YES,
		      Field.Index.NOT_ANALYZED));
    doc.add(new Field("telephone", contact.getTelephone(),
		      Field.Store.YES,
		      Field.Index.NOT_ANALYZED));
  }

  public static void main(String[] args) throws Exception {
    DigesterXMLDocument handler = new DigesterXMLDocument();
    Document doc =
         handler.getDocument(new FileInputStream(new File(args[0])));
    System.out.println(doc);
  }
}
