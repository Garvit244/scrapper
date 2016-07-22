

public class Test {

	public static void testDateOOO() {
		ExtractInfos extractInfos = new ExtractInfos();
		String subject = "OOO 12-13";
		LeaveRange actualValue = extractInfos.getLeave(subject);
		LeaveRange expected = new LeaveRange("12", "13", "July");
		if(actualValue.equals(expected))
			System.out.println("Success");
	}

	public static void testDateWFH() {
		ExtractInfos extractInfos = new ExtractInfos();
		String subject = "WFH 12 15";
		LeaveRange actualValue = extractInfos.getLeave(subject);
		LeaveRange expected = new LeaveRange("12", "15", "July");
		if(actualValue.equals(expected))
			System.out.println("Success");
	}

	public static void main(String[] args) {
		testDateOOO();
		testDateWFH();
	}
}
