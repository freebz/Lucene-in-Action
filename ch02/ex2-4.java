// 예제 2.4  문서와 필드에 중요도 지정

Document doc = new Document();
String senderEmail = getSenderEmail();
String senderName = getSenderName();
String subject = getSubject();
String body = getBody();

doc.add(new Field("senderEmail", senderEmail,
		  Field.Store.YES,
		  Field.Index.NOT_ANALYZED));
doc.add(new Field("senderName", senderName,
		  Field.Store.YES,
		  Field.Index.ANALYZED));
doc.add(new Field("subject", subject,
		  Field.Store.YES,
		  Field.Index.ANALYZED));
doc.add(new Field("body", body,
		  Field.Store.NO,
		  Field.Index.ANALYZED));
String lowerDomain = getSenderDomain().toLowerCase();

if (isImportant(lowerDomain)) {
  doc.setBoost(1.5F);
} else if (isUnimportant(lowerDomain)) {
  doc.setBoost(0.1F);
}
writer.addDocument(doc);
