package listener;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import domain.LunchVO;

public class Parser {
	public LunchVO getMenu(String menu) {
		String url = "http://www.y-y.hs.kr/lunch.view?date=" + menu;
		LunchVO lunch = new LunchVO();
		
		try {
			Document doc = Jsoup.connect(url).get();
			
			Element span = doc.selectFirst("#morning .menuName span");
			
			lunch.setDate(menu);
			lunch.setMenuString(span.html());
		} catch (IOException e) {
			lunch.setMenuString("메뉴 없음");
		}
		
		return lunch;
	}
}
