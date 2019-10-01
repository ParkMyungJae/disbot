package listener;

import domain.LunchVO;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LunchListener extends ListenerAdapter {
	
	private Parser parser = new Parser();
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String msg = event.getMessage().getContentRaw();
		
		if(msg.startsWith("!yy")) {
			int idx = msg.indexOf(" "); // 가장 처음으로 나오는 공백의 index
			if(idx < 0) {
//				event.getChannel().sendMessage("올바른 명령어를 입력하세요").queue();
				sayMsg(event, "올바른 명령어를 입력하세요.");
				return;
			}
			//!yy가 짤려나감.
			String cmd = msg.substring(idx + 1);
			int paramIdx = cmd.indexOf(" ");
			String param = "";
			if(paramIdx >= 0) {
				param = cmd.substring(paramIdx) + 1;
				cmd = cmd.substring(0, paramIdx);
			}
			//if문이 끝나면 cmd에는 lunch가 있고 param에는 20191001이 있게 된다.
			
			switch(cmd) {
			case "echo" :
				if(param.isEmpty()) {
					sayMsg(event, "echo명령은 할 말을 입력해야 합니다");
				}else {
					sayMsg(event, param);
				}
				break;
				
			case "lunch" :
				//param이 올바른 값인지 검사하는 로직이 들어가야함.
				LunchVO lunch = parser.getMenu(param);
				sayMsg(event, lunch.getDate() + "의 메뉴는 " + lunch.getMenuString());
				parser.getMenu(param);
				break;
			case "help" :
				String helpText = "skillbot의 명령은 다음과 같습니다. \n";
				helpText += "echo : 뒷북을 해드립니다. \n";
				helpText += "lunch : 급식을 알려줍니다. \n";
				helpText += "help : 디스봇의 명령어 사용법을 알려줍니다.";
				
				sayMsg(event, helpText);
				break;
			default :
				sayMsg(event, "알 수 없는 명령입니다.");
			}
		}
	}	

	private void sayMsg(MessageReceivedEvent e, String msg) {
		e.getChannel().sendMessage(msg).queue();
	}
}
