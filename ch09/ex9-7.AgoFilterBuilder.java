// 예제 9.7  FilterBuilder를 상속받아 별도의 XML 태그 처리 기능 추가

public class AgoFilterBuilder implements FilterBuilder {

  static HashMap<String,Integer> timeUnits=new HashMap<String,Integer>();

  public Filter getFilter(Element element) throws ParserException {
    String fieldName = DOMUtils.getAttributeWithInheritanceOrFail(
				       element, "fieldName");
    String timeUnit = DOMUtils.getAttribute(element, "timeUnit", "days");
    Integer calUnit = timeUnits.get(timeUnit);
    if (calUnit == null) {
      throw new ParserException("Illegal time unit:"
				+timeUnit+
				" - must be days, months of years");
    }
    int agoStart = DOMUtils.getAttribute(element, "from",0);
    int agoEnd = DOMUtils.getAttribute(element, "to", 0);
    if (agoStart < agoEnd) {
      int oldAgoStart = agoStart;
      agoStart = agoEnd;
      agoEnd = oldAgoStart;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    Calendar start = Calendar.getInstance();
    start.add(calUnit, agoStart*-1);

    Calendar end = Calendar.getInstance();
    end.add(calUnit, agoEnd*-1);

    return NumericRangeFilter.newIntRange(
	   fieldName,
	   Integer.valueOf(sdf.format(start.getTime())),
	   Integer.valueOf(sdf.format(end.getTime())),
	   true, true);
  }

  static {
    timeUnits.put("days", Calendar.DAY_OF_YEAR);
    timeUnits.put("months", Calendar.MONTH);
    timeUnits.put("years", Calendar.YEAR);
  }
}
