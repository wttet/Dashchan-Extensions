package com.mishiranu.dashchan.chan.yakujimoe;

import chan.content.ChanConfiguration;

public class YakujiMoeChanConfiguration extends ChanConfiguration {
	public YakujiMoeChanConfiguration() {
		request(OPTION_READ_POSTS_COUNT);
		setDefaultName("Аноним");
		setDefaultName("an", "Кот Синкая");
		setDefaultName("au", "Джереми Кларксон");
		setDefaultName("b", "Сырно");
		setDefaultName("bro", "Эпплджек");
		setDefaultName("l", "Ф. М. Достоевский");
		setDefaultName("m", "Копипаста-гей");
		setDefaultName("maid", "Госюдзин-сама");
		setDefaultName("med", "Антон Буслов");
		setDefaultName("mi", "Й. Швейк");
		setDefaultName("mu", "Виктор Цой");
		setDefaultName("ne", "Пушок");
		setDefaultName("p", "Б. В. Грызлов");
		setDefaultName("s", "Чии");
		setDefaultName("sci", "Гриша Перельман");
		setDefaultName("sp", "Спортакус");
		setDefaultName("tran", "Е. Д. Поливанов");
		setDefaultName("tv", "К. С. Станиславский");
		setDefaultName("vg", "Марио");
		setDefaultName("x", "Эмма Ай");
		setDefaultName("a", "Мокона");
		setDefaultName("aa", "Ракка");
		setDefaultName("azu", "Осака");
		setDefaultName("fi", "Фигурка анонима");
		setDefaultName("jp", "\u540d\u7121\u3057\u3055\u3093");
		setDefaultName("hau", "\u4e09\u56db");
		setDefaultName("ls", "Цукаса");
		setDefaultName("ma", "Иноуэ Орихимэ");
		setDefaultName("me", "Лакс Кляйн");
		setDefaultName("rm", "Суйгинто");
		setDefaultName("sos", "Кёнко");
		setDefaultName("tan", "Уныл-тян");
		setDefaultName("to", "Нитори");
		setDefaultName("vn", "Сэйбер");
		setDefaultName("d", "Мод-тян");
		addCaptchaType("wakaba");
	}

	@Override
	public Board obtainBoardConfiguration(String boardName) {
		Board board = new Board();
		board.allowPosting = "dev".equals(boardName);
		board.allowDeleting = "dev".equals(boardName);
		return board;
	}

	@Override
	public Captcha obtainCustomCaptchaConfiguration(String captchaType) {
		if ("wakaba".equals(captchaType)) {
			Captcha captcha = new Captcha();
			captcha.title = "Wakaba";
			captcha.input = Captcha.Input.LATIN;
			captcha.validity = Captcha.Validity.IN_THREAD;
			return captcha;
		}
		return null;
	}

	@Override
	public Posting obtainPostingConfiguration(String boardName, boolean newThread) {
		Posting posting = new Posting();
		posting.allowName = true;
		posting.allowName = true;
		posting.allowEmail = true;
		posting.allowSubject = true;
		posting.attachmentCount = 1;
		posting.attachmentMimeTypes.add("image/*");
		return posting;
	}

	@Override
	public Deleting obtainDeletingConfiguration(String boardName) {
		Deleting deleting = new Deleting();
		deleting.password = true;
		deleting.multiplePosts = true;
		deleting.optionFilesOnly = true;
		return deleting;
	}
}