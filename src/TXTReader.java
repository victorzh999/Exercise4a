import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TXTReader {

	private static String sql1 = " ";

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String fileName = "/Users/vzhang/eclipse-workspace/Exercise4a/assignment1.sql";
		List<String> lines = Files.lines(Paths.get(fileName)).collect(Collectors.toList());

		List<String> sqls = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++){
			if (lines.get(i).startsWith("--")){
				continue;
			}
			else if (lines.get(i).contains(";")) {
				sqls.add(sql1 + lines.get(i));
				sql1 = " ";
			}
			else {
				sql1  += lines.get(i);
			}
		}
			
		for (String sql: sqls) {
			sql = sql.trim();
			if (sql.startsWith("use")){
				String sqlStatement = sql;
				System.out.println(sqlStatement);
			}
			else {
				String sqlStatement1 = sql;
				System.out.println(sqlStatement1);
			}
		}
		
		fileName = "/Users/vzhang/eclipse-workspace/Exercise4a/assignment1_triggers.sql";
		lines = Files.lines(Paths.get(fileName)).collect(Collectors.toList());

		sql1 = " ";
		sqls = new ArrayList<>();
		for (int i = 0; i < lines.size(); i++){
			if (lines.get(i).startsWith("--")){
				continue;
			}
			else if (lines.get(i).contains("END;")) {
				sqls.add(sql1 + lines.get(i));
				sql1 = " ";
			}
			else {
				sql1  += lines.get(i);
			}
		}
			
		for (String sql: sqls) {
			sql = sql.trim();
			if (sql.startsWith("use")){
				String sqlStatement = sql;
				System.out.println(sqlStatement);
			}
			else {
				String sqlStatement1 = sql;
				System.out.println(sqlStatement1);
			}
		}
		
	}

}
