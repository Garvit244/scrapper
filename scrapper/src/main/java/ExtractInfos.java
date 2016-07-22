import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.IOException;
import java.util.*;

public class ExtractInfos {

	private static List<String> leaveReasons = new ArrayList<String>();

	ExtractInfos() {
		leaveReasons.add("OOO");
		leaveReasons.add("WFH");
		leaveReasons.add("LEAVE");
	}

	public Map<String, String> extract(Message[] messages) throws Exception {
		Map<String, String> userDateMap = new HashMap<String, String>();
		try {
			for (Message message : messages) {
				String in = message.getFrom()[0].toString();
				String subject = message.getSubject();
				for(String leave : leaveReasons) {
					if (subject.toUpperCase().contains(leave)) {
						LeaveRange leaveRange = extractDays(message);
						if(leaveRange != null) {
							String currentLeaveRange = leaveRange.toString();
							if (userDateMap.get(in) != null)
								currentLeaveRange += " " + userDateMap.get(in);
							userDateMap.put(in, currentLeaveRange);
						}
					}
				}
			}
		}catch (Exception e) {
			throw e;
		}
		return userDateMap;
	}

	public LeaveRange extractDays(Message message) throws MessagingException, IOException {
		String subject = message.getSubject().toLowerCase();
		String body = ((Multipart)message.getContent()).getBodyPart(0).getContent().toString();
		String month = getStringMonth(message.getReceivedDate().getMonth()+1);
		if(contains(subject,"today") || contains(body,"today")) {
			return new LeaveRange(message.getReceivedDate().toString(), message.getReceivedDate().toString(), month);
		} else if(contains(subject,"tomorrow") || contains(body,"tomorrow")){
			String date = String.valueOf(new Date(message.getReceivedDate().getTime()+(1000*60*24*60)));
			return new LeaveRange(date, date, month);
		} else if((contains(subject,"today") && contains(subject,"tomorrow")) || (contains(subject,"today") && contains(subject,"tomorrow"))){
			String start =  message.getReceivedDate().toString();
			String end = String.valueOf(new Date(message.getReceivedDate().getTime()+(1000*60*24*60)));
			return new LeaveRange(start, end, month);
		} else if((contains(subject, "next") && contains(subject, "two")) || contains(subject, "next") && contains(subject, "two")){
			String start =  message.getReceivedDate().toString();
			String end = String.valueOf(new Date(message.getReceivedDate().getTime()+(2000*60*24*60)));
			return new LeaveRange(start, end, month);
		} else if((contains(subject, "next") && contains(subject, "three")) || (contains(subject, "next") && contains(subject, "three"))){
			String start =  message.getReceivedDate().toString();
			String end = String.valueOf(new Date(message.getReceivedDate().getTime()+(3000*60*24*60)));
			return new LeaveRange(start, end, month);
		} else if((contains(subject, "next") && contains(subject, "four")) || (contains(subject, "next") && contains(subject, "four"))){
			String start =  message.getReceivedDate().toString();
			String end = String.valueOf(new Date(message.getReceivedDate().getTime()+(4000*60*24*60)));
			return new LeaveRange(start, end, month);
		} else if((contains(subject, "next") && contains(subject, "week")) || (contains(subject, "next") && contains(subject, "week"))){
			String start =  message.getReceivedDate().toString();
			String end = String.valueOf(new Date(message.getReceivedDate().getTime()+(7000*60*24*60)));
			return new LeaveRange(start, end, month);
		} else if(contains(subject, "1") || contains(subject,"2") || contains(subject,"3")){
				return getLeave(subject);
		} else
			return getLeave(body);
	}

	private static boolean contains(String str, String pattern){
		return str.toLowerCase().contains(pattern.toLowerCase());
	}

	public static LeaveRange getLeave(String str){
		String[] allMatches = new String[2];
		int i =0;
		Scanner scanner = new Scanner(str).useDelimiter("\\D+");
		while(scanner.hasNext()) {
			allMatches[i++] = scanner.next();
		}

		if(i==1)
			allMatches[1] = allMatches[0];

		String month = str.substring(str.indexOf(allMatches[1])+allMatches[1].length(), str.length());
		return new LeaveRange(allMatches[0], allMatches[1], month);
	}

	private String getStringMonth(int month) {
		String monthString;
		switch (month) {
		case 1:  monthString = "January";       break;
		case 2:  monthString = "February";      break;
		case 3:  monthString = "March";         break;
		case 4:  monthString = "April";         break;
		case 5:  monthString = "May";           break;
		case 6:  monthString = "June";          break;
		case 7:  monthString = "July";          break;
		case 8:  monthString = "August";        break;
		case 9:  monthString = "September";     break;
		case 10: monthString = "October";       break;
		case 11: monthString = "November";      break;
		case 12: monthString = "December";      break;
		default: monthString = "Invalid month"; break;
		}
		return monthString;
	}
}
