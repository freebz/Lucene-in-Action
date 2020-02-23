// 예제 6.14  SpecialsFilter를 통해 외부 정보를 불러와 필터로 활용

public class SpecialsFilter extends Filter {
  private SpecialsAccessor accessor;

  public SpecialsFilter(SpecialsAccessor accessor) {
    this.accessor = accessor;
  }

  public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
    OpenBitSet bits = new OpenBitSet(reader.maxDoc());

    String[] isbns = accessor.isbns();

    int[] docs = new int[1];
    int[] freqs = new int[1];

    for (String isbn : isbns) {
      if (isbn != null) {
	TermDocs termDocs =
	     reader.termDocs(new Term("isbn", isbn));
	int count = termDocs(docs, freqs);
	if (count == 1) {
	  bits.set(docs[0]);
	}
      }
    }

    return bits;
  }
}
