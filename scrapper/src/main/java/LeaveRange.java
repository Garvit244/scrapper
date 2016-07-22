import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeaveRange {
	private String start;

	private String end;

	private String month;
}
