package org.example;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import org.riversun.slacklet.Slacklet;
import org.riversun.slacklet.SlackletRequest;
import org.riversun.slacklet.SlackletResponse;
import org.riversun.slacklet.SlackletService;
import org.riversun.xternal.simpleslackapi.SlackUser;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

public class ChickenBot {

	public static void main(String[] args) throws IOException {
		String botToken = ResourceBundle.getBundle("credentials").getString("slack.bot_api_token");
		SlackletService slackService = new SlackletService(botToken);

		//メニュー数(6)
				String menus[] =
						{ "オムライスはいかがですか？\n https://cookpad.com/recipe/2221374",
								"かに玉はいかがですか？\n https://cookpad.com/recipe/5826415",
								"天津飯はいかがですか？\n https://cookpad.com/recipe/5735315",
								"厚焼き玉子はいかがですか？\n https://cookpad.com/recipe/253305",
								"卵豆腐はいかがですか？\n https://beergirl.net/bjrecipe_796/",
								"だし巻き卵はいかがですか？\n https://beergirl.net/bjrecipe_721/",
								"エビの卵とじはいかがですか？\n https://erecipe.woman.excite.co.jp/detail/2894f8d6db79b4c584ecaa65a27362e5.html?_s=3b9ca33b4c27a93c2a2982a4449ba42f"
						};

				String other[] =
						{ "唐揚げはいかがですか？\n https://cookpad.com/recipe/691327",
								"カレーはいかがですか？\n https://www.kurashiru.com/recipes/579409fd-876b-486b-a63d-019b1de6b984",
								"肉じゃがはいかがですか？\n https://www.kikkoman.co.jp/homecook/search/recipe/00004691/index.html",
								"ハンバーグはいかがですか？\n https://app.slack.com/client/TLMV1BT41/CMEUA2VA7",
								"麻婆豆腐はいかがですか？\n https://cookpad.com/recipe/1729093" };
				String another[] = { "!卵以外", "!他のレシピ" , "！卵以外", "！他のレシピ"};

		//日の出クラス
		SunriseSunsetMain s = new SunriseSunsetMain();//日の出クラス

		//チャンネル名
		String channelName = "jtmw";

		//String datetime = datetimeformatter.format(localDateTime);
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				// 定期的に実行したい処理
				LocalDateTime localDateTime = LocalDateTime.now();

				//何時何分
				DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("HH:mm");//指定の書式に日付データを渡す

				//String型に変換
				String datetime = datetimeformatter.format(localDateTime);
				if (Objects.equals(datetime, s.sunriseTime)) {
					slackService.sendMessageTo(channelName, "コケコッコー");
				}
			}
		};

		slackService.start();

		timer.scheduleAtFixedRate(task, 1000, 60000);

		// slackletを追加する

		slackService.addSlacklet(new Slacklet() {

			@Override// ダイレクトメッセージがユーザーからポストされた
			public void onDirectMessagePosted(SlackletRequest req, SlackletResponse resp) {

				// メッセージを投下したユーザーの情報を取得
				SlackUser user = req.getSender();

				// ユーザー情報の中からユーザー名を取り出し変数userNameに代入
				String userName = user.getUserName();

				// メッセージ本文を取得
				String cont = req.getContent();

				if (!(userName.contains("bot")) && !(userName.contains("ボット")) && !(cont.contains("しないで")) && !(cont.contains("やらないで"))
						&& !(cont.contains("わないで")) && !(cont.contains("えないで"))) {
					// 運勢用のランダム1~7の整数を生成 0が出ないように+1計算
					int un;
					un = (int) (Math.random() * 6 + 1);

					// ランダム1~3の整数を生成 0が出ないように+1計算
					int ran;
					ran = (int) (Math.random() * 2 + 1);

					// 投下されたメッセージによって条件分岐

					//マイナー映画
					int m1 = 0;
					int m2 = 0;
					m1 = MovieMDAO.m;

					if ((cont.contains("!映画")) || (cont.contains("!おすすめの映画")) || (cont.contains("!オススメの映画")) || (cont.contains("!オススメ映画")) || (cont.contains("!おすすめ映画"))
							|| (cont.contains("！映画")) || (cont.contains("！おすすめの映画")) || (cont.contains("！オススメの映画")) || (cont.contains("！オススメ映画")) || (cont.contains("!おすすめ映画"))) {

						MovieMDAO movieMDAO = new MovieMDAO();
						List<MovieDTO> sd = movieMDAO.findAll();
						m2 = (int) (Math.random() * m1);
						resp.reply(sd.get(m2).getUrl() + "\n" + sd.get(m2).getCom()+
								"\nTSUTAYA : " + sd.get(m2).getT() + "\nGEO : " + sd.get(m2).getG() + "\nNetflix : " + sd.get(m2).getN());

					}

					//朝の挨拶
					if ((cont.contains("おはよ")) || (cont.contains("おはー")) || (cont.contains("おは-"))) {
						switch (ran) {
						case 1:
							resp.reply("おはよう！");
							break;
						case 2:
							resp.reply("コケコッコー!(おはよう！)");
							break;
						case 3:
							resp.reply("コケーーーーッ!(おはよう！)");
							break;
						}
					}

					//お昼の挨拶
					else if ((cont.contains("こんに")) || (cont.contains("こんちゃ")) || (cont.contains("ちわっす"))) {
						switch (ran) {
						case 1:
							resp.reply("こんにちはー！");
							break;
						case 2:
							resp.reply("コッコー!(こんにちはー！)");
							break;
						case 3:
							resp.reply("コケーッ!(こんにちはー！)");
							break;
						}
					}

					//夜の挨拶
					else if ((cont.contains("こんば")) || (cont.contains("ばんちゃ"))
							|| (cont.contains("ばんわー")) || (cont.contains("ばんわ-"))) {
						switch (ran) {
						case 1:
							resp.reply("こんばんはー！");
							break;
						case 2:
							resp.reply("コッコ!(こんばんはー！)");
							break;
						case 3:
							resp.reply("コケコケ(こんばんは)");
							break;
						}
					}
					//運勢
					else if (cont.contains("!運勢") || (cont.contains("！運勢"))) {
						switch (un) {
						case 1:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--小吉--+--+--\n--+--+--+--+--+--+--\nコケーーーッ！！(おめでとう！！)");
							break;
						case 2:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--中吉--+--+--\n--+--+--+--+--+--+--\nコケッ！(おめでとう！)");
							break;
						case 3:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--大吉--+--+--\n--+--+--+--+--+--+--\nコケッ(おめでとう)");
							break;
						case 4:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--+吉+--+--+--\n--+--+--+--+--+--+--\nコケ～(びみょ～)");
							break;
						case 5:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--末吉--+--+--\n--+--+--+--+--+--+--\nコケ～(びみょ～)");
							break;
						case 6:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--+凶+--+--+--\n--+--+--+--+--+--+--\nコケ(どんまい)");
							break;
						case 7:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--大凶--+--+--\n--+--+--+--+--+--+--\nコケッケ笑");
							break;
						}
					}

					//メニュー
					else if ((cont.contains("!メニュー")) || (cont.contains("！メニュー"))) {
						resp.reply("!割り当てメーカー、!運勢、!掃除当番、!映画");
					}

					//掃除当番
					else if (cont.contains("!掃除") || (cont.contains("！掃除"))) {

						//掃除の担当
						String[] souji = { "掃除機", "掃除機", "トイレ", "床", "床", "壁", "壁" };

						// 配列からListへ変換
						List<String> list = Arrays.asList(souji);

						// リストの並びをシャッフル
						Collections.shuffle(list);

						// listから配列へ戻す
						String[] souji2 = (String[]) list.toArray(new String[list.size()]);

						// シャッフルされた配列の先頭を取得
						String result1 = souji2[0];
						resp.reply("向井 : " + result1);
						String result2 = souji2[1];
						resp.reply("佐藤 : " + result2);
						String result3 = souji2[2];
						resp.reply("渡邉 : " + result3);
						String result4 = souji2[3];
						resp.reply("山口 : " + result4);
						String result5 = souji2[4];
						resp.reply("ジェイミー : " + result5);
						String result6 = souji2[5];
						resp.reply("寺崎 : " + result6);
						String result7 = souji2[6];
						resp.reply("平原 : " + result7);
					}

					//割り当てメーカー
					else if (cont.contains("!割り当てメーカー") || (cont.contains("!割り当て"))
							|| (cont.contains("！割り当てメーカー") || (cont.contains("！割り当て")))) {
						if (cont.contains("!割り当てメーカー\n") || (cont.contains("!割り当て\n"))
								|| (cont.contains("！割り当てメーカー\n") || (cont.contains("！割り当て\n")))) {

							//記述の読み込み
							int aa = 0;
							int bb = 0;
							int ab = 0;
							String[] bun1 = cont.split("\n");
							String bun2 = bun1[1];
							String[] groupA = bun2.split("\\.");
							for (int a = 0; a < groupA.length; a++) {
								aa = aa + 1;
							}
							String bun3 = bun1[2];
							String[] groupB = bun3.split("\\.");
							for (int b = 0; b < groupB.length; b++) {
								bb = bb + 1;
							}
							if (aa >= bb) {
								ab = aa;
							} else {
								ab = bb;
							}

							// 配列からListへ変換
							List<String> lista = Arrays.asList(groupA);
							List<String> listb = Arrays.asList(groupB);

							// リストの並びをシャッフル
							for (int sh = 0; sh < ran; sh++) {
								Collections.shuffle(lista);
								Collections.shuffle(listb);
							}

							// listから配列へ戻す
							String[] groupA2 = (String[]) lista.toArray(new String[lista.size()]);
							String[] groupB2 = (String[]) listb.toArray(new String[listb.size()]);

							// シャッフルされた配列の先頭を取得
							for (int s = 0; s < ab; s++) {
								String resulta = groupA2[s];
								String resultb = groupB2[s];
								resp.reply(resulta + " - " + resultb + "\n");
							}
						} else {
							resp.reply("!割り当てメーカー(改行)\n"
									+ "選択肢1.選択肢2.選択肢3～(改行)\n"
									+ "選択肢1.選択肢2.選択肢3～\n"
									+ "の書き方でお願いします。");
						}

					}
					//割り当てメーカーv2
					else if (cont.contains("!wmv2") || (cont.contains("!wmv2"))
							|| (cont.contains("！wmv2") || (cont.contains("！wmv2")))) {
						if (cont.contains("!wmv2\n") || (cont.contains("!wmv2\n"))
								|| (cont.contains("！wmv2\n") || (cont.contains("！wmv2\n")))) {

							//記述の読み込み
							int aa = 0;
							int bb = 0;
							int cc = 0;
							int ab = 0;
							String[] bun1 = cont.split("\n");
							String bun2 = bun1[1];
							String[] groupA = bun2.split("\\.");
							for (int a = 0; a < groupA.length; a++) {
								aa = aa + 1;
							}
							String bun3 = bun1[2];
							String[] groupB = bun3.split("\\.");
							for (int b = 0; b < groupB.length; b++) {
								bb = bb + 1;
							}
							String bun4 = bun1[3];
							String[] groupC = bun4.split("\\.");
							for (int c = 0; c < groupC.length; c++) {
								cc = cc + 1;
							}
							if (aa >= bb) {
								ab = aa;
							}if (aa >= cc) {
								ab = aa;
							} if (bb >= cc) {
								ab = bb;
							}
							else {
								ab = cc;
							}

							// 配列からListへ変換
							List<String> lista = Arrays.asList(groupA);
							List<String> listb = Arrays.asList(groupB);
							List<String> listc = Arrays.asList(groupC);

							// リストの並びをシャッフル
							for (int sh = 0; sh < ran; sh++) {
								Collections.shuffle(lista);
								Collections.shuffle(listb);
								Collections.shuffle(listc);
							}

							// listから配列へ戻す
							String[] groupA2 = (String[]) lista.toArray(new String[lista.size()]);
							String[] groupB2 = (String[]) listb.toArray(new String[lista.size()]);
							String[] groupC2 = (String[]) listc.toArray(new String[lista.size()]);

							// シャッフルされた配列の先頭を取得
							for (int s = 0; s < ab; s++) {
								String resulta = groupA2[s];
								String resultb = groupB2[s];
								String resultc = groupC2[s];
								resp.reply(resulta + " - " + resultb + " - " + resultc + "\n");
							}
						} else {
							resp.reply("!wmv2(改行)\n"
									+ "選択肢1.選択肢2.選択肢3～(改行)(一行目に選択肢が多いもの)\n"
									+ "選択肢1.選択肢2.選択肢3～(改行)\n"
									+ "選択肢1.選択肢2.選択肢3～\n"
									+ "の書き方でお願いします。");
						}
					}
					// case外のメッセージ
					else {
					}
				}
			}

			//チャンネル
			public void onMessagePosted(SlackletRequest req, SlackletResponse resp) {

				// メッセージを投下したユーザーの情報を取得
				SlackUser user = req.getSender();

				// ユーザー情報の中からユーザー名を取り出し変数userNameに代入
				String userName = user.getUserName();

				// メッセージ本文を取得
				String cont = req.getContent();

				if (!(userName.contains("bot")) && !(userName.contains("ボット")) && !(cont.contains("しないで")) && !(cont.contains("やらないで"))
						&& !(cont.contains("わないで")) && !(cont.contains("えないで"))) {
					// 運勢用のランダム1~7の整数を生成 0が出ないように+1計算
					int un;
					un = (int) (Math.random() * 6 + 1);

					// ランダム1~3の整数を生成 0が出ないように+1計算
					int ran;
					ran = (int) (Math.random() * 2 + 1);

					// 投下されたメッセージによって条件分岐

					//朝の挨拶
					if ((cont.contains("おはよ")) || (cont.contains("おはー")) || (cont.contains("おは-"))) {
						switch (ran) {
						case 1:
							resp.reply("おはよう！");
							break;
						case 2:
							resp.reply("コケコッコー!(おはよう！)");
							break;
						case 3:
							resp.reply("コケーーーーッ!(おはよう！)");
							break;
						}
					}

					//お昼の挨拶
					else if ((cont.contains("こんに")) || (cont.contains("こんちゃ")) || (cont.contains("ちわっす"))) {
						switch (ran) {
						case 1:
							resp.reply("こんにちはー！");
							break;
						case 2:
							resp.reply("コッコー!(こんにちはー！)");
							break;
						case 3:
							resp.reply("コケーッ!(こんにちはー！)");
							break;
						}
					}

					//夜の挨拶
					else if ((cont.contains("こんば")) || (cont.contains("ばんちゃ"))
							|| (cont.contains("ばんわー")) || (cont.contains("ばんわ-"))) {
						switch (ran) {
						case 1:
							resp.reply("こんばんはー！");
							break;
						case 2:
							resp.reply("コッコ!(こんばんはー！)");
							break;
						case 3:
							resp.reply("コケコケ(こんばんは)");
							break;
						}
					}
					//運勢
					else if (cont.contains("!運勢") || (cont.contains("！運勢"))) {
						switch (un) {
						case 1:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--小吉--+--+--\n--+--+--+--+--+--+--\nコケーーーッ！！(おめでとう！！)");
							break;
						case 2:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--中吉--+--+--\n--+--+--+--+--+--+--\nコケッ！(おめでとう！)");
							break;
						case 3:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--大吉--+--+--\n--+--+--+--+--+--+--\nコケッ(おめでとう)");
							break;
						case 4:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--+吉+--+--+--\n--+--+--+--+--+--+--\nコケ～(びみょ～)");
							break;
						case 5:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--末吉--+--+--\n--+--+--+--+--+--+--\nコケ～(びみょ～)");
							break;
						case 6:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--+凶+--+--+--\n--+--+--+--+--+--+--\nコケ(どんまい)");
							break;
						case 7:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--大凶--+--+--\n--+--+--+--+--+--+--\nコケッケ笑");
							break;
						}
					}

					//メニュー
					else if ((cont.contains("!メニュー")) || (cont.contains("！メニュー"))) {
						resp.reply("!献立、!割り当てメーカー、!運勢、!掃除当番、日の出アラーム(固定)");
					}

					//掃除当番
					else if (cont.contains("!掃除") || (cont.contains("！掃除"))) {

						//掃除の担当
						String[] souji = { "掃除機", "掃除機", "トイレ", "床", "床", "壁", "壁" };

						// 配列からListへ変換
						List<String> list = Arrays.asList(souji);

						// リストの並びをシャッフル
						Collections.shuffle(list);

						// listから配列へ戻す
						String[] souji2 = (String[]) list.toArray(new String[list.size()]);

						// シャッフルされた配列の先頭を取得
						String result1 = souji2[0];
						resp.reply("向井 : " + result1);
						String result2 = souji2[1];
						resp.reply("佐藤 : " + result2);
						String result3 = souji2[2];
						resp.reply("渡邉 : " + result3);
						String result4 = souji2[3];
						resp.reply("山口 : " + result4);
						String result5 = souji2[4];
						resp.reply("ジェイミー : " + result5);
						String result6 = souji2[5];
						resp.reply("寺崎 : " + result6);
						String result7 = souji2[6];
						resp.reply("平原 : " + result7);
					}

					//割り当てメーカー
					else if (cont.contains("!割り当てメーカー") || (cont.contains("!割り当て"))
							|| (cont.contains("！割り当てメーカー") || (cont.contains("！割り当て")))) {
						if (cont.contains("!割り当てメーカー\n") || (cont.contains("!割り当て\n"))
								|| (cont.contains("！割り当てメーカー\n") || (cont.contains("！割り当て\n")))) {

							//記述の読み込み
							int aa = 0;
							int bb = 0;
							int ab = 0;
							String[] bun1 = cont.split("\n");
							String bun2 = bun1[1];
							String[] groupA = bun2.split("\\.");
							for (int a = 0; a < groupA.length; a++) {
								aa = aa + 1;
							}
							String bun3 = bun1[2];
							String[] groupB = bun3.split("\\.");
							for (int b = 0; b < groupB.length; b++) {
								bb = bb + 1;
							}
							if (aa > bb) {
								ab = aa;
							} else {
								ab = bb;
							}

							// 配列からListへ変換
							List<String> lista = Arrays.asList(groupA);
							List<String> listb = Arrays.asList(groupB);

							// リストの並びをシャッフル
							for (int sh = 0; sh < ran; sh++) {
								Collections.shuffle(lista);
								Collections.shuffle(listb);
							}

							// listから配列へ戻す
							String[] groupA2 = (String[]) lista.toArray(new String[lista.size()]);
							String[] groupB2 = (String[]) listb.toArray(new String[listb.size()]);

							// シャッフルされた配列の先頭を取得
							for (int s = 0; s < ab; s++) {
								String resulta = groupA2[s];
								String resultb = groupB2[s];
								resp.reply(resulta + " - " + resultb + "\n");
							}
						} else {
							resp.reply("!割り当てメーカー(改行)\n"
									+ "選択肢1.選択肢2.選択肢3～(改行)\n"
									+ "選択肢1.選択肢2.選択肢3～\n"
									+ "の書き方でお願いします。");
						}
					}
					//割り当てメーカーv2
					else if (cont.contains("!wmv2") || (cont.contains("!wmv2"))
							|| (cont.contains("！wmv2") || (cont.contains("！wmv2")))) {
						if (cont.contains("!wmv2\n") || (cont.contains("!wmv2\n"))
								|| (cont.contains("！wmv2\n") || (cont.contains("！wmv2\n")))) {

							//記述の読み込み
							int aa = 0;
							int bb = 0;
							int cc = 0;
							int ab = 0;
							String[] bun1 = cont.split("\n");
							String bun2 = bun1[1];
							String[] groupA = bun2.split("\\.");
							for (int a = 0; a < groupA.length; a++) {
								aa = aa + 1;
							}
							String bun3 = bun1[2];
							String[] groupB = bun3.split("\\.");
							for (int b = 0; b < groupB.length; b++) {
								bb = bb + 1;
							}
							String bun4 = bun1[3];
							String[] groupC = bun4.split("\\.");
							for (int c = 0; c < groupC.length; c++) {
								cc = cc + 1;
							}
							if (aa >= bb) {
								ab = aa;
							}if (aa >= cc) {
								ab = aa;
							} if (bb >= cc) {
								ab = bb;
							}
							else {
								ab = cc;
							}

							// 配列からListへ変換
							List<String> lista = Arrays.asList(groupA);
							List<String> listb = Arrays.asList(groupB);
							List<String> listc = Arrays.asList(groupC);

							// リストの並びをシャッフル
							for (int sh = 0; sh < ran; sh++) {
								Collections.shuffle(lista);
								Collections.shuffle(listb);
								Collections.shuffle(listc);
							}

							// listから配列へ戻す
							String[] groupA2 = (String[]) lista.toArray(new String[lista.size()]);
							String[] groupB2 = (String[]) listb.toArray(new String[lista.size()]);
							String[] groupC2 = (String[]) listc.toArray(new String[lista.size()]);

							// シャッフルされた配列の先頭を取得
							for (int s = 0; s < ab; s++) {
								String resulta = groupA2[s];
								String resultb = groupB2[s];
								String resultc = groupC2[s];
								resp.reply(resulta + " - " + resultb + " - " + resultc + "\n");
							}
						} else {
							resp.reply("!wmv2(改行)\n"
									+ "選択肢1.選択肢2.選択肢3～(改行)(一行目に選択肢が多いもの)\n"
									+ "選択肢1.選択肢2.選択肢3～(改行)\n"
									+ "選択肢1.選択肢2.選択肢3～\n"
									+ "の書き方でお願いします。");
						}
					}

					//配列をランダム処理

					Random r = new Random();

					String egg = menus[r.nextInt(7)];

					String message = other[r.nextInt(5)];
					if (cont.contentEquals("！献立") || (cont.contains("!献立")) || (cont.contains("!料理"))
							|| (cont.contains("！料理"))) {

						//配列をランダム処理
						resp.reply(egg);
					}
					else if (Arrays.asList(another).contains(cont)) {
						resp.reply("卵料理以外であれば、" + message);
					}
					// case外のメッセージ
					else {
					}
				}
			}

			@Override//メンション
			public void onMentionedMessagePosted(SlackletRequest req, SlackletResponse resp) {

				// メッセージを投下したユーザーの情報を取得
				SlackUser user = req.getSender();

				// ユーザー情報の中からユーザー名を取り出し変数userNameに代入
				String userName = user.getUserName();

				// メッセージ本文を取得
				String cont = req.getContent();

				if (!(userName.contains("bot")) && !(userName.contains("ボット")) && !(cont.contains("しないで")) && !(cont.contains("やらないで"))

						&& !(cont.contains("わないで")) && !(cont.contains("えないで"))) {

					// 運勢用のランダム1~7の整数を生成 0が出ないように+1計算

					int un;
					un = (int) (Math.random() * 6 + 1);

					// ランダム1~3の整数を生成 0が出ないように+1計算

					int ran;
					ran = (int) (Math.random() * 2 + 1);

					//メジャー映画
					int m1 = 0;
					int m2 = 0;
					m1 = MovieDAO.m;

					if ((cont.contains("!映画")) || (cont.contains("!おすすめの映画")) || (cont.contains("!オススメの映画")) || (cont.contains("!オススメ映画")) || (cont.contains("!おすすめ映画"))
							|| (cont.contains("！映画")) || (cont.contains("！おすすめの映画")) || (cont.contains("！オススメの映画")) || (cont.contains("！オススメ映画")) || (cont.contains("！おすすめ映画"))) {

						MovieDAO movieDAO = new MovieDAO();
						List<MovieDTO> sd = movieDAO.findAll();
						m2 = (int) (Math.random() * m1);
						resp.reply(sd.get(m2).getUrl() + "\n" + sd.get(m2).getCom()+
								"\nTSUTAYA : " + sd.get(m2).getT() + "\nGEO : " + sd.get(m2).getG());

					}

					//朝の挨拶
					if ((cont.contains("おはよ")) || (cont.contains("おはー")) || (cont.contains("おは-"))) {
						switch (ran) {
						case 1:
							resp.reply("おはよう！");
							break;
						case 2:
							resp.reply("コケコッコー!(おはよう！)");
							break;
						case 3:
							resp.reply("コケーーーーッ!(おはよう！)");
							break;
						}
					}

					//お昼の挨拶
					else if ((cont.contains("こんに")) || (cont.contains("こんちゃ")) || (cont.contains("ちわっす"))) {
						switch (ran) {
						case 1:
							resp.reply("こんにちはー！");
							break;
						case 2:
							resp.reply("コッコー!(こんにちはー！)");
							break;
						case 3:
							resp.reply("コケーッ!(こんにちはー！)");
							break;
						}
					}

					//夜の挨拶
					else if ((cont.contains("こんば")) || (cont.contains("ばんちゃ"))
							|| (cont.contains("ばんわー")) || (cont.contains("ばんわ-"))) {
						switch (ran) {
						case 1:
							resp.reply("こんばんはー！");
							break;
						case 2:
							resp.reply("コッコ!(こんばんはー！)");
							break;
						case 3:
							resp.reply("コケコケ(こんばんは)");
							break;
						}
					}

					//運勢
					else if (cont.contains("!運勢") || (cont.contains("！運勢"))) {
						switch (un) {
						case 1:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--小吉--+--+--\n--+--+--+--+--+--+--\nコケーーーッ！！(おめでとう！！)");
							break;
						case 2:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--中吉--+--+--\n--+--+--+--+--+--+--\nコケッ！(おめでとう！)");
							break;
						case 3:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--大吉--+--+--\n--+--+--+--+--+--+--\nコケッ(おめでとう)");
							break;
						case 4:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--+吉+--+--+--\n--+--+--+--+--+--+--\nコケ～(びみょ～)");
							break;
						case 5:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--末吉--+--+--\n--+--+--+--+--+--+--\nコケ～(びみょ～)");
							break;
						case 6:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--+凶+--+--+--\n--+--+--+--+--+--+--\nコケ(どんまい)");
							break;
						case 7:
							resp.reply("運勢は～～～～～\n--+--+--+--+--+--+--\n--+--+--大凶--+--+--\n--+--+--+--+--+--+--\nコケッケ笑");
							break;
						}
					}

					//メニュー

					else if ((cont.contains("!メニュー")) || (cont.contains("！メニュー"))) {
						resp.reply("!割り当てメーカー、!運勢、!おすすめの映画、!掃除当番");
					}

					//掃除当番

					else if (cont.contains("!掃除") || (cont.contains("！掃除"))
							|| (cont.contains("！掃除当番")) || (cont.contains("！掃除当番"))) {

						//掃除の担当

						String[] souji = { "掃除機", "掃除機", "トイレ", "床", "床", "壁", "壁" };

						// 配列からListへ変換

						List<String> list = Arrays.asList(souji);

						// リストの並びをシャッフル

						Collections.shuffle(list);

						// listから配列へ戻す

						String[] souji2 = (String[]) list.toArray(new String[list.size()]);

						// シャッフルされた配列の先頭を取得

						String result1 = souji2[0];

						resp.reply("向井 : " + result1);

						String result2 = souji2[1];

						resp.reply("佐藤 : " + result2);

						String result3 = souji2[2];

						resp.reply("渡邉 : " + result3);

						String result4 = souji2[3];

						resp.reply("山口 : " + result4);

						String result5 = souji2[4];

						resp.reply("ジェイミー : " + result5);

						String result6 = souji2[5];

						resp.reply("寺崎 : " + result6);

						String result7 = souji2[6];

						resp.reply("平原 : " + result7);

					}

					//割り当てメーカー

					else if (cont.contains("!割り当てメーカー") || (cont.contains("!割り当て")) || (cont.contains("！割り当てメーカー") || (cont.contains("！割り当て")))) {
						if (cont.contains("!割り当てメーカー\n") || (cont.contains("!割り当て\n")) || (cont.contains("！割り当てメーカー\n") || (cont.contains("！割り当て\n")))) {

							//記述の読み込み

							int aa = 0;
							int bb = 0;
							int ab = 0;

							String[] bun1 = cont.split("\n");
							String bun2 = bun1[1];
							String[] groupA = bun2.split("\\.");

							for (int a = 0; a < groupA.length; a++) {
								aa = aa + 1;
							}

							String bun3 = bun1[2];
							String[] groupB = bun3.split("\\.");

							for (int b = 0; b < groupB.length; b++) {
								bb = bb + 1;
							}

							if (aa >= bb) {
								ab = aa;
							} else {
								ab = bb;
							}

							// 配列からListへ変換
							List<String> lista = Arrays.asList(groupA);
							List<String> listb = Arrays.asList(groupB);

							// リストの並びをシャッフル
							for (int sh = 0; sh < ran; sh++) {
								Collections.shuffle(lista);
								Collections.shuffle(listb);
							}

							// listから配列へ戻す
							String[] groupA2 = (String[]) lista.toArray(new String[lista.size()]);
							String[] groupB2 = (String[]) listb.toArray(new String[listb.size()]);

							// シャッフルされた配列の先頭を取得
							for (int s = 0; s < ab; s++) {
								String resulta = groupA2[s];
								String resultb = groupB2[s];
								resp.reply(resulta + " - " + resultb + "\n");
							}

						} else {
							resp.reply("!割り当てメーカー(改行)\n"
									+ "選択肢1.選択肢2.選択肢3～(改行)\n"
									+ "選択肢1.選択肢2.選択肢3～\n"
									+ "の書き方でお願いします。");
						}
					}

					//割り当てメーカーv2
					else if (cont.contains("!wmv2") || (cont.contains("!wmv2"))
							|| (cont.contains("！wmv2") || (cont.contains("！wmv2")))) {
						if (cont.contains("!wmv2\n") || (cont.contains("!wmv2\n"))
								|| (cont.contains("！wmv2\n") || (cont.contains("！wmv2\n")))) {

							//記述の読み込み
							int aa = 0;
							int bb = 0;
							int cc = 0;
							int ab = 0;
							String[] bun1 = cont.split("\n");
							String bun2 = bun1[1];
							String[] groupA = bun2.split("\\.");
							for (int a = 0; a < groupA.length; a++) {
								aa = aa + 1;
							}
							String bun3 = bun1[2];
							String[] groupB = bun3.split("\\.");
							for (int b = 0; b < groupB.length; b++) {
								bb = bb + 1;
							}
							String bun4 = bun1[3];
							String[] groupC = bun4.split("\\.");
							for (int c = 0; c < groupC.length; c++) {
								cc = cc + 1;
							}
							if (aa >= bb) {
								ab = aa;
							}if (aa >= cc) {
								ab = aa;
							} if (bb >= cc) {
								ab = bb;
							}
							else {
								ab = cc;
							}

							// 配列からListへ変換
							List<String> lista = Arrays.asList(groupA);
							List<String> listb = Arrays.asList(groupB);
							List<String> listc = Arrays.asList(groupC);

							// リストの並びをシャッフル
								for (int sh = 0; sh < ran; sh++) {
								Collections.shuffle(lista);
								Collections.shuffle(listb);
								Collections.shuffle(listc);
							}

							// listから配列へ戻す
							String[] groupA2 = (String[]) lista.toArray(new String[lista.size()]);
							String[] groupB2 = (String[]) listb.toArray(new String[lista.size()]);
							String[] groupC2 = (String[]) listc.toArray(new String[lista.size()]);

							// シャッフルされた配列の先頭を取得
							for (int s = 0; s < ab; s++) {
								String resulta = groupA2[s];
								String resultb = groupB2[s];
								String resultc = groupC2[s];
								resp.reply(resulta + " - " + resultb + " - " + resultc + "\n");
							}
						} else {
							resp.reply("!wmv2(改行)\n"
									+ "選択肢1.選択肢2.選択肢3～(改行)(一行目に選択肢が多いもの)\n"
									+ "選択肢1.選択肢2.選択肢3～(改行)\n"
									+ "選択肢1.選択肢2.選択肢3～\n"
									+ "の書き方でお願いします。");
						}
					}

					// case外のメッセージ

					else {
					}

				}

			}

		});

	}
}

class SunriseSunsetMain {

	// 計算する場所・タイムゾーン・日付を準備する

	Location location = new Location(35.681298d, 139.7662469d);

	TimeZone timeZone = TimeZone.getTimeZone("Asia/Tokyo");

	Calendar calendar = Calendar.getInstance(timeZone);

	// 日出・日没時刻を取得する

	SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, timeZone);
	String sunriseTime = calculator.getOfficialSunriseForDate(calendar);
	String sunsetTime = calculator.getOfficialSunsetForDate(calendar);

}
