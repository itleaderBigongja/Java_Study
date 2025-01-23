package Lambda.Lambda_StreamAPI.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 파일에서 특정 단어 빈도수 계산
public class Lambda_StreamAPI_FindWordInFile {

	public static void main(String[] args) {
		// 파일 경로
		Path filePath = Paths.get("src/fileDir/sample_1.txt");
		
		// 파일에서 찾을 단어
		String targetWord = "itleaderBigongja";
		
		// 파일에서 라인별로 스트림 생성
		try(Stream<String>lines = Files.lines(filePath)){
			// 라인을 공백, 구두점 등으로 분리하여 단어 스트림 생성
			long count = lines.flatMap(line -> Arrays.stream(line.split("[\\s.,;?!'\"()\\-]+")))
							   // 대소문자 구분 없이 찾을 단어와 일치하는 단어 필터링
							  .filter(word -> word.equalsIgnoreCase(targetWord))
							   // 일치하는 단어 개수 체크
							  .count();
			System.out.println("내가 찾는 단어는 : " + targetWord + "이며, 단어의 개수는 " + count + "개 입니다.");
		} catch (IOException e) {
			// TODO: handle exception
			System.err.println("Error reading file: " + e.getMessage());
		}
		
		
		// 파일에서 라인별로 스트림 생성
		try(Stream<String> lines = Files.lines(filePath)){
			Map<String, Long> wordCounts	// 정규 표현식으로 라인을 단어 스트림으로 변환
					= lines.flatMap(line -> Pattern.compile("[\\s.,;?!'\"()\\-]+").splitAsStream(line))
					       .map(String::toLowerCase)
					       .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
			
	        System.out.println(wordCounts); // 단어와 빈도수 출력		
		} catch (IOException e) {
			System.err.println("Error reading file:" + e.getMessage());
		}
	}
}
